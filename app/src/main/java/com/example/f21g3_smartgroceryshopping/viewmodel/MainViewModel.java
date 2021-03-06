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
import com.example.f21g3_smartgroceryshopping.storage.entity.StorageDishWithIngredients;

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
        cartItems = Transformations.map(this.mainRepository.getCartItemsLiveData(), List::size);
    }

    /**
     * @return liveData object holding the result of completion of loadDishes()
     */
    public LiveData<LoadResponse<List<Dish>>> getDishesResponse() {
        return dishesResponse;
    }

    /**
     * @return liveData object monitoring the current cart size. If the size is updated the object will update the value automatically
     */
    public LiveData<Integer> getCartSize() {
        return cartItems;
    }

    /**
     * triggers loading of available dishes and returns the result of the request to dishesResponse LiveData object
     */
    public void loadDishes(){
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
            result.add(toDish(storageDish.storageDish, ingredients));
        }

        return result;
    }

    /**
     * Clears all cart items
     */
    public void clearCart() {
        CompletableFuture.runAsync(mainRepository::deleteAllCartItems);
    }

}
