package com.example.f21g3_smartgroceryshopping.repository;


import androidx.lifecycle.LiveData;

import com.example.f21g3_smartgroceryshopping.response.RepositoryResponse;
import com.example.f21g3_smartgroceryshopping.service.SmartGroceryShoppingService;
import com.example.f21g3_smartgroceryshopping.service.entity.Dish;
import com.example.f21g3_smartgroceryshopping.service.entity.Ingredient;
import com.example.f21g3_smartgroceryshopping.storage.dao.SmartGroceryShoppingDao;
import com.example.f21g3_smartgroceryshopping.storage.entity.StorageCartItem;
import com.example.f21g3_smartgroceryshopping.storage.entity.StorageDish;
import com.example.f21g3_smartgroceryshopping.storage.entity.StorageDishWithIngredients;
import com.example.f21g3_smartgroceryshopping.storage.entity.StorageIngredient;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class MainRepository {

    private final SmartGroceryShoppingService shoppingService;
    private final SmartGroceryShoppingDao shoppingDao;

    @Inject
    public MainRepository(SmartGroceryShoppingService shoppingService, SmartGroceryShoppingDao shoppingDao) {
        this.shoppingService = shoppingService;
        this.shoppingDao = shoppingDao;
    }

    public RepositoryResponse<List<StorageDishWithIngredients>> getAllDishes() {
        try {
            List<Dish> serviceDishes = shoppingService.getDishes();
            List<StorageDishWithIngredients> storageStorageDishes = toStorageDishWithIngredients(serviceDishes);

            shoppingDao.insertAll(storageStorageDishes);

            return new RepositoryResponse<>(shoppingDao.getAllDishesWithIngredients());
        } catch (Exception e) {
            return new RepositoryResponse<>(shoppingDao.getAllDishesWithIngredients(), e);
        }
    }

    private List<StorageDishWithIngredients> toStorageDishWithIngredients(List<Dish> list) {
        List<StorageDishWithIngredients> result = new ArrayList<>(list.size());

        for (Dish d: list) {
            List<StorageIngredient> storageIngredients = toStorageIngredients(d, d.getIngredients());

            result.add(new StorageDishWithIngredients(
                    new StorageDish(d.getUid(),
                    d.getTitle(),
                    d.getShortDescription(),
                    d.getLongDescription(),
                    d.getImageUrl(),
                    d.isFavorite()),
                    storageIngredients));
        }

        return result;
    }

    private List<StorageIngredient> toStorageIngredients(Dish dish, List<Ingredient> ingredients) {
        List<StorageIngredient> result = new ArrayList<>(ingredients.size());
        for (Ingredient ingredient: ingredients) {
            result.add(new StorageIngredient(ingredient.getUid(), dish.getUid(), ingredient.getTitle(), ingredient.getQuantity(), ingredient.getQuantityUnit()));
        }

        return result;
    }

    public LiveData<List<StorageCartItem>> getCartItems() {
        return shoppingDao.getCartItems();
    }

}
