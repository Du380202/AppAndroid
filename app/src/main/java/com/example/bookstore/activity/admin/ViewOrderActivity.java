package com.example.bookstore.activity.admin;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookstore.R;
import com.example.bookstore.adapter.admin.ProductAdapter;
import com.example.bookstore.dto.Book;

import java.util.ArrayList;
import java.util.List;

public class ViewOrderActivity extends AppCompatActivity {
    RecyclerView recyclerViewProductList;
    ProductAdapter productAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_order);

        recyclerViewProductList = findViewById(R.id.recyclerViewProductList);
        recyclerViewProductList.setLayoutManager(new LinearLayoutManager(this));

        // Sample product list
        List<Book> bookList = new ArrayList<>();
        bookList.add(new Book("Nhà giả kim", "Paulo Coelho", "$15", "Còn hàng", R.drawable.img));
        bookList.add(new Book("Đắc nhân tâm", "Dale Carnegie", "$15", "Hết hàng", R.drawable.img_1));
        bookList.add(new Book("Thao túng tâm lý", "Shannon Thomas", "$20", "Còn hàng", R.drawable.img_2));

        // Set up the adapter
        productAdapter = new ProductAdapter(this, bookList);
        recyclerViewProductList.setAdapter(productAdapter);
    }
}
