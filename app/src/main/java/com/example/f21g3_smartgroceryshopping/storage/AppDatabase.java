package com.example.f21g3_smartgroceryshopping.storage;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.f21g3_smartgroceryshopping.storage.dao.SmartGroceryShoppingDao;
import com.example.f21g3_smartgroceryshopping.storage.entity.StorageCartItem;
import com.example.f21g3_smartgroceryshopping.storage.entity.StorageDish;
import com.example.f21g3_smartgroceryshopping.storage.entity.StorageIngredient;
import com.example.f21g3_smartgroceryshopping.storage.entity.StorageOrder;

@Database(entities = {StorageDish.class, StorageCartItem.class, StorageIngredient.class, StorageOrder.class}, version = 1)
@TypeConverters(DateToLongConverter.class)
public abstract class AppDatabase extends RoomDatabase {

    public abstract SmartGroceryShoppingDao smartGroceryShoppingDao();

}
