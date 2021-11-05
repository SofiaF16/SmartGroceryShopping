package com.example.f21g3_smartgroceryshopping;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.f21g3_smartgroceryshopping.viewmodel.OrdersHistoryViewModel;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class OrdersHistoryActivity extends AppCompatActivity {

    private OrdersHistoryViewModel ordersHistoryViewModel;


    Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders_history);

        ordersHistoryViewModel = new ViewModelProvider(this).get(OrdersHistoryViewModel.class);

        toolbar = findViewById(R.id.toolbarHistory);
        toolbar.setTitle(R.string.txtTitleHistory);
        toolbar.setNavigationIcon(R.drawable.ic_baseline);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        setSupportActionBar(toolbar);


    }


    public static void launch(Context context) {
        Intent intent = new Intent(context, OrdersHistoryActivity.class);

        context.startActivity(intent);
    }

}

