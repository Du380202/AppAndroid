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

import com.example.bookstore.R;
import com.example.bookstore.api.admin.AuthorApi;
import com.example.bookstore.api.admin.CategoryApi;
import com.example.bookstore.dto.AuthorDto;
import com.example.bookstore.dto.CategoryDto;
import com.example.bookstore.model.Author;
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

public class AddAuthorActivity extends AppCompatActivity {
    private static final int MY_REQUEST_CODE = 10;
    EditText edtAuthorName, edtBiography;
    ImageView imgAuthorCover, backIcon;
    Button btnSave;
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
                            Bitmap bitmap = ImageUtils.getBitmapFromUri(AddAuthorActivity.this, uri);
                            imgAuthorCover.setImageBitmap(bitmap);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_author);
        setCotrol();
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait ...");
        imgAuthorCover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickRequestPermission();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("UploadSave", "Lỗi: ");
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

    private void setCotrol() {
        edtAuthorName = findViewById(R.id.edtAuthorName);
        edtBiography = findViewById(R.id.edtBiography);
        imgAuthorCover = findViewById(R.id.imgAuthorCover);
        btnSave = findViewById(R.id.btnSave);
        backIcon = findViewById(R.id.back_iconAuthor);
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
            Log.e("UploadSaveCds", "Lỗi: ");
            progressDialog.show();
            File tempFile = ImageUtils.createTempFileFromUri(AddAuthorActivity.this, mUri);
            String name = edtAuthorName.getText().toString();
            String description = edtBiography.getText().toString();
            Author authorDto = new Author(name, description);
            String authorDtoJson = new Gson().toJson(authorDto);

            RequestBody authorDtoBody = RequestBody.create(MediaType.parse("application/json"), authorDtoJson);
            RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), tempFile);
            MultipartBody.Part image = MultipartBody.Part.createFormData("image", tempFile.getName(), requestBody);

            ApiService apiService = new ApiService();
            AuthorApi authorApi = apiService.getRetrofit().create(AuthorApi.class);
            authorApi.createAuthor(image, authorDtoBody).enqueue(new Callback<Author>() {
                @Override
                public void onResponse(Call<Author> call, Response<Author> response) {
                    progressDialog.dismiss();
                    if (response.isSuccessful() && response.body() != null) {
                        DialogUtils.showSuccessDialog(AddAuthorActivity.this, "Thêm tác giả thành công", new DialogCallBack() {
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
                public void onFailure(Call<Author> call, Throwable throwable) {
                    Log.e("Upload", "Thất bại: " + throwable.getMessage());
                }
            });

        } catch (IOException e) {
            Log.e("Error", "Không thể mở URI: " + e.getMessage());
        }
    }
}