package com.example.f21g3_smartgroceryshopping.storage.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "OrderItem", foreignKeys = {@ForeignKey(entity = StorageOrder.class,
        parentColumns = "orderId",
        childColumns = "fk_order",
        onDelete = ForeignKey.CASCADE)
})
public class StorageOrderItem {

    @PrimaryKey(autoGenerate = true)
    public long orderItemId;

    @ColumnInfo(name = "fk_order", index = true)
    public long fk_order;

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

    public void setFk_order(long fk_order) {
        this.fk_order = fk_order;
    }
}