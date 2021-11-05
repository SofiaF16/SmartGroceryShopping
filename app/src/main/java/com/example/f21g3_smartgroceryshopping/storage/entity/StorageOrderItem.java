package com.example.f21g3_smartgroceryshopping.storage.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "OrderItem", foreignKeys = {@ForeignKey(entity = StorageOrder.class,
        parentColumns = "orderId",
        childColumns = "orderId",
        onDelete = ForeignKey.CASCADE)
})
public class StorageOrderItem {

    @PrimaryKey
    public long orderId;

    @ColumnInfo(name = "dishId")
    public final long dishId;

    @ColumnInfo(name = "dishTitle")
    public final String dishTitle;

    @ColumnInfo(name = "portions")
    public final int portions;

    public StorageOrderItem(long dishId, String dishTitle, int portions) {
        this.dishId = dishId;
        this.dishTitle = dishTitle;
        this.portions = portions;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

}