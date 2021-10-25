package com.example.f21g3_smartgroceryshopping.storage.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Dish {

    @PrimaryKey
    public int uid;

    @ColumnInfo(name = "title")
    public String title;

}
