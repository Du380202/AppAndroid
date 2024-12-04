package com.example.bookstore.activity.admin;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.bookstore.R;
import com.example.bookstore.adapter.admin.OrderAdapter;
import com.example.bookstore.adapter.admin.OrderUserAdapter;
import com.example.bookstore.api.admin.OrderApi;
import com.example.bookstore.dto.Order;
import com.example.bookstore.model.Category;
import com.example.bookstore.model.User;
import com.example.bookstore.retrofit.ApiService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CustomerDetailActivity extends AppCompatActivity {
    private RecyclerView recyclerViewOrderList;
    private OrderUserAdapter orderAdapter;
    private List<Order> orderList = new ArrayList<>();
    TextView tvCustomerName,tvCustomerPhone,tvCustomerEmail;
    ImageView customerAvatar, backIcon;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_detail);

        Bundle bundle = getIntent().getExtras();
        if (bundle == null) return;
        User user;
        user = (User) bundle.getSerializable("Object:", User.class);
        setControl();
//        assert user != null;
        getOrderByUser(user.getUserId());
        setDataInView(user);
        backIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getOnBackPressedDispatcher().onBackPressed();
            }
        });

    }

    private void setDataInView(User user) {
        tvCustomerName.setText(user.getFullName());
        tvCustomerEmail.setText(user.getEmail());
        tvCustomerPhone.setText(user.getPhoneNumber());
        Glide.with(CustomerDetailActivity.this)
                .load(user.getAvatar())
                .into(customerAvatar);
    }

    private void setControl() {
        recyclerViewOrderList = findViewById(R.id.recyclerViewOrderList);
        recyclerViewOrderList.setLayoutManager(new LinearLayoutManager(this));
        backIcon = findViewById(R.id.back_icon);
        tvCustomerPhone = findViewById(R.id.tvCustomerPhone);
        tvCustomerName = findViewById(R.id.tvCustomerName);
        tvCustomerEmail = findViewById(R.id.tvCustomerEmail);
        customerAvatar = findViewById(R.id.customerAvatar);
    }

    private void getOrderByUser(Integer userId) {
        ApiService apiService = new ApiService();
        OrderApi orderApi = apiService.getRetrofit().create(OrderApi.class);
        orderApi.getOrdersByUserId(userId).enqueue(new Callback<List<Order>>() {
            @Override
            public void onResponse(Call<List<Order>> call, Response<List<Order>> response) {
                orderList = response.body();
                orderAdapter = new OrderUserAdapter(orderList,CustomerDetailActivity.this  );
                recyclerViewOrderList.setAdapter(orderAdapter);
            }

            @Override
            public void onFailure(Call<List<Order>> call, Throwable throwable) {
                Log.e("LogCustomer", throwable.getMessage());
            }
        });
    }
}