package com.example.f21g3_smartgroceryshopping;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.f21g3_smartgroceryshopping.adapter.CartDishRecyclerViewAdapter;
import com.example.f21g3_smartgroceryshopping.adapter.CartIngredientRecyclerViewAdapter;
import com.example.f21g3_smartgroceryshopping.response.ErrorLoadResponse;
import com.example.f21g3_smartgroceryshopping.response.LoadResponse;
import com.example.f21g3_smartgroceryshopping.response.LoadingLoadResponse;
import com.example.f21g3_smartgroceryshopping.response.SuccessLoadResponse;
import com.example.f21g3_smartgroceryshopping.service.entity.CartItem;
import com.example.f21g3_smartgroceryshopping.service.entity.CurrentCartItem;
import com.example.f21g3_smartgroceryshopping.service.entity.Ingredient;
import com.example.f21g3_smartgroceryshopping.viewmodel.CartViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
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
        setSupportActionBar(toolbar); //sets the toolbar as the app bar for the activity

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

        cartDishRecyclerViewAdapter = new CartDishRecyclerViewAdapter(new CartDishRecyclerViewAdapter.OnCartDishDeleteClickListener() {
            @Override
            public void onCartDishClickDelete(CartItem cartItem) {
                cartViewModel.deleteCartItem(new CurrentCartItem(cartItem.getCartItemKey(), cartItem.getDish().getUid(), cartItem.getDish().getTitle(), cartItem.getPortions()));
            }
        }, new CartDishRecyclerViewAdapter.OnCartDishUpdateClickListener() {
            @Override
            public void onCartDishClickUpdate(CartItem cartItem, int portions) {
                cartViewModel.updateCartItem(new CurrentCartItem(cartItem.getCartItemKey(), cartItem.getDish().getUid(), cartItem.getDish().getTitle(), portions));
                Toast.makeText(CartActivity.this, getString(R.string.item_updated_message), Toast.LENGTH_SHORT).show();
            }
        });

        recyclerViewDishes.setAdapter(cartDishRecyclerViewAdapter);

        linearLayoutManagerIngredient = new LinearLayoutManager(this);
        recyclerViewIngredients.setLayoutManager(linearLayoutManagerIngredient);
        cartIngredientRecyclerViewAdapter = new CartIngredientRecyclerViewAdapter();
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
            // if cart is empty - forbid going to ingredients calculation
            if(orderDishesLayout.getVisibility() == View.VISIBLE && cartDishRecyclerViewAdapter.getItemCount() == 0) {
                Toast.makeText(CartActivity.this, getString(R.string.empty_cart_error_message), Toast.LENGTH_SHORT).show();
                return;
            }

            if(orderDishesLayout.getVisibility() == View.VISIBLE) {
                orderDishesLayout.setVisibility(View.GONE);
                orderIngredientsLayout.setVisibility(View.VISIBLE);
                cartViewModel.loadIngredientsList();
            } else if(orderIngredientsLayout.getVisibility() == View.VISIBLE) {

                cartViewModel.postOrder();
            }
        });

        btnBackToMain.setOnClickListener((View view) ->{
            finish();
        });

        subscribeOnCartItemsResponse();
        subscribeOnIngredientListResponse();
        subscribeOnPostOrderStatusResponse();

        //do not trigger data loading if the device was rotated
        if (savedInstanceState == null) {
            cartViewModel.loadCartItems();
        }
    }

    public static void launch(Context context) {
        Intent intent = new Intent(context, CartActivity.class);
        context.startActivity(intent);
    }

    //method to subscribe on the event, which accepts the result of cartViewModel.loadCartItems()
    private void subscribeOnCartItemsResponse(){
        cartViewModel.getCartItemsResponse().observe(this, new Observer<LoadResponse<List<CartItem>>>() {
            @Override
            public void onChanged(LoadResponse<List<CartItem>> listLoadResponse) {
                handleCartItemsResponse(listLoadResponse);
            }
        });
    }
    //method to subscribe on the event, which accepts the result of ingredients list generation
    private void subscribeOnIngredientListResponse(){
        cartViewModel.getIngredientsListResponse().observe(this, new Observer<LoadResponse<List<Ingredient>>>() {
            @Override
            public void onChanged(LoadResponse<List<Ingredient>> listLoadResponse) {
                handleIngredientsItemsResponse(listLoadResponse);
            }
        });
    }
    //method to subscribe on the event, which accepts the result of order processing
    private void subscribeOnPostOrderStatusResponse(){
        cartViewModel.getPostOrderStatusResponse().observe(this, new Observer<LoadResponse<String>>() {
            @Override
            public void onChanged(LoadResponse<String> stringLoadResponse) {
                handlePostOrderResponse(stringLoadResponse);
            }
        });
    }

    private void handleCartItemsResponse(LoadResponse<List<CartItem>> listLoadResponse) {

        if(listLoadResponse instanceof SuccessLoadResponse) {
            if(listLoadResponse.getResponse() != null) {
                List<CartItem> cartItems = listLoadResponse.getResponse();
                cartDishRecyclerViewAdapter.addAll(cartItems);
            }
            return;
        }

        if(listLoadResponse instanceof ErrorLoadResponse) {

            Toast.makeText(CartActivity.this, getString(R.string.error_cart_item_load), Toast.LENGTH_SHORT).show();
            return;
        }
    }

    private void handleIngredientsItemsResponse(LoadResponse<List<Ingredient>> listLoadResponse) {

        if(listLoadResponse instanceof SuccessLoadResponse) {
            if(listLoadResponse.getResponse() != null) {
                List<Ingredient> ingredientItems = listLoadResponse.getResponse();
                cartIngredientRecyclerViewAdapter.addAll(ingredientItems);
            }
            return;
        }

        if(listLoadResponse instanceof ErrorLoadResponse) {

            Toast.makeText(CartActivity.this, getString(R.string.error_ingredient_item_load), Toast.LENGTH_SHORT).show();
            return;
        }
    }

    private void handlePostOrderResponse(LoadResponse<String> stringLoadResponse) {
        if(stringLoadResponse instanceof LoadingLoadResponse){
            orderIngredientsLayout.setVisibility(View.GONE);
            orderProcessingLayout.setVisibility(View.VISIBLE);
            orderBtn.setVisibility(View.GONE);
        }

        if(stringLoadResponse instanceof SuccessLoadResponse) {
            if(stringLoadResponse.getResponse() != null) {
                orderSuccessLayout.setVisibility(View.VISIBLE);
                orderProcessingLayout.setVisibility(View.GONE);
            }
            return;
        }

        if(stringLoadResponse instanceof ErrorLoadResponse) {
            orderIngredientsLayout.setVisibility(View.VISIBLE);
            orderProcessingLayout.setVisibility(View.GONE);
            orderBtn.setVisibility(View.VISIBLE);
            Toast.makeText(CartActivity.this, getString(R.string.error_post_order_load), Toast.LENGTH_SHORT).show();
            return;
        }
    }

}