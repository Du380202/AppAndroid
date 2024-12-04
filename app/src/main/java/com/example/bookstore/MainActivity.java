package com.example.bookstore;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.bookstore.activity.admin.AddBookActivity;
import com.example.bookstore.activity.admin.AuthorActivity;
import com.example.bookstore.activity.admin.BookActivity;
import com.example.bookstore.activity.admin.CategoryActivity;
import com.example.bookstore.activity.admin.ChartActivity;
import com.example.bookstore.activity.admin.CustomerActivity;
import com.example.bookstore.activity.admin.DiscountActivity;
import com.example.bookstore.activity.admin.OrderActivity;
import com.example.bookstore.activity.admin.PublisherActivity;

public class MainActivity extends AppCompatActivity {
    CardView cardAuthor, cardBook, cardCategory, cardChart, cardPublisher, cardCustomer, cardOrder, cardDiscount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setControl();
        setEvent();
    }

    private void setEvent() {
        cardBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, BookActivity.class);
                startActivity(intent);
            }
        });

        cardPublisher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, PublisherActivity.class);
                startActivity(intent);
            }
        });

        cardCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CustomerActivity.class);
                startActivity(intent);
            }
        });

        cardAuthor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AuthorActivity.class);
                startActivity(intent);
            }
        });
        cardCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CategoryActivity.class);
                startActivity(intent);
            }
        });

        cardChart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ChartActivity.class);
                startActivity(intent);
            }
        });

        cardOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, OrderActivity.class);
                startActivity(intent);
            }
        });

        cardDiscount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, DiscountActivity.class);
                startActivity(intent);
            }
        });
    }

    private void setControl() {
        cardAuthor = findViewById(R.id.cardAuthor);
        cardBook = findViewById(R.id.cardBook);
        cardCategory = findViewById(R.id.cardCategory);
        cardCustomer = findViewById(R.id.cardCustomer);
        cardChart = findViewById(R.id.cardChart);
        cardPublisher = findViewById(R.id.cardPublisher);
        cardOrder = findViewById(R.id.cardOrder);
        cardDiscount = findViewById(R.id.cardDiscount);
    }


}