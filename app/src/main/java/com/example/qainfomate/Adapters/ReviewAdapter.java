package com.example.qainfomate.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.qainfomate.Models.Review;
import com.example.qainfomate.R;

import java.util.ArrayList;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.Holder> {
    private ArrayList<Review> list;
    Holder.recInterface listener;

    public ReviewAdapter(ArrayList<Review> list, Holder.recInterface _listener) {
        this.list = list;
        listener = _listener;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.reviews_recview_card, parent, false);
        Holder holder = new Holder(v, listener);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int i) {
        holder.stuId.setText("Student: " + list.get(i).getStuId());
        holder.review.setText(list.get(i).getReview());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public static class Holder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView stuId, review;
        recInterface listener;

        public Holder(@NonNull View itemView, recInterface _listener) {
            super(itemView);
            stuId = itemView.findViewById(R.id.tv_fromID_review);
            review = itemView.findViewById(R.id.tv_review_reviews_rec_view);
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
