package com.example.f21g3_smartgroceryshopping.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.f21g3_smartgroceryshopping.DishDetailsActivity;
import com.example.f21g3_smartgroceryshopping.R;
import com.example.f21g3_smartgroceryshopping.service.entity.Dish;

import java.util.ArrayList;
import java.util.List;

public class DishRecyclerViewAdapter extends RecyclerView.Adapter<DishRecyclerViewAdapter.DishViewHolder> {
    private List<Dish> DishList;

    private OnDishClickListener onDishClickListener;

    public DishRecyclerViewAdapter(OnDishClickListener onDishClickListener) {
       this.onDishClickListener = onDishClickListener;
       DishList = new ArrayList<>();
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
        String imageUrl = DishList.get(position).getImageUrl();

        Glide.with(dishViewHolder.dishPic.getContext())
                .load(imageUrl)
                .override(dishViewHolder.dishPic.getWidth(), dishViewHolder.dishPic.getHeight())
                .error(R.drawable.error_loand_image)
                .placeholder(R.drawable.error_loand_image)
                .into(dishViewHolder.dishPic);


        /*dishViewHolder.itemView.setOnClickListener((View view) -> {
        // open another activity on item click
            DishDetailsActivity.launch(dishViewHolder.dishPic.getContext(), position);
        });*/
    }

    @Override
    public int getItemCount() {
        return DishList.size();
    }

    protected class DishViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private CardView cardViewMeal;
        private TextView dishName;
        private TextView dishDesc;
        private ImageView dishPic;

        public DishViewHolder(@NonNull View itemView) {
            super(itemView);
            cardViewMeal = itemView.findViewById(R.id.cardViewMeal);
            dishName = itemView.findViewById(R.id.textViewDishName);
            dishDesc = itemView.findViewById(R.id.textViewDishDesc);
            dishPic = itemView.findViewById(R.id.imageViewDish);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onDishClickListener.onDishClick(DishList.get(getBindingAdapterPosition()));
        }
    }

    public void clear() {
        DishList.clear();
        notifyDataSetChanged();
    }

    public void addAll(List<Dish> list) {
        DishList.clear();
        DishList.addAll(list);
        notifyDataSetChanged();
    }

    public interface OnDishClickListener{
        void onDishClick(Dish dish);
    }

}