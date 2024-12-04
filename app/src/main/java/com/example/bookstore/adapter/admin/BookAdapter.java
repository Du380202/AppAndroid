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
import androidx.recyclerview.widget.RecyclerView;
import androidx.cardview.widget.CardView;

import com.bumptech.glide.Glide;
import com.example.bookstore.R;
import com.example.bookstore.activity.admin.BookActivity;
import com.example.bookstore.activity.admin.EditBookActivity;
import com.example.bookstore.api.admin.BookApi;
import com.example.bookstore.dto.BookDto;
import com.example.bookstore.model.ApiResponse;
import com.example.bookstore.model.Book;
import com.example.bookstore.retrofit.ApiService;
import com.example.bookstore.support.DialogCallBack;
import com.example.bookstore.utils.DialogUtils;
import com.example.bookstore.utils.HttpCodeUtils;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.List;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookViewHolder> {

    private List<Book> bookList;
    private Context context;

    public BookAdapter(List<Book> bookList, Context context) {
        this.bookList = bookList;
        this.context = context;
    }

    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_book_item, parent, false);
        return new BookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
        Book book = bookList.get(position);
        holder.tvBookTitle.setText(book.getBookName());
        holder.tvAuthor.setText(getLimitedWords(book.getDescription(), 5));
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.tvPrice.setText(decimalFormat.format(book.getPrice()));
        Glide.with(holder.itemView.getContext())
                .load(book.getImage())
                .into(holder.bookImg);
        holder.trashIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message = "Bạn chắc chắn muốn xóa sách: " + book.getBookName() + "?";
                DialogUtils.showWarningDialog(context, message, new DialogCallBack() {
                    @Override
                    public void onConfirm() {
                        deleteBook(book, position);
                    }
                });

            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onclickDetail(book);
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

    private void deleteBook(Book book, int position) {
        ApiService apiService = new ApiService();
        BookApi bookApi = apiService.getRetrofit().create(BookApi.class);
        bookApi.deleteBook(book.getBookId()).enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.isSuccessful() && response.body() != null) {

                    ApiResponse apiResponse = response.body();
                    DialogUtils.showSuccessDialog(context, apiResponse.getMessage());
                    bookList.remove(book);
                    notifyItemRemoved(position);
                    notifyItemRangeChanged(position, bookList.size());
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
            public void onFailure(Call<ApiResponse> call, Throwable throwable) {
                Log.e("ErrorDelBook", throwable.getMessage());
            }
        });
    }

    @Override
    public int getItemCount() {
        return bookList.size();
    }

    public static class BookViewHolder extends RecyclerView.ViewHolder {
        TextView tvBookTitle, tvAuthor, tvPrice, tvStatus;
        ImageView bookImg, trashIcon;
        CardView cardView;

        public BookViewHolder(@NonNull View itemView) {
            super(itemView);
            tvBookTitle = itemView.findViewById(R.id.tvBookTitle);
            tvAuthor = itemView.findViewById(R.id.tvAuthor);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            tvStatus = itemView.findViewById(R.id.tvStatus);
            bookImg = itemView.findViewById(R.id.bookImg);
            trashIcon = itemView.findViewById(R.id.trashIcon);
            cardView = (CardView) itemView;
        }
    }

    private void onclickDetail(Book book) {
        Intent detail = new Intent(context, EditBookActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("Object:" , book);
        detail.putExtras(bundle);
        ((BookActivity)context).startActivityForResult(detail, HttpCodeUtils.REQUEST_SUCCESS);
    }
}
