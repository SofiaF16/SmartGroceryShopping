package com.example.f21g3_smartgroceryshopping.storage.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Cart")
public class StorageCartItem {

    @PrimaryKey(autoGenerate = true)
    public int cartItemKey;

    @ColumnInfo(name = "dishId")
    public final int dishId;

    @ColumnInfo(name = "portions")
    public final int portions;

    public StorageCartItem(int dishId, int portions) {
        this.dishId = dishId;
        this.portions = portions;
    }

}
