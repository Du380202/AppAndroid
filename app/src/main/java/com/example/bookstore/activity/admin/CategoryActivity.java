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
import com.example.bookstore.adapter.admin.CategoryAdapter;
import com.example.bookstore.dto.Category;

import java.util.ArrayList;
import java.util.List;

public class CategoryActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    List<Category> categoryList;
    CategoryAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        recyclerView = findViewById(R.id.recyclerCategory);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        categoryList = new ArrayList<>();
        categoryList.add(new Category(R.drawable.img_6, "Huyền bí" , 15));
        categoryList.add(new Category( R.drawable.img_7,"Giả tưởng", 30));
        categoryList.add(new Category( R.drawable.img_8, "Tiểu thuyết",20));

        adapter = new CategoryAdapter(categoryList, this);
        recyclerView.setAdapter(adapter);
    }
}