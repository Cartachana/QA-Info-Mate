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
        //on creation of view, our item card is inflated into the holder
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.forum_topic_card, parent, false);
        Holder holder = new Holder(v);
        return holder;
    }

    @Override //gets size of list of items co tell the adapter how many cards are needed
    public void onBindViewHolder(@NonNull Holder holder, int i) {
        holder.rep.setVisibility(View.INVISIBLE);
        holder.reps.setVisibility(View.INVISIBLE);

        holder.fromId.setText("by: " + threads.get(i).getFromID());
        holder.comment.setText("Message: " + threads.get(i).getMessage());
        holder.date.setText("On " + threads.get(i).getDate());
    }

    @Override
    public int getItemCount() {return threads.size();}


    public static class Holder extends RecyclerView.ViewHolder {
        //creating java counterparts of the item card
        TextView fromId, date, comment, rep, reps;

        public Holder(@NonNull View itemView) {
            super(itemView);
            //our holder will link our java counterparts with the xml elements
            fromId = itemView.findViewById(R.id.tv_fromID_forum_topic_rec_view);
            date = itemView.findViewById(R.id.tv_date_forum);
            comment = itemView.findViewById(R.id.tv_main_subject_forum_topic_rec_view);
            rep = itemView.findViewById(R.id.tv_numReplies_forumCard);
            reps = itemView.findViewById(R.id.tv_numReplies);
        }
    }
}
