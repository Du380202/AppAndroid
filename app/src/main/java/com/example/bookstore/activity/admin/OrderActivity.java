package com.example.bookstore.activity.admin;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookstore.R;
import com.example.bookstore.adapter.admin.OrderAdapter;
import com.example.bookstore.dto.Order;

import java.util.ArrayList;
import java.util.List;

public class OrderActivity extends AppCompatActivity {
    private RecyclerView orderRecyclerView;
    private OrderAdapter orderAdapter;
    private List<Order> orderList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        orderRecyclerView = findViewById(R.id.orderView);
        orderRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        orderList = new ArrayList<>();
        orderList.add(new Order("001", "Nguyễn Văn A", "0901234567", "123 đường ABC, TP.HCM", "Đang giao hàng", "1x Laptop Dell - 15,000,000 VND"));
        orderList.add(new Order("002", "Lê Thị B", "0909876543", "456 đường XYZ, Hà Nội fdbdf dgd gfbd fgbd ", "Đã giao hàng", "2x Điện thoại Samsung - 20,000,000 VND"));

        orderAdapter = new OrderAdapter(this, orderList);
        orderRecyclerView.setAdapter(orderAdapter);
    }
}