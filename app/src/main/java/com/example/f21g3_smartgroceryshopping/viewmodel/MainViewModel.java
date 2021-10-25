package com.example.f21g3_smartgroceryshopping.viewmodel;

import androidx.lifecycle.ViewModel;
import com.example.f21g3_smartgroceryshopping.repository.MainRepository;
import com.example.f21g3_smartgroceryshopping.service.entity.Dish;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class MainViewModel extends ViewModel {

    private MainRepository mainRepository;

    @Inject
    public MainViewModel(MainRepository mainRepository) {
        this.mainRepository = mainRepository;
    }

    public List<Dish> getDishes() {
        return mainRepository.getDishes();
    }

}
