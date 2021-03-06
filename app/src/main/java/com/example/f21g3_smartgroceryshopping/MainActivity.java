package com.example.f21g3_smartgroceryshopping;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.f21g3_smartgroceryshopping.adapter.DishRecyclerViewAdapter;
import com.example.f21g3_smartgroceryshopping.response.ErrorLoadResponse;
import com.example.f21g3_smartgroceryshopping.response.LoadResponse;
import com.example.f21g3_smartgroceryshopping.response.LoadingLoadResponse;
import com.example.f21g3_smartgroceryshopping.response.SuccessLoadResponse;
import com.example.f21g3_smartgroceryshopping.service.entity.Dish;
import com.example.f21g3_smartgroceryshopping.viewmodel.MainViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;
import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity {

    private TextView cartSizeTextView;
    private Toolbar toolbarMain;
    private SwipeRefreshLayout swipeContainer;
    private RecyclerView recyclerViewMeals;
    private LinearLayoutManager linearLayoutManager;
    private DishRecyclerViewAdapter dishRecyclerViewAdapter;
    private FloatingActionButton fabBasket;
    private MainViewModel mainViewModel;

    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbarMain = findViewById(R.id.toolbarMain);
        setSupportActionBar(toolbarMain);

        toolbarMain.setOnMenuItemClickListener((MenuItem item) -> {
            //check if clicked item in toolbar is cart icon and proceed to CartActivity
            if (item.getItemId() == R.id.iconToolbarCart) {
                transferToCartActivity();
            }
            return true;
        });

        cartSizeTextView = findViewById(R.id.textViewCartSizeMain);

        swipeContainer = findViewById(R.id.swipeContainer);

        recyclerViewMeals = findViewById(R.id.recyclerViewMeals);
        linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerViewMeals.setLayoutManager(linearLayoutManager);

        dishRecyclerViewAdapter = new DishRecyclerViewAdapter(dish ->
                DishDetailsActivity.launch(MainActivity.this, dish.getUid()));
        recyclerViewMeals.setAdapter(dishRecyclerViewAdapter);

        fabBasket = findViewById(R.id.fabBasket);
        fabBasket.setOnClickListener((View view) -> transferToCartActivity());

        bottomNavigationView = findViewById(R.id.navigation);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.actionHistory:
                    OrdersHistoryActivity.launch(MainActivity.this);
                    break;
                case R.id.actionClearCart:
                    mainViewModel.clearCart();
                    break;
            }
            return true;
        });

        mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);

        swipeContainer.setOnRefreshListener(() -> mainViewModel.loadDishes());

        subscribeOnGetCartSizeResponse();
        subscribeOnDishesResponse();

        //do not trigger data loading if the device was rotated
        if (savedInstanceState == null) {
            mainViewModel.loadDishes();
        }
    }

    private void transferToCartActivity() {
        //proceed to CartActivity only if cart is not empty
        if (getCartSize() != 0) {
            CartActivity.launch(MainActivity.this);
        } else {
            Toast.makeText(MainActivity.this, getString(R.string.error_empty_car), Toast.LENGTH_LONG).show();
        }
    }

    private int getCartSize() {
        try {
            return Integer.parseInt(cartSizeTextView.getText().toString());
        } catch (Exception e) {
            return 0;
        }
    }

    //method to subscribe on the event, which accepts the result of LiveData object with the size of cart
    private void subscribeOnGetCartSizeResponse() {
        mainViewModel.getCartSize().observe(this, cartSize -> {
            //update the cart size TextView in toolbar
            if(cartSize == 0) {
                cartSizeTextView.setText(R.string.empty_cart);
            } else {
                cartSizeTextView.setText(String.valueOf(cartSize));
            }
        });
    }

    //method to subscribe on the event, which accepts the result of dish list loading
    private void subscribeOnDishesResponse() {
        mainViewModel.getDishesResponse().observe(this, dishLoadResponse ->
                handleResponse(dishLoadResponse));
    }

    private void handleResponse(LoadResponse<List<Dish>> dishLoadResponse) {
        if(dishLoadResponse instanceof LoadingLoadResponse) {
            swipeContainer.setRefreshing(true);
            return;
        }

        if(dishLoadResponse instanceof SuccessLoadResponse) {
            swipeContainer.setRefreshing(false);
            if(dishLoadResponse.getResponse() != null) {
                List<Dish> dishes = dishLoadResponse.getResponse();
                dishRecyclerViewAdapter.addAll(dishes);
            }
            return;
        }

        if(dishLoadResponse instanceof ErrorLoadResponse) {
            swipeContainer.setRefreshing(false);

            Toast.makeText(MainActivity.this, getString(R.string.error_dish_load), Toast.LENGTH_SHORT).show();
            return;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

}