package com.example.f21g3_smartgroceryshopping;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

import com.example.f21g3_smartgroceryshopping.adapter.DishRecyclerViewAdapter;
import com.example.f21g3_smartgroceryshopping.response.ErrorLoadResponse;
import com.example.f21g3_smartgroceryshopping.response.LoadResponse;
import com.example.f21g3_smartgroceryshopping.response.LoadingLoadResponse;
import com.example.f21g3_smartgroceryshopping.response.SuccessLoadResponse;
import com.example.f21g3_smartgroceryshopping.service.entity.Dish;
import com.example.f21g3_smartgroceryshopping.service.entity.Ingredient;
import com.example.f21g3_smartgroceryshopping.viewmodel.MainViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity {

    private Toolbar toolbarMain;
    private SwipeRefreshLayout swipeContainer;
    private RecyclerView recyclerViewMeals;
    private LinearLayoutManager linearLayoutManager;
    private DishRecyclerViewAdapter dishRecyclerViewAdapter;
    private FloatingActionButton fabBasket;
    private List<Dish> DishList = new ArrayList<>();
    private MainViewModel mainViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbarMain = findViewById(R.id.toolbarMain);
        setSupportActionBar(toolbarMain);

        swipeContainer = findViewById(R.id.swipeContainer);

        //to be implemented
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
            }
        });

        recyclerViewMeals = findViewById(R.id.recyclerViewMeals);
        linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerViewMeals.setLayoutManager(linearLayoutManager);

        dishRecyclerViewAdapter = new DishRecyclerViewAdapter(DishList);
        recyclerViewMeals.setAdapter(dishRecyclerViewAdapter);

        //Showing toast for test only
        //To be implemented
        fabBasket = findViewById(R.id.fabBasket);
        fabBasket.setOnClickListener((View view) -> Toast.makeText(MainActivity.this, "FAB clicked", Toast.LENGTH_SHORT).show());

        // mock data to test
        List<Ingredient> ingredients = new ArrayList<>(Arrays.asList());
        DishList.add(new Dish(1,"Toasted Ravioli", "Breaded Deep-Fried Ravioli ", "Dish Desription", "https://drive.google.com/uc?export=download&id=1VPFTVd4HJXZVidaVKtDjx6uJT7W24mmN", false, ingredients));
        DishList.add(new Dish(2,"Lamb Kofta", "Mediterranean Grilled Lamb", "Dish Desription", "https://drive.google.com/uc?export=download&id=1rAZJy9iYfMAPFbqedMDPF0s4nOOPUPQU", false, ingredients));
        DishList.add(new Dish(3,"Margarita", "Classic Vegetarian Pizza", "Dish Desription", "https://www.foodiecrush.com/wp-content/uploads/2019/05/Grilled-Salmon-foodiecrush.com-023.jpg", false, ingredients));

        mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);

        mainViewModel.getDishesResponse().observe(this, new Observer<LoadResponse<List<Dish>>>() {
            @Override
            public void onChanged(LoadResponse<List<Dish>> dishLoadResponse) {

                handleResponse(dishLoadResponse);

//                Log.d("myLogs", "dishLoadResponse");
//                if(dishLoadResponse.getResponse() != null) {
//                    List<Dish> dishes = dishLoadResponse.getResponse();
//
//                    Log.d("myLogs", String.valueOf(dishes.size()));
//
//                    if(dishes.size() != 0) {
//                        Log.d("myLogs", String.valueOf(dishes.get(0)));
//                    }
//
//                }
            }
        });

        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mainViewModel.loadDishes();
            }
        });



        mainViewModel.getCartSize().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                Log.d("myLogs", "getCartSize " + integer);
            }
        });

    }

    private void handleResponse(LoadResponse<List<Dish>> dishLoadResponse) {
//        if(dishLoadResponse instanceof LoadingLoadResponse) {
//
//        } else {
//            m(dishLoadResponse);
//        }
        if(dishLoadResponse instanceof LoadingLoadResponse) {
            return;
        }

        if(dishLoadResponse instanceof SuccessLoadResponse) {
            return;
        }

        if(dishLoadResponse instanceof ErrorLoadResponse) {
            return;
        }


    }

    private void m(LoadResponse<List<Dish>> dishLoadResponse) {
        if(dishLoadResponse instanceof SuccessLoadResponse) {
            dishRecyclerViewAdapter.addAll(dishLoadResponse.getResponse());
        } else {
            Toast.makeText(MainActivity.this, dishLoadResponse.getError().getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    //Test Commit
}