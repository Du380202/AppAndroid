package com.example.bookstore.adapter.admin;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.widget.TextView;

import com.example.bookstore.R;
import com.example.bookstore.dto.Order;
import com.example.bookstore.dto.OrderDetail;
import com.google.gson.Gson;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.List;

public class OrderUserAdapter extends RecyclerView.Adapter<OrderUserAdapter.OrderUserViewHolder> {
    private List<Order> orderList;
    private RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();
    Context context;

    public OrderUserAdapter(List<Order> orderList, Context context) {
        this.orderList = orderList;
        this.context = context;
    }

    @NonNull
    @Override
    public OrderUserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_don_hang, parent, false);
        return new OrderUserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderUserViewHolder holder, int position) {
        Order donHang = orderList.get(position);
        DecimalFormat dft = new DecimalFormat("###,###,###");
        Log.d("OrderId", donHang.getOrderId()+"");
        int totalQuantity = 0;
        BigDecimal totalPrice = BigDecimal.ZERO;
        for (OrderDetail orderDetail : donHang.getOrderDetails())
        {
            totalQuantity += orderDetail.getQuantity();
            totalPrice = totalPrice.add(orderDetail.getTotalPrice());
        }
        holder.txtTotalItems.setText(String.format("%d sản phẩm", totalQuantity));
        holder.txtTotalcost.setText(String.format("Thành tiền: đ%s", totalPrice));
        if(donHang.getStatus() == 0){
            holder.txtStatus.setText("Chờ xác nhận");
        }else if(donHang.getStatus() == 1){
            holder.txtStatus.setText("Đang giao");
        }else if(donHang.getStatus() == 2){
            holder.txtStatus.setText("Đã giao");
        }else if(donHang.getStatus() == 3){
            holder.txtStatus.setText("Đã hủy");
        }
        LinearLayoutManager layoutManager = new LinearLayoutManager(
                holder.recyclerViewchitiet.getContext(),
                LinearLayoutManager.VERTICAL, false
        );
        layoutManager.setInitialPrefetchItemCount(donHang.getOrderDetails().size());

        ProductAdapter chiTietDonHangAdapter = new ProductAdapter(context, donHang.getOrderDetails());
        holder.recyclerViewchitiet.setLayoutManager(layoutManager);
        holder.recyclerViewchitiet.setAdapter(chiTietDonHangAdapter);
        holder.recyclerViewchitiet.setRecycledViewPool(viewPool);

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, ProductAdapter.class);
            intent.putExtra("orderId", donHang.getOrderId());
            intent.putExtra("totalPrice", donHang.getTotalPrice().toString());
            intent.putExtra("status", donHang.getStatus());
            intent.putExtra("orderDetails", new Gson().toJson(donHang.getOrderDetails()));
            intent.putExtra("fullName", donHang.getFullName());
            intent.putExtra("address", donHang.getAddress());
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    public static class OrderUserViewHolder extends RecyclerView.ViewHolder {
        TextView txtdonhang, txtStatus, txtTotalItems, txtTotalcost;
        RecyclerView recyclerViewchitiet;

        public OrderUserViewHolder(@NonNull View itemView) {
            super(itemView);
            txtdonhang = itemView.findViewById(R.id.iddonhang);
            txtStatus = itemView.findViewById(R.id.trangThai);
            txtTotalItems = itemView.findViewById(R.id.totalItems);
            txtTotalcost = itemView.findViewById(R.id.totalCost);
            recyclerViewchitiet = itemView.findViewById(R.id.recyclerview_chitiet);
        }
    }
}