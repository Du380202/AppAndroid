package com.example.bookstore.activity.admin;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.bookstore.R;
import com.example.bookstore.adapter.admin.AuthorAdapter;
import com.example.bookstore.adapter.admin.CategoryAdapter;
import com.example.bookstore.api.admin.AuthorApi;
import com.example.bookstore.api.admin.BookApi;
import com.example.bookstore.api.admin.CategoryApi;
import com.example.bookstore.api.admin.PublisherApi;
import com.example.bookstore.dto.BookDto;
import com.example.bookstore.dto.CategoryDto;
import com.example.bookstore.dto.MessageCode;
import com.example.bookstore.model.Author;
import com.example.bookstore.model.Book;
import com.example.bookstore.model.Category;
import com.example.bookstore.model.Publisher;
import com.example.bookstore.retrofit.ApiService;
import com.example.bookstore.support.DialogCallBack;
import com.example.bookstore.support.OnSelectionCompleteListener;
import com.example.bookstore.utils.DialogUtils;
import com.example.bookstore.utils.ExcelReader;
import com.example.bookstore.utils.ImageUtils;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.chrono.ChronoLocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddBookActivity extends AppCompatActivity {
    private static final int MY_REQUEST_CODE = 10;
    private ImageView imgBookCover, backIcon;
    private Button btnSave;
    private TextView tvErrorBookTitle, tvErrorAuthor, tvErrorQuantity, tvErrorPrice, tvErrorDescription,
            tvErrorYear, tvErrorPublisher, tvErrorCategory, tvErrorDateSale;
    private EditText edtBookTitle, edtQuantity, edtPrice, edtDescription, edtSaleDate, edtPublicationYear;
    private AutoCompleteTextView autoCompleteTextViewAuthor, autoCompleteTextViewPublisher, autoCompleteTextViewGenre;
    List<Category> categoryList = new ArrayList<>();
    List<Author> authors = new ArrayList<>();
    List<Publisher> publishers = new ArrayList<>();
    Integer puInteger;
    List<Integer> authorIds = new ArrayList<>();
    List<Integer> categoryIds = new ArrayList<>();
    Uri mUri;
    private ProgressDialog progressDialog;
    Map<MessageCode, String> errorMessages;

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
                            Bitmap bitmap = ImageUtils.getBitmapFromUri(AddBookActivity.this, uri);
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
        setContentView(R.layout.activity_add_book);
        setControl();
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait ...");
        getDataCategory();
        getDataAuthor();
        getDataPublisher();
        setupInputWatchers();
        imgBookCover.setOnClickListener(new View.OnClickListener() {
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
        autoCompleteTextViewPublisher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showSingleChoicePublisher(publishers, new OnSelectionCompleteListener() {
                    @Override
                    public void onSelectionComplete(List<Integer> selectedIds) {

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
                LocalDate today = LocalDate.now();
                Log.e("AuthorIds", authorIds.toString());
                Log.d("âsasdasd", autoCompleteTextViewAuthor.getText().toString());
                if (edtBookTitle.getText().toString().trim().isEmpty()) {
                    showError(tvErrorBookTitle, errorMessages.get(MessageCode.INPUT_TITLE_ERROR));
                }
                else if ( authorIds == null || authorIds.isEmpty()) {
                    showError(tvErrorAuthor, errorMessages.get(MessageCode.INPUT_AUTHOR_ERROR));
                    Toast.makeText(AddBookActivity.this, errorMessages.get(MessageCode.INPUT_AUTHOR_ERROR), Toast.LENGTH_SHORT).show();
                }

                // Kiểm tra Số lượng
                else if (edtQuantity.getText().toString().trim().isEmpty()) {
                    showError(tvErrorQuantity, errorMessages.get(MessageCode.INPUT_QUANTITY_ERROR));
                }

                // Kiểm tra Giá
                else if (edtPrice.getText().toString().trim().isEmpty()) {
                    showError(tvErrorPrice, errorMessages.get(MessageCode.INPUT_PRICE_ERROR));
                }

                // Kiểm tra Mô tả
                else if (edtDescription.getText().toString().trim().isEmpty()) {
                    showError(tvErrorDescription, errorMessages.get(MessageCode.CONNECTION_TIMEOUT));
                }

                // Kiểm tra Năm xuất bản
                else if (edtPublicationYear.getText().toString().trim().isEmpty()) {
                    showError(tvErrorYear, errorMessages.get(MessageCode.INPUT_PUBLISHER_ERROR));
                }
                else if (autoCompleteTextViewPublisher.getText().toString().isEmpty()) {
                    showError(tvErrorPublisher, errorMessages.get(MessageCode.INPUT_PUBLISHER_ERROR));
                    Toast.makeText(AddBookActivity.this, errorMessages.get(MessageCode.INPUT_PUBLISHER_ERROR), Toast.LENGTH_SHORT).show();

                }
                // Kiểm tra Thể loại
                else if (categoryIds.isEmpty()) {
                    showError(tvErrorCategory, errorMessages.get(MessageCode.INPUT_CATEGORY_ERROR));
                    Toast.makeText(AddBookActivity.this, errorMessages.get(MessageCode.INPUT_CATEGORY_ERROR), Toast.LENGTH_SHORT).show();

                }
                // Kiểm tra Ngày bán
                else if (today.isAfter(LocalDate.parse(edtSaleDate.getText()))) {
                    showError(tvErrorDateSale, errorMessages.get(MessageCode.INPUT_DATESALE_ERROR));
                }

                else {
                    postData();
                }

            }
        });
        backIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getOnBackPressedDispatcher().onBackPressed();
            }
        });
    }

    private void showError(TextView errorTextView, String message) {
        // Set message và hiển thị TextView
        errorTextView.setText(message);
        errorTextView.setVisibility(View.VISIBLE);

    }

    private void setupTextWatcher(final EditText editText, final TextView errorTextView) {
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().trim().isEmpty()) {
                    hideError(errorTextView);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void hideError(TextView errorTextView) {
        errorTextView.setVisibility(View.GONE);
    }

    private void setupInputWatchers() {
        setupTextWatcher(edtBookTitle, tvErrorBookTitle);
        setupTextWatcher(edtQuantity, tvErrorQuantity);
        setupTextWatcher(edtPrice, tvErrorPrice);
        setupTextWatcher(edtDescription, tvErrorDescription);
        setupTextWatcher(edtPublicationYear, tvErrorYear);
        setupTextWatcher(edtSaleDate, tvErrorDateSale);
        setupTextWatcher(autoCompleteTextViewAuthor, tvErrorAuthor);
        setupTextWatcher(autoCompleteTextViewGenre, tvErrorCategory);
        setupTextWatcher(autoCompleteTextViewPublisher, tvErrorPublisher);
//        setupTextWatcher(autoCompleteTextViewAuthor, tvErrorAuthor);

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
        tvErrorBookTitle = findViewById(R.id.tvErrorBookTitle);
        tvErrorDateSale = findViewById(R.id.tvErrorDateSale);
        tvErrorCategory = findViewById(R.id.tvErrorCategory);
        tvErrorPublisher = findViewById(R.id.tvErrorPublisher);
        tvErrorYear = findViewById(R.id.tvErrorYear);
        tvErrorDescription = findViewById(R.id.tvErrorDescription);
        tvErrorPrice = findViewById(R.id.tvErrorPrice);
        tvErrorQuantity = findViewById(R.id.tvErrorQuantity);
        tvErrorAuthor = findViewById(R.id.tvErrorAuthor);
        InputStream inputStream = null;
        try {
            inputStream = getAssets().open("messagecode.xlsx");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        errorMessages = ExcelReader.readErrorMessages(inputStream);

    }

    private BookDto getDataFromLayout() {
        String bookName = edtBookTitle.getText().toString();
        int bookQuantity = Integer.parseInt(edtQuantity.getText().toString());
        String daySaleDate = edtSaleDate.getText().toString();
        String bookDescription = edtDescription.getText().toString();
        BigDecimal bookPrice = new BigDecimal(edtPrice.getText().toString());
        int publicationYear = Integer.parseInt(edtPublicationYear.getText().toString());
        int status = 1;
        return new BookDto(bookName, bookPrice, bookDescription, bookQuantity, publicationYear, status, puInteger, 0, categoryIds, authorIds, daySaleDate);
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

    private void postData() {
        BookDto bookDto = getDataFromLayout();
        if (mUri == null) {
            Toast.makeText(this, "Vui lòng chọn ảnh trước khi tải lên!", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            progressDialog.show();
            File tempFile = ImageUtils.createTempFileFromUri(AddBookActivity.this, mUri);
            String bookDtoJson = new Gson().toJson(bookDto);
            Log.d("bookDtoJson", bookDtoJson);
            RequestBody bookDtoBody = RequestBody.create(MediaType.parse("application/json"), bookDtoJson);
            RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), tempFile);
            MultipartBody.Part image = MultipartBody.Part.createFormData("image", tempFile.getName(), requestBody);

            ApiService apiService = new ApiService();
            BookApi bookApi = apiService.getRetrofit().create(BookApi.class);
            bookApi.createBook(image, bookDtoBody).enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    progressDialog.dismiss();
                    if (response.isSuccessful() && response.body() != null) {
                        DialogUtils.showSuccessDialog(AddBookActivity.this, "Thêm sách thành công", new DialogCallBack() {
                            @Override
                            public void onConfirm() {
                                setResult(Activity.RESULT_OK);
                                finish();
                            }
                        });
                    } else {
                        Log.e("Upload", "Lỗi: " + response.errorBody());
                    }
                }
                @Override
                public void onFailure(Call<String> call, Throwable throwable) {
                    DialogUtils.showErrorDialog(AddBookActivity.this, errorMessages.get(MessageCode.CONNECTION_ERROR));
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

    private void showSingleChoicePublisher(List<Publisher> authors, OnSelectionCompleteListener listener) {
        String[] publisherNames = new String[authors.size()];
        for (int i = 0; i < authors.size(); i++) {
            publisherNames[i] = authors.get(i).getPublisherName();
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Chọn mục");

        builder.setSingleChoiceItems(publisherNames, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Lấy thông tin mục được chọn
                String selectedName = authors.get(which).getPublisherName();
                puInteger = authors.get(which).getPublisherId();

                // Cập nhật TextView
                autoCompleteTextViewPublisher.setText(selectedName);

                // Gọi listener
                listener.onSelectionComplete(Collections.singletonList(puInteger));

                // Đóng dialog
                dialog.dismiss();
            }
        });

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

    private void getDataPublisher() {
        ApiService apiService = new ApiService();
        PublisherApi authorApi = apiService.getRetrofit().create(PublisherApi.class);
        authorApi.getAll().enqueue(new Callback<List<Publisher>>() {
            @Override
            public void onResponse(Call<List<Publisher>> call, Response<List<Publisher>> response) {
                publishers = response.body();
            }

            @Override
            public void onFailure(Call<List<Publisher>> call, Throwable throwable) {

            }
        });
    }
}
