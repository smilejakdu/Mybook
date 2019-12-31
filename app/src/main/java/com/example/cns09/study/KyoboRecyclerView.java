package com.example.cns09.study;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class KyoboRecyclerView extends RecyclerView.Adapter<KyoboRecyclerView.ViewHolder> {

    private ArrayList<KyoboItem> bookList;
    private Context context;

    public KyoboRecyclerView(ArrayList<KyoboItem> bookList, Context context) {
        this.bookList = bookList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.kyobo_row, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bookTitle.setText(String.valueOf(bookList.get(position).getTitle()));
        String title = bookList.get(position).getTitle().trim();
        holder.bookAuthor.setText(String.valueOf(bookList.get(position).getAuthor()));
        holder.bookPrice.setText(String.valueOf(bookList.get(position).getPrice()));
        Glide.with(holder.itemView)
                .load(bookList.get(position).getImg_url())
                .into(holder.bookImg);

        holder.llBookBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, bookList.get(position).getTitle(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, KyoboWebActivity.class);
                intent.putExtra("title", title);
                context.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return bookList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private LinearLayout llBookBox;
        private ImageView bookImg;
        private TextView bookTitle;
        private TextView bookAuthor;
        private TextView bookPrice;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            llBookBox = itemView.findViewById(R.id.ll_book_box);
            bookPrice = itemView.findViewById(R.id.tv_my_sell_money);
            bookImg = itemView.findViewById(R.id.iv_book_img);
            bookTitle = itemView.findViewById(R.id.tv_book_title);
            bookAuthor = itemView.findViewById(R.id.tv_book_author);
        }
    }
}
