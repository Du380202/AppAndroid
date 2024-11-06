package com.example.bookstore.activity.admin;

import android.os.Bundle;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bookstore.R;

public class EditBookActivity extends AppCompatActivity {
    private EditText edtBookTitle, edtAuthor, edtQuantity, edtPrice, edtDescription, edtPublishDate, edtGenre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_edit);

        // Ánh xạ các EditText
        edtBookTitle = findViewById(R.id.edtBookTitle);
        edtAuthor = findViewById(R.id.edtAuthor);
        edtQuantity = findViewById(R.id.edtQuantity);
        edtPrice = findViewById(R.id.edtPrice);
        edtDescription = findViewById(R.id.edtDescription);
        edtPublishDate = findViewById(R.id.edtPublishDate);
        edtGenre = findViewById(R.id.edtGenre);

        setSampleData();
    }

    private void setSampleData() {
        edtBookTitle.setText("Nhà Giả Kim");
        edtAuthor.setText("Paulo Coelho");
        edtQuantity.setText("100");
        edtPrice.setText("120000"); // Giá sách giả định (120,000 VND)
        edtDescription.setText("Cuốn sách kể về hành trình của một chàng trai trẻ đi tìm kho báu và khám phá ra sự thật về chính mình.");
        edtPublishDate.setText("1988"); // Năm xuất bản giả định
        edtGenre.setText("Tiểu thuyết, Triết học");
    }
}