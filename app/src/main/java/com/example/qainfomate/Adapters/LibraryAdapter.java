package com.example.qainfomate.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.qainfomate.Models.Library_Book;
import com.example.qainfomate.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class LibraryAdapter extends RecyclerView.Adapter<LibraryAdapter.Holder> {

    ArrayList<Library_Book> list;
    Holder.recInterface listener;

    public LibraryAdapter(ArrayList<Library_Book> list, Holder.recInterface _listener) {
        this.list = list;
        listener = _listener;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.lib_book_card, parent, false);
        Holder holder = new Holder(v, listener);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int i) {
        holder.title.setText(list.get(i).getTitle());
        holder.author.setText(list.get(i).getAuthor());
        Picasso.get().load(list.get(i).getImageUrl()).fit().into(holder.bookimg);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class Holder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView title, author;
        ImageView bookimg;
        recInterface listener;
        public Holder(@NonNull View itemView, recInterface _listener) {
            super(itemView);
            title = itemView.findViewById(R.id.tv_title_libcard);
            author = itemView.findViewById(R.id.tv_author_libcard);
            bookimg = itemView.findViewById(R.id.iv_bookImg_libcard);
            listener = _listener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            listener.onItemClick(getAdapterPosition());
        }
        public interface recInterface{
            void onItemClick(int i);
        }
    }
}