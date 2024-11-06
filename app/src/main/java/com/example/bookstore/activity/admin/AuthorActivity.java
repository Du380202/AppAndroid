package com.example.bookstore.activity.admin;

import android.os.Bundle;
import android.text.TextUtils;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookstore.R;
import com.example.bookstore.adapter.admin.AuthorAdapter;
import com.example.bookstore.dto.Author;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AuthorActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private AuthorAdapter adapter;
    private List<Author> authorList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_author);
        recyclerView = findViewById(R.id.recyclerTask);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        String limitedText = getLimitedWords("Paulo Coelho sinh tại Rio de Janeiro (Brasil). Ông học đại học trường luật, nhưng đã bỏ học năm 1970 để du lịch qua México, Peru, Bolivia và Chile, cũng như châu Âu và Bắc Phi. Hai năm sau, ông trở về Brasil và bắt đầu soạn lời nhạc pop. Ông cộng tác với những nhạc sĩ pop như Raul Seixas. Năm 1974, ông bị bắt giam một thời gian ngắn vì những hoạt động chống lại chế độ độc tài thời đó ở Brazil.", 5);
        String limit = getLimitedWords("Dale Breckenridge Carnegie (trước kia là Carnagey cho tới năm 1922 và có thể một thời gian muộn hơn) (24 tháng 11 năm 1888 – 1 tháng 11 năm 1955) là một nhà văn và nhà thuyết trình Mỹ và là người phát triển các lớp tự giáo dục, nghệ thuật bán hàng, huấn luyện đoàn thể, nói trước công chúng và các kỹ năng giao tiếp giữa mọi người. Ra đời trong cảnh nghèo đói tại một trang trại ở Missouri, ông là tác giả cuốn Đắc Nhân Tâm, được xuất bản lần đầu năm 1936, một cuốn sách hàng bán chạy nhất và được biết đến nhiều nhất cho đến tận ngày nay, nội dung nói về cách ứng xử, cư xử trong cuộc sống. Ông cũng viết một cuốn tiểu sử Abraham Lincoln, với tựa đề Lincoln con người chưa biết, và nhiều cuốn sách khác.", 5);
        authorList = new ArrayList<>();
        authorList.add(new Author(R.drawable.img_4, "Paulo Coelho", limitedText));
        authorList.add(new Author(R.drawable.img_5, "Dale Carnegie",  limit));
        adapter = new AuthorAdapter(authorList, this);
        recyclerView.setAdapter(adapter);
    }

    private String getLimitedWords(String text, int wordLimit) {
        String[] words = text.split("\\s+");  // Split text by whitespace
        if (words.length > wordLimit) {
            // Join only the first 'wordLimit' words and add "..."
            return TextUtils.join(" ", Arrays.copyOfRange(words, 0, wordLimit)) + "...";
        }
        return text;  // If text has 5 or fewer words, return as is
    }
}