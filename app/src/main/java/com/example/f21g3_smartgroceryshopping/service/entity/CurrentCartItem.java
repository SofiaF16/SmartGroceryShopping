package com.example.f21g3_smartgroceryshopping.service.entity;


public class CurrentCartItem {

    private final int cartItemKey;

    private final long dishId;

    private final String dishTitle;

    private final int portions;

    public CurrentCartItem(int cartItemKey, long dishId, String dishTitle, int portions) {
        this.cartItemKey = cartItemKey;
        this.dishId = dishId;
        this.dishTitle = dishTitle;
        this.portions = portions;
    }

    public int getCartItemKey() {
        return cartItemKey;
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
