package com.example.f21g3_smartgroceryshopping;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.example.f21g3_smartgroceryshopping.adapter.HistoryRecyclerViewAdapter;
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

    private List<Order> OrdersList = new ArrayList<>();
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

        // mock data to test
        //    public Order(int orderId, Date orderDate, List<OrderItem> orderItems) {
        Date date = new Date();

        //public OrderItem(long dishId, String dishTitle, int portions) {

        List<OrderItem> orderItems = new ArrayList<>(Arrays.asList());
        OrderItem orderItem = new OrderItem(1, "Toasted Ravioli", 2);
        OrderItem orderItem2 = new OrderItem(1, "Lamb Kofta", 1);
        OrderItem orderItem3 = new OrderItem(1, "Margarita", 3);
        orderItems.add(orderItem);
        orderItems.add(orderItem2);
        orderItems.add(orderItem3);

        Order order = new Order(1,date, orderItems);
        Order order2 = new Order(2,date, orderItems);
        Order order3 = new Order(3,date, orderItems);

        OrdersList.add(order);
        OrdersList.add(order2);
        OrdersList.add(order3);

        recyclerViewHistory = findViewById(R.id.recyclerViewHistory);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerViewHistory.setLayoutManager(linearLayoutManager);
        historyRecyclerViewAdapter = new HistoryRecyclerViewAdapter(OrdersList);
        recyclerViewHistory.setAdapter(historyRecyclerViewAdapter);

        toolbar.setNavigationOnClickListener((View view) -> {
            finish();
        });
    }


    public static void launch(Context context) {
        Intent intent = new Intent(context, OrdersHistoryActivity.class);

        context.startActivity(intent);
    }

}

