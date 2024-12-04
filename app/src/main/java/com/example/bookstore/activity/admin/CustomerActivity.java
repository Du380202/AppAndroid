package com.example.bookstore.activity.admin;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookstore.R;
import com.example.bookstore.adapter.admin.CustomerAdapter;
import com.example.bookstore.api.admin.UserApi;
import com.example.bookstore.dto.UserDto;
import com.example.bookstore.model.User;
import com.example.bookstore.retrofit.ApiService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CustomerActivity extends AppCompatActivity {

    private RecyclerView recyclerViewCustomerList;
    private CustomerAdapter customerAdapter;
    private List<User> customerList = new ArrayList<>();
    ImageView backIcon;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer);
        setControl();
        getData();
        backIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getOnBackPressedDispatcher().onBackPressed();
            }
        });

    }

    private void setControl() {
        recyclerViewCustomerList = findViewById(R.id.recyclerCustomer);
        recyclerViewCustomerList.setLayoutManager(new LinearLayoutManager(this));
        backIcon = findViewById(R.id.back_icon);
    }

    private void getData() {
        ApiService apiService = new ApiService();
        UserApi userApi = apiService.getRetrofit().create(UserApi.class);
        userApi.getAllUser().enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                customerList = response.body();
                customerAdapter = new CustomerAdapter(customerList, CustomerActivity.this);
                recyclerViewCustomerList.setAdapter(customerAdapter);
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable throwable) {

            }
        });
    }
}