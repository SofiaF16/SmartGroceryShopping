package com.example.f21g3_smartgroceryshopping.service.entity;

import java.util.Date;
import java.util.List;

public class Order {

    private int orderId;
    private Date orderDate;
    private List<OrderItem> orderItems;

    public Order(int orderId, Date orderDate, List<OrderItem> orderItems) {
        this.orderId = orderId;
        this.orderDate = orderDate;
        this.orderItems = orderItems;
    }

    public int getOrderId() {
        return orderId;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

}
