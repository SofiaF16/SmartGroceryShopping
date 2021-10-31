package com.example.f21g3_smartgroceryshopping.storage.entity;

import androidx.room.Embedded;
import androidx.room.Relation;
import java.util.List;

public class StorageOrderWithCartItems {

    @Embedded
    public final StorageOrder storageOrder;

    @Relation(
            parentColumn = "orderId",
            entityColumn = "orderItemKey"
    )
    public final List<StorageOrderItem> orderItems;

    public StorageOrderWithCartItems(StorageOrder storageOrder, List<StorageOrderItem> orderItems) {
        this.storageOrder = storageOrder;
        this.orderItems = orderItems;
    }

}
