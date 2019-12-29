package com.example.cns09.study.Navi.BookAdapter;

import android.content.Context;
import android.content.Intent;
import android.text.Html;
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
import com.example.cns09.study.KyoboWebActivity;
import com.example.cns09.study.Navi.BookModel.Item;
import com.example.cns09.study.R;

import java.util.ArrayList;

public class BookAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private ArrayList<Item> mBookInfoArrayList;

    public BookAdapter(Context context, ArrayList<Item> bookInfoArrayList) {
        this.mContext = context;
        this.mBookInfoArrayList = bookInfoArrayList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.book_item, parent, false);
        return new BookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        BookViewHolder bookViewHolder = (BookViewHolder) holder;
        Item item = mBookInfoArrayList.get(position);
        bookViewHolder.tvTitle.setText(Html.fromHtml(item.getTitle()));
        String bookTitle = item.getTitle().replaceAll("<b>", "").replaceAll("</b>", "").trim();
        bookViewHolder.tvAuthor.setText(Html.fromHtml(item.getAuthor()));
        bookViewHolder.tvPrice.setText(Html.fromHtml(item.getPrice()));
        bookViewHolder.llBookBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, KyoboWebActivity.class);
                intent.putExtra("title", bookTitle);
                mContext.startActivity(intent);
            }
        });
        Glide.with(mContext)
                .load(item.getImage())
                .into(bookViewHolder.getImage());
    }

    @Override
    public int getItemCount() {
        return mBookInfoArrayList.size();
    }

    public void clearItems() {
        mBookInfoArrayList.clear();
        notifyDataSetChanged();
    }

    public void clearAndAddItems(ArrayList<Item> items) {
        mBookInfoArrayList.clear();
        mBookInfoArrayList.addAll(items);
        notifyDataSetChanged();
    }

    public static class BookViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout llBookBox;
        private ImageView ivPoster;
        private TextView tvTitle, tvAuthor, tvPrice;

        public BookViewHolder(@NonNull View itemView) {
            super(itemView);
            llBookBox = itemView.findViewById(R.id.ll_book_box);
            ivPoster = itemView.findViewById(R.id.iv_poster);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvAuthor = itemView.findViewById(R.id.tv_author);
            tvPrice = itemView.findViewById(R.id.tv_price);
        }

        public ImageView getImage() {
            return ivPoster;
        }
    }
}
