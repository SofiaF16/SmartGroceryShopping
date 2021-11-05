package com.example.f21g3_smartgroceryshopping;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.f21g3_smartgroceryshopping.viewmodel.DishDetailsViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class DishDetailsActivity extends AppCompatActivity {

    private static final String DISH_ID = "DishDetailsActivityDishId";

    private int dishId;
    FloatingActionButton fabOrder;
    ProgressDialog progressDialog;
    Toolbar toolbar;

    private DishDetailsViewModel dishDetailsViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dish_details);
        dishId = getIntent().getIntExtra(DISH_ID, dishId);

        fabOrder = findViewById(R.id.fabOrder);
        toolbar = findViewById(R.id.toolbarOrder);
        toolbar.setTitle(R.string.app_name);
        toolbar.setNavigationIcon(R.drawable.ic_baseline);
        setSupportActionBar(toolbar);


        dishDetailsViewModel = new ViewModelProvider(this).get(DishDetailsViewModel.class);
    }

    public static void launch(Context context, int dishId) {
        Intent intent = new Intent(context, DishDetailsActivity.class);
        intent.putExtra(DISH_ID, dishId);
        context.startActivity(intent);
    }






}