package com.example.f21g3_smartgroceryshopping.storage.entity;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class StorageDishWithIngredients {

    @Embedded
    public final StorageDish storageDish;

    @Relation(
            parentColumn = "uid",
            entityColumn = "uid"
    )
    public final List<StorageIngredient> ingredients;

    public StorageDishWithIngredients(StorageDish storageDish, List<StorageIngredient> ingredients) {
        this.storageDish = storageDish;
        this.ingredients = ingredients;
    }

}
