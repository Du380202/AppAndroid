package com.example.bookstore.api.admin;

import com.example.bookstore.dto.UserDto;
import com.example.bookstore.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface UserApi {
    @GET("user")
    Call<List<User>> getAllUser();

}
