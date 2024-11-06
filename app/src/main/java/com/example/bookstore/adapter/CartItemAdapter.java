package com.example.bookstore.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookstore.R;
import com.example.bookstore.dto.CartItem;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class CartItemAdapter extends RecyclerView.Adapter<CartItemAdapter.ViewHolder> {
    ArrayList<CartItem> listCartItem;
    private Context context;

    public CartItemAdapter(Context context, ArrayList<CartItem> listCartItem) {
        this.context = context;
        this.listCartItem = listCartItem;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView bookImg;
        TextView bookTitle, price, totalPrice, quantity;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            bookImg = itemView.findViewById(R.id.bookImg);
            bookTitle = itemView.findViewById(R.id.tvBookTitle);
            price = itemView.findViewById(R.id.tvPrice);
            totalPrice = itemView.findViewById(R.id.tvTotalPrice);
            quantity = itemView.findViewById(R.id.tvQuantity);
        }
    }

    @NonNull
    @Override
    public CartItemAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_view_cart, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartItemAdapter.ViewHolder holder, int position) {
        CartItem cartItem = listCartItem.get(position);
        Log.e("CartAdapte", "Position: " + listCartItem.size() + " is null");
        if (cartItem == null) {
            Log.e("CartAdapte", "Position: " + position + " is null");
            return;
        }

        holder.bookTitle.setText(cartItem.getBookTitle());
        holder.price.setText(String.format("%s", "$" + cartItem.getPrice() + "0"));
        holder.totalPrice.setText(String.format("%s","$" + cartItem.getTotalPrice() + "0"));
        holder.quantity.setText(String.format("%s", cartItem.getQuantity()));
        holder.bookImg.setImageResource(cartItem.getImageResId());
    }

    @Override
    public int getItemCount() {
        return listCartItem.size();
    }


}
