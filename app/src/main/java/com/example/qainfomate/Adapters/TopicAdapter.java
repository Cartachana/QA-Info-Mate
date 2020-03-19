package com.example.qainfomate.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.qainfomate.Models.Thread;
import com.example.qainfomate.R;

import java.util.ArrayList;

public class TopicAdapter extends RecyclerView.Adapter<TopicAdapter.Holder> {
    ArrayList<Thread> threads;

    public TopicAdapter(ArrayList<Thread> threads) {
        this.threads = threads;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.forum_topic_card, parent, false);
        Holder holder = new Holder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int i) {
        holder.fromId.setText("by: " + threads.get(i).getFromID());
        holder.comment.setText("Subject: " + threads.get(i).getMessage());
        holder.date.setText("On " + threads.get(i).getDate());
    }

    @Override
    public int getItemCount() {return threads.size();}


    public static class Holder extends RecyclerView.ViewHolder {

        TextView fromId, date, comment;

        public Holder(@NonNull View itemView) {
            super(itemView);
            fromId = itemView.findViewById(R.id.tv_fromID_forum_topic_rec_view);
            date = itemView.findViewById(R.id.tv_date_forum);
            comment = itemView.findViewById(R.id.tv_main_subject_forum_topic_rec_view);
        }
    }
}