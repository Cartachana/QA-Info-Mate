package com.example.qainfomate.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.qainfomate.Models.Library_Book;
import com.example.qainfomate.Models.Session;
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
        //on creation of view, our item card is inflated into the holder
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.lib_book_card, parent, false);
        Holder holder = new Holder(v, listener);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int i) {
        //at each item card gets the required values from our list and set's the holder's attributes with them
        holder.title.setText(list.get(i).getTitle());
        holder.author.setText(list.get(i).getAuthor());
        Picasso.get().load(list.get(i).getImageUrl()).fit().into(holder.bookimg);
            //if the book is NOT Available and it is NOT loaned to current user, set background RED
        if(!(list.get(i).getLoanedTo().equals("Available")) && !(list.get(i).getLoanedTo().equals(Session.LiveSession.user.getStuID()))){
            holder.libcard.setBackgroundResource(R.drawable.bg_red);
        }
    }

    @Override //gets size of list of items co tell the adapter how many cards are needed
    public int getItemCount() {
        return list.size();
    }

    public static class Holder extends RecyclerView.ViewHolder implements View.OnClickListener{
        //creating java counterparts of the item card
        TextView title, author;
        ImageView bookimg;
        recInterface listener;
        RelativeLayout libcard;

        public Holder(@NonNull View itemView, recInterface _listener) {
            super(itemView);
            //our holder will link our java counterparts with the xml elements
            title = itemView.findViewById(R.id.tv_title_libcard);
            author = itemView.findViewById(R.id.tv_author_libcard);
            bookimg = itemView.findViewById(R.id.iv_bookImg_libcard);
            listener = _listener;
            libcard = itemView.findViewById(R.id.lib_card_layout);
            itemView.setOnClickListener(this);
        }

        @Override //gets the adapter position where the user taps
        public void onClick(View v) {
            listener.onItemClick(getAdapterPosition());
        }

        public interface recInterface{
            /*interface to be implemented by activity class to tell adapter what to do on item click
            and that same class will be  passed to the adapter constructor as a listener interface for the item touch*/
            void onItemClick(int i);
        }
    }
}
