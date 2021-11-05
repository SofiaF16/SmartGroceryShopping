package com.example.f21g3_smartgroceryshopping;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.f21g3_smartgroceryshopping.service.entity.Dish;

import java.util.List;

public class DishRecyclerViewAdapter extends RecyclerView.Adapter<DishRecyclerViewAdapter.DishViewHolder> {
    private List<Dish> DishList;

    public DishRecyclerViewAdapter(List<Dish> dishList) {
       DishList = dishList;
    }

    @NonNull
    @Override
    public DishViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_meal_item, parent, false);
        return new DishViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DishViewHolder dishViewHolder, int position) {
        dishViewHolder.dishName.setText(DishList.get(position).getTitle());
        dishViewHolder.dishDesc.setText(DishList.get(position).getShortDescription());
        Glide.with(dishViewHolder.dishPic.getContext())
                .load(DishList.get(position).getImageUrl())
                .override(dishViewHolder.dishPic.getWidth(), dishViewHolder.dishPic.getHeight())
                .error(R.drawable.salmon)
                .placeholder(R.drawable.salmon)
                .into(dishViewHolder.dishPic);
    }

    @Override
    public int getItemCount() {
        return DishList.size();
    }

    public class DishViewHolder extends RecyclerView.ViewHolder {
        CardView cardViewMeal;
        TextView dishName;
        TextView dishDesc;
        ImageView dishPic;

        public DishViewHolder(@NonNull View itemView) {
            super(itemView);
            cardViewMeal = itemView.findViewById(R.id.cardViewMeal);
            dishName = itemView.findViewById(R.id.textViewDishName);
            dishDesc = itemView.findViewById(R.id.textViewDishDesc);
            dishPic = itemView.findViewById(R.id.imageViewDish);
        }
    }

    public void clear() {
        DishList.clear();
        notifyDataSetChanged();
    }

    public void addAll(List<Dish> list) {
        DishList.addAll(list);
        notifyDataSetChanged();
    }

}