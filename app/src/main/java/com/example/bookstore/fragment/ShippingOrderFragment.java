package com.example.bookstore.fragment;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bookstore.R;
import com.example.bookstore.adapter.admin.OrderAdapter;
import com.example.bookstore.api.admin.OrderApi;
import com.example.bookstore.dto.Order;
import com.example.bookstore.retrofit.ApiService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ShippingOrderFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ShippingOrderFragment extends Fragment {
    private RecyclerView orderRecyclerView;
    private OrderAdapter orderAdapter;
    private List<Order> orderList = new ArrayList<>();
    public ShippingOrderFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_order, container, false);
        orderRecyclerView = view.findViewById(R.id.orderView);
        orderRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        getData();
        return view;
    }

    private void getData() {
        ApiService apiService = new ApiService();
        OrderApi orderApi = apiService.getRetrofit().create(OrderApi.class);

        orderApi.getOrdersByStatus(1).enqueue(new Callback<List<Order>>() {
            @Override
            public void onResponse(Call<List<Order>> call, Response<List<Order>> response) {
                orderList  = response.body();
                orderAdapter = new OrderAdapter(getContext(), orderList);
                orderRecyclerView.setAdapter(orderAdapter);
            }

            @Override
            public void onFailure(Call<List<Order>> call, Throwable throwable) {

            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        getData();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}