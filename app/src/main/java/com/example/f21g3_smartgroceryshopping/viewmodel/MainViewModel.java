package com.example.f21g3_smartgroceryshopping.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.f21g3_smartgroceryshopping.repository.MainRepository;
import com.example.f21g3_smartgroceryshopping.service.entity.Dish;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import javax.inject.Inject;
import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class MainViewModel extends ViewModel {

    private final MutableLiveData<List<Dish>> dishesResponse = new MutableLiveData<>();

    private final MainRepository mainRepository;

    @Inject
    public MainViewModel(MainRepository mainRepository) {
        this.mainRepository = mainRepository;
    }

    public LiveData<List<Dish>> getDishesResponse() {
        return dishesResponse;
    }

    public void getDishes(){
        CompletableFuture.runAsync(() -> {
            List<com.example.f21g3_smartgroceryshopping.storage.entity.Dish> storageDishes = mainRepository.getAllDishes();
            List<Dish> serviceDish = toServiceDish(storageDishes);
            dishesResponse.postValue(serviceDish);
        });
    }


    private List<Dish> toServiceDish(List<com.example.f21g3_smartgroceryshopping.storage.entity.Dish> list) {
        List<Dish> result = new ArrayList<>(list.size());
        for (com.example.f21g3_smartgroceryshopping.storage.entity.Dish d: list) {
            result.add(new Dish(d.uid, d.title));
        }

        return result;
    }


}
