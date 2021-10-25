package com.example.f21g3_smartgroceryshopping.di;

import android.content.Context;
import androidx.room.Room;
import com.example.f21g3_smartgroceryshopping.storage.AppDatabase;
import com.example.f21g3_smartgroceryshopping.storage.dao.SmartGroceryShoppingDao;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public class StorageModule {

    @Provides
    @Singleton
    public static AppDatabase provideAppDatabase(@ApplicationContext Context context) {
        return Room.databaseBuilder(context, AppDatabase.class, "SmartGroceryShoppingDatabase").build();
    }

    @Provides
    @Singleton
    public static SmartGroceryShoppingDao provideSmartGroceryShoppingDao(AppDatabase appDatabase) {
        return appDatabase.smartGroceryShoppingDao();
    }

}

