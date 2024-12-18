package com.example.bookstore.activity.admin;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookstore.R;
import com.example.bookstore.adapter.admin.DicountBookAdapter;
import com.example.bookstore.adapter.admin.ProductAdapter;
import com.example.bookstore.api.admin.DiscountApi;
import com.example.bookstore.dto.Order;
import com.example.bookstore.model.ApiResponse;
import com.example.bookstore.model.Discount;
import com.example.bookstore.retrofit.ApiService;
import com.example.bookstore.utils.DialogUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DiscountDetailActivity extends AppCompatActivity {
    RecyclerView recyclerViewProductListDiscount;
    DicountBookAdapter productAdapter;
    TextView tvStatus,tvDiscountPercent,tvDiscountName;
    ImageView backIcon;
    Button btnSave;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discount_detail);
        Bundle bundle = getIntent().getExtras();
        if (bundle == null) return;
        Discount order;
        order = (Discount) bundle.getSerializable("Object:", Discount.class);
        setControl();
        setData(order);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancelDiscount(order.getDiscountId());
            }
        });
        backIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getOnBackPressedDispatcher().onBackPressed();
            }
        });

    }

    private void cancelDiscount(Integer id) {
        ApiService apiService = new ApiService();
        DiscountApi discountApi = apiService.getRetrofit().create(DiscountApi.class);
        discountApi.cancelDiscount(id).enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ApiResponse apiResponse = response.body();
                    DialogUtils.showSuccessDialog(DiscountDetailActivity.this, apiResponse.getMessage());
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable throwable) {

            }
        });
    }

    private void setData(Discount discount) {
        tvDiscountName.setText(discount.getDiscountName());
        tvDiscountPercent.setText(String.valueOf(discount.getDiscountPercent()+ "% OFF") ) ;
        tvStatus.setText(String.valueOf(discount.getStatus()));
        backIcon = findViewById(R.id.back_icon);
        productAdapter = new DicountBookAdapter(this, discount.getBooks(), discount.getDiscountPercent());
        recyclerViewProductListDiscount.setAdapter(productAdapter);

    }

    private void setControl() {
        recyclerViewProductListDiscount = findViewById(R.id.recyclerViewProductList);
        recyclerViewProductListDiscount.setLayoutManager(new LinearLayoutManager(this));
        tvStatus = findViewById(R.id.tvStatus);
        tvDiscountPercent = findViewById(R.id.tvDiscountPercent);
        tvDiscountName = findViewById(R.id.tvDiscountName);
        backIcon = findViewById(R.id.back_icon);
        btnSave = findViewById(R.id.btnSave);
    }
}