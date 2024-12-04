package com.example.bookstore.api.admin;

import com.example.bookstore.dto.Order;
import com.example.bookstore.dto.RevenueDto;
import com.example.bookstore.model.ApiResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface OrderApi {
    @GET("order")
    Call<List<Order>> getOrdersByUserId(@Query("userId") Integer userId);

    @GET("order/all")
    Call<List<Order>> getOrders();

    @GET("order/status")
    Call<List<Order>> getOrdersByStatus(@Query("status") Integer status);

    @GET("order/revenue")
    Call<List<RevenueDto>> getRevenue(@Query("year") Integer year);

    @PUT("order/update")
    Call<ApiResponse> updateStatus(@Query("orderId") Integer orderId, @Query("statusNumber") Integer statusNumber);
}
