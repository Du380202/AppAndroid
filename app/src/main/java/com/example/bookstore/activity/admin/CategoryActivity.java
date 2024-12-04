package com.example.bookstore.activity.admin;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookstore.R;
import com.example.bookstore.adapter.admin.CategoryAdapter;
import com.example.bookstore.api.admin.AuthorApi;
import com.example.bookstore.api.admin.CategoryApi;
import com.example.bookstore.dto.Order;
import com.example.bookstore.model.Category;
import com.example.bookstore.retrofit.ApiService;
import com.example.bookstore.utils.HttpCodeUtils;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    List<Category> categoryList = new ArrayList<>();
    CategoryAdapter adapter;
    ImageView backIcon ;
    FloatingActionButton addNewCategory;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        setControl();
        getData();
        addNewCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CategoryActivity.this, AddCategoryActivity.class);
                startActivityForResult(intent, HttpCodeUtils.REQUEST_SUCCESS);
            }
        });

        backIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getOnBackPressedDispatcher().onBackPressed();
            }
        });
    }

    private void setControl() {
        recyclerView = findViewById(R.id.recyclerCategory);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        addNewCategory = findViewById(R.id.addNewCategory);
        backIcon = findViewById(R.id.back_icon);
    }

    private void getData() {
        ApiService apiService = new ApiService();
        CategoryApi categoryApi = apiService.getRetrofit().create(CategoryApi.class);
        categoryApi.getAllCategory().enqueue(new Callback<List<Category>>() {
            @Override
            public void onResponse(@NonNull Call<List<Category>> call, @NonNull Response<List<Category>> response) {
                categoryList = response.body();
                adapter = new CategoryAdapter(categoryList, CategoryActivity.this);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(@NonNull Call<List<Category>> call, @NonNull Throwable throwable) {
                Log.e("LogE", Objects.requireNonNull(throwable.getMessage()));
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == HttpCodeUtils.REQUEST_SUCCESS && resultCode == RESULT_OK) {
            getData();
        }
    }

}