package com.example.f21g3_smartgroceryshopping.storage.entity;

import static androidx.room.ForeignKey.CASCADE;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "Ingredient", foreignKeys = @ForeignKey(entity = StorageDish.class,
        parentColumns = "uid",
        childColumns = "fk_dish",
        onDelete = CASCADE))
public class StorageIngredient {

    @PrimaryKey
    public final int uid;

    @ColumnInfo(name = "fk_dish", index = true)
    public int fk_dish;

    @ColumnInfo(name = "title")
    public final String title;

    @ColumnInfo(name = "quantity")
    public final double quantity;

    @ColumnInfo(name = "quantityUnit")
    public final String quantityUnit;

    public StorageIngredient(int uid, int fk_dish, String title, double quantity, String quantityUnit) {
        this.uid = uid;
        this.fk_dish = fk_dish;
        this.title = title;
        this.quantity = quantity;
        this.quantityUnit = quantityUnit;
    }
}
