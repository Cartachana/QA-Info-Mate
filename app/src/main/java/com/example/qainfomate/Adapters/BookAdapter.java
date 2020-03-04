package com.example.qainfomate.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.qainfomate.Models.Book_for_Sale;
import com.example.qainfomate.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.Holder> {

    ArrayList<Book_for_Sale> list;
    Holder.recInterface listener;

    public BookAdapter(ArrayList<Book_for_Sale> list, Holder.recInterface _listener) {
        this.list = list;
        listener = _listener;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.listitem_book_rec_view, parent, false);
        Holder holder = new Holder(v, listener);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int i) {
        //IF IT IS A BOOK FOR SALE USE THIS HOLDER
        if(list.get(i).getType().equals("BFS")) {
            if (list.get(i).getAvaiable() == true) {
                holder.title.setText("Title: " + list.get(i).getTitle());
                holder.author.setText("Author: " + list.get(i).getAuthor());
                holder.category.setText("Category: " + list.get(i).getCategory());
                holder.sellerID.setText("Seller ID: " + list.get(i).getStuId());
                Picasso.get().load(list.get(i).getImageUrl()).fit().into(holder.bookimg);
            }
        }
        //IF IT IS A LIBRARY BOOK USE THIS ONE...ETC


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class Holder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView title, author, sellerID, category;
        ImageView bookimg;
        recInterface listener;

        public Holder(@NonNull View itemView, recInterface _listener) {
            super(itemView);
            title = itemView.findViewById(R.id.title_book_sale);
            author = itemView.findViewById(R.id.author_booksale);
            sellerID = itemView.findViewById(R.id.tv_sellerID_booksale);
            category = itemView.findViewById(R.id.category_booksale);
            bookimg = itemView.findViewById(R.id.bookImage);
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
