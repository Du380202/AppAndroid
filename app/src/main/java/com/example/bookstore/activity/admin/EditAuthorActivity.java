package com.example.bookstore.activity.admin;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.bookstore.R;

public class EditAuthorActivity extends AppCompatActivity {
    private EditText edtAuthorName;
    private EditText edtDescription;
    private ImageView imgAuthorCover;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_author);
        // Ánh xạ các thành phần
        edtAuthorName = findViewById(R.id.edtAuthorName);
        edtDescription = findViewById(R.id.edtDescription);
        imgAuthorCover = findViewById(R.id.imgAuthorCover);

        // Đặt dữ liệu mẫu
        edtAuthorName.setText("Paulo Coelho");
        edtDescription.setText("Paulo Coelho sinh tại Rio de Janeiro (Brasil). Ông học đại học trường luật, nhưng đã bỏ học năm 1970 để du lịch qua México, Peru, Bolivia và Chile, cũng như châu Âu và Bắc Phi. Hai năm sau, ông trở về Brasil và bắt đầu soạn lời nhạc pop. Ông cộng tác với những nhạc sĩ pop như Raul Seixas. Năm 1974, ông bị bắt giam một thời gian ngắn vì những hoạt động chống lại chế độ độc tài thời đó ở Brazil.");

        // Đặt ảnh mẫu nếu có
        imgAuthorCover.setImageResource(R.drawable.img_4);
    }
}