package com.example.f21g3_smartgroceryshopping.repository;

import com.example.f21g3_smartgroceryshopping.service.SmartGroceryShoppingService;
import com.example.f21g3_smartgroceryshopping.service.entity.Dish;
import com.example.f21g3_smartgroceryshopping.storage.dao.SmartGroceryShoppingDao;
import java.util.List;
import javax.inject.Inject;

public class MainRepository {

    private SmartGroceryShoppingService shoppingService;
    private SmartGroceryShoppingDao shoppingDao;

    @Inject
    public MainRepository(SmartGroceryShoppingService shoppingService, SmartGroceryShoppingDao shoppingDao) {
        this.shoppingService = shoppingService;
        this.shoppingDao = shoppingDao;
    }

    public List<Dish> getDishes() {
        return shoppingService.getDishes();
    }

}
