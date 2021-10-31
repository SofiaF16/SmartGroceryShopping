package com.example.f21g3_smartgroceryshopping.service.entity;

import java.util.List;

public class Dish {

    private int uid;
    private String title;
    private String shortDescription;
    private String longDescription;
    private String imageUrl;
    private boolean isFavorite;

    private List<Ingredient> ingredients;

    public Dish(int uid, String title, String shortDescription, String longDescription, String imageUrl, boolean isFavorite, List<Ingredient> ingredients) {
        this.uid = uid;
        this.title = title;
        this.shortDescription = shortDescription;
        this.longDescription = longDescription;
        this.imageUrl = imageUrl;
        this.isFavorite = isFavorite;
        this.ingredients = ingredients;
    }

    public int getUid() {
        return uid;
    }

    public String getTitle() {
        return title;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public String getLongDescription() {
        return longDescription;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    @Override
    public String toString() {
        return "Dish{" +
                "uid=" + uid +
                ", title='" + title + '\'' +
                ", shortDescription='" + shortDescription + '\'' +
                ", longDescription='" + longDescription + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", isFavorite=" + isFavorite +
                ", ingredients=" + ingredients +
                '}';
    }
}
