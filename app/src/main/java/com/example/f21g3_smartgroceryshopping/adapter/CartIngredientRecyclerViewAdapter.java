package com.example.f21g3_smartgroceryshopping.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.f21g3_smartgroceryshopping.R;
import com.example.f21g3_smartgroceryshopping.service.entity.Dish;
import com.example.f21g3_smartgroceryshopping.service.entity.Ingredient;

import java.util.List;

public class CartIngredientRecyclerViewAdapter extends RecyclerView.Adapter<CartIngredientRecyclerViewAdapter.CartIngredientViewHolder>{

    private List<Ingredient>IngredientList;

    public CartIngredientRecyclerViewAdapter(List<Ingredient> ingredientList) {
        IngredientList = ingredientList;
    }



    @NonNull
    @Override
    public CartIngredientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_ingredient_cart_item, parent, false);
        return new CartIngredientViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartIngredientViewHolder holder, int position) {
        holder.ingredientQuantity.setText(IngredientList.get(position).getQuantity() + " " + IngredientList.get(position).getQuantityUnit());
        holder.ingredientTitle.setText(IngredientList.get(position).getTitle());
    }

    @Override
    public int getItemCount() {
        return IngredientList.size();
    }

    protected class CartIngredientViewHolder extends RecyclerView.ViewHolder {
        private TextView ingredientTitle;
        private TextView ingredientQuantity;

        public CartIngredientViewHolder(@NonNull View itemView) {
            super(itemView);
            ingredientTitle = itemView.findViewById(R.id.txtViewIngredientTitle);
            ingredientQuantity = itemView.findViewById(R.id.txtViewNumOfIngredients);
        }
    }
    public void addAll(List<Ingredient> list) {
        IngredientList.clear();
        IngredientList.addAll(list);
        notifyDataSetChanged();
    }

}
