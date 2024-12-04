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
import com.example.bookstore.api.admin.AuthorApi;
import com.example.bookstore.api.admin.CategoryApi;
import com.example.bookstore.dto.AuthorDto;
import com.example.bookstore.model.Author;
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

public class EditAuthorActivity extends AppCompatActivity {
    private static final int MY_REQUEST_CODE = 10;
    private EditText edtAuthorName;
    private EditText edtDescription;
    private ImageView imgAuthorCover;
    private Button btnSave;
    private Button btnEditImage;
    private Button btnDel;
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
                            Bitmap bitmap = ImageUtils.getBitmapFromUri(EditAuthorActivity.this, uri);
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
        setContentView(R.layout.activity_edit_author);
        Bundle bundle = getIntent().getExtras();
        if (bundle == null) return;
        Author author;
        author = (Author) bundle.getSerializable("Object:", Author.class);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait ...");
        setControl();
        setDataInView(author);
        btnEditImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickRequestPermission();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("UploadBtnSave", "Lỗi: ");
                postData(author);
            }
        });
    }

    private void setDataInView(Author author) {
        edtAuthorName.setText(author.getAuthorName());
        edtDescription.setText(author.getBiography());
        Glide.with(EditAuthorActivity.this)
                .load(author.getAuthorImg())
                .into(imgAuthorCover);
    }

    private void setControl() {
        edtAuthorName = findViewById(R.id.edtAuthorName);
        edtDescription = findViewById(R.id.edtDescription);
        imgAuthorCover = findViewById(R.id.imgAuthorCover);
        btnSave = findViewById(R.id.btnSave);
        btnEditImage = findViewById(R.id.btnEditImage);
        btnDel = findViewById(R.id.btnDel);
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

    private void postData(Author author) {
        try {
            Log.e("UploadPostData", "Lỗi: ");
            progressDialog.show();
            MultipartBody.Part image = null;
            File tempFile = null;
            RequestBody requestBody = null;
            if (mUri != null) {
                tempFile = ImageUtils.createTempFileFromUri(EditAuthorActivity.this, mUri);
                requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), tempFile);
                image = MultipartBody.Part.createFormData("image", tempFile.getName(), requestBody);
                Log.e("UploadPostData1", "Lỗi: ");
            }
            String name = edtAuthorName.getText().toString();
            String description = edtDescription.getText().toString();
            author.setAuthorName(name);
            author.setBiography(description);
            String authorDtoJson = new Gson().toJson(author);

            RequestBody authorDtoBody = RequestBody.create(MediaType.parse("application/json"), authorDtoJson);
            ApiService apiService = new ApiService();
            AuthorApi authorApi = apiService.getRetrofit().create(AuthorApi.class);
            authorApi.updateAuthor(image, authorDtoBody).enqueue(new Callback<Author>() {
                @Override
                public void onResponse(Call<Author> call, Response<Author> response) {
                    Log.e("Upload", "Lỗi: " );
                    progressDialog.dismiss();
                    if (response.isSuccessful() && response.body() != null) {
                        DialogUtils.showSuccessDialog(EditAuthorActivity.this, "Thêm tác giả thành công", new DialogCallBack() {
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