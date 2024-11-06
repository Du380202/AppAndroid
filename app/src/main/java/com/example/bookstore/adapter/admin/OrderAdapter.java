package com.example.bookstore.adapter.admin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookstore.R;
import com.example.bookstore.dto.Order;

import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {

    private Context context;
    private List<Order> orderList;

    public OrderAdapter(Context context, List<Order> orderList) {
        this.context = context;
        this.orderList = orderList;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_order_item, parent, false);
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        Order order = orderList.get(position);
        holder.tvOrderId.setText(order.getOrderId());
        holder.tvCustomerName.setText(order.getCustomerName());
        holder.tvPhoneNumber.setText(order.getPhoneNumber());
        holder.tvShippingAddress.setText(order.getShippingAddress());
        holder.tvOrderStatus.setText(order.getOrderStatus());
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    public static class OrderViewHolder extends RecyclerView.ViewHolder {
        TextView tvOrderId, tvCustomerName, tvPhoneNumber, tvShippingAddress, tvOrderStatus, tvProducts;

        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            tvOrderId = itemView.findViewById(R.id.tvOrderId);
            tvCustomerName = itemView.findViewById(R.id.tvCustomerName);
            tvPhoneNumber = itemView.findViewById(R.id.tvPhoneNumber);
            tvShippingAddress = itemView.findViewById(R.id.tvShippingAddress);
            tvOrderStatus = itemView.findViewById(R.id.tvOrderStatus);
        }
    }
}
