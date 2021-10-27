package com.example.f21g3_smartgroceryshopping.storage.dao;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import com.example.f21g3_smartgroceryshopping.storage.entity.StorageDish;

import java.util.List;

@Dao
public interface SmartGroceryShoppingDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<StorageDish> storageDishes);

    @Query("SELECT * FROM Dish")
    List<StorageDish> getAllDishes();

}
