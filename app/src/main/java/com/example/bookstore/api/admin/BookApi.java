package com.example.bookstore.api.admin;

import com.example.bookstore.model.ApiResponse;
import com.example.bookstore.model.Book;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface BookApi {

    @GET("book")
    Call<List<Book>> getAllBook();

    @Multipart
    @POST("book")
    Call<String> createBook(@Part MultipartBody.Part image,
                                @Part("bookDto") RequestBody bookDto);

    @Multipart
    @PUT("book")
    Call<String> updateBook(@Part MultipartBody.Part image,
                            @Part("bookDto") RequestBody bookDto);

    @DELETE("book/{ids}")
    Call<ApiResponse> deleteBook(@Path("ids") int ids);
}
