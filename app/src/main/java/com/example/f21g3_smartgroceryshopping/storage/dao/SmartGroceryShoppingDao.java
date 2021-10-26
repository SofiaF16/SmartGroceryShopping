package com.example.f21g3_smartgroceryshopping.storage.dao;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import com.example.f21g3_smartgroceryshopping.storage.entity.Dish;
import java.util.List;

@Dao
public interface SmartGroceryShoppingDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Dish> dishes);

    @Query("SELECT * FROM Dish")
    List<Dish> getAllDishes();

}
