package com.example.f21g3_smartgroceryshopping.storage.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "Order")
public class StorageOrder {

    @PrimaryKey
    public int orderId;

    @ColumnInfo(name = "orderDate")
    public Date orderDate;

    public StorageOrder(int orderId, Date orderDate) {
        this.orderId = orderId;
        this.orderDate = orderDate;
    }

}
