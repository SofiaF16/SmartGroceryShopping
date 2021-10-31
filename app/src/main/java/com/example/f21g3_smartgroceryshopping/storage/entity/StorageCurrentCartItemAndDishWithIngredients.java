package com.example.f21g3_smartgroceryshopping.storage.entity;

import androidx.room.Embedded;
import androidx.room.Relation;

public class StorageCurrentCartItemAndDishWithIngredients {

    @Embedded
    public final StorageCurrentCartItem storageCurrentCartItem;

    @Relation(
            entity = StorageDish.class,
            parentColumn = "dishId",
            entityColumn = "uid"
    )
    public final StorageDishWithIngredients storageDishWithIngredients;

    public StorageCurrentCartItemAndDishWithIngredients(StorageCurrentCartItem storageCurrentCartItem, StorageDishWithIngredients storageDishWithIngredients) {
        this.storageCurrentCartItem = storageCurrentCartItem;
        this.storageDishWithIngredients = storageDishWithIngredients;
    }

}
