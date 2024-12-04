package com.example.bookstore.activity.admin;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.example.bookstore.R;
import com.example.bookstore.api.admin.CategoryApi;
import com.example.bookstore.dto.CategoryDto;
import com.example.bookstore.model.Book;
import com.example.bookstore.model.Category;
import com.example.bookstore.retrofit.ApiService;
import com.example.bookstore.support.DialogCallBack;
import com.example.bookstore.utils.DialogUtils;
import com.example.bookstore.utils.ImageUtils;
import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditCategoryActivity extends AppCompatActivity {
    private static final int MY_REQUEST_CODE = 10;
    ImageView imgCategoryCover, backIcon;
    Button btnSave, btnEditImage, btnDel;
    EditText edtCategoryName, edtDescription;
    Uri mUri;
    private ProgressDialog progressDialog;

    private final ActivityResultLauncher<Intent> mActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        if (data == null) {
                            return;
                        }
                        Uri uri = data.getData();
                        mUri = uri;
                        try {
                            Bitmap bitmap = ImageUtils.getBitmapFromUri(EditCategoryActivity.this, uri);
                            imgCategoryCover.setImageBitmap(bitmap);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_category);
        Bundle bundle = getIntent().getExtras();
        if (bundle == null) return;
        Category category;
        category = (Category) bundle.getSerializable("Object:", Category.class);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait ...");
        setCotroll();
//        assert category != null;
        setDataInView(category);
        btnEditImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickRequestPermission();
            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                putData(category);
            }
        });
    }

    private void setCotroll() {
        imgCategoryCover = findViewById(R.id.imgCategoryCover);
        btnSave = findViewById(R.id.btnSave);
        edtDescription = findViewById(R.id.edtDescription);
        edtCategoryName = findViewById(R.id.edtCategoryName);
        backIcon = findViewById(R.id.back_icon);
        btnDel = findViewById(R.id.btnDel);
        backIcon = findViewById(R.id.back_icon);
        btnEditImage = findViewById(R.id.btnEditImage);
    }

    private void setDataInView(Category category) {
        edtCategoryName.setText(category.getCategoryName());
        edtDescription.setText(category.getDepscription());
        Glide.with(EditCategoryActivity.this)
                .load(category.getCategoryImg())
                .into(imgCategoryCover);
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

    private void putData(Category category) {
        try {
            progressDialog.show();
            MultipartBody.Part image = null;
            File tempFile = null;
            RequestBody requestBody = null;
            if (mUri != null) {
                tempFile = ImageUtils.createTempFileFromUri(EditCategoryActivity.this, mUri);
                requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), tempFile);
                image = MultipartBody.Part.createFormData("image", tempFile.getName(), requestBody);
            }

            String name = edtCategoryName.getText().toString();
            String description = edtDescription.getText().toString();
            category.setCategoryName(name);
            category.setDepscription(description);
            String categoryDtoJson = new Gson().toJson(category);

            RequestBody categoryDtoBody = RequestBody.create(MediaType.parse("application/json"), categoryDtoJson);
            ApiService apiService = new ApiService();
            CategoryApi categoryApi = apiService.getRetrofit().create(CategoryApi.class);
            categoryApi.updateCategory(image, categoryDtoBody).enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    progressDialog.dismiss();
                    if (response.isSuccessful() && response.body() != null) {
                        DialogUtils.showSuccessDialog(EditCategoryActivity.this, "Cập nhật thành công", new DialogCallBack() {
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
                    Log.e("Upload", "Thất bại: " + throwable.getMessage());
                }
            });

        } catch (IOException e) {
            Log.e("Error", "Không thể mở URI: " + e.getMessage());
        }
    }
}