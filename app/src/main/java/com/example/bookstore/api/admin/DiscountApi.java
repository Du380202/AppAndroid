package com.example.bookstore.api.admin;

import com.example.bookstore.dto.DiscountDto;
import com.example.bookstore.model.ApiResponse;
import com.example.bookstore.model.Discount;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface DiscountApi {
    @GET("discount")
    Call<List<Discount>> getAll();

    @POST("discount")
    Call<Discount> createDiscount(@Body DiscountDto discountDto);

    @PUT("discount")
    Call<ApiResponse> cancelDiscount(@Query("discountId") Integer discountId);
}
