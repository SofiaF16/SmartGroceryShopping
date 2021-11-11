package com.example.f21g3_smartgroceryshopping.di;

import com.example.f21g3_smartgroceryshopping.maker.FullIngredientsListMaker;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public class IngredientsMakerModule {

    @Provides
    @Singleton
    public FullIngredientsListMaker provideIngredientsListMaker() {
        return new FullIngredientsListMaker();
    }

}
