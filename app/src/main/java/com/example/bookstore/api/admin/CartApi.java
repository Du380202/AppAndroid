package com.example.bookstore.api.admin;

import com.example.bookstore.model.Cart;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface CartApi {

    @GET("cart")
    Call<List<Cart>> getCartByUserId(@Query("userId") Integer userId);
}
