package com.example.f21g3_smartgroceryshopping.storage.entity;

import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.List;

@Entity(tableName = "Dish")
public class StorageDish {

    @PrimaryKey
    public final int uid;

    @ColumnInfo(name = "title")
    public final String title;

    @ColumnInfo(name = "shortDescription")
    public final String shortDescription;

    @ColumnInfo(name = "longDescription")
    public final String longDescription;

    @ColumnInfo(name = "imageUrl")
    public final String imageUrl;

    @ColumnInfo(name = "isFavorite")
    public final boolean isFavorite;

    public StorageDish(int uid, String title, String shortDescription, String longDescription, String imageUrl, boolean isFavorite) {
        this.uid = uid;
        this.title = title;
        this.shortDescription = shortDescription;
        this.longDescription = longDescription;
        this.imageUrl = imageUrl;
        this.isFavorite = isFavorite;
    }
}
