package com.example.bookstore.activity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookstore.R;
import com.example.bookstore.adapter.CartItemAdapter;
import com.example.bookstore.dto.AddressOrder;
import com.example.bookstore.dto.CartItem;

import java.util.ArrayList;

public class CartActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private CartItemAdapter cartItemAdapter;
    private TextView tvSelectedAddress;

    ArrayList<AddressOrder> addressOrders = new ArrayList<>();
    ArrayList<CartItem> cartItems = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        recyclerView = findViewById(R.id.cartView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        addressOrders.add(new AddressOrder(1, "123 Main St, City A", "123-456-7890"));
        addressOrders.add(new AddressOrder(2, "456 Oak Dr, City B", "234-567-8901"));
        addressOrders.add(new AddressOrder(3, "789 Pine Ln, City C", "345-678-9012"));

        cartItems.add(new CartItem(R.drawable.img, "Nhà giả kim", 15.00, 1, 15.00));
        cartItems.add(new CartItem(R.drawable.img_1,"Đắc nhân tâm", 20.00, 2, 40.00));

        tvSelectedAddress = findViewById(R.id.tvSelectedAddress);
        tvSelectedAddress.setOnClickListener(view -> showAddressSelectionDialog());
        System.out.println(cartItems.size());
        cartItemAdapter = new CartItemAdapter(CartActivity.this, cartItems);
        recyclerView.setAdapter(cartItemAdapter);
    }

    private void showAddressSelectionDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select Delivery Address");

        // Tạo danh sách địa chỉ dưới dạng chuỗi để hiển thị trong AlertDialog
        String[] addressStrings = new String[addressOrders.size()];
        for (int i = 0; i < addressOrders.size(); i++) {
            addressStrings[i] = addressOrders.get(i).toString();
        }

        builder.setItems(addressStrings, (dialog, which) -> {
            // Lấy địa chỉ được chọn và hiển thị lên TextView
            AddressOrder selectedAddress = addressOrders.get(which);
            tvSelectedAddress.setText(selectedAddress.getAddress() + "\n" + selectedAddress.getPhoneNumber());
        });

        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());
        builder.show();
    }
}