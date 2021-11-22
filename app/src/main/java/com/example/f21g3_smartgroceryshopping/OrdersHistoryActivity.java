package com.example.f21g3_smartgroceryshopping;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.f21g3_smartgroceryshopping.adapter.DishRecyclerViewAdapter;
import com.example.f21g3_smartgroceryshopping.adapter.HistoryRecyclerViewAdapter;
import com.example.f21g3_smartgroceryshopping.response.ErrorLoadResponse;
import com.example.f21g3_smartgroceryshopping.response.LoadResponse;
import com.example.f21g3_smartgroceryshopping.response.LoadingLoadResponse;
import com.example.f21g3_smartgroceryshopping.response.SuccessLoadResponse;
import com.example.f21g3_smartgroceryshopping.service.entity.CartItem;
import com.example.f21g3_smartgroceryshopping.service.entity.Dish;
import com.example.f21g3_smartgroceryshopping.service.entity.Ingredient;
import com.example.f21g3_smartgroceryshopping.service.entity.Order;
import com.example.f21g3_smartgroceryshopping.service.entity.OrderItem;
import com.example.f21g3_smartgroceryshopping.viewmodel.OrdersHistoryViewModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class OrdersHistoryActivity extends AppCompatActivity {

    private OrdersHistoryViewModel ordersHistoryViewModel;


    private Toolbar toolbar;
    private RecyclerView recyclerViewHistory;
    private LinearLayoutManager linearLayoutManager;
    private HistoryRecyclerViewAdapter historyRecyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders_history);

        ordersHistoryViewModel = new ViewModelProvider(this).get(OrdersHistoryViewModel.class);

        toolbar = findViewById(R.id.toolbarHistory);
        toolbar.setTitle(R.string.app_name);
        toolbar.setNavigationIcon(R.drawable.back_arrow);
        setSupportActionBar(toolbar);


        recyclerViewHistory = findViewById(R.id.recyclerViewHistory);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerViewHistory.setLayoutManager(linearLayoutManager);
        historyRecyclerViewAdapter = new HistoryRecyclerViewAdapter(new HistoryRecyclerViewAdapter.OnOrderHistoryClickListener() {
            @Override
            public void onOrderHistoryClick(Order order) {
                ordersHistoryViewModel.updateCartWith(order);
            }
        });

        recyclerViewHistory.setAdapter(historyRecyclerViewAdapter);

        toolbar.setNavigationOnClickListener((View view) -> {
            finish();
        });
        subscribeOnOrderHistoryResponse();
        subscribeOnUpdateCartResponse();

        if (savedInstanceState == null) {
            ordersHistoryViewModel.loadOrders();
        }
    }

    public static void launch(Context context) {
        Intent intent = new Intent(context, OrdersHistoryActivity.class);

        context.startActivity(intent);
    }

    private void subscribeOnOrderHistoryResponse(){
        ordersHistoryViewModel.getOrderResponse().observe(this, new Observer<LoadResponse<List<Order>>>() {
            @Override
            public void onChanged(LoadResponse<List<Order>> listLoadResponse) {
                handleOrderHistoryResponse(listLoadResponse);
            }
        });
    }

    private void subscribeOnUpdateCartResponse(){
        ordersHistoryViewModel.getUpdateCartResponse().observe(this, new Observer<LoadResponse<Long>>() {
            @Override
            public void onChanged(LoadResponse<Long> longLoadResponse) {
                handleUpdateCartResponse(longLoadResponse);
            }
        });
    }

    private void handleOrderHistoryResponse(LoadResponse<List<Order>> listLoadResponse){

        if(listLoadResponse instanceof SuccessLoadResponse) {
            if(listLoadResponse.getResponse() != null) {
                List<Order> orderItems = listLoadResponse.getResponse();
                historyRecyclerViewAdapter.addAll(orderItems);
            }
            return;
        }

        if(listLoadResponse instanceof ErrorLoadResponse) {

            Toast.makeText(OrdersHistoryActivity.this, getString(R.string.error_order_history_load), Toast.LENGTH_SHORT).show();
            return;
        }
    }

    private void handleUpdateCartResponse(LoadResponse<Long> longLoadResponse){

        if(longLoadResponse instanceof SuccessLoadResponse) {
            CartActivity.launch(OrdersHistoryActivity.this);
            finish();
            return;
        }

        if(longLoadResponse instanceof ErrorLoadResponse) {

            Toast.makeText(OrdersHistoryActivity.this, getString(R.string.error_update_cart_load), Toast.LENGTH_SHORT).show();
            return;
        }
    }



}

