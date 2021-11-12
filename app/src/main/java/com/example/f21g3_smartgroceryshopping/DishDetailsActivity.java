package com.example.f21g3_smartgroceryshopping;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.f21g3_smartgroceryshopping.response.ErrorLoadResponse;
import com.example.f21g3_smartgroceryshopping.response.LoadResponse;
import com.example.f21g3_smartgroceryshopping.response.LoadingLoadResponse;
import com.example.f21g3_smartgroceryshopping.response.SuccessLoadResponse;
import com.example.f21g3_smartgroceryshopping.service.entity.Dish;
import com.example.f21g3_smartgroceryshopping.viewmodel.DishDetailsViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class DishDetailsActivity extends AppCompatActivity {

    private static final String DISH_ID = "DishDetailsActivityDishId";

    private int dishId;
    private ConstraintLayout dishDetailsLayout;
    private Toolbar toolbarDishDetails;
    private FloatingActionButton fabAddDish;
    private CardView cardViewDish;
    private ImageView dishImage;
    private TextView dishName;
    private TextView dishDesc;
    private NumberPicker numberOfPortions;
    private DishDetailsViewModel dishDetailsViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dish_details);
        dishId = getIntent().getIntExtra(DISH_ID, dishId);

        fabAddDish = findViewById(R.id.fabDish);

        toolbarDishDetails = findViewById(R.id.toolbarDishDetails);
        //toolbarDishDetails.setTitle(R.string.app_name);
        toolbarDishDetails.setNavigationIcon(R.drawable.back_arrow);
        setSupportActionBar(toolbarDishDetails);

        cardViewDish = findViewById(R.id.cardViewDish);
        dishImage = findViewById(R.id.imageViewDishDetails);
        dishName = findViewById(R.id.textViewDetailsDishName);
        dishDesc = findViewById(R.id.textViewDetailsDishDesc);
        numberOfPortions = findViewById(R.id.numberOfMealsPicker);
        numberOfPortions.setMinValue(0);
        numberOfPortions.setMaxValue(20);


        dishDetailsViewModel = new ViewModelProvider(this).get(DishDetailsViewModel.class);
        subscribeOnDishLoadResponse();

        if (savedInstanceState == null) {
            dishDetailsViewModel.loadDish(dishId);
        }

    }

    public static void launch(Context context, int dishId) {
        Intent intent = new Intent(context, DishDetailsActivity.class);
        intent.putExtra(DISH_ID, dishId);
        context.startActivity(intent);
    }

    private void subscribeOnDishLoadResponse() {

        /*dishDetailsViewModel.addToCartResponse().observe(this, new Observer<LoadResponse<Long>>() {
            @Override
            public void onChanged(LoadResponse<Long> longLoadResponse) {
                handleAdd
            }
        });*/

        dishDetailsViewModel.getDish().observe(this, new Observer<LoadResponse<Dish>>() {
            @Override
            public void onChanged(LoadResponse<Dish> dishLoadResponse) {
                handleResponse(dishLoadResponse);
            }
        });
    }

    private void handleResponse(LoadResponse<Dish> dishResponse) {
        if(dishResponse instanceof LoadingLoadResponse) {
            return;
        }

        if(dishResponse instanceof SuccessLoadResponse) {
            if(dishResponse.getResponse() != null) {
                Dish response = dishResponse.getResponse();
                dishName.setText(response.getTitle());
                dishDesc.setText(response.getLongDescription());

                Glide.with(dishImage.getContext())
                        .load(response.getImageUrl())
                        .override(dishImage.getWidth(), dishImage.getHeight())
                        .error(R.drawable.error_loand_image)
                        .placeholder(R.drawable.error_loand_image)
                        .into(dishImage);
            }
            return;
        }

        if(dishResponse instanceof ErrorLoadResponse) {

            Toast.makeText(DishDetailsActivity.this, "Error", Toast.LENGTH_SHORT).show();
            return;
        }
    }

}