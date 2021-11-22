package com.example.f21g3_smartgroceryshopping.adapter;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.f21g3_smartgroceryshopping.R;
import com.example.f21g3_smartgroceryshopping.service.entity.CartItem;
import com.example.f21g3_smartgroceryshopping.service.entity.Dish;
import com.example.f21g3_smartgroceryshopping.service.entity.Order;
import com.example.f21g3_smartgroceryshopping.service.entity.OrderItem;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class HistoryRecyclerViewAdapter extends RecyclerView.Adapter<HistoryRecyclerViewAdapter.HistoryViewHolder> {

    private List<Order> OrdersList;
    private List<OrderItem> OrderItemList;
    private OnOrderHistoryClickListener onOrderHistoryClickListener;


    public HistoryRecyclerViewAdapter(OnOrderHistoryClickListener onOrderHistoryClickListener) {
        this.onOrderHistoryClickListener = onOrderHistoryClickListener;
        OrdersList = new ArrayList<>();
        OrderItemList = new ArrayList<>();
    }

    @NonNull
    @Override
    public HistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_history_item, parent, false);
        return new HistoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryViewHolder holder, int position) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd", Locale.getDefault());
        List<OrderItem> list = OrdersList.get(position).getOrderItems();
        holder.dishTitle.setText(combineDishTitles(list));
        holder.date.setText(dateFormat.format(OrdersList.get(position).getOrderDate()));
    }

    private String combineDishTitles(List<OrderItem> orderItems){
        StringBuilder str = new StringBuilder("");
        for(int i=0; i < orderItems.size(); i++){
            str.append(orderItems.get(i).getDishTitle()).append(", ");
            if(i>=2){
                break;
            }
        }
        if(str.length() > 0){
            str.setLength(str.length() - 2);
        }
        return str.toString();
    }

    @Override
    public int getItemCount() {
        return OrdersList.size();
    }

    protected class HistoryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView dishTitle;
        private TextView date;

        public HistoryViewHolder(@NonNull View itemView) {
            super(itemView);
            dishTitle = itemView.findViewById(R.id.txtViewDishTitle);
            date = itemView.findViewById(R.id.txtViewDate);
            itemView.setOnClickListener(this);
        }
        @Override
        public void onClick(View view) {
            onOrderHistoryClickListener.onOrderHistoryClick(OrdersList.get(getBindingAdapterPosition()));
        }
    }
    public void addAll(List<Order> list) {
        OrdersList.clear();
        OrdersList.addAll(list);
        notifyDataSetChanged();
    }

    public interface OnOrderHistoryClickListener{
        void onOrderHistoryClick(Order order);
    }



}
