package com.example.f21g3_smartgroceryshopping.service.entity;

public class OrderItem {

    private long dishId;
    private String dishTitle;
    private int portions;

    public OrderItem(long dishId, String dishTitle, int portions) {
        this.dishId = dishId;
        this.dishTitle = dishTitle;
        this.portions = portions;
    }

    public long getDishId() {
        return dishId;
    }

    public String getDishTitle() {
        return dishTitle;
    }

    public int getPortions() {
        return portions;
    }

}
