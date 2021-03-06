package com.example.f21g3_smartgroceryshopping.service.entity;


public class Ingredient {

    private int uid;
    private String title;
    private double quantity;
    private String quantityUnit;

    public Ingredient(int uid, String title, double quantity, String quantityUnit) {
        this.uid = uid;
        this.title = title;
        this.quantity = quantity;
        this.quantityUnit = quantityUnit;
    }

    public int getUid() {
        return uid;
    }

    public String getTitle() {
        return title;
    }

    public double getQuantity() {
        return quantity;
    }

    public String getQuantityUnit() {
        return quantityUnit;
    }

}
