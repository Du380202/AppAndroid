package com.example.bookstore.api.admin;

import com.example.bookstore.dto.Order;
import com.example.bookstore.model.ApiResponse;
import com.example.bookstore.model.Author;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface AuthorApi {
    @GET("author")
    Call<List<Author>> getAllAuthor();

    @Multipart
    @POST("author")
    Call<Author> createAuthor(@Part MultipartBody.Part image,
                            @Part("authorDto") RequestBody authorDto);
    @Multipart
    @PUT("author")
    Call<Author> updateAuthor(@Part MultipartBody.Part image,
                              @Part("authorDto") RequestBody authorDto);

    @DELETE("author/{id}")
    Call<ApiResponse> deleteAuthor(@Path("id") int id);
}
