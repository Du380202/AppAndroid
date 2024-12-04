package com.example.bookstore.activity.admin;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookstore.R;
import com.example.bookstore.adapter.admin.AuthorAdapter;
import com.example.bookstore.adapter.admin.BookAdapter;
import com.example.bookstore.adapter.admin.PublisherAdapter;
import com.example.bookstore.api.admin.PublisherApi;
import com.example.bookstore.model.Publisher;
import com.example.bookstore.retrofit.ApiService;
import com.example.bookstore.utils.HttpCodeUtils;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PublisherActivity extends AppCompatActivity {
    List<Publisher> publishers = new ArrayList<>();
    FloatingActionButton addNewPublisher;
    ImageView backIcon;
    private RecyclerView recyclerView;
    PublisherAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publisher);
        setControl();
        getDataPublisher();

        addNewPublisher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PublisherActivity.this, AddPublisherActivity.class);
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
        addNewPublisher = findViewById(R.id.addNewPublisher);
        backIcon = findViewById(R.id.back_icon);
    }

    private void getDataPublisher() {
        ApiService apiService = new ApiService();
        PublisherApi authorApi = apiService.getRetrofit().create(PublisherApi.class);
        authorApi.getAll().enqueue(new Callback<List<Publisher>>() {
            @Override
            public void onResponse(Call<List<Publisher>> call, Response<List<Publisher>> response) {
                publishers = response.body();
                Log.e("Sevfsdvsd", publishers.size()+"");
                adapter = new PublisherAdapter(publishers, PublisherActivity.this);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<Publisher>> call, Throwable throwable) {
                Log.e("LogPub", throwable.getMessage());
            }
        });
    }
}