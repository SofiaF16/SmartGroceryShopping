package com.example.f21g3_smartgroceryshopping.storage.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Dish")
public class StorageDish {

    @PrimaryKey
    public final int uid;

    @ColumnInfo(name = "title")
    public final String title;

    public StorageDish(int uid, String title) {
        this.uid = uid;
        this.title = title;
    }

}
