package com.example.f21g3_smartgroceryshopping.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.f21g3_smartgroceryshopping.R;
import com.example.f21g3_smartgroceryshopping.service.entity.CartItem;

import java.util.ArrayList;
import java.util.List;

public class CartDishRecyclerViewAdapter extends RecyclerView.Adapter<CartDishRecyclerViewAdapter.CartDishViewHolder> {

    private List<CartItem> CartItemList;
    private OnCartDishDeleteClickListener onCartDishDeleteClickListener;
    private OnCartDishUpdateClickListener onCartDishUpdateClickListener;

    public CartDishRecyclerViewAdapter(OnCartDishDeleteClickListener onCartDishDeleteClickListener, OnCartDishUpdateClickListener onCartDishUpdateClickListener) {
        CartItemList = new ArrayList<>();
        this.onCartDishDeleteClickListener = onCartDishDeleteClickListener;
        this.onCartDishUpdateClickListener = onCartDishUpdateClickListener;
    }

    @NonNull
    @Override
    public CartDishViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_dish_cart_item, parent, false);
        return new CartDishViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartDishViewHolder holder, int position) {
        holder.dishName.setText(CartItemList.get(position).getDish().getTitle());
        holder.numOfDishes.setText(String.valueOf(CartItemList.get(position).getPortions()));
    }

    @Override
    public int getItemCount() {
        return CartItemList.size();
    }

    protected class CartDishViewHolder extends RecyclerView.ViewHolder {

        private TextView dishName;
        private EditText numOfDishes;
        private Button btnUpdate;
        private ImageView imgCancel;

        public CartDishViewHolder(@NonNull View itemView) {
            super(itemView);
            dishName = itemView.findViewById(R.id.txtViewDishName);
            numOfDishes = itemView.findViewById(R.id.editTextNumOfDishes);
            btnUpdate = itemView.findViewById(R.id.btnUpdate);
            imgCancel = itemView.findViewById(R.id.imgViewCancel);
            btnUpdate.setOnClickListener((View view) -> {
                    onCartDishUpdateClickListener.onCartDishClickUpdate(CartItemList.get(getBindingAdapterPosition()),
                            Integer.parseInt(numOfDishes.getText().toString()));

            });

            imgCancel.setOnClickListener((View view) -> {
                    onCartDishDeleteClickListener.onCartDishClickDelete(CartItemList.get(getBindingAdapterPosition()));

            });
        }
    }
    public void addAll(List<CartItem> list) {
        CartItemList.clear();
        CartItemList.addAll(list);
        notifyDataSetChanged();
    }

    public interface OnCartDishDeleteClickListener{
        void onCartDishClickDelete(CartItem cartItem);
    }

    public interface OnCartDishUpdateClickListener{
        void onCartDishClickUpdate(CartItem cartItem, int portions);
    }

}
