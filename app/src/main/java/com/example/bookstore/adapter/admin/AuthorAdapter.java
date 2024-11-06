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
import com.example.bookstore.dto.Author;
import com.example.bookstore.dto.Book;

import java.util.List;

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
    public void onBindViewHolder(@NonNull AuthorAdapter.AuthorViewHolder holder, int position) {
        Author author = authorList.get(position);
        holder.authorImg.setImageResource(author.getImgId());
        holder.tvAuthorName.setText(author.getAuthorName());
        holder.tvYear.setText(author.getYearBirthday());

    }

    @Override
    public int getItemCount() {
        return authorList.size();
    }

    public static class AuthorViewHolder extends RecyclerView.ViewHolder {
        ImageView authorImg;
        TextView tvAuthorName, tvYear;
        CardView cardView;

        public AuthorViewHolder(@NonNull View itemView) {
            super(itemView);
            authorImg = itemView.findViewById(R.id.authorImg);
            tvAuthorName = itemView.findViewById(R.id.tvAuthorName);
            tvYear = itemView.findViewById(R.id.tvYear);
            cardView = (CardView) itemView;
        }
    }

}
