package com.example.f21g3_smartgroceryshopping.di;

import com.example.f21g3_smartgroceryshopping.maker.FullIngredientsListMaker;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;

/**
 * Module class providing the object to calculate total list of ingredients in the order
 */
@Module
@InstallIn(SingletonComponent.class)
public class IngredientsMakerModule {

    @Provides
    @Singleton //A singleton is a design pattern that restricts the instantiation of a class to only one instance.
    public FullIngredientsListMaker provideIngredientsListMaker() {
        return new FullIngredientsListMaker();
    }

}
