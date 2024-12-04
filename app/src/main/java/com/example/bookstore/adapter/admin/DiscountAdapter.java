package com.example.bookstore.adapter.admin;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookstore.R;
import com.example.bookstore.activity.admin.BookActivity;
import com.example.bookstore.activity.admin.DiscountActivity;
import com.example.bookstore.activity.admin.DiscountDetailActivity;
import com.example.bookstore.activity.admin.EditBookActivity;
import com.example.bookstore.model.Book;
import com.example.bookstore.model.Discount;
import com.example.bookstore.utils.HttpCodeUtils;

import java.util.List;

public class DiscountAdapter extends RecyclerView.Adapter<DiscountAdapter.DiscountViewHolder> {

    private final List<Discount> discountItems;
    private final Context context;

    // Constructor
    public DiscountAdapter(Context context, List<Discount> discountItems) {
        this.context = context;
        this.discountItems = discountItems;
    }

    // ViewHolder class
    public static class DiscountViewHolder extends RecyclerView.ViewHolder {
        TextView tvProductName, tvDiscount, tvOriginalPrice;
        ImageView btnDelete;

        public DiscountViewHolder(@NonNull View itemView) {
            super(itemView);

            tvProductName = itemView.findViewById(R.id.tvProductName);
            tvDiscount = itemView.findViewById(R.id.tvDiscount);
//            tvOriginalPrice = itemView.findViewById(R.id.tvOriginalPrice);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }

    @NonNull
    @Override
    public DiscountViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_item_discount, parent, false);
        return new DiscountViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DiscountViewHolder holder, int position) {
        Discount item = discountItems.get(position);

        holder.tvProductName.setText(item.getDiscountName());
        holder.tvDiscount.setText(String.valueOf(item.getDiscountPercent()) + "% OFF");
//        holder.tvOriginalPrice.setText(item.getOriginalPrice());

        holder.btnDelete.setOnClickListener(v -> {
            Toast.makeText(context, "Deleted item at position: " + position, Toast.LENGTH_SHORT).show();
            discountItems.remove(position);
            notifyItemRemoved(position);
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onclickDetail(item);
            }
        });
    }

    private void onclickDetail(Discount discount) {
        Intent detail = new Intent(context, DiscountDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("Object:" , discount);
        detail.putExtras(bundle);
        ((DiscountActivity)context).startActivityForResult(detail, HttpCodeUtils.REQUEST_SUCCESS);
    }

    @Override
    public int getItemCount() {
        return discountItems.size();
    }
}
