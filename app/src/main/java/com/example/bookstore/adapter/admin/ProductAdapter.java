package com.example.bookstore.adapter.admin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.bookstore.R;
import com.example.bookstore.dto.BookDto;
import com.example.bookstore.dto.OrderDetail;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {
    private Context context;
    private List<OrderDetail> bookList;

    public ProductAdapter(Context context, List<OrderDetail> bookList) {
        this.context = context;
        this.bookList = bookList;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.book_item, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        OrderDetail product = bookList.get(position);
        holder.tvProductName.setText(product.getBook().getBookName());
        holder.tvProductQuantity.setText("Số lương: " + String.valueOf(product.getQuantity()));
        holder.tvTotalPrice.setText("Tổng tiền: đ" + product.getTotalPrice().toString());
        Glide.with(holder.itemView.getContext())
                .load(product.getBook().getImage())
                .into(holder.ivProductImage);
    }

    @Override
    public int getItemCount() {
        return bookList.size();
    }

    public static class ProductViewHolder extends RecyclerView.ViewHolder {
        ImageView ivProductImage;
        TextView tvProductName, tvProductQuantity, tvTotalPrice;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            ivProductImage = itemView.findViewById(R.id.imgDetail);
            tvProductName = itemView.findViewById(R.id.titleBook);
            tvProductQuantity = itemView.findViewById(R.id.tvQuantity);
            tvTotalPrice = itemView.findViewById(R.id.tvTotalPrice);
        }
    }
}
