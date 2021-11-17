package com.example.f21g3_smartgroceryshopping.viewmodel;

import static com.example.f21g3_smartgroceryshopping.util.Converter.toDish;
import static com.example.f21g3_smartgroceryshopping.util.Converter.toIngredients;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.example.f21g3_smartgroceryshopping.repository.MainRepository;
import com.example.f21g3_smartgroceryshopping.response.ErrorLoadResponse;
import com.example.f21g3_smartgroceryshopping.response.LoadResponse;
import com.example.f21g3_smartgroceryshopping.response.LoadingLoadResponse;
import com.example.f21g3_smartgroceryshopping.response.RepositoryResponse;
import com.example.f21g3_smartgroceryshopping.response.SuccessLoadResponse;
import com.example.f21g3_smartgroceryshopping.service.entity.Dish;
import com.example.f21g3_smartgroceryshopping.service.entity.Ingredient;
import com.example.f21g3_smartgroceryshopping.storage.entity.StorageCurrentCartItem;
import com.example.f21g3_smartgroceryshopping.storage.entity.StorageDish;
import com.example.f21g3_smartgroceryshopping.storage.entity.StorageDishWithIngredients;
import com.example.f21g3_smartgroceryshopping.storage.entity.StorageIngredient;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class DishDetailsViewModel extends ViewModel {

    private static final long ADD_TO_CART_LOADING = 0;
    private static final long ADD_TO_CART_ERROR = -1;


    private final MutableLiveData<LoadResponse<Dish>> dishResponse = new MutableLiveData<>();
    private final MutableLiveData<LoadResponse<Long>> addToCartResponse = new MutableLiveData<>();
    private final MutableLiveData<LoadResponse<Integer>> dishPortionsNumberResponse = new MutableLiveData<>();

    private final MainRepository mainRepository;
    private final LiveData<Integer> cartItems;

    @Inject
    public DishDetailsViewModel(MainRepository mainRepository) {
        this.mainRepository = mainRepository;
        cartItems = Transformations.map(this.mainRepository.getCartItemsLiveData(), List::size);
    }

    public LiveData<Integer> getCartSize() {
        return cartItems;
    }

    public LiveData<LoadResponse<Dish>> getDish() {
        return dishResponse;
    }

    public LiveData<LoadResponse<Long>> addToCartResponse() {
        return addToCartResponse;
    }

    public LiveData<LoadResponse<Integer>> getDishPortionsNumberResponse() {
        return dishPortionsNumberResponse;
    }

    public void loadDish(final int dishId) {
        CompletableFuture.runAsync(() -> {
            RepositoryResponse<StorageDishWithIngredients> storageDishResponse = mainRepository.getStorageDishBy(dishId);

            LoadResponse<Dish> dishesLoadResponse = prepareResponse(storageDishResponse);
            dishResponse.postValue(dishesLoadResponse);

            RepositoryResponse<StorageCurrentCartItem> currentCartItemRepositoryResponse = mainRepository.getStorageCurrentCartItemByDishId(dishId);
            if(currentCartItemRepositoryResponse.getResponse() != null) {
                dishPortionsNumberResponse.postValue(new SuccessLoadResponse<>(currentCartItemRepositoryResponse.getResponse().portions));
            }
        });
    }

    private LoadResponse<Dish> prepareResponse(RepositoryResponse<StorageDishWithIngredients> storageDishResponse) {
        if(storageDishResponse.getError() != null) {
            return new ErrorLoadResponse<>(storageDishResponse.getError());
        }

        if(storageDishResponse.getResponse() != null) {

            StorageDish storageDish = storageDishResponse.getResponse().storageDish;
            List<StorageIngredient> storageIngredients = storageDishResponse.getResponse().ingredients;

            List<Ingredient> ingredients = toIngredients(storageIngredients);
            return new SuccessLoadResponse<>(toDish(storageDish, ingredients));
        } else {
            return new ErrorLoadResponse<>(null, new Exception("empty list"));
        }
    }

    public void addToCart(final int dishId, final String dishTitle, final int portionsNumber) {
        CompletableFuture.runAsync(() -> {
            addToCartResponse.postValue(new LoadingLoadResponse<>(ADD_TO_CART_LOADING));

            long resultId = mainRepository.addToCartOrUpdate(new StorageCurrentCartItem(dishId, dishTitle, portionsNumber));

            if(resultId == ADD_TO_CART_ERROR) {
                addToCartResponse.postValue(new ErrorLoadResponse<>(resultId));
            } else {
                addToCartResponse.postValue(new SuccessLoadResponse<>(resultId));
            }
        });
    }


}
