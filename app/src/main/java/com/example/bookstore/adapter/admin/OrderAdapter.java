package com.example.bookstore.adapter.admin;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookstore.R;
import com.example.bookstore.activity.admin.CategoryActivity;
import com.example.bookstore.activity.admin.EditCategoryActivity;
import com.example.bookstore.activity.admin.OrderActivity;
import com.example.bookstore.activity.admin.ViewOrderActivity;
import com.example.bookstore.dto.Order;
import com.example.bookstore.model.Category;
import com.example.bookstore.utils.HttpCodeUtils;

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
//        holder.tvOrderId.setText(order.getOrderId());
        holder.tvCustomerName.setText(order.getFullName());
        holder.tvPhoneNumber.setText(order.getEmail());
        holder.tvShippingAddress.setText(order.getAddress());
        if(order.getStatus() == 0) {
            holder.tvOrderStatus.setText("Chờ xác nhận");
            holder.tvOrderStatus.setTextColor(Color.BLACK);
        }
        if(order.getStatus() == 1) {
            holder.tvOrderStatus.setText("Đang giao");
            holder.tvOrderStatus.setTextColor(Color.YELLOW);
        }
        if(order.getStatus() == 2) {
            holder.tvOrderStatus.setText("Hoàn thành");
            holder.tvOrderStatus.setTextColor(Color.GREEN);
        }
        if(order.getStatus() == 3) {
            holder.tvOrderStatus.setText("Đã hủy");
            holder.tvOrderStatus.setTextColor(Color.RED);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("LogOrder", "Dax clcik");
                onclickDetail(order);
            }
        });

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

    private void onclickDetail(Order order) {
        Log.d("Clcik", "sjfnskjf");
        Intent detail = new Intent(context, ViewOrderActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("Object:" , order);
        detail.putExtras(bundle);
        ((OrderActivity) context).startActivityForResult(detail, HttpCodeUtils.REQUEST_SUCCESS);
    }
}
