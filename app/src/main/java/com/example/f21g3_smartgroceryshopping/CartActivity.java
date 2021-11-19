package com.example.f21g3_smartgroceryshopping;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.ViewModelProvider;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.f21g3_smartgroceryshopping.adapter.CartDishRecyclerViewAdapter;
import com.example.f21g3_smartgroceryshopping.adapter.CartIngredientRecyclerViewAdapter;
import com.example.f21g3_smartgroceryshopping.service.entity.CartItem;
import com.example.f21g3_smartgroceryshopping.service.entity.CurrentCartItem;
import com.example.f21g3_smartgroceryshopping.service.entity.Dish;
import com.example.f21g3_smartgroceryshopping.service.entity.Ingredient;
import com.example.f21g3_smartgroceryshopping.viewmodel.CartViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class CartActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private FloatingActionButton orderBtn;

    private ConstraintLayout orderDishesLayout;
    private RecyclerView recyclerViewDishes;

    private ConstraintLayout orderIngredientsLayout;
    private RecyclerView recyclerViewIngredients;

    private ConstraintLayout orderProcessingLayout;

    private ConstraintLayout orderSuccessLayout;
    private Button btnBackToMain;

    private CartViewModel cartViewModel;

    private LinearLayoutManager linearLayoutManagerDish;
    private CartDishRecyclerViewAdapter cartDishRecyclerViewAdapter;

    private LinearLayoutManager linearLayoutManagerIngredient;
    private CartIngredientRecyclerViewAdapter cartIngredientRecyclerViewAdapter;

    private List<CartItem> DishList = new ArrayList<>();
    private List<Ingredient> IngredientsList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        toolbar = findViewById(R.id.toolbarCart);
        toolbar.setTitle(R.string.app_name);
        toolbar.setNavigationIcon(R.drawable.back_arrow);
        setSupportActionBar(toolbar);

        orderDishesLayout = findViewById(R.id.layoutOrderDishes);
        recyclerViewDishes = findViewById(R.id.recyclerViewDishes);
        orderIngredientsLayout = findViewById(R.id.layoutOrderIngredients);
        recyclerViewIngredients = findViewById(R.id.recyclerViewIngredients);
        orderProcessingLayout = findViewById(R.id.layoutOrderSending);
        orderSuccessLayout = findViewById(R.id.layoutOrderSuccessful);
        btnBackToMain = findViewById(R.id.btnBackToMain);
        orderBtn = findViewById(R.id.fabOrder);

        linearLayoutManagerDish = new LinearLayoutManager(this);
        recyclerViewDishes.setLayoutManager(linearLayoutManagerDish);
        cartDishRecyclerViewAdapter = new CartDishRecyclerViewAdapter(DishList);
        recyclerViewDishes.setAdapter(cartDishRecyclerViewAdapter);

        linearLayoutManagerIngredient = new LinearLayoutManager(this);
        recyclerViewIngredients.setLayoutManager(linearLayoutManagerIngredient);
        cartIngredientRecyclerViewAdapter = new CartIngredientRecyclerViewAdapter(IngredientsList);
        recyclerViewIngredients.setAdapter(cartIngredientRecyclerViewAdapter);

        toolbar.setNavigationOnClickListener((View view) -> {
            if(orderIngredientsLayout.getVisibility() == View.VISIBLE){
                orderDishesLayout.setVisibility(View.VISIBLE);
                orderIngredientsLayout.setVisibility(View.GONE);
            } else{
                finish();
            }
        });

        cartViewModel = new ViewModelProvider(this).get(CartViewModel.class);

        orderBtn.setOnClickListener((View view) -> {
            setVisibility();
        });

        btnBackToMain.setOnClickListener((View view) ->{
            finish();
        });
    }

    public static void launch(Context context) {
        Intent intent = new Intent(context, CartActivity.class);

        context.startActivity(intent);
    }

    private void setVisibility(){
        if(orderDishesLayout.getVisibility() == View.VISIBLE){
            orderDishesLayout.setVisibility(View.GONE);
            orderIngredientsLayout.setVisibility(View.VISIBLE);
        } else if(orderIngredientsLayout.getVisibility() == View.VISIBLE){
            orderIngredientsLayout.setVisibility(View.GONE);
            orderProcessingLayout.setVisibility(View.VISIBLE);
            orderBtn.setVisibility(View.GONE);
            //test progress bar
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    orderSuccessLayout.setVisibility(View.VISIBLE);
                    orderProcessingLayout.setVisibility(View.GONE);
                }
            }, 3000);
        }
    }

}