package com.example.bookstore.activity.admin;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookstore.R;
import com.example.bookstore.adapter.admin.ProductAdapter;
import com.example.bookstore.api.admin.OrderApi;
import com.example.bookstore.dto.BookDto;
import com.example.bookstore.dto.Order;
import com.example.bookstore.model.ApiResponse;
import com.example.bookstore.model.Category;
import com.example.bookstore.retrofit.ApiService;
import com.example.bookstore.support.DialogCallBack;
import com.example.bookstore.utils.DialogUtils;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewOrderActivity extends AppCompatActivity {
    RecyclerView recyclerViewProductList;
    ProductAdapter productAdapter;
    TextView tvOrderStatus, tvShippingAddress, tvCustomerName;
    ImageView backIcon;
    Button btnSave;
    Spinner spinnerStatus;
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_order);
        Bundle bundle = getIntent().getExtras();
        if (bundle == null) return;
        Order order;
        order = (Order) bundle.getSerializable("Object:", Order.class);
        setControl();
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait ...");
        setData(order);
        backIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getOnBackPressedDispatcher().onBackPressed();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateStatus(order);
            }
        });


    }

    private void setControl() {
        recyclerViewProductList = findViewById(R.id.recyclerViewProductList);
        recyclerViewProductList.setLayoutManager(new LinearLayoutManager(this));
        tvCustomerName = findViewById(R.id.tvCustomerName);
        tvOrderStatus = findViewById(R.id.tvOrderStatus);
        tvShippingAddress = findViewById(R.id.tvShippingAddress);
        backIcon = findViewById(R.id.back_icon);
        btnSave = findViewById(R.id.btnSave);
        spinnerStatus = findViewById(R.id.spinnerOrderStatus);
    }

    private void setData(Order order) {
        tvCustomerName.setText(order.getFullName());
        tvShippingAddress.setText(order.getAddress());
        if (order.getStatus() == 3 || order.getStatus() == 2) {
            spinnerStatus.setEnabled(false);
            btnSave.setEnabled(false);
            btnSave.setAlpha(0.5f);
        } else {
            spinnerStatus.setSelection(order.getStatus());
        }

        if(order.getStatus() == 0) {
            tvOrderStatus.setText("Chờ xác nhận");
            tvOrderStatus.setTypeface(null, Typeface.BOLD);
            tvOrderStatus.setTextColor(Color.BLACK);
        }
        if(order.getStatus() == 1) {
            tvOrderStatus.setText("Đang giao");
            tvOrderStatus.setTypeface(null, Typeface.BOLD);
            tvOrderStatus.setTextColor(Color.YELLOW);
        }
        if(order.getStatus() == 2) {
           tvOrderStatus.setText("Hoàn thành");
            tvOrderStatus.setTypeface(null, Typeface.BOLD);
            tvOrderStatus.setTextColor(Color.GREEN);
        }
        if(order.getStatus() == 3) {
            tvOrderStatus.setText("Đã hủy");
            tvOrderStatus.setTypeface(null, Typeface.BOLD);
            tvOrderStatus.setTextColor(Color.RED);
            btnSave.setAlpha(0.5f);
        }
        productAdapter = new ProductAdapter(this, order.getOrderDetails());
        recyclerViewProductList.setAdapter(productAdapter);
    }

    private void updateStatus(Order order) {
        progressDialog.show();
        ApiService apiService = new ApiService();
        OrderApi orderApi = apiService.getRetrofit().create(OrderApi.class);
        orderApi.updateStatus(order.getOrderId(), getStatusNumber()).enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                progressDialog.dismiss();
                if (response.isSuccessful() && response.body() != null) {

                    ApiResponse apiResponse = response.body();
                    DialogUtils.showSuccessDialog(ViewOrderActivity.this, apiResponse.getMessage(), new DialogCallBack() {
                        @Override
                        public void onConfirm() {
                            setResult(Activity.RESULT_OK);
                            finish();
                        }
                    });
                } else {
                    try {
                        if (response.errorBody() != null) {
                            Gson gson = new Gson();
                            ApiResponse errorResponse = gson.fromJson(response.errorBody().string(), ApiResponse.class);
                            DialogUtils.showErrorDialog(ViewOrderActivity.this, errorResponse.getMessage());
                        } else {
                            Log.e("Error", "HTTP Status: " + response.code() + ", Message: Không có nội dung lỗi");
                        }
                    } catch (IOException e) {
                        Log.e("Error", e.getMessage(), e);
                    }
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable throwable) {

            }
        });
    }

    private int getStatusNumber() {
        String selectedStatus = spinnerStatus.getSelectedItem().toString();
        if (selectedStatus.equals("Chờ xác nhận")) {
            return 0;
        }
        if (selectedStatus.equals("Đang giao")) {
            return 1;
        }
        if(selectedStatus.equals("Đang giao hàng")) {
            return 4;
        }
        return 0;
    }
}
