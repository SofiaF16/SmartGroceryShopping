package com.example.f21g3_smartgroceryshopping.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.f21g3_smartgroceryshopping.repository.MainRepository;
import com.example.f21g3_smartgroceryshopping.response.ErrorLoadResponse;
import com.example.f21g3_smartgroceryshopping.response.LoadResponse;
import com.example.f21g3_smartgroceryshopping.response.LoadingLoadResponse;
import com.example.f21g3_smartgroceryshopping.response.RepositoryResponse;
import com.example.f21g3_smartgroceryshopping.response.SuccessLoadResponse;
import com.example.f21g3_smartgroceryshopping.service.entity.Dish;
import com.example.f21g3_smartgroceryshopping.storage.entity.StorageDish;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import javax.inject.Inject;
import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class MainViewModel extends ViewModel {

    private final MutableLiveData<LoadResponse<List<Dish>>> dishesResponse = new MutableLiveData<>();

    private final MainRepository mainRepository;

    @Inject
    public MainViewModel(MainRepository mainRepository) {
        this.mainRepository = mainRepository;
    }

    public LiveData<LoadResponse<List<Dish>>> getDishesResponse() {
        return dishesResponse;
    }

    public void getDishes(){
        CompletableFuture.runAsync(() -> {

            dishesResponse.postValue(new LoadingLoadResponse<>(new ArrayList<>()));
            RepositoryResponse<List<StorageDish>> storageDishesResponse = mainRepository.getAllDishes();

            LoadResponse<List<Dish>> dishesLoadResponse = prepareResponse(storageDishesResponse);
            dishesResponse.postValue(dishesLoadResponse);
        });
    }

    private LoadResponse<List<Dish>> prepareResponse(RepositoryResponse<List<StorageDish>> storageDishesResponse) {
        if(storageDishesResponse.getResponse() != null) {
            List<Dish> serviceDish = toServiceDish(storageDishesResponse.getResponse());
            return new SuccessLoadResponse<>(serviceDish, storageDishesResponse.getError());
        }

        return new ErrorLoadResponse<>(storageDishesResponse.getError());
    }

    private List<Dish> toServiceDish(List<StorageDish> list) {
        List<Dish> result = new ArrayList<>(list.size());
        for (StorageDish d: list) {
            result.add(new Dish(d.uid, d.title));
        }

        return result;
    }

}
