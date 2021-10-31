package com.example.f21g3_smartgroceryshopping;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.f21g3_smartgroceryshopping.viewmodel.DishDetailsViewModel;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class DishDetailsActivity extends AppCompatActivity {

    private static final String DISH_ID = "DishDetailsActivityDishId";

    private int dishId;

    private DishDetailsViewModel dishDetailsViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dish_details);

        dishId = getIntent().getIntExtra(DISH_ID, dishId);

        dishDetailsViewModel = new ViewModelProvider(this).get(DishDetailsViewModel.class);
    }


    public static void launch(Context context, int dishId) {
        Intent intent = new Intent(context, DishDetailsActivity.class);
        intent.putExtra(DISH_ID, dishId);
        context.startActivity(intent);
    }


}