package com.example.f21g3_smartgroceryshopping.util;

import com.example.f21g3_smartgroceryshopping.service.entity.Dish;
import com.example.f21g3_smartgroceryshopping.service.entity.Ingredient;
import com.example.f21g3_smartgroceryshopping.storage.entity.StorageDish;
import com.example.f21g3_smartgroceryshopping.storage.entity.StorageIngredient;

import java.util.ArrayList;
import java.util.List;

public class Converter {

    public static List<Ingredient> toIngredients(List<StorageIngredient> ingredients) {
        if(ingredients == null) {
            return new ArrayList<>();
        }

        List<Ingredient> result = new ArrayList<>(ingredients.size());

        for (StorageIngredient ingredient: ingredients) {
            result.add(new Ingredient(ingredient.uid, ingredient.title, ingredient.quantity, ingredient.quantityUnit));
        }

        return result;
    }

    public static Dish toDish(StorageDish storageDish, List<Ingredient> ingredients) {
        return new Dish(storageDish.uid,
                storageDish.title,
                storageDish.shortDescription,
                storageDish.longDescription,
                storageDish.imageUrl,
                storageDish.isFavorite,
                ingredients);
    }

}
