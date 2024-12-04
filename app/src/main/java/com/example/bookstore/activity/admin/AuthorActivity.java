package com.example.bookstore.activity.admin;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookstore.R;
import com.example.bookstore.adapter.admin.AuthorAdapter;
import com.example.bookstore.api.admin.AuthorApi;
import com.example.bookstore.api.admin.CartApi;
import com.example.bookstore.model.Author;
import com.example.bookstore.model.Cart;
import com.example.bookstore.retrofit.ApiService;
import com.example.bookstore.utils.HttpCodeUtils;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AuthorActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private AuthorAdapter adapter;
    ImageView backIcon;
    private FloatingActionButton addNewAuthor;
    private List<com.example.bookstore.model.Author> authorList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_author);
        setControl();
        getData();

        addNewAuthor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AuthorActivity.this, AddAuthorActivity.class);
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
        recyclerView = findViewById(R.id.recyclerTask);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        addNewAuthor = findViewById(R.id.addNewAuthor);
        backIcon = findViewById(R.id.back_icon);

    }

    private void getData() {
        ApiService apiService = new ApiService();
        AuthorApi authorApi = apiService.getRetrofit().create(AuthorApi.class);
        authorApi.getAllAuthor().enqueue(new Callback<List<Author>>() {
            @Override
            public void onResponse(@NonNull Call<List<Author>> call, @NonNull Response<List<Author>> response) {
                authorList = response.body();
                int code = response.code();
                Log.e("HTTP code", code + "");
                adapter = new AuthorAdapter(authorList, AuthorActivity.this);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<Author>> call, Throwable throwable) {
                Log.e("LogE", throwable.getMessage());
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