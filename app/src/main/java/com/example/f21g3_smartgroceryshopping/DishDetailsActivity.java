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
    private Toolbar toolbarDishDetails;
    private FloatingActionButton fabAddDish;
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
            //check if clicked item in toolbar is cart icon and proceed to CartActivity
            if (item.getItemId() == R.id.iconToolbarCart) {
                transferToCartActivity();
            }
            return true;
        });

        //close the current activity when arrow icon in toolbar is clicked
        toolbarDishDetails.setNavigationOnClickListener((View view) -> {
            finish();
        });

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

        //do not trigger data loading if the device was rotated
        if (savedInstanceState == null) {
            dishDetailsViewModel.loadDish(dishId);
        }
    }

    private void transferToCartActivity() {
        //proceed to CartActivity only if cart is not empty
        if (getCartSize() != 0) {
            CartActivity.launch(DishDetailsActivity.this);
            finish();
        } else {
            Toast.makeText(DishDetailsActivity.this,
                            getString(R.string.error_empty_car), Toast.LENGTH_LONG).show();
        }
    }

    private int getCartSize() {
        try {
            return Integer.parseInt(textViewCartSize.getText().toString());
        } catch (Exception e) {
            return 0;
        }
    }

    public static void launch(Context context, int dishId) {
        Intent intent = new Intent(context, DishDetailsActivity.class);
        intent.putExtra(DISH_ID, dishId);
        context.startActivity(intent);
    }

    //method to subscribe on the event, which accepts the result of LiveData object with the number of portions
    private void subscribeOnDishPortions() {
        //update the value of NumberPicker
        dishDetailsViewModel.getDishPortionsNumberResponse().observe(this, integerLoadResponse -> {
            if(integerLoadResponse.getResponse() != null && integerLoadResponse.getResponse() > 0) {
                numberOfPortionsPicker.setValue(integerLoadResponse.getResponse());
            }
        });
    }

    //method to subscribe on the event, which accepts the result of LiveData object with the size of cart
    private void subscribeOnGetCartSizeResponse() {
        //update the cart size TextView in toolbar
        dishDetailsViewModel.getCartSize().observe(this, cartSize -> {
            if(cartSize == 0) {
                textViewCartSize.setText(R.string.empty_cart);
            } else {
                textViewCartSize.setText(String.valueOf(cartSize));
            }
        });
    }

    private void subscribeOnAddDishResponse() {
        dishDetailsViewModel.addToCartResponse().observe(this, longLoadResponse ->
                handleAddDishResponse(longLoadResponse));
    }

    private void subscribeOnDishLoadResponse() {
        dishDetailsViewModel.getDish().observe(this, dishLoadResponse ->
                handleDishLoadResponse(dishLoadResponse));
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
            Toast.makeText(DishDetailsActivity.this,
                            getString(R.string.error_dish_information_load), Toast.LENGTH_SHORT).show();
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