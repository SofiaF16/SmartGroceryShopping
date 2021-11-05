package com.example.f21g3_smartgroceryshopping.service;

import com.example.f21g3_smartgroceryshopping.service.entity.Dish;
import com.example.f21g3_smartgroceryshopping.service.entity.Ingredient;

import java.util.ArrayList;
import java.util.List;

public class MockSmartGroceryShoppingService implements SmartGroceryShoppingService {

    @Override
    public List<Dish> getDishes() {
        Ingredient carrot = new Ingredient(1, "carrot", 10, "g");
        Ingredient onion = new Ingredient(2, "onion", 20, "g");
        Ingredient pepper = new Ingredient(3, "pepper", 30, "g");

        List<Ingredient> firstDishIngredients = new ArrayList<>();
        firstDishIngredients.add(carrot);
        firstDishIngredients.add(onion);
        firstDishIngredients.add(pepper);

        Dish firstDish = new Dish(1, "dish", "sort descr", "longdescr", "https://www.foodiecrush.com/wp-content/uploads/2019/05/Grilled-Salmon-foodiecrush.com-023.jpg", false, firstDishIngredients);

        Ingredient jam = new Ingredient(4, "jam", 15, "g");
        Ingredient salt = new Ingredient(5, "salt", 27, "g");
        Ingredient apples = new Ingredient(6, "apples", 11, "g");

        List<Ingredient> secondDishIngredients = new ArrayList<>();
        secondDishIngredients.add(jam);
        secondDishIngredients.add(salt);
        secondDishIngredients.add(apples);

        Dish secondDish = new Dish(2, "dish2", "dish2 sort descr", "dish2 longdescr", "https://www.foodiecrush.com/wp-content/uploads/2019/05/Grilled-Salmon-foodiecrush.com-023.jpg", false, secondDishIngredients);


        List<Dish> list = new ArrayList<>();
        list.add(firstDish);
        list.add(secondDish);

        try {
            Thread.sleep(2000);
        } catch (InterruptedException ie) {
            Thread.currentThread().interrupt();
        }

        return list;
    }

}
