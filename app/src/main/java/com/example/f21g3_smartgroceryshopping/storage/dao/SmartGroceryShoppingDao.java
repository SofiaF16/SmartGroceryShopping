package com.example.f21g3_smartgroceryshopping.storage.dao;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import com.example.f21g3_smartgroceryshopping.storage.entity.StorageDish;
import com.example.f21g3_smartgroceryshopping.storage.entity.StorageDishWithIngredients;
import com.example.f21g3_smartgroceryshopping.storage.entity.StorageIngredient;

import java.util.List;

@Dao
public abstract class SmartGroceryShoppingDao {

    @Transaction
    public void insertAll(List<StorageDishWithIngredients> storageDishes) {
        for (StorageDishWithIngredients dishWithIngredients : storageDishes) {
            insertAll(dishWithIngredients.storageDish);
            insertIngredients(dishWithIngredients.ingredients);
        }
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insertAll(StorageDish storageDish);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insertIngredients(List<StorageIngredient> ingredients);

    @Transaction
    @Query("SELECT * FROM Dish")
    public abstract List<StorageDishWithIngredients> getAllDishesWithIngredients();

}
