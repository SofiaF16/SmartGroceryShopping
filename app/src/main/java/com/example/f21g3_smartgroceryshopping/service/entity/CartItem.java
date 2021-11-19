package com.example.f21g3_smartgroceryshopping.service.entity;

public class CartItem {

    private int cartItemKey;
    private Dish dish;
    private int portions;


    public CartItem(Dish dish, int portions) {
        this.dish = dish;
        this.portions = portions;
    }

    public CartItem(int cartItemKey, Dish dish, int portions) {
        this.cartItemKey = cartItemKey;
        this.dish = dish;
        this.portions = portions;
    }

    public int getCartItemKey() {
        return cartItemKey;
    }

    public Dish getDish() {
        return dish;
    }

    public int getPortions() {
        return portions;
    }

}
