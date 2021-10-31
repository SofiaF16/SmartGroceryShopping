package com.example.f21g3_smartgroceryshopping.service.entity;

public class CartItem {

    private Dish dish;
    private int portions;

    public CartItem(Dish dish, int portions) {
        this.dish = dish;
        this.portions = portions;
    }

    public Dish getDish() {
        return dish;
    }

    public int getPortions() {
        return portions;
    }

}
