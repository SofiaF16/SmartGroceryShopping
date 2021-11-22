package com.example.f21g3_smartgroceryshopping.service;

import com.example.f21g3_smartgroceryshopping.service.entity.Dish;
import com.example.f21g3_smartgroceryshopping.service.entity.Ingredient;

import java.util.ArrayList;
import java.util.List;

public class MockSmartGroceryShoppingService implements SmartGroceryShoppingService {

    @Override
    public List<Dish> getDishes() {
        // First dish

        Ingredient pork = new Ingredient(1, "pork", 25, "g");
        Ingredient potatoes = new Ingredient(2, "potatoes", 25, "g");
        Ingredient beetroot = new Ingredient(3, "beetroot", 30, "g");
        Ingredient cabbage = new Ingredient(4, "cabbage", 12.5, "g");
        Ingredient tomatoes = new Ingredient(5, "tomatoes", 12.5, "g");
        Ingredient salt = new Ingredient(6, "salt", 2, "g");
        Ingredient parsley = new Ingredient(7, "parsley", 10, "g");
        Ingredient onion = new Ingredient(8, "onion", 20, "g");
        Ingredient water = new Ingredient(9, "water", 30, "g");
        Ingredient oliveOil = new Ingredient(10, "oliveOil", 0.18, "l");

        List<Ingredient> firstDishIngredients = new ArrayList<>();
        firstDishIngredients.add(pork);
        firstDishIngredients.add(potatoes);
        firstDishIngredients.add(beetroot);
        firstDishIngredients.add(cabbage);
        firstDishIngredients.add(onion);
        firstDishIngredients.add(tomatoes);
        firstDishIngredients.add(salt);
        firstDishIngredients.add(parsley);
        firstDishIngredients.add(onion);
        firstDishIngredients.add(water);
        firstDishIngredients.add(oliveOil);

        Dish firstDish = new Dish(1, "Borscht",
                "Borscht is a sour soup common in Eastern Europe and Northern Asia.",
                "Borscht derives from an ancient soup originally cooked from pickled stems, leaves and umbels of common hogweed (Heracleum sphondylium), a herbaceous plant growing in damp meadows, which lent the dish its Slavic name. With time, it evolved into a diverse array of tart soups, among which the Ukrainian beet-based red borscht has become the most popular. It is typically made by combining meat or bone stock with sautéed vegetables, which – as well as beetroots – usually include cabbage, carrots, onions, potatoes, and tomatoes. ",
                "https://drive.google.com/uc?export=download&id=1VPFTVd4HJXZVidaVKtDjx6uJT7W24mmN",
                false,
                firstDishIngredients);

        // Second dish

        Ingredient flour = new Ingredient(11, "flour", 15, "g");
        Ingredient water2 = new Ingredient(12, "water", 27, "g");
        Ingredient eggs = new Ingredient(13, "eggs", 1, "item");
        Ingredient salt2 = new Ingredient(14, "salt", 2, "g");
        Ingredient pork2 = new Ingredient(15, "pork", 39, "g");
        Ingredient salo = new Ingredient(16, "salo", 2.5, "g");
        Ingredient onion2 = new Ingredient(17, "onion", 60, "g");
        Ingredient blackPepper = new Ingredient(18, "black pepper", 5, "g");

        List<Ingredient> secondDishIngredients = new ArrayList<>();
        secondDishIngredients.add(flour);
        secondDishIngredients.add(water2);
        secondDishIngredients.add(eggs);
        secondDishIngredients.add(salt2);
        secondDishIngredients.add(pork2);
        secondDishIngredients.add(salo);
        secondDishIngredients.add(onion2);
        secondDishIngredients.add(blackPepper);

        Dish secondDish = new Dish(2,
                "Manty",
                "Manty is a type of dumpling popular in most Turkic cuisines, as well as in the cuisines of the South Caucasus and Balkans",
                "The dumplings typically consist of a spiced meat mixture, usually lamb or ground beef, in a thin dough wrapper and either boiled or steamed. Size and shape vary significantly depending on the geographical location.[1] Manti resemble the Chinese jiaozi and baozi, Korean mandu, Mongolian buuz and the Tibetan momo and the dish's name is cognate with the Korean mandu, Chinese mantou and Japanese manjū, although the modern Chinese and Japanese counterparts refer to different dishes.",
                "https://drive.google.com/uc?export=download&id=1rAZJy9iYfMAPFbqedMDPF0s4nOOPUPQU",
                false,
                secondDishIngredients);

        // Third dish

        Ingredient oliveOil2 = new Ingredient(19, "oliveOil", 1, "tbsp");
        Ingredient bacon = new Ingredient(20, "bacon", 60, "g");
        Ingredient garlic  = new Ingredient(21, "garlic ", 1, "clove");
        Ingredient eggs2 = new Ingredient(22, "eggs", 1, "item");
        Ingredient parmesan  = new Ingredient(23, "parmesan", 0.25, "cup");
        Ingredient salt3 = new Ingredient(24, "salt", 2, "g");
        Ingredient spaghetti  = new Ingredient(25, "spaghetti ", 125, "g");


        List<Ingredient> thirdDishIngredients = new ArrayList<>();
        thirdDishIngredients.add(oliveOil2);
        thirdDishIngredients.add(bacon);
        thirdDishIngredients.add(garlic);
        thirdDishIngredients.add(eggs2);
        thirdDishIngredients.add(parmesan);
        thirdDishIngredients.add(salt3);
        thirdDishIngredients.add(spaghetti);

        Dish thirdDish = new Dish(3, "Pasta Carbonara",
                "Carbonara is an Italian pasta dish from Rome made with eggs, hard cheese, cured pork, and black pepper.",
                "The dish arrived at its modern form, with its current name, in the middle of the 20th century. The cheese is usually Pecorino Romano, Parmigiano-Reggiano, or a combination of the two. Spaghetti is the most common pasta, but fettuccine, rigatoni, linguine, or bucatini are also used. Normally guanciale or pancetta are used for the meat component, but lardons of smoked bacon are a common substitute outside Italy.",
                "https://drive.google.com/uc?export=download&id=13JpN6PLJ-jkN1rGfxI4dpyKYts283Ulw",
                false,
                thirdDishIngredients);

        List<Dish> list = new ArrayList<>();
        list.add(firstDish);
        list.add(secondDish);
        list.add(thirdDish);

        try {
            Thread.sleep(2000);
        } catch (InterruptedException ie) {
            Thread.currentThread().interrupt();
        }

        return list;
    }

}
