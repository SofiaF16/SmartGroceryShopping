package com.example.f21g3_smartgroceryshopping.storage.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Ingredient")
public class StorageIngredient {

    @PrimaryKey
    public final int uid;

    @ColumnInfo(name = "title")
    public final String title;

    @ColumnInfo(name = "quantity")
    public final int quantity;

    @ColumnInfo(name = "quantityUnit")
    public final String quantityUnit;

    public StorageIngredient(int uid, String title, int quantity, String quantityUnit) {
        this.uid = uid;
        this.title = title;
        this.quantity = quantity;
        this.quantityUnit = quantityUnit;
    }

}
