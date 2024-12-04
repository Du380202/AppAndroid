package com.example.bookstore.activity.admin;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.example.bookstore.R;
import com.example.bookstore.api.admin.AuthorApi;
import com.example.bookstore.api.admin.BookApi;
import com.example.bookstore.api.admin.CategoryApi;
import com.example.bookstore.dto.BookDto;
import com.example.bookstore.model.Author;
import com.example.bookstore.model.Book;
import com.example.bookstore.model.Category;
import com.example.bookstore.retrofit.ApiService;
import com.example.bookstore.support.DialogCallBack;
import com.example.bookstore.support.OnSelectionCompleteListener;
import com.example.bookstore.utils.DialogUtils;
import com.example.bookstore.utils.ImageUtils;
import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditBookActivity extends AppCompatActivity {
    private static final int MY_REQUEST_CODE = 10;
    private ImageView imgBookCover, backIcon;
    private Button btnSave;
    private Button btnEditImage;
    private EditText edtBookTitle, edtQuantity, edtPrice, edtDescription, edtSaleDate, edtPublicationYear;
    private AutoCompleteTextView autoCompleteTextViewAuthor, autoCompleteTextViewPublisher, autoCompleteTextViewGenre;
    List<Category> categoryList = new ArrayList<>();
    List<Author> authors = new ArrayList<>();
    List<Integer> authorIds = new ArrayList<>();
    List<Integer> categoryIds = new ArrayList<>();
    Uri mUri;
    private ProgressDialog progressDialog;

    private final ActivityResultLauncher<Intent> mActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    Log.e("LoggggTAG", "onActivityResult");
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        if (data == null) {
                            return;
                        }
                        Uri uri = data.getData();
                        mUri = uri;
                        try {
                            Bitmap bitmap = ImageUtils.getBitmapFromUri(EditBookActivity.this, uri);
                            imgBookCover.setImageBitmap(bitmap);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_edit);
        Bundle bundle = getIntent().getExtras();
        if (bundle == null) return;
        Book book;
        book = (Book) bundle.getSerializable("Object:", Book.class);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait ...");
        getDataCategory();
        getDataAuthor();
        // Ánh xạ các EditText
        setControl();
        setSampleData(book);
        btnEditImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickRequestPermission();
            }
        });
        autoCompleteTextViewGenre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showMultiChoiceCategory(categoryList, new OnSelectionCompleteListener() {
                    @Override
                    public void onSelectionComplete(List<Integer> selectedIds) {
                        categoryIds = selectedIds;
                    }
                });
            }
        });

        autoCompleteTextViewAuthor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showMultiChoiceAuthor(authors, new OnSelectionCompleteListener() {
                    @Override
                    public void onSelectionComplete(List<Integer> selectedIds) {
                        authorIds = selectedIds;
                    }
                });
            }

        });

        edtSaleDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                postData(book);
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
        edtBookTitle = findViewById(R.id.edtBookTitle);
        edtQuantity = findViewById(R.id.edtQuantity);
        edtPrice = findViewById(R.id.edtPrice);
        edtDescription = findViewById(R.id.edtDescription);
        edtSaleDate = findViewById(R.id.edtSaleDate);
        edtPublicationYear = findViewById(R.id.edtPublicationYear);
        autoCompleteTextViewAuthor = findViewById(R.id.autoCompleteTextViewStatus);
        imgBookCover = findViewById(R.id.imgBookCover);
        autoCompleteTextViewAuthor = findViewById(R.id.autoCompleteTextViewStatus);
        autoCompleteTextViewPublisher = findViewById(R.id.autoCompleteTextViewPublisher);
        autoCompleteTextViewGenre = findViewById(R.id.autoCompleteTextViewGenre);
        btnSave = findViewById(R.id.btnSave);
        backIcon = findViewById(R.id.back_icon);
        btnEditImage = findViewById(R.id.btnEditImage);
    }

    private void setSampleData(Book book) {
        edtBookTitle.setText(book.getBookName());
        String authorString = "", categoryString = "";
        for (Author author : book.getAuthors()) {
            authorString = authorString + author.getAuthorName() + ",";
        }
        if (authorString != null && authorString.length() > 0) {
            authorString = authorString.substring(0, authorString.length() - 1);
        }

        for (Category category : book.getCategories()) {
            categoryString = categoryString + category.getCategoryName() + ",";
        }

        if (categoryString != null && categoryString.length() > 0)  {
            categoryString = categoryString.substring(0, categoryString.length() - 1);
        }
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        edtPrice.setText(decimalFormat.format(book.getPrice()));
        autoCompleteTextViewAuthor.setText(authorString);
        autoCompleteTextViewPublisher.setText(book.getPublisher().getPublisherName());
        edtQuantity.setText(String.valueOf(book.getQuantity()));
        autoCompleteTextViewGenre.setText(categoryString);
        edtDescription.setText(book.getDescription());
        edtPublicationYear.setText(String.valueOf(book.getPublicationYear()));
        Glide.with(EditBookActivity.this)
                .load(book.getImage())
                .into(imgBookCover);
        if (book.getDaySaleDate() != null) {
            edtSaleDate.setText(book.getDaySaleDate());
        }

    }

    private BookDto getDataFromLayout(Book book) {
        String bookName = edtBookTitle.getText().toString();
        int bookQuantity = Integer.parseInt(edtQuantity.getText().toString());
        String daySaleDate = edtSaleDate.getText().toString();
        String bookDescription = edtDescription.getText().toString();
        BigDecimal bookPrice = new BigDecimal(edtPrice.getText().toString());
        int publicationYear = Integer.parseInt(edtPublicationYear.getText().toString());
        int status = 1;
        return new BookDto(book.getBookId(), bookName, bookPrice, bookDescription, bookQuantity, publicationYear, daySaleDate, status, 3, 0, categoryIds, authorIds);
    }

    private void onClickRequestPermission() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            ImageUtils.openGallery(mActivityResultLauncher);
            return;
        }
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            ImageUtils.openGallery(mActivityResultLauncher);
        } else {
            String[] permission = { Manifest.permission.READ_EXTERNAL_STORAGE };
            requestPermissions(permission, MY_REQUEST_CODE);
            ImageUtils.openGallery(mActivityResultLauncher);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                ImageUtils.openGallery(mActivityResultLauncher);
            }
        }
    }

    private void postData(Book book) {

        try {
            BookDto bookDto = getDataFromLayout(book);
            Log.e("BookDto", bookDto.toString());
            progressDialog.show();
            MultipartBody.Part image = null;
            File tempFile = null;
            RequestBody requestBody = null;
            if (mUri != null) {
                tempFile = ImageUtils.createTempFileFromUri(EditBookActivity.this, mUri);
                requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), tempFile);
                image = MultipartBody.Part.createFormData("image", tempFile.getName(), requestBody);
            }
            String bookDtoJson = new Gson().toJson(bookDto);
            Log.d("bookDtoJson", bookDtoJson);
            RequestBody bookDtoBody = RequestBody.create(MediaType.parse("application/json"), bookDtoJson);
            ApiService apiService = new ApiService();
            BookApi bookApi = apiService.getRetrofit().create(BookApi.class);
            bookApi.updateBook(image, bookDtoBody).enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    progressDialog.dismiss();
                    if (response.isSuccessful() && response.body() != null) {
                        DialogUtils.showSuccessDialog(EditBookActivity.this, "Cập nhật sách thành công", new DialogCallBack() {
                            @Override
                            public void onConfirm() {
                                setResult(Activity.RESULT_OK);
                                finish();
                            }
                        });
                    } else {
                        try {
                            String errorMessage = response.errorBody() != null ? response.errorBody().string() : "Unknown error";
                            Log.e("Upload", "Lỗi: " + errorMessage);
                        } catch (IOException e) {
                            Log.e("Upload", "Không thể đọc lỗi: " + e.getMessage());
                        }
                    }
                }
                @Override
                public void onFailure(Call<String> call, Throwable throwable) {
                    Log.e("Upload", "Thất bại: " + throwable.getMessage());
                }
            });

        } catch (IOException e) {
            Log.e("Error", "Không thể mở URI: " + e.getMessage());
        }
    }


    private void showDatePickerDialog() {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String selectedDate = String.format("%04d-%02d-%02d", year, month + 1, dayOfMonth);
                edtSaleDate.setText(selectedDate);
            }
        }, year, month, day);

        datePickerDialog.show();
    }

    private void showMultiChoiceCategory(List<Category> categories, OnSelectionCompleteListener listener ) {
        String[] categoryNames = new String[categories.size()];
        for (int i = 0; i < categories.size(); i++) {
            categoryNames[i] = categories.get(i).getCategoryName();
        }

        boolean[] checkedItems = new boolean[categories.size()];

        final List<Integer> selectedIds = new ArrayList<>();
        final List<String> selectedItems = new ArrayList<>();

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Chọn các mục");

        builder.setMultiChoiceItems(categoryNames, checkedItems, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                if (isChecked) {
                    selectedIds.add(categories.get(which).getCategoryId()); // Thêm ID
                    selectedItems.add(categories.get(which).getCategoryName());
                } else {
                    selectedIds.remove(categories.get(which).getCategoryId()); // Xóa ID
                    selectedItems.remove(categories.get(which).getCategoryName());
                }
            }
        });

        builder.setPositiveButton("Xác nhận", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String joinedNames = String.join(", ", selectedItems);
                autoCompleteTextViewGenre.setText(joinedNames);
                listener.onSelectionComplete(selectedIds);
            }
        });
        builder.setNegativeButton("Hủy", null);

        builder.create().show();
    }

    private void showMultiChoiceAuthor(List<Author> authors, OnSelectionCompleteListener listener) {
        String[] auhthorNames = new String[authors.size()];
        for (int i = 0; i < authors.size(); i++) {
            auhthorNames[i] = authors.get(i).getAuthorName();
        }

        boolean[] checkedItems = new boolean[authors.size()];

        final List<Integer> selectedIds = new ArrayList<>();
        final List<String> selectedItems = new ArrayList<>();

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Chọn các mục");

        builder.setMultiChoiceItems(auhthorNames, checkedItems, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                if (isChecked) {
                    selectedIds.add(authors.get(which).getAuthorId()); // Thêm ID
                    selectedItems.add(authors.get(which).getAuthorName());
                } else {
                    selectedIds.remove(authors.get(which).getAuthorId()); // Xóa ID
                    selectedItems.remove(authors.get(which).getAuthorName());
                }
            }
        });

        builder.setPositiveButton("Xác nhận", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                autoCompleteTextViewAuthor.setText(String.join(", ", selectedItems));
                listener.onSelectionComplete(selectedIds);
            }
        });
        builder.setNegativeButton("Hủy", null);

        builder.create().show();
    }

    private void getDataCategory() {
        ApiService apiService = new ApiService();
        CategoryApi categoryApi = apiService.getRetrofit().create(CategoryApi.class);
        categoryApi.getAllCategory().enqueue(new Callback<List<Category>>() {
            @Override
            public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                categoryList = response.body();
            }

            @Override
            public void onFailure(Call<List<com.example.bookstore.model.Category>> call, Throwable throwable) {
                Log.e("LogE", throwable.getMessage());
            }
        });
    }

    private void getDataAuthor() {
        ApiService apiService = new ApiService();
        AuthorApi authorApi = apiService.getRetrofit().create(AuthorApi.class);
        authorApi.getAllAuthor().enqueue(new Callback<List<com.example.bookstore.model.Author>>() {
            @Override
            public void onResponse(Call<List<com.example.bookstore.model.Author>> call, Response<List<com.example.bookstore.model.Author>> response) {
                authors = response.body();
            }

            @Override
            public void onFailure(Call<List<com.example.bookstore.model.Author>> call, Throwable throwable) {
                Log.e("LogE", throwable.getMessage());
            }
        });
    }
}