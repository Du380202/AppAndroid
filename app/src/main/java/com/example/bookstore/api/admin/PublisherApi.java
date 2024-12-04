package com.example.bookstore.api.admin;

import com.example.bookstore.model.Publisher;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface PublisherApi {

    @GET("publisher")
    Call<List<Publisher>> getAll();

}
