package com.example.bookstore.adapter.admin;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.bookstore.R;
import com.example.bookstore.activity.admin.CategoryActivity;
import com.example.bookstore.activity.admin.EditBookActivity;
import com.example.bookstore.activity.admin.EditCategoryActivity;
import com.example.bookstore.api.admin.CategoryApi;
import com.example.bookstore.model.ApiResponse;
import com.example.bookstore.model.Book;
import com.example.bookstore.model.Category;
import com.example.bookstore.retrofit.ApiService;
import com.example.bookstore.support.DialogCallBack;
import com.example.bookstore.utils.DialogUtils;
import com.example.bookstore.utils.HttpCodeUtils;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {
    private List<Category> categoryList;
    private Context context;

    public CategoryAdapter(List<Category> categoryList, Context context) {
        this.categoryList = categoryList;
        this.context = context;
    }

    @NonNull
    @Override
    public CategoryAdapter.CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_item_category, parent, false);
        return new CategoryAdapter.CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        Category category = categoryList.get(position);
        holder.tvQuantityBook.setText(String.format("%s", "Số lượng sách:"));
        holder.tvCategoryName.setText(category.getCategoryName());
        Glide.with(holder.itemView.getContext())
                .load(category.getCategoryImg())
                .into(holder.categoryImg);

        holder.trashIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message = "Bạn chắc chắn muốn xóa thể loại: " + category.getCategoryName() + "?";
                DialogUtils.showWarningDialog(context, message, new DialogCallBack() {
                    @Override
                    public void onConfirm() {
                        deleteCategory(category, holder.getAdapterPosition());
                    }
                });

            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                onclickDetail(category);
            }
        });
    }

    private void deleteCategory(Category category, int position) {
        ApiService apiService = new ApiService();
        CategoryApi categoryApi = apiService.getRetrofit().create(CategoryApi.class);
        categoryApi.deleteCategory(category.getCategoryId()).enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(@NonNull Call<ApiResponse> call, @NonNull Response<ApiResponse> response) {
                if (response.isSuccessful() && response.body() != null) {

                    ApiResponse apiResponse = response.body();
                    DialogUtils.showSuccessDialog(context, apiResponse.getMessage());
                    categoryList.remove(category);
                    notifyItemRemoved(position);
                    notifyItemRangeChanged(position, categoryList.size());
                } else {
                    try {
                        if (response.errorBody() != null) {
                            Gson gson = new Gson();
                            ApiResponse errorResponse = gson.fromJson(response.errorBody().string(), ApiResponse.class);
                            DialogUtils.showErrorDialog(context, errorResponse.getMessage());
                        } else {
                            Log.e("Error", "HTTP Status: " + response.code() + ", Message: Không có nội dung lỗi");
                        }
                    } catch (IOException e) {
                        Log.e("Error", e.getMessage(), e);
                    }
                }

            }

            @Override
            public void onFailure(@NonNull Call<ApiResponse> call, @NonNull Throwable throwable) {
                Log.e("Error", Objects.requireNonNull(throwable.getMessage()));
            }
        });
    }

    private void onclickDetail(Category category) {
        Intent detail = new Intent(context, EditCategoryActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("Object:", category);
        detail.putExtras(bundle);
        ((CategoryActivity) context).startActivityForResult(detail, HttpCodeUtils.REQUEST_SUCCESS);
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    public static class CategoryViewHolder extends RecyclerView.ViewHolder {
        TextView tvQuantityBook, tvCategoryName;
        ImageView categoryImg, trashIcon;
        CardView cardView;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCategoryName = itemView.findViewById(R.id.tvCategoryName);
            tvQuantityBook=itemView.findViewById(R.id.tvQuantityBook);
            categoryImg = itemView.findViewById(R.id.categoryImg);
            trashIcon = itemView.findViewById(R.id.trashIcon);
            cardView = (CardView) itemView;
        }
    }
}
