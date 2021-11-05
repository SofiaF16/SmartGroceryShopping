package com.example.f21g3_smartgroceryshopping;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.ViewModelProvider;
import androidx.appcompat.widget.Toolbar;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.example.f21g3_smartgroceryshopping.viewmodel.CartViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class CartActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private LinearLayout ingredientsLayout;
    private ConstraintLayout orderProcessingLayout;
    private ConstraintLayout orderSuccessLayout;

    private FloatingActionButton orderBtn;

    private CartViewModel cartViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        toolbar = findViewById(R.id.toolbarCart);
        toolbar.setTitle(R.string.app_name);
        toolbar.setNavigationIcon(R.drawable.ic_baseline);
        setSupportActionBar(toolbar);

        ingredientsLayout = findViewById(R.id.layoutIngredientsView);
        orderProcessingLayout = findViewById(R.id.layoutOrderSending);
        orderSuccessLayout = findViewById(R.id.layoutOrderSuccessful);

        orderBtn = findViewById(R.id.fabOrder);

        cartViewModel = new ViewModelProvider(this).get(CartViewModel.class);

        orderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                orderProcessingLayout.setVisibility(View.GONE);
                orderSuccessLayout.setVisibility(View.VISIBLE);
            }
        });
    }


    public static void launch(Context context) {
        Intent intent = new Intent(context, CartActivity.class);

        context.startActivity(intent);
    }

}