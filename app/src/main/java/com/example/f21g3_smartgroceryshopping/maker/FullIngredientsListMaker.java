package com.example.f21g3_smartgroceryshopping.maker;

import com.example.f21g3_smartgroceryshopping.service.entity.Ingredient;
import com.example.f21g3_smartgroceryshopping.storage.entity.StorageCurrentCartItemAndDishWithIngredients;
import com.example.f21g3_smartgroceryshopping.storage.entity.StorageIngredient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FullIngredientsListMaker {

    private static final int roundCoefficient = 10;
    private static final int numberOfFractionNumbers = 3;

    public List<Ingredient> makeFullIngredientList(List<StorageCurrentCartItemAndDishWithIngredients> cartItems) {
        Map<String, Ingredient> map = new HashMap<>();

        for (StorageCurrentCartItemAndDishWithIngredients cartItem: cartItems) {
            for (StorageIngredient ingredient : cartItem.storageDishWithIngredients.ingredients) {
                if(map.containsKey(ingredient.title)) {
                    Ingredient currentIngredient = map.get(ingredient.title);
                    Ingredient updatedIngredient = getMergedQuantitiesIngredient(currentIngredient, ingredient, cartItem.storageCurrentCartItem.portions);

                    map.put(ingredient.title, updatedIngredient);
                } else {
                    map.put(ingredient.title, new Ingredient(ingredient.uid, ingredient.title,
                            roundTo(ingredient.quantity * cartItem.storageCurrentCartItem.portions, numberOfFractionNumbers),
                            ingredient.quantityUnit));
                }
            }
        }

        return new ArrayList<>(map.values());
    }

    private Ingredient getMergedQuantitiesIngredient(Ingredient currentIngredient, StorageIngredient ingredientToAdd, int numberOfPortions) {
        double newQuantity = currentIngredient.getQuantity() + (ingredientToAdd.quantity * numberOfPortions);
        newQuantity = roundTo(newQuantity, numberOfFractionNumbers);

        return new Ingredient(currentIngredient.getUid(),
                currentIngredient.getTitle(),
                newQuantity,
                currentIngredient.getQuantityUnit());
    }

    private double roundTo(double newQuantity, int numberOfFractionNumbers) {
        double scale = Math.pow(roundCoefficient, numberOfFractionNumbers);
        return Math.round(newQuantity * scale) / scale;
    }

}
