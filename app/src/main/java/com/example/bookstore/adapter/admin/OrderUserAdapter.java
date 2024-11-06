package com.example.bookstore.adapter.admin;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import android.widget.TextView;

import com.example.bookstore.R;
import com.example.bookstore.dto.Order;

import java.util.List;

public class OrderUserAdapter extends RecyclerView.Adapter<OrderUserAdapter.OrderUserViewHolder> {
    private List<Order> orderList;

    // Constructor để truyền vào danh sách đơn hàng
    public OrderUserAdapter(List<Order> orderList) {
        this.orderList = orderList;
    }

    @NonNull
    @Override
    public OrderUserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_item_user, parent, false);
        return new OrderUserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderUserViewHolder holder, int position) {
        Order order = orderList.get(position);
        holder.tvOrderId.setText("Mã đơn hàng: " + order.getOrderId());
        holder.tvOrderDate.setText("Ngày đặt: " + order.getNgaydat());
        holder.tvOrderStatus.setText("Trạng thái: " + order.getOrderStatus());
        holder.tvOrderTotal.setText("Tổng giá: " + order.getTotal());
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    // ViewHolder để ánh xạ các thành phần giao diện trong item_order.xml
    public static class OrderUserViewHolder extends RecyclerView.ViewHolder {
        TextView tvOrderId, tvOrderDate, tvOrderStatus, tvOrderTotal;

        public OrderUserViewHolder(@NonNull View itemView) {
            super(itemView);
            tvOrderId = itemView.findViewById(R.id.tvOrderId);
            tvOrderDate = itemView.findViewById(R.id.tvOrderDate);
            tvOrderStatus = itemView.findViewById(R.id.tvOrderStatus);
            tvOrderTotal = itemView.findViewById(R.id.tvOrderTotal);
        }
    }
}