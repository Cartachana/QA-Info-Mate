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
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.forum_topic_card, parent, false);
        Holder holder = new Holder(v, listener);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int i) {
        holder.fromId.setText("Published by: " + topics.get(i).getStuId());
        holder.subject.setText("Subject: " + topics.get(i).getSubject());
        holder.date.setText("On " + topics.get(i).getDate());
    }

    @Override
    public int getItemCount() {return topics.size();}

    public static class Holder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView fromId, subject, date;
        ForumInterface listener;

        public Holder(@NonNull View itemView, ForumInterface _listener) {
            super(itemView);
            fromId = itemView.findViewById(R.id.tv_fromID_forum_topic_rec_view);
            subject = itemView.findViewById(R.id.tv_main_subject_forum_topic_rec_view);
            date = itemView.findViewById(R.id.tv_date_forum);
            listener = _listener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {listener.onItemClick(getAdapterPosition());

        }

        public interface ForumInterface{
            public void onItemClick(int i);
        }
    }

}
