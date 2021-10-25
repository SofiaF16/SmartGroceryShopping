package com.example.f21g3_smartgroceryshopping.storage.dao;

import androidx.room.Dao;
import androidx.room.Insert;

import com.example.f21g3_smartgroceryshopping.storage.entity.Dish;

@Dao
public interface SmartGroceryShoppingDao {

    @Insert
    void insertAll(Dish... dishes);

}
