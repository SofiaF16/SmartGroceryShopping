package com.example.f21g3_smartgroceryshopping.service;

import com.example.f21g3_smartgroceryshopping.service.entity.Dish;
import com.example.f21g3_smartgroceryshopping.storage.entity.StorageOrderWithOrderItems;

import java.util.List;

public interface SmartGroceryShoppingService {

    List<Dish> getDishes();

    boolean postOrder(StorageOrderWithOrderItems storageOrderWithOrderItems);
}
