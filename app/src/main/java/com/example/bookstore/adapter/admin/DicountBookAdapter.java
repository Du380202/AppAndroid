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
import com.example.bookstore.dto.OrderDetail;
import com.example.bookstore.model.Book;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.List;

public class DicountBookAdapter extends RecyclerView.Adapter<DicountBookAdapter.ProductViewHolder> {
    private Context context;
    private Integer discountPercent;
    private List<Book> bookList;

    public DicountBookAdapter(Context context, List<Book> bookList, Integer discountPercent) {
        this.context = context;
        this.bookList = bookList;
        this.discountPercent = discountPercent;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.book_item, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Book product = bookList.get(position);
        holder.tvProductName.setText(product.getBookName());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.tvProductQuantity.setText("Giá gốc: đ" + decimalFormat.format(product.getPrice()));
        BigDecimal discountPrice = product.getPrice()
                .multiply(BigDecimal.valueOf(30))
                .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);

        holder.tvTotalPrice.setText("Giá giảm: đ" + decimalFormat.format(product.getPrice().subtract(discountPrice)));
        Glide.with(holder.itemView.getContext())
                .load(product.getImage())
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
