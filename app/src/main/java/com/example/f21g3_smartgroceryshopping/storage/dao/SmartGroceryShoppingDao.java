package com.example.f21g3_smartgroceryshopping.storage.dao;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import com.example.f21g3_smartgroceryshopping.storage.entity.StorageCurrentCartItem;
import com.example.f21g3_smartgroceryshopping.storage.entity.StorageCurrentCartItemAndDishWithIngredients;
import com.example.f21g3_smartgroceryshopping.storage.entity.StorageDish;
import com.example.f21g3_smartgroceryshopping.storage.entity.StorageDishWithIngredients;
import com.example.f21g3_smartgroceryshopping.storage.entity.StorageIngredient;
import com.example.f21g3_smartgroceryshopping.storage.entity.StorageOrder;
import com.example.f21g3_smartgroceryshopping.storage.entity.StorageOrderItem;
import com.example.f21g3_smartgroceryshopping.storage.entity.StorageOrderWithCartItems;

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

    @Query("SELECT * FROM Dish")
    @Transaction
    public abstract List<StorageDishWithIngredients> getAllDishesWithIngredients();

    @Query("SELECT * FROM Cart")
    public abstract LiveData<List<StorageCurrentCartItem>> getCartItems();

    @Query("DELETE FROM Cart")
    public abstract void deleteAllCartItems();

    @Query("SELECT * FROM Dish WHERE uid=:dishId")
    @Transaction
    public abstract StorageDishWithIngredients getStorageDishBy(int dishId);

    @Insert
    public abstract long insertCartItem(StorageCurrentCartItem cartItem);

    @Query("SELECT * FROM Cart")
    @Transaction
    public abstract List<StorageCurrentCartItemAndDishWithIngredients> getCartItemsWithDishesAndIngredients();

    @Transaction
    public long[] insert(StorageOrderWithCartItems storageOrderWithCartItems) {
        long id  = insert(storageOrderWithCartItems.storageOrder);

        for (int i = 0; i < storageOrderWithCartItems.orderItems.size(); i++) {
            storageOrderWithCartItems.orderItems.get(i).setOrderId(id);
        }

        return insert(storageOrderWithCartItems.orderItems);
    }

    @Insert
    public abstract long insert(StorageOrder storageOrder);

    @Insert
    public abstract long[] insert(List<StorageOrderItem> storageOrderItems);

    @Query("DELETE FROM Cart")
    public abstract void deleteAllCartItems();

}
