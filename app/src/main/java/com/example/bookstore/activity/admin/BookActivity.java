package com.example.bookstore.activity.admin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookstore.R;
import com.example.bookstore.adapter.admin.BookAdapter;
import com.example.bookstore.api.admin.BookApi;
import com.example.bookstore.dto.BookDto;
import com.example.bookstore.model.Book;
import com.example.bookstore.retrofit.ApiService;
import com.example.bookstore.utils.HttpCodeUtils;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    FloatingActionButton addNewBook;
    ImageView backIcon;
    BookAdapter bookAdapter;
    List<Book> bookList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);
        setControl();
        getData();

        addNewBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BookActivity.this, AddBookActivity.class);
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
        recyclerView = findViewById(R.id.recyclerBook);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        addNewBook = findViewById(R.id.addNewBook);
        backIcon = findViewById(R.id.back_icon);
    }

    private void getData() {
        ApiService apiService = new ApiService();
        BookApi bookApi = apiService.getRetrofit().create(BookApi.class);
        bookApi.getAllBook().enqueue(new Callback<List<Book>>() {
            @Override
            public void onResponse(Call<List<Book>> call, Response<List<Book>> response) {
                bookList = (List<Book>) response.body();
                bookAdapter = new BookAdapter(bookList, BookActivity.this);
                recyclerView.setAdapter(bookAdapter);
            }

            @Override
            public void onFailure(Call<List<Book>> call, Throwable throwable) {

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