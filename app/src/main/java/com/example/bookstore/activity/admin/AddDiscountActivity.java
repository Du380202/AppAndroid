package com.example.bookstore.activity.admin;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.bookstore.R;
import com.example.bookstore.api.admin.BookApi;
import com.example.bookstore.api.admin.DiscountApi;
import com.example.bookstore.dto.DiscountDto;
import com.example.bookstore.model.Book;
import com.example.bookstore.model.Discount;
import com.example.bookstore.retrofit.ApiService;
import com.example.bookstore.support.OnSelectionCompleteListener;
import com.example.bookstore.utils.DialogUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddDiscountActivity extends AppCompatActivity {
    EditText edtStartDate, edtEndDate, edtDiscountTitle, edtPercentDiscount,edtDescription;
    AutoCompleteTextView autoCompleteTextViewBook;
    Button btnSave;
    List<Book> bookList = new ArrayList<>();
    List<Integer> bookIds;
    ImageView backIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_discount);
        setControl();
        getData();
        edtStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog(edtStartDate);
            }
        });
        edtEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog(edtEndDate);
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                postData();
            }
        });

        autoCompleteTextViewBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showMultiChoiceCategoryCustom(bookList, new OnSelectionCompleteListener() {
                    @Override
                    public void onSelectionComplete(List<Integer> selectedIds) {
                        bookIds = selectedIds;
                        Toast.makeText(AddDiscountActivity.this, bookIds.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        backIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getOnBackPressedDispatcher().onBackPressed();
            }
        });
    }

    private DiscountDto getDataFromLayout() {
        DiscountDto discountDto = new DiscountDto();
        discountDto.setDiscountName(edtDiscountTitle.getText().toString());
        discountDto.setDescription(edtDescription.getText().toString());
        discountDto.setBookIds(bookIds);
        discountDto.setStatus(1);
        discountDto.setEndDate(edtEndDate.getText().toString());
        discountDto.setStartDate(edtStartDate.getText().toString());
        discountDto.setDiscountPercent(Integer.valueOf(edtPercentDiscount.getText().toString()));
        return discountDto;
    }

    private void postData() {
        DiscountDto discountDto = getDataFromLayout();
        Log.d("DiscountDto", discountDto.toString());
        ApiService apiService = new ApiService();
        DiscountApi discountApi = apiService.getRetrofit().create(DiscountApi.class);
        discountApi.createDiscount(discountDto).enqueue(new Callback<Discount>() {
            @Override
            public void onResponse(Call<Discount> call, Response<Discount> response) {
                if (response.isSuccessful() && response.body() != null) {
                    DialogUtils.showSuccessDialog(AddDiscountActivity.this, "Thêm thành công");
                } else {
                    Log.e("Upload", "Lỗi: " + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<Discount> call, Throwable throwable) {
                Log.e("LogDiscount", throwable.getMessage());
            }
        });
    }
    private void getData() {
        ApiService apiService = new ApiService();
        BookApi bookApi = apiService.getRetrofit().create(BookApi.class);
        bookApi.getAllBook().enqueue(new Callback<List<Book>>() {
            @Override
            public void onResponse(Call<List<Book>> call, Response<List<Book>> response) {
                bookList = (List<Book>) response.body();

            }

            @Override
            public void onFailure(Call<List<Book>> call, Throwable throwable) {

            }
        });
    }



    private void setControl() {
        autoCompleteTextViewBook = findViewById(R.id.autoCompleteTextViewBook);
        edtDescription = findViewById(R.id.edtDescription);
        edtDiscountTitle = findViewById(R.id.edtDiscountTitle);
        edtEndDate = findViewById(R.id.edtEndDate);
        edtStartDate = findViewById(R.id.edtStartDate);
        edtPercentDiscount = findViewById(R.id.edtPercentDiscount);
        btnSave = findViewById(R.id.btnSave);
        backIcon = findViewById(R.id.back_icon);
    }

    private void showDatePickerDialog(EditText editText) {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String selectedDate = String.format("%04d-%02d-%02d", year, month + 1, dayOfMonth);
                editText.setText(selectedDate);
            }
        }, year, month, day);

        datePickerDialog.show();
    }

    private void showMultiChoiceCategoryCustom(List<Book> books, OnSelectionCompleteListener listener) {
        LayoutInflater inflater = LayoutInflater.from(this);
        View dialogView = inflater.inflate(R.layout.layout_discount, null);

        LinearLayout optionContainer = dialogView.findViewById(R.id.optionContainer);


        final List<Integer> selectedIds = new ArrayList<>();
        final List<String> selectedItems = new ArrayList<>();

        for (Book book : books) {
            LinearLayout vnpayOption = new LinearLayout(this);
            vnpayOption.setOrientation(LinearLayout.HORIZONTAL);
            vnpayOption.setPadding(16, 16, 16, 16);


            ImageView imageView = new ImageView(this);
            imageView.setLayoutParams(new LinearLayout.LayoutParams(50, 50));
            imageView.setImageResource(R.drawable.vnpay_logo); // Thay logo nếu cần
            vnpayOption.addView(imageView);
            TextView textView = new TextView(this);
            textView.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            ));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, // Chiều rộng
                    LinearLayout.LayoutParams.WRAP_CONTENT  // Chiều cao
            );

            int marginInDp = 8;
            float scale = getResources().getDisplayMetrics().density;
            int marginInPx = (int) (marginInDp * scale + 0.5f);

            params.setMargins(marginInPx, marginInPx, marginInPx, marginInPx);


            textView.setText(book.getBookName());
            textView.setPadding(16, 0, 0, 0);
            textView.setTextSize(16);
            vnpayOption.addView(textView);
            vnpayOption.setLayoutParams(params);
            boolean isSelected = false;
            vnpayOption.setBackgroundColor(isSelected ? Color.LTGRAY : Color.TRANSPARENT);

            vnpayOption.setOnClickListener(v -> {
                if (selectedIds.contains(book.getBookId())) {
                    selectedIds.remove((Integer) book.getBookId());
                    selectedItems.remove(book.getBookName());
                    vnpayOption.setBackgroundColor(Color.TRANSPARENT);
                } else {
                    selectedIds.add(book.getBookId());
                    selectedItems.add(book.getBookName());
                    vnpayOption.setBackgroundColor(Color.LTGRAY);
                }
            });

            optionContainer.addView(vnpayOption);
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialogView);
        builder.setPositiveButton("Xác nhận", (dialog, which) -> {
            String joinedNames = String.join(", ", selectedItems);
            autoCompleteTextViewBook.setText(joinedNames);
            listener.onSelectionComplete(selectedIds);
        });
        builder.setNegativeButton("Hủy", null);

        builder.create().show();
    }
}