package com.example.qainfomate.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.qainfomate.Models.Topic;
import com.example.qainfomate.R;

import java.util.ArrayList;

public class ForumAdapter extends RecyclerView.Adapter<ForumAdapter.Holder>{
    ArrayList<Topic> topics;
    Holder.ForumInterface listener;

    public ForumAdapter(ArrayList topics, Holder.ForumInterface _listener) {
        this.topics = topics;
        listener = _listener;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //on creation of view, our item card is inflated into the holder
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.forum_topic_card, parent, false);
        Holder holder = new Holder(v, listener);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int i) {
        //at each item card gets the required values from our list and set's the holder's attributes with them
        holder.fromId.setText("Published by: " + topics.get(i).getStuId());
        holder.subject.setText("Subject: " + topics.get(i).getSubject());
        holder.date.setText("On " + topics.get(i).getDate());
    }

    @Override //gets size of list of items co tell the adapter how many cards are needed
    public int getItemCount() {return topics.size();}

    public static class Holder extends RecyclerView.ViewHolder implements View.OnClickListener{

        //creating java counterparts of the item card
        private TextView fromId, subject, date;
        ForumInterface listener;

        public Holder(@NonNull View itemView, ForumInterface _listener) {
            super(itemView);
            //our holder will link our java counterparts with the xml elements
            fromId = itemView.findViewById(R.id.tv_fromID_forum_topic_rec_view);
            subject = itemView.findViewById(R.id.tv_main_subject_forum_topic_rec_view);
            date = itemView.findViewById(R.id.tv_date_forum);
            listener = _listener;
            itemView.setOnClickListener(this);
        }

        @Override //gets the adapter position where the user taps
        public void onClick(View v) {listener.onItemClick(getAdapterPosition());

        }

        public interface ForumInterface{
            /*interface to be implemented by activity class to tell adapter what to do on item click
            and that same class will be  passed to the adapter constructor as a listener interface for the item touch*/
            public void onItemClick(int i);
        }
    }

}
