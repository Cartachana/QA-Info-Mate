package com.example.qainfomate.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.qainfomate.Models.Message;
import com.example.qainfomate.R;

import java.util.ArrayList;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.Holder> {

    ArrayList<Message> msgs;
    Holder.MsgInterface listener;

    public MessageAdapter(ArrayList<Message> msgs, Holder.MsgInterface listener) {
        this.msgs = msgs;
        this.listener = listener;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.msg_rec_view, parent, false);
        Holder holder = new Holder(v, listener);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int i) {
        holder.fromID.setText(msgs.get(i).getIDfrom());
        holder.bookTitle.setText(msgs.get(i).getBookTitle());
        if(msgs.get(i).getRead()==false){
            holder.mail.setImageResource(R.drawable.closed_mail);
        }else{
            holder.mail.setImageResource(R.drawable.opened_envelope);
        }
        holder.time.setText(msgs.get(i).getDate());
            }

    @Override
    public int getItemCount() {
        return msgs.size();
    }

    public static class Holder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView fromID, bookTitle, time;
        ImageView mail;
        MsgInterface listener;

    public Holder(@NonNull View itemView, MsgInterface _listener) {
        super(itemView);
        fromID = itemView.findViewById(R.id.tv_fromID_msgRec);
        bookTitle = itemView.findViewById(R.id.tv_booktitle_msgRec);
        time = itemView.findViewById(R.id.tv_time_msgRec);
        mail = itemView.findViewById(R.id.iv_bookImg_msgRec);
        listener = _listener;
        itemView.setOnClickListener(this);
    }

        @Override
        public void onClick(View v) {listener.onItemClick(getAdapterPosition());}

        public interface MsgInterface{
        public void onItemClick(int i);
    }

}

}
