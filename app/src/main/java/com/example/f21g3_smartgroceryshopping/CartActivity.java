package com.example.f21g3_smartgroceryshopping;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.f21g3_smartgroceryshopping.viewmodel.CartViewModel;
import com.example.f21g3_smartgroceryshopping.viewmodel.DishDetailsViewModel;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class CartActivity extends AppCompatActivity {

    private CartViewModel cartViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        cartViewModel = new ViewModelProvider(this).get(CartViewModel.class);
    }


    public static void launch(Context context) {
        Intent intent = new Intent(context, CartActivity.class);

        context.startActivity(intent);
    }

}