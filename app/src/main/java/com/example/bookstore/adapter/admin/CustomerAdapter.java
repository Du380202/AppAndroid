package com.example.bookstore.adapter.admin;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.bookstore.R;
import com.example.bookstore.activity.admin.CategoryActivity;
import com.example.bookstore.activity.admin.CustomerDetailActivity;
import com.example.bookstore.activity.admin.EditCategoryActivity;
import com.example.bookstore.dto.UserDto;
import com.example.bookstore.model.Category;
import com.example.bookstore.model.User;
import com.example.bookstore.utils.HttpCodeUtils;

import java.util.List;

public class CustomerAdapter extends RecyclerView.Adapter<CustomerAdapter.CustomerViewHolder> {
    private List<User> userList;
    private Context context;

    public CustomerAdapter(List<User> userList, Context context) {
        this.userList = userList;
        this.context = context;
    }

    @NonNull
    @Override
    public CustomerAdapter.CustomerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_item_customer, parent, false);
        return new CustomerAdapter.CustomerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomerAdapter.CustomerViewHolder holder, int position) {
        User user = userList.get(position);
        holder.tvCustomerName.setText(user.getFullName());
        holder.tvCustomerPhone.setText(user.getPhoneNumber());
        Glide.with(holder.itemView.getContext())
                .load(user.getAvatar())
                .into(holder.imgAvatar);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onclickDetail(user);
            }
        });
    }
    private void onclickDetail(User user) {
        Intent detail = new Intent(context, CustomerDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("Object:" , user);
        detail.putExtras(bundle);
        context.startActivity(detail);

    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public static class CustomerViewHolder extends RecyclerView.ViewHolder {
        TextView tvCustomerName, tvCustomerPhone;
        ImageView imgAvatar;

        public CustomerViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCustomerName = itemView.findViewById(R.id.tvCustomerName);
            tvCustomerPhone = itemView.findViewById(R.id.tvCustomerPhone);
            imgAvatar = itemView.findViewById(R.id.customerImg);
        }
    }
}
