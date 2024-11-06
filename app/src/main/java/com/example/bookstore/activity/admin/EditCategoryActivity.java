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

public class EditCategoryActivity extends AppCompatActivity {
    private EditText edtCategoryName;
    private EditText edtDescription;
    private ImageView imgCategoryCover;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_category);
        // Ánh xạ các thành phần
        edtCategoryName = findViewById(R.id.edtCategoryName);
        edtDescription = findViewById(R.id.edtDescription);
        imgCategoryCover = findViewById(R.id.imgCategoryCover);

        // Đặt dữ liệu mẫu
        edtCategoryName.setText("Tiểu thuyết");
        edtDescription.setText("Tiểu thuyết là một thể loại văn xuôi có hư cấu, thông qua nhân vật, hoàn cảnh, sự việc để phản ánh bức tranh xã hội rộng lớn và những vấn đề của cuộc sống con người, biểu hiện tính chất tường thuật, tính chất kể chuyện bằng ngôn ngữ văn xuôi theo những chủ đề xác định.");

        // Đặt ảnh mẫu nếu có
        imgCategoryCover.setImageResource(R.drawable.img_8);
    }
}