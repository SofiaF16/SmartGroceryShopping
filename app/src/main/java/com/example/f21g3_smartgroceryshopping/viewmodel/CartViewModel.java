package com.example.f21g3_smartgroceryshopping.viewmodel;

import static com.example.f21g3_smartgroceryshopping.util.Converter.toDish;
import static com.example.f21g3_smartgroceryshopping.util.Converter.toIngredients;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.f21g3_smartgroceryshopping.maker.FullIngredientsListMaker;
import com.example.f21g3_smartgroceryshopping.repository.MainRepository;
import com.example.f21g3_smartgroceryshopping.response.ErrorLoadResponse;
import com.example.f21g3_smartgroceryshopping.response.LoadResponse;
import com.example.f21g3_smartgroceryshopping.response.LoadingLoadResponse;
import com.example.f21g3_smartgroceryshopping.response.RepositoryResponse;
import com.example.f21g3_smartgroceryshopping.response.SuccessLoadResponse;
import com.example.f21g3_smartgroceryshopping.service.entity.CartItem;
import com.example.f21g3_smartgroceryshopping.service.entity.CurrentCartItem;
import com.example.f21g3_smartgroceryshopping.service.entity.Dish;
import com.example.f21g3_smartgroceryshopping.service.entity.Ingredient;
import com.example.f21g3_smartgroceryshopping.storage.entity.StorageCurrentCartItem;
import com.example.f21g3_smartgroceryshopping.storage.entity.StorageCurrentCartItemAndDishWithIngredients;
import com.example.f21g3_smartgroceryshopping.storage.entity.StorageDishWithIngredients;
import com.example.f21g3_smartgroceryshopping.storage.entity.StorageOrder;
import com.example.f21g3_smartgroceryshopping.storage.entity.StorageOrderItem;
import com.example.f21g3_smartgroceryshopping.storage.entity.StorageOrderWithOrderItems;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class CartViewModel extends ViewModel {

    private final MutableLiveData<LoadResponse<List<CartItem>>> cartItemsResponse = new MutableLiveData<>();
    private final MutableLiveData<LoadResponse<List<Ingredient>>> fullIngredientsListResponse = new MutableLiveData<>();
    private final MutableLiveData<LoadResponse<String>> postOrderStatusResponse = new MutableLiveData<>();

    private final MainRepository mainRepository;
    private final FullIngredientsListMaker fullIngredientsListMaker;

    @Inject
    public CartViewModel(MainRepository mainRepository, FullIngredientsListMaker fullIngredientsListMaker) {
        this.mainRepository = mainRepository;
        this.fullIngredientsListMaker = fullIngredientsListMaker;
    }

    /**
     * @return liveData object holding the result of completion of loadCartItems(), updateCartItem(), and deleteCartItem();
     */
    public LiveData<LoadResponse<List<CartItem>>> getCartItemsResponse() {
        return cartItemsResponse;
    }

    /**
     * @return liveData object holding the result of completion of loadIngredientsList()
     */
    public LiveData<LoadResponse<List<Ingredient>>> getIngredientsListResponse() {
        return fullIngredientsListResponse;
    }

    /**
     * @return liveData object holding the result of completion of postOrder()
     */
    public LiveData<LoadResponse<String>> getPostOrderStatusResponse() {
        return postOrderStatusResponse;
    }

    /**
     * triggers the loading of cart items stored in the cart table and returns the result to cartItemsResponse
     */
    public void loadCartItems() {
        CompletableFuture.runAsync(this::refreshCartItems);
    }

    private void refreshCartItems() {
        RepositoryResponse<List<StorageCurrentCartItemAndDishWithIngredients>> repositoryResponse = mainRepository.getCartItemsWithDishesAndIngredients();
        LoadResponse<List<CartItem>> loadResponse = prepareResponse(repositoryResponse);
        cartItemsResponse.postValue(loadResponse);
    }

    private LoadResponse<List<CartItem>> prepareResponse(RepositoryResponse<List<StorageCurrentCartItemAndDishWithIngredients>> repositoryResponse) {
        if(repositoryResponse.getError() == null && repositoryResponse.getResponse() != null) {
            List<CartItem> cartItems = new ArrayList<>(repositoryResponse.getResponse().size());

            for (StorageCurrentCartItemAndDishWithIngredients item: repositoryResponse.getResponse()) {
                int cartItemKey = item.storageCurrentCartItem.cartItemKey;
                StorageDishWithIngredients storageDishWithIngredients = item.storageDishWithIngredients;
                Dish dish = toDish(storageDishWithIngredients.storageDish, toIngredients(storageDishWithIngredients.ingredients));

                cartItems.add(new CartItem(cartItemKey, dish, item.storageCurrentCartItem.portions));
            }

            return new SuccessLoadResponse<>(cartItems);
        }

        return new ErrorLoadResponse<>(repositoryResponse.getError());
    }

    /**
     * triggers the generating and returning of the full list of ingredients. The result will be returned to fullIngredientsListResponse
     */
    public void loadIngredientsList() {
        CompletableFuture.runAsync(() -> {
            RepositoryResponse<List<StorageCurrentCartItemAndDishWithIngredients>> response = mainRepository.getCartItemsWithDishesAndIngredients();

            if(response.getResponse() != null) {
                List<StorageCurrentCartItemAndDishWithIngredients> cartItems = response.getResponse();
                List<Ingredient> fullIngredientsList = fullIngredientsListMaker.makeFullIngredientList(cartItems);
                fullIngredientsListResponse.postValue(new SuccessLoadResponse<>(fullIngredientsList));
            }
        });
    }

    /**
     * triggers the posting of the order to backend, saves the order in the orders history table. The result will be returned to postOrderStatusResponse
     */
    public void postOrder() {
        CompletableFuture.runAsync(() -> {
            postOrderStatusResponse.postValue(new LoadingLoadResponse<>(""));

            List<StorageCurrentCartItem> currentCartItems = mainRepository.getCartItems();
            StorageOrderWithOrderItems storageOrderWithOrderItems = createStorageOrderWithCartItems(currentCartItems);

            if(mainRepository.postOrder(storageOrderWithOrderItems)) {
                List<Long> result = mainRepository.addToHistory(storageOrderWithOrderItems);

                mainRepository.deleteAllCartItems();
                LoadResponse<String> response = prepareResponse(result);
                postOrderStatusResponse.postValue(response);
            } else {
                postOrderStatusResponse.postValue(new ErrorLoadResponse<>(""));
            }
        });
    }

    private StorageOrderWithOrderItems createStorageOrderWithCartItems(List<StorageCurrentCartItem> currentCartItems) {
        List<StorageOrderItem> storageOrderItems = toStorageOrderItems(currentCartItems);
        StorageOrder storageOrder = new StorageOrder(new Date());
        return new StorageOrderWithOrderItems(storageOrder, storageOrderItems);
    }

    private List<StorageOrderItem> toStorageOrderItems(List<StorageCurrentCartItem> currentCartItems) {
        List<StorageOrderItem> storageOrderItems = new ArrayList<>(currentCartItems.size());
        for (StorageCurrentCartItem item: currentCartItems) {
            storageOrderItems.add(toStorageOrderItem(item));
        }

        return storageOrderItems;
    }

    private StorageOrderItem toStorageOrderItem(StorageCurrentCartItem storageCurrentCartItem) {
        return new StorageOrderItem(storageCurrentCartItem.dishId, storageCurrentCartItem.dishTitle, storageCurrentCartItem.portions);
    }

    private LoadResponse<String> prepareResponse(List<Long> result) {
        if(result.size() != 0) {
            return new SuccessLoadResponse<>("");
        }

        return new ErrorLoadResponse<>("");
    }

    /**
     * updates cart item, cart item table, and triggers the loading of the new cart items list. The result will be returned to cartItemsResponse
     * @param currentCartItem
     */
    public void updateCartItem(final CurrentCartItem currentCartItem) {
        CompletableFuture.runAsync(() -> {
            long l = mainRepository.updateCurrentCartItem(currentCartItem);

            if(l != -1) {
                refreshCartItems();
            } else {
                cartItemsResponse.postValue(new ErrorLoadResponse<>(cartItemsResponse.getValue().getResponse(), new Exception("Update failed")));
            }
        });
    }

    /**
     * updates cart item, cart item table, and triggers the loading of the new cart items list. The result will be returned to cartItemsResponse
     * @param currentCartItem
     */
    public void deleteCartItem(final CurrentCartItem currentCartItem) {
        CompletableFuture.runAsync(() -> {
            int l = mainRepository.deleteCurrentCartItem(currentCartItem);

            if(l != -1) {
                refreshCartItems();
            } else {
                cartItemsResponse.postValue(new ErrorLoadResponse<>(cartItemsResponse.getValue().getResponse(), new Exception("Delete failed")));
            }
        });
    }


}
