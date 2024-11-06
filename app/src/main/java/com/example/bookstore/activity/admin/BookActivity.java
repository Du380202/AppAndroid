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
import com.example.bookstore.adapter.admin.BookAdapter;
import com.example.bookstore.dto.Book;

import java.util.ArrayList;
import java.util.List;

public class BookActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    BookAdapter bookAdapter;
    List<Book> bookList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);
        recyclerView = findViewById(R.id.recyclerTask);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Create book data
        bookList = new ArrayList<>();
        bookList.add(new Book("Nhà giả kim", "Paulo Coelho", "$15", "Còn hàng", R.drawable.img));
        bookList.add(new Book("Đắc nhân tâm", "Dale Carnegie", "$15", "Hết hàng", R.drawable.img_1));
        bookList.add(new Book("Thao túng tâm lý", "Shannon Thomas", "$20", "Còn hàng", R.drawable.img_2));

        // Set adapter
        bookAdapter = new BookAdapter(bookList, this);
        recyclerView.setAdapter(bookAdapter);


    }
}