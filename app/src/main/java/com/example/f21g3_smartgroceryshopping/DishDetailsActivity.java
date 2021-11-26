package com.example.f21g3_smartgroceryshopping;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
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

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class DishDetailsActivity extends AppCompatActivity {

    private static final String DISH_ID = "DishDetailsActivityDishId";

    private int dishId;
    private Dish dish;
    private int numberOfPortions;
    private ConstraintLayout dishDetailsLayout;
    private Toolbar toolbarDishDetails;
    private FloatingActionButton fabAddDish;
    private CardView cardViewDish;
    private ImageView dishImage;
    private TextView dishName;
    private TextView dishDesc;
    private TextView textViewCartSize;
    private NumberPicker numberOfPortionsPicker;
    private DishDetailsViewModel dishDetailsViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dish_details);
        dishId = getIntent().getIntExtra(DISH_ID, dishId);

        fabAddDish = findViewById(R.id.fabDish);

        toolbarDishDetails = findViewById(R.id.toolbarDishDetails);
        toolbarDishDetails.setNavigationIcon(R.drawable.back_arrow);
        setSupportActionBar(toolbarDishDetails);
        toolbarDishDetails.setOnMenuItemClickListener((MenuItem item) -> {
            if (item.getItemId() == R.id.iconToolbarCart) {
                if (dishDetailsViewModel.getCartSize().getValue() != 0) {
                    CartActivity.launch(DishDetailsActivity.this);
                    finish();
                } else {
                    Toast.makeText(DishDetailsActivity.this,
                            "The Cart Is Empty. \nPlease Add A Dish And Try Again.", Toast.LENGTH_LONG).show();
                }
            }
            return true;
        });

        toolbarDishDetails.setNavigationOnClickListener((View view) -> {
            finish();
        });

        cardViewDish = findViewById(R.id.cardViewDish);
        dishImage = findViewById(R.id.imageViewDishDetails);
        dishName = findViewById(R.id.textViewDetailsDishName);
        dishDesc = findViewById(R.id.textViewDetailsDishDesc);
        textViewCartSize = findViewById(R.id.textViewCartSizeDishDetails);

        numberOfPortionsPicker = findViewById(R.id.numberOfMealsPicker);
        numberOfPortionsPicker.setMinValue(1);
        numberOfPortionsPicker.setMaxValue(20);
        numberOfPortionsPicker.setWrapSelectorWheel(false);
        numberOfPortionsPicker.setClickable(false);

        numberOfPortionsPicker.setOnValueChangedListener((NumberPicker picker, int oldVal, int newVal) -> {
            numberOfPortionsPicker.setValue(newVal);
            numberOfPortions = newVal;
        });

        fabAddDish.setOnClickListener((View view) -> {
            if (dish != null) {
                dishDetailsViewModel.addToCart(dish.getUid(), dish.getTitle(), numberOfPortionsPicker.getValue());
            }
        });

        dishDetailsViewModel = new ViewModelProvider(this).get(DishDetailsViewModel.class);
        subscribeOnDishPortions();
        subscribeOnDishLoadResponse();
        subscribeOnAddDishResponse();
        subscribeOnGetCartSizeResponse();

        if (savedInstanceState == null) {
            dishDetailsViewModel.loadDish(dishId);
        }
    }

    public static void launch(Context context, int dishId) {
        Intent intent = new Intent(context, DishDetailsActivity.class);
        intent.putExtra(DISH_ID, dishId);
        context.startActivity(intent);
    }

    private void subscribeOnDishPortions() {
        dishDetailsViewModel.getDishPortionsNumberResponse().observe(this, new Observer<LoadResponse<Integer>>() {
            @Override
            public void onChanged(LoadResponse<Integer> integerLoadResponse) {
                if(integerLoadResponse.getResponse() != null && integerLoadResponse.getResponse() > 0) {
                    numberOfPortionsPicker.setValue(integerLoadResponse.getResponse());
                }
            }
        });
    }

    private void subscribeOnGetCartSizeResponse() {
        dishDetailsViewModel.getCartSize().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer cartSize) {
                if(cartSize == 0) {
                    textViewCartSize.setText(R.string.empty_cart);
                } else {
                    textViewCartSize.setText(String.valueOf(cartSize));
                }
            }
        });
    }

    private void subscribeOnAddDishResponse() {
        dishDetailsViewModel.addToCartResponse().observe(this, new Observer<LoadResponse<Long>>() {
            @Override
            public void onChanged(LoadResponse<Long> longLoadResponse) {
                handleAddDishResponse(longLoadResponse);
            }
        });
    }

    private void subscribeOnDishLoadResponse() {

        dishDetailsViewModel.getDish().observe(this, new Observer<LoadResponse<Dish>>() {
            @Override
            public void onChanged(LoadResponse<Dish> dishLoadResponse) {
                handleDishLoadResponse(dishLoadResponse);
            }
        });
    }

    private void handleDishLoadResponse(LoadResponse<Dish> dishResponse) {
        if(dishResponse instanceof LoadingLoadResponse) {
            return;
        }

        if(dishResponse instanceof SuccessLoadResponse) {
            if(dishResponse.getResponse() != null) {
                Dish response = dishResponse.getResponse();
                dish = response;
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
            Toast.makeText(DishDetailsActivity.this, getString(R.string.error_dish_information_load), Toast.LENGTH_SHORT).show();
            return;
        }
    }

    private void handleAddDishResponse(LoadResponse<Long> dishResponse) {
        if(dishResponse instanceof LoadingLoadResponse) {
            return;
        }

        if(dishResponse instanceof SuccessLoadResponse) {
            finish();
            Toast.makeText(DishDetailsActivity.this, getString(R.string.success_add_dish_to_cart), Toast.LENGTH_SHORT).show();
            return;
        }

        if(dishResponse instanceof ErrorLoadResponse) {
            Toast.makeText(DishDetailsActivity.this, getString(R.string.error_add_dish_to_cart), Toast.LENGTH_SHORT).show();
            return;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
}