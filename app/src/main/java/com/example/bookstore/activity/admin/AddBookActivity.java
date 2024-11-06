package com.example.bookstore.activity.admin;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bookstore.R;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Calendar;

public class AddBookActivity extends AppCompatActivity {

    private EditText edtBookTitle, edtQuantity, edtPrice, edtDescription, edtSaleDate;
    private AutoCompleteTextView autoCompleteTextViewAuthor, autoCompleteTextViewPublisher, autoCompleteTextViewGenre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);

        edtBookTitle = findViewById(R.id.edtBookTitle);
        edtQuantity = findViewById(R.id.edtQuantity);
        edtPrice = findViewById(R.id.edtPrice);
        edtDescription = findViewById(R.id.edtDescription);
        edtSaleDate = findViewById(R.id.edtSaleDate);
        autoCompleteTextViewAuthor = findViewById(R.id.autoCompleteTextViewStatus);

        // Initialize AutoCompleteTextView
        autoCompleteTextViewAuthor = findViewById(R.id.autoCompleteTextViewStatus);
        autoCompleteTextViewPublisher = findViewById(R.id.autoCompleteTextViewPublisher);
        autoCompleteTextViewGenre = findViewById(R.id.autoCompleteTextViewGenre);

        // Set sample data
        setSampleData();

        // Setting up DatePicker for Sale Date
        edtSaleDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });
    }

    private void setSampleData() {
        String[] authors = {"Paulo Coelho", "Dale Carnegie"};
        ArrayAdapter<String> authorAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, authors);
        autoCompleteTextViewAuthor.setAdapter(authorAdapter);

        // Sample data for AutoCompleteTextView (Publisher)
        String[] publishers = {"NXB Kim Đồng", "NXB Trẻ"};
        ArrayAdapter<String> publisherAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, publishers);
        autoCompleteTextViewPublisher.setAdapter(publisherAdapter);

        // Sample data for AutoCompleteTextView (Genre)
        String[] genres = {"Tiểu thuyết", "Giả tưởng", "Bí ẩn"};
        ArrayAdapter<String> genreAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, genres);
        autoCompleteTextViewGenre.setAdapter(genreAdapter);
    }

    private void showDatePickerDialog() {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                // Format and display date in the EditText
                String selectedDate = String.format("%02d/%02d/%04d", dayOfMonth, month + 1, year);
                edtSaleDate.setText(selectedDate);
            }
        }, year, month, day);

        datePickerDialog.show();
    }
}
