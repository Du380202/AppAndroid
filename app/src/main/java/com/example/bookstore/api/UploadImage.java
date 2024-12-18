package com.example.bookstore.api;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface UploadImage {
    @Multipart
    @POST("upload")
    Call<String> uploadImage(@Part MultipartBody.Part image,
                             @Part("categoryDto") RequestBody categoryDto);
//    @Multipart
//    @POST("upload")
//    Call<String> uploadImage(@Part MultipartBody.Part image);
}
