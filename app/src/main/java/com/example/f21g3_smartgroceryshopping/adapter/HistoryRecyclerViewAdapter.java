package com.example.f21g3_smartgroceryshopping.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.f21g3_smartgroceryshopping.R;
import com.example.f21g3_smartgroceryshopping.service.entity.CartItem;
import com.example.f21g3_smartgroceryshopping.service.entity.Order;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class HistoryRecyclerViewAdapter extends RecyclerView.Adapter<HistoryRecyclerViewAdapter.HistoryViewHolder> {

    private List<Order> OrdersList;

    public HistoryRecyclerViewAdapter(List<Order> ordersList) {
        OrdersList = ordersList;
    }

    @NonNull
    @Override
    public HistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_history_item, parent, false);
        return new HistoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryViewHolder holder, int position) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        holder.dishTitle.setText(OrdersList.get(position).getOrderItems().get(position).getDishTitle());
        holder.date.setText(dateFormat.format(OrdersList.get(position).getOrderDate()));
    }

    @Override
    public int getItemCount() {
        return OrdersList.size();
    }

    protected class HistoryViewHolder extends RecyclerView.ViewHolder {
        private TextView dishTitle;
        private TextView date;

        public HistoryViewHolder(@NonNull View itemView) {
            super(itemView);
            dishTitle = itemView.findViewById(R.id.txtViewDishTitle);
            date = itemView.findViewById(R.id.txtViewDate);
        }
    }
    public void addAll(List<Order> list) {
        OrdersList.clear();
        OrdersList.addAll(list);
        notifyDataSetChanged();
    }
}
