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
import com.example.bookstore.adapter.admin.CustomerAdapter;
import com.example.bookstore.dto.User;

import java.util.ArrayList;
import java.util.List;

public class CustomerActivity extends AppCompatActivity {

    private RecyclerView recyclerViewCustomerList;
    private CustomerAdapter customerAdapter;
    private List<User> customerList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer);
        recyclerViewCustomerList = findViewById(R.id.recyclerCustomer);
        recyclerViewCustomerList.setLayoutManager(new LinearLayoutManager(this));
        // Khởi tạo RecyclerView và Adapter

        customerList = new ArrayList<>();

        // Thêm dữ liệu mẫu vào danh sách khách hàng
        customerList.add(new User(R.drawable.user,"Nguyễn Văn A", "0123456789"));
        customerList.add(new User(R.drawable.user, "Trần Thị B", "0987654321" ));
        // Thêm nhiều khách hàng khác...

        customerAdapter = new CustomerAdapter(customerList, this);
        recyclerViewCustomerList.setAdapter(customerAdapter);
    }
}