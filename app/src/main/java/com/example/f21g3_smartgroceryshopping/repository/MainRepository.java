package com.example.f21g3_smartgroceryshopping.repository;


import com.example.f21g3_smartgroceryshopping.response.RepositoryResponse;
import com.example.f21g3_smartgroceryshopping.service.SmartGroceryShoppingService;
import com.example.f21g3_smartgroceryshopping.storage.dao.SmartGroceryShoppingDao;
import com.example.f21g3_smartgroceryshopping.storage.entity.StorageDish;

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

    public RepositoryResponse<List<StorageDish>> getAllDishes() {
        try {
            List<com.example.f21g3_smartgroceryshopping.service.entity.Dish> serviceDishes = shoppingService.getDishes();
            List<StorageDish> storageStorageDishes = toStorageDish(serviceDishes);

            shoppingDao.insertAll(storageStorageDishes);

            return new RepositoryResponse<>(shoppingDao.getAllDishes());
        } catch (Exception e) {
            return new RepositoryResponse<>(shoppingDao.getAllDishes(), e);
        }
    }

    private List<StorageDish> toStorageDish(List<com.example.f21g3_smartgroceryshopping.service.entity.Dish> list) {
        List<StorageDish> result = new ArrayList<>(list.size());
        for (com.example.f21g3_smartgroceryshopping.service.entity.Dish d: list) {
            result.add(new StorageDish(d.getUid(), d.getTitle()));
        }

        return result;
    }

}
