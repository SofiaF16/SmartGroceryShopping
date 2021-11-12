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
import com.example.f21g3_smartgroceryshopping.storage.entity.StorageOrderWithOrderItems;

import java.util.ArrayList;
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
    public abstract LiveData<List<StorageCurrentCartItem>> getCartItemsLiveData();

    @Query("SELECT * FROM Cart")
    public abstract List<StorageCurrentCartItem> getCartItems();

    @Query("SELECT * FROM `Order`")
    @Transaction
    public abstract List<StorageOrderWithOrderItems> getAllOrders();

    @Query("SELECT * FROM Dish WHERE uid=:dishId")
    @Transaction
    public abstract StorageDishWithIngredients getStorageDishBy(int dishId);

    @Insert
    public abstract long insertCartItem(StorageCurrentCartItem cartItem);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract long updateCartItems(StorageCurrentCartItem cartItem);

    @Insert
    public abstract long[] insertCartItem(List<StorageCurrentCartItem> cartItems);

    @Query("SELECT * FROM Cart")
    @Transaction
    public abstract List<StorageCurrentCartItemAndDishWithIngredients> getCartItemsWithDishesAndIngredients();

    @Transaction
    public List<Long> insert(StorageOrderWithOrderItems storageOrderWithOrderItems) {
        List<Long> list = new ArrayList<>();

        long orderId = insert(storageOrderWithOrderItems.storageOrder);

        if(orderId <= 0) {
            return list;
        }

        for (StorageOrderItem orderItem : storageOrderWithOrderItems.orderItems) {
            orderItem.setFk_order(orderId);
            list.add(insert(orderItem));
        }

        return list;
    }

    @Insert
    public abstract long insert(StorageOrder storageOrder);
    @Insert
    public abstract long insert(StorageOrderItem storageOrderItems);

    @Query("DELETE FROM Cart")
    public abstract void deleteAllCartItems();

    @Query("DELETE FROM Cart WHERE cartItemKey=:cartItemKey")
    public abstract int deleteCartItem(int cartItemKey);

}
