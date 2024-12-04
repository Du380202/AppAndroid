package com.example.bookstore.adapter.admin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookstore.R;
import com.example.bookstore.model.Publisher;

import java.util.List;

public class PublisherAdapter extends RecyclerView.Adapter<PublisherAdapter.PublisherViewHolder> {
    private List<Publisher> publishers;
    private Context context;

    public PublisherAdapter(List<Publisher> publishers, Context context) {
        this.publishers = publishers;
        this.context = context;
    }

    @NonNull
    @Override
    public PublisherAdapter.PublisherViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_item_publisher, parent, false);
        return new PublisherAdapter.PublisherViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PublisherAdapter.PublisherViewHolder holder, int position) {
        Publisher publisher = publishers.get(position);
        holder.tvPublisherName.setText(publisher.getPublisherName());
        holder.tvPhone.setText(publisher.getHotline());
        holder.publisherImg.setImageResource(R.drawable.publishinghouse);
    }

    @Override
    public int getItemCount() {
        return publishers.size();
    }

    public static class PublisherViewHolder extends RecyclerView.ViewHolder {
        TextView tvPublisherName, tvPhone;
        ImageView publisherImg;
        CardView cardView;

        public PublisherViewHolder(@NonNull View itemView) {
            super(itemView);
            publisherImg = itemView.findViewById(R.id.publisherImg);
            tvPublisherName = itemView.findViewById(R.id.tvPublisherName);
            tvPhone = itemView.findViewById(R.id.tvPhone);
            cardView = (CardView) itemView;
        }
    }
}
