package com.example.f21g3_smartgroceryshopping;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.View;


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

        fabOrder.setOnClickListener((View view) -> {

            progressDialog = new ProgressDialog(DishDetailsActivity.this);
            progressDialog.show();
            progressDialog.setContentView(R.layout.loading_page);
            progressDialog.setCancelable(false);
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    progressDialog.dismiss();
                    setContentView(R.layout.order_successful);
                }
            }, 5000);

        });


        dishDetailsViewModel = new ViewModelProvider(this).get(DishDetailsViewModel.class);
    }

    public static void launch(Context context, int dishId) {
        Intent intent = new Intent(context, DishDetailsActivity.class);
        intent.putExtra(DISH_ID, dishId);
        context.startActivity(intent);
    }






}