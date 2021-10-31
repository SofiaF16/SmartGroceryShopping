package com.example.f21g3_smartgroceryshopping.viewmodel;

import static com.example.f21g3_smartgroceryshopping.util.Converter.toDish;
import static com.example.f21g3_smartgroceryshopping.util.Converter.toIngredients;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.f21g3_smartgroceryshopping.repository.MainRepository;
import com.example.f21g3_smartgroceryshopping.response.ErrorLoadResponse;
import com.example.f21g3_smartgroceryshopping.response.LoadResponse;
import com.example.f21g3_smartgroceryshopping.response.LoadingLoadResponse;
import com.example.f21g3_smartgroceryshopping.response.RepositoryResponse;
import com.example.f21g3_smartgroceryshopping.response.SuccessLoadResponse;
import com.example.f21g3_smartgroceryshopping.service.entity.CartItem;
import com.example.f21g3_smartgroceryshopping.service.entity.Dish;
import com.example.f21g3_smartgroceryshopping.service.entity.Ingredient;
import com.example.f21g3_smartgroceryshopping.storage.entity.StorageCurrentCartItem;
import com.example.f21g3_smartgroceryshopping.storage.entity.StorageCurrentCartItemAndDishWithIngredients;
import com.example.f21g3_smartgroceryshopping.storage.entity.StorageDishWithIngredients;
import com.example.f21g3_smartgroceryshopping.storage.entity.StorageOrder;
import com.example.f21g3_smartgroceryshopping.storage.entity.StorageOrderItem;
import com.example.f21g3_smartgroceryshopping.storage.entity.StorageOrderWithCartItems;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class CartViewModel extends ViewModel {

    private static final long SLEEP_TIME = 3000;

    private final MutableLiveData<LoadResponse<List<CartItem>>> cartItems = new MutableLiveData<>();
    private final MutableLiveData<LoadResponse<Ingredient>> fullIngredientsList = new MutableLiveData<>();
    private final MutableLiveData<LoadResponse<String>> postOrderStatus = new MutableLiveData<>();

    private final MainRepository mainRepository;

    // TODO add module to generate list of ingredients

    @Inject
    public CartViewModel(MainRepository mainRepository) {
        this.mainRepository = mainRepository;
    }

    public LiveData<LoadResponse<List<CartItem>>> getCartItems() {
        return cartItems;
    }

    public LiveData<LoadResponse<Ingredient>> getIngredientsList() {
        return fullIngredientsList;
    }

    public LiveData<LoadResponse<String>> getPostOrderStatus() {
        return postOrderStatus;
    }

    public void loadCartItems() {
        CompletableFuture.runAsync(() -> {
            RepositoryResponse<List<StorageCurrentCartItemAndDishWithIngredients>> repositoryResponse = mainRepository.getCartItemsWithDishesAndIngredients();
            LoadResponse<List<CartItem>> loadResponse = prepareResponse(repositoryResponse);
            cartItems.postValue(loadResponse);
        });
    }

    private LoadResponse<List<CartItem>> prepareResponse(RepositoryResponse<List<StorageCurrentCartItemAndDishWithIngredients>> repositoryResponse) {
        if(repositoryResponse.getError() == null && repositoryResponse.getResponse() != null) {
            List<CartItem> cartItems = new ArrayList<>(repositoryResponse.getResponse().size());

            for (StorageCurrentCartItemAndDishWithIngredients item: repositoryResponse.getResponse()) {
                StorageDishWithIngredients storageDishWithIngredients = item.storageDishWithIngredients;
                Dish dish = toDish(storageDishWithIngredients.storageDish, toIngredients(storageDishWithIngredients.ingredients));

                cartItems.add(new CartItem(dish, item.storageCurrentCartItem.portions));
            }

            return new SuccessLoadResponse<>(cartItems);
        }

        return new ErrorLoadResponse<>(repositoryResponse.getError());
    }

    public void loadIngredientsList() {
        // TODO implement logic
    }

    public void postOrder() {
        CompletableFuture.runAsync(() -> {
            postOrderStatus.postValue(new LoadingLoadResponse<>(""));

            internetDelay();

            List<StorageCurrentCartItem> currentCartItems = mainRepository.getCartItems().getValue();
            StorageOrderWithCartItems storageOrderWithCartItems = createStorageOrderWithCartItems(currentCartItems);
            long[] result = mainRepository.addToHistory(storageOrderWithCartItems);

            LoadResponse<String> response = prepareResponse(result);
            postOrderStatus.postValue(response);
        });
    }

    private void internetDelay() {
        try {
            Thread.sleep(SLEEP_TIME);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private StorageOrderWithCartItems createStorageOrderWithCartItems(List<StorageCurrentCartItem> currentCartItems) {
        List<StorageOrderItem> storageOrderItems = toStorageOrderItems(currentCartItems);
        StorageOrder storageOrder = new StorageOrder(new Date());
        return new StorageOrderWithCartItems(storageOrder, storageOrderItems);
    }

    private List<StorageOrderItem> toStorageOrderItems(List<StorageCurrentCartItem> currentCartItems) {
        List<StorageOrderItem> storageOrderItems = new ArrayList<>(currentCartItems.size());
        for (StorageCurrentCartItem item: currentCartItems) {
            storageOrderItems.add(toStorageOrderItem(item));
        }

        return storageOrderItems;
    }

    private StorageOrderItem toStorageOrderItem(StorageCurrentCartItem storageCurrentCartItem) {
        return new StorageOrderItem(storageCurrentCartItem.dishId, storageCurrentCartItem.portions);
    }

    private LoadResponse<String> prepareResponse(long[] result) {
        if(result.length != 0) {
            return new SuccessLoadResponse<>("");
        }

        return new ErrorLoadResponse<>("");
    }

}
