package com.example.f21g3_smartgroceryshopping.repository;


import com.example.f21g3_smartgroceryshopping.service.SmartGroceryShoppingService;
import com.example.f21g3_smartgroceryshopping.storage.dao.SmartGroceryShoppingDao;
import com.example.f21g3_smartgroceryshopping.storage.entity.Dish;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class MainRepository {

    private SmartGroceryShoppingService shoppingService;
    private SmartGroceryShoppingDao shoppingDao;

    @Inject
    public MainRepository(SmartGroceryShoppingService shoppingService, SmartGroceryShoppingDao shoppingDao) {
        this.shoppingService = shoppingService;
        this.shoppingDao = shoppingDao;
    }

    public List<Dish> getAllDishes() {
        try {
            List<com.example.f21g3_smartgroceryshopping.service.entity.Dish> serviceDishes = shoppingService.getDishes();
            List<com.example.f21g3_smartgroceryshopping.storage.entity.Dish> storageDishes = toStorageDish(serviceDishes);

            shoppingDao.insertAll(storageDishes);

            return shoppingDao.getAllDishes();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    private List<com.example.f21g3_smartgroceryshopping.storage.entity.Dish> toStorageDish(List<com.example.f21g3_smartgroceryshopping.service.entity.Dish> list) {
        List<com.example.f21g3_smartgroceryshopping.storage.entity.Dish> result = new ArrayList<>(list.size());
        for (com.example.f21g3_smartgroceryshopping.service.entity.Dish d: list) {
            result.add(new com.example.f21g3_smartgroceryshopping.storage.entity.Dish(d.getUid(), d.getTitle()));
        }

        return result;
    }

}
