package com.gadget_mart.admin.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gadget_mart.admin.R;
import com.gadget_mart.admin.model.OrderModel;

import java.util.List;

public class MainOrderAdapter extends RecyclerView.Adapter<MainOrderAdapter.OrdersHolder> {

    private Context context;
    private List<OrderModel> ordersList;

    public MainOrderAdapter(Context context, List<OrderModel> ordersList) {
        this.context = context;
        this.ordersList = ordersList;
    }

    @NonNull
    @Override
    public OrdersHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_custom_layout, parent, false);
        return new OrdersHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrdersHolder holder, int position) {
        holder.bind(ordersList.get(position));
    }

    @Override
    public int getItemCount() {
        return ordersList.size();
    }

    public interface OnItemClickListener {
        void onItemClick(OrderModel orders);
    }

    public static class OrdersHolder extends RecyclerView.ViewHolder {

        public OrdersHolder(@NonNull View itemView) {
            super(itemView);
        }

        public void bind(final OrderModel orders) {

        }
    }
}
