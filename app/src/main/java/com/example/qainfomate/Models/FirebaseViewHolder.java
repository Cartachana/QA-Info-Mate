package com.example.qainfomate.Models;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.qainfomate.R;

public class FirebaseViewHolder extends RecyclerView.ViewHolder {
    public TextView title, author, description, category;
    public ImageView bookimg;

    public FirebaseViewHolder(@NonNull View itemView) {
        super(itemView);

    }
}
