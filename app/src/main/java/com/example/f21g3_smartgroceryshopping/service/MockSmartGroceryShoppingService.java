package com.example.f21g3_smartgroceryshopping.service;

import com.example.f21g3_smartgroceryshopping.service.entity.Dish;
import java.util.ArrayList;
import java.util.List;

public class MockSmartGroceryShoppingService implements SmartGroceryShoppingService {

    @Override
    public List<Dish> getDishes() {
        List<Dish> list = new ArrayList<>();
        list.add(new Dish());

        return list;
    }

}
