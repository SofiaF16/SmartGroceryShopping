package com.example.f21g3_smartgroceryshopping.service.entity;

public class Dish {

    private int uid;
    private String title;

    public Dish() {
    }

    public Dish(int uid, String title) {
        this.uid = uid;
        this.title = title;
    }

    public int getUid() {
        return uid;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public String toString() {
        return "Dish{" +
                "uid=" + uid +
                ", title='" + title + '\'' +
                '}';
    }
}
