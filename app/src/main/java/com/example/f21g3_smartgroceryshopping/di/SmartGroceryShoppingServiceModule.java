package com.example.f21g3_smartgroceryshopping.di;

import com.example.f21g3_smartgroceryshopping.service.MockSmartGroceryShoppingService;
import com.example.f21g3_smartgroceryshopping.service.SmartGroceryShoppingService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public class SmartGroceryShoppingServiceModule {

    @Provides
    @Singleton
    public static SmartGroceryShoppingService provideSmartGroceryShoppingService() {
        return new MockSmartGroceryShoppingService();
    }

}
