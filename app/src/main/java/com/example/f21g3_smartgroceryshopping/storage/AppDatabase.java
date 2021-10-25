package com.example.f21g3_smartgroceryshopping.storage;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.f21g3_smartgroceryshopping.storage.dao.SmartGroceryShoppingDao;
import com.example.f21g3_smartgroceryshopping.storage.entity.Dish;

@Database(entities = {Dish.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    public abstract SmartGroceryShoppingDao smartGroceryShoppingDao();

}
