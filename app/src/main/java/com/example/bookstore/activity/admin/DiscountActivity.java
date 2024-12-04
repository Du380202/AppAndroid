package com.example.bookstore.activity.admin;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookstore.R;
import com.example.bookstore.adapter.admin.BookAdapter;
import com.example.bookstore.adapter.admin.DiscountAdapter;
import com.example.bookstore.api.admin.BookApi;
import com.example.bookstore.api.admin.DiscountApi;
import com.example.bookstore.dto.DiscountDto;
import com.example.bookstore.model.Book;
import com.example.bookstore.model.Discount;
import com.example.bookstore.retrofit.ApiService;
import com.example.bookstore.support.DialogCallBack;
import com.example.bookstore.support.OnSelectionCompleteListener;
import com.example.bookstore.utils.DialogUtils;
import com.example.bookstore.utils.HttpCodeUtils;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DiscountActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    FloatingActionButton addNewDiscount;
    ImageView backIcon;
    DiscountAdapter discountAdapter;
    List<Discount> discounts = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discount);
        setControl();
        getData();
        addNewDiscount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DiscountActivity.this, AddDiscountActivity.class);
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

    private void getData() {
        ApiService apiService = new ApiService();
        DiscountApi discountApi = apiService.getRetrofit().create(DiscountApi.class);
        discountApi.getAll().enqueue(new Callback<List<Discount>>() {
            @Override
            public void onResponse(Call<List<Discount>> call, Response<List<Discount>> response) {
                discounts = response.body();
                discountAdapter = new DiscountAdapter(DiscountActivity.this, discounts);
                recyclerView.setAdapter(discountAdapter);
            }

            @Override
            public void onFailure(Call<List<Discount>> call, Throwable throwable) {

            }
        });
    }

    private void setControl() {
        recyclerView = findViewById(R.id.recyclerBook);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        addNewDiscount = findViewById(R.id.addNewDiscount);
        backIcon = findViewById(R.id.back_icon);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == HttpCodeUtils.REQUEST_SUCCESS && resultCode == RESULT_OK) {
            getData();
        }
    }

}