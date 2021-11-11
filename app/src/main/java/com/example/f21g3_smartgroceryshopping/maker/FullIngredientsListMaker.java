package com.example.f21g3_smartgroceryshopping.maker;

import com.example.f21g3_smartgroceryshopping.service.entity.Ingredient;
import com.example.f21g3_smartgroceryshopping.storage.entity.StorageCurrentCartItemAndDishWithIngredients;
import com.example.f21g3_smartgroceryshopping.storage.entity.StorageIngredient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FullIngredientsListMaker {

    public List<Ingredient> makeFullIngredientList(List<StorageCurrentCartItemAndDishWithIngredients> cartItems) {
        Map<String, Ingredient> map = new HashMap<>();

        for (StorageCurrentCartItemAndDishWithIngredients cartItem: cartItems) {
            for (StorageIngredient ingredient : cartItem.storageDishWithIngredients.ingredients) {
                if(map.containsKey(ingredient.title)) {
                    Ingredient currentIngredient = map.get(ingredient.title);
                    Ingredient updatedIngredient = getMergedQuantitiesIngredient(currentIngredient, ingredient);

                    map.put(ingredient.title, updatedIngredient);
                } else {
                    map.put(ingredient.title, new Ingredient(ingredient.uid, ingredient.title, ingredient.quantity, ingredient.quantityUnit));
                }
            }
        }

        return new ArrayList<>(map.values());
    }

    private Ingredient getMergedQuantitiesIngredient(Ingredient currentIngredient, StorageIngredient ingredientToAdd) {
        return new Ingredient(currentIngredient.getUid(),
                currentIngredient.getTitle(),
                currentIngredient.getQuantity() + ingredientToAdd.quantity,
                currentIngredient.getQuantityUnit());
    }

}
