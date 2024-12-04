package com.example.bookstore.adapter.admin;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.bookstore.R;
import com.example.bookstore.activity.admin.AuthorActivity;
import com.example.bookstore.activity.admin.CategoryActivity;
import com.example.bookstore.activity.admin.EditAuthorActivity;
import com.example.bookstore.activity.admin.EditCategoryActivity;
import com.example.bookstore.api.admin.AuthorApi;
import com.example.bookstore.api.admin.CategoryApi;
import com.example.bookstore.model.ApiResponse;
import com.example.bookstore.model.Author;
import com.example.bookstore.model.Category;
import com.example.bookstore.retrofit.ApiService;
import com.example.bookstore.support.DialogCallBack;
import com.example.bookstore.utils.DialogUtils;
import com.example.bookstore.utils.HttpCodeUtils;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AuthorAdapter extends RecyclerView.Adapter<AuthorAdapter.AuthorViewHolder>{

    private List<Author> authorList;
    private Context context;

    public AuthorAdapter(List<Author> authorList, Context context) {
        this.authorList = authorList;
        this.context = context;
    }
    @NonNull
    @Override
    public AuthorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_item_author, parent, false);
        return new AuthorAdapter.AuthorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AuthorViewHolder holder, int position) {
        Author author = authorList.get(position);
        String limitedText = getLimitedWords(author.getBiography(), 5);
        holder.authorImg.setImageResource(R.drawable.img_4);
        holder.tvAuthorName.setText(author.getAuthorName());
        holder.tvYear.setText(limitedText);
        Glide.with(holder.itemView.getContext())
                .load(author.getAuthorImg())
                .into(holder.authorImg);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onclickDetail(author);
            }
        });

        holder.trashIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message = "Bạn chắc chắn muốn xóa tác giả: " + author.getAuthorName() + "?";
                DialogUtils.showWarningDialog(context, message, new DialogCallBack() {
                    @Override
                    public void onConfirm() {
                        deleteAuthor(author, position);
                    }
                });

            }
        });
    }

    private void deleteAuthor(Author author, int position) {
        ApiService apiService = new ApiService();
        AuthorApi authorApi = apiService.getRetrofit().create(AuthorApi.class);
        authorApi.deleteAuthor(author.getAuthorId()).enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(@NonNull Call<ApiResponse> call, @NonNull Response<ApiResponse> response) {
                if (response.isSuccessful() && response.body() != null) {

                    ApiResponse apiResponse = response.body();
                    DialogUtils.showSuccessDialog(context, apiResponse.getMessage());
                    authorList.remove(author);
                    notifyItemRemoved(position);
                    notifyItemRangeChanged(position, authorList.size());
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

    private String getLimitedWords(String text, int wordLimit) {
        String[] words = text.split("\\s+");
        if (words.length > wordLimit) {
            return TextUtils.join(" ", Arrays.copyOfRange(words, 0, wordLimit)) + "...";
        }
        return text;
    }

    private void onclickDetail(Author author) {
        Intent detail = new Intent(context, EditAuthorActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("Object:" , author);
        detail.putExtras(bundle);
        ((AuthorActivity) context).startActivityForResult(detail, HttpCodeUtils.REQUEST_SUCCESS);
    }

    @Override
    public int getItemCount() {
        return authorList.size();
    }

    public static class AuthorViewHolder extends RecyclerView.ViewHolder {
        ImageView authorImg, trashIcon;
        TextView tvAuthorName, tvYear;
        CardView cardView;

        public AuthorViewHolder(@NonNull View itemView) {
            super(itemView);
            authorImg = itemView.findViewById(R.id.authorImg);
            tvAuthorName = itemView.findViewById(R.id.tvAuthorName);
            tvYear = itemView.findViewById(R.id.tvYear);
            trashIcon = itemView.findViewById(R.id.trashIcon);
            cardView = (CardView) itemView;
        }
    }

}
