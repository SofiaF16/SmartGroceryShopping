package com.example.f21g3_smartgroceryshopping.storage.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Cart")
public class StorageCurrentCartItem {

    @PrimaryKey(autoGenerate = true)
    public int cartItemKey;

    @ColumnInfo(name = "dishId")
    public final long dishId;

    @ColumnInfo(name = "dishTitle")
    public final String dishTitle;

    @ColumnInfo(name = "portions")
    public final int portions;

    public StorageCurrentCartItem(long dishId, String dishTitle, int portions) {
        this.dishId = dishId;
        this.dishTitle = dishTitle;
        this.portions = portions;
    }
}
