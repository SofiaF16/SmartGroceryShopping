package com.example.f21g3_smartgroceryshopping.storage.entity;

import androidx.room.Embedded;
import androidx.room.Relation;
import java.util.List;

public class StorageOrderWithCartItems {

    @Embedded
    public final StorageOrder storageOrder;

    @Relation(
            parentColumn = "orderId",
            entityColumn = "cartItemKey"
    )
    public final List<StorageCartItem> cartItems;

    public StorageOrderWithCartItems(StorageOrder storageOrder, List<StorageCartItem> cartItems) {
        this.storageOrder = storageOrder;
        this.cartItems = cartItems;
    }

}
