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

public class MyBooksAdapter extends RecyclerView.Adapter<MyBooksAdapter.Holder> {

    ArrayList<Book_for_Sale> list;
    Holder.recInterface listener;

    public MyBooksAdapter(ArrayList list, Holder.recInterface _listener) {
        this.list = list;
        listener = _listener;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //on creation of view, our item card is inflated into the holder
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.mybooks_recview_card, parent, false);
        Holder holder = new Holder(v, listener);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyBooksAdapter.Holder holder, int i) {
        //at each item card gets the required values from our list and set's the holder's attributes with them
        holder.title.setText("Title: " + list.get(i).getTitle());
        holder.author.setText("Author: " + list.get(i).getAuthor());
        holder.category.setText("Category: " + list.get(i).getCategory());
        if(list.get(i).getAvaiable()){
            holder.isAvailable.setText("Book is Available");
        }else{holder.isAvailable.setText("Book is NOT Available");}
        Picasso.get().load(list.get(i).getImageUrl()).fit().into(holder.bookimg);
    }

    @Override //gets size of list of items co tell the adapter how many cards are needed
    public int getItemCount() {return list.size();}

    public static class Holder extends RecyclerView.ViewHolder implements View.OnClickListener{
        //creating java counterparts of the item card
        TextView title, author, isAvailable, category;
        ImageView bookimg;
        recInterface listener;

        public Holder(@NonNull View itemView, recInterface _listener) {
            super(itemView);
            //our holder will link our java counterparts with the xml elements
            title = itemView.findViewById(R.id.tv_title_mybooks);
            author = itemView.findViewById(R.id.tv_author_mybooks);
            isAvailable = itemView.findViewById(R.id.tv_isavaiable_mybooks);
            category = itemView.findViewById(R.id.tv_category_mybooks);
            bookimg = itemView.findViewById(R.id.iv_bookImage_mybooks);
            listener = _listener;
            itemView.setOnClickListener(this);
        }

        @Override //gets the adapter position where the user taps
        public void onClick(View v) {listener.onItemClick(getAdapterPosition());}

        public interface recInterface{
            /*interface to be implemented by activity class to tell adapter what to do on item click
            and that same class will be  passed to the adapter constructor as a listener interface for the item touch*/
            void onItemClick(int i);
        }
    }
}
