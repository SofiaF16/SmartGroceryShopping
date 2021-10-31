package com.example.f21g3_smartgroceryshopping.viewmodel;

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
import com.example.f21g3_smartgroceryshopping.storage.entity.StorageDishWithIngredients;
import com.example.f21g3_smartgroceryshopping.storage.entity.StorageIngredient;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import javax.inject.Inject;
import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class MainViewModel extends ViewModel {

    private final MutableLiveData<LoadResponse<List<Dish>>> dishesResponse = new MutableLiveData<>();
    private final LiveData<Integer> cartItems;

    private final MainRepository mainRepository;

    @Inject
    public MainViewModel(MainRepository mainRepository) {
        this.mainRepository = mainRepository;
        cartItems = Transformations.map(this.mainRepository.getCartItems(), List::size);
    }

    public LiveData<LoadResponse<List<Dish>>> getDishesResponse() {
        return dishesResponse;
    }

    public LiveData<Integer> getCartSize() {
        return cartItems;
    }

    public void getDishes(){
        CompletableFuture.runAsync(() -> {

            dishesResponse.postValue(new LoadingLoadResponse<>(new ArrayList<>()));
            RepositoryResponse<List<StorageDishWithIngredients>> storageDishesResponse = mainRepository.getAllDishes();

            LoadResponse<List<Dish>> dishesLoadResponse = prepareResponse(storageDishesResponse);
            dishesResponse.postValue(dishesLoadResponse);
        });
    }

    private LoadResponse<List<Dish>> prepareResponse(RepositoryResponse<List<StorageDishWithIngredients>> storageDishesResponse) {
        if(storageDishesResponse.getResponse() != null) {
            List<Dish> serviceDish = toServiceDish(storageDishesResponse.getResponse());
            return new SuccessLoadResponse<>(serviceDish, storageDishesResponse.getError());
        }

        return new ErrorLoadResponse<>(storageDishesResponse.getError());
    }

    private List<Dish> toServiceDish(List<StorageDishWithIngredients> list) {
        List<Dish> result = new ArrayList<>(list.size());

        for (StorageDishWithIngredients storageDish: list) {
            List<Ingredient> ingredients = toIngredients(storageDish.ingredients);

            result.add(new Dish(storageDish.storageDish.uid,
                    storageDish.storageDish.title,
                    storageDish.storageDish.shortDescription,
                    storageDish.storageDish.longDescription,
                    storageDish.storageDish.imageUrl,
                    storageDish.storageDish.isFavorite,
                    ingredients));
        }

        return result;
    }

    private List<Ingredient> toIngredients(List<StorageIngredient> ingredients) {
        List<Ingredient> result = new ArrayList<>(ingredients.size());

        for (StorageIngredient ingredient: ingredients) {
            result.add(new Ingredient(ingredient.uid, ingredient.title, ingredient.quantity, ingredient.quantityUnit));
        }

        return result;
    }

    public void clearCart() {
        mainRepository.deleteAllCartItems();
    }

}
