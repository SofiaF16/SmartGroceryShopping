package com.example.f21g3_smartgroceryshopping.storage.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "Order")
public class StorageOrder {

    @PrimaryKey(autoGenerate = true)
    public int orderId;

    @ColumnInfo(name = "orderDate")
    public final Date orderDate;

    public StorageOrder(Date orderDate) {
        this.orderDate = orderDate;
    }

}
