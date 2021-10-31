package com.example.f21g3_smartgroceryshopping;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.util.Log;

import com.example.f21g3_smartgroceryshopping.response.LoadResponse;
import com.example.f21g3_smartgroceryshopping.service.entity.Dish;
import com.example.f21g3_smartgroceryshopping.viewmodel.MainViewModel;

import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity {

    private MainViewModel mainViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);


        mainViewModel.getDishesResponse().observe(this, new Observer<LoadResponse<List<Dish>>>() {
            @Override
            public void onChanged(LoadResponse<List<Dish>> dishLoadResponse) {
                Log.d("myLogs", "dishLoadResponse");
                if(dishLoadResponse.getResponse() != null) {
                    List<Dish> dishes = dishLoadResponse.getResponse();

                    Log.d("myLogs", String.valueOf(dishes.size()));

                    if(dishes.size() != 0) {
                        Log.d("myLogs", String.valueOf(dishes.get(0)));
                    }

                }
            }
        });

        mainViewModel.getDishes();

        mainViewModel.getCartSize().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                Log.d("myLogs", "getCartSize " + integer);
            }
        });
    }

    //Test Commit
}