package com.example.bookstore.activity.admin;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
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
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.loader.content.CursorLoader;

import com.example.bookstore.R;
import com.example.bookstore.adapter.admin.CategoryAdapter;
import com.example.bookstore.api.UploadImage;
import com.example.bookstore.api.admin.CategoryApi;
import com.example.bookstore.config.RealPathUtil;
import com.example.bookstore.dto.AuthorDto;
import com.example.bookstore.dto.CategoryDto;
import com.example.bookstore.model.Category;
import com.example.bookstore.retrofit.ApiService;
import com.example.bookstore.support.DialogCallBack;
import com.example.bookstore.utils.DialogUtils;
import com.example.bookstore.utils.ImageUtils;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddCategoryActivity extends AppCompatActivity {
    private static final int MY_REQUEST_CODE = 10;
    private ImageView imgCategoryCover, backIcon;
    Button btnSave;
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
                            Bitmap bitmap = ImageUtils.getBitmapFromUri(AddCategoryActivity.this, uri);
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
        setContentView(R.layout.activity_add_category);
        setCotroll();
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait ...");
        imgCategoryCover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickRequestPermission();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                postData();

            }
        });

        backIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getOnBackPressedDispatcher().onBackPressed();
            }
        });
    }

    private void setCotroll() {
        imgCategoryCover = findViewById(R.id.imgCategoryCover);
        btnSave = findViewById(R.id.btnSave);
        edtDescription = findViewById(R.id.edtDescription);
        edtCategoryName = findViewById(R.id.edtCategoryName);
        backIcon = findViewById(R.id.back_icon);
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
        if (mUri == null) {
            Toast.makeText(this, "Vui lòng chọn ảnh trước khi tải lên!", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            progressDialog.show();
            File tempFile = ImageUtils.createTempFileFromUri(AddCategoryActivity.this, mUri);
            String name = edtCategoryName.getText().toString();
            String description = edtDescription.getText().toString();
            CategoryDto authorDto = new CategoryDto(name, description);
            String authorDtoJson = new Gson().toJson(authorDto);

            RequestBody categoryDtoBody = RequestBody.create(MediaType.parse("application/json"), authorDtoJson);
            RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), tempFile);
            MultipartBody.Part image = MultipartBody.Part.createFormData("image", tempFile.getName(), requestBody);

            ApiService apiService = new ApiService();
            CategoryApi categoryApi = apiService.getRetrofit().create(CategoryApi.class);
            categoryApi.createCategory(image, categoryDtoBody).enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    progressDialog.dismiss();
                    if (response.isSuccessful() && response.body() != null) {
                        DialogUtils.showSuccessDialog(AddCategoryActivity.this, "Tạo thể loại thành công", new DialogCallBack() {
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