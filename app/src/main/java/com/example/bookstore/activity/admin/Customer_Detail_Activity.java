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

public class Customer_Detail_Activity extends AppCompatActivity {
    private RecyclerView recyclerViewOrderList;
    private OrderAdapter orderAdapter;
    private List<Order> orderList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_detail);
        recyclerViewOrderList = findViewById(R.id.recyclerViewOrderList);
        recyclerViewOrderList.setLayoutManager(new LinearLayoutManager(this));
        // Tạo danh sách đơn hàng mẫu
        orderList = new ArrayList<>();
        orderList.add(new Order("001", "2024-10-01", "Đang giao hàng", "500,000 VND"));
        orderList.add(new Order("002", "2024-10-05", "Đã giao hàng", "300,000 VND"));
        orderList.add(new Order("003", "2024-10-10", "Đang xử lý", "250,000 VND"));

        // Thiết lập Adapter và LayoutManager cho RecyclerView
        orderAdapter = new OrderAdapter(this, orderList );
        recyclerViewOrderList.setAdapter(orderAdapter);
    }
}