package com.example.qainfomate.View;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.qainfomate.Models.Book_for_Sale;
import com.example.qainfomate.R;
import com.squareup.picasso.Picasso;

public class Book_for_sale_detail extends AppCompatActivity {

    TextView title, author, cat, desc, sellerID;
    ImageView bookImg, back;
    Button msgSeller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_for_sale_detail);

        title = findViewById(R.id.tv_title_bfsDetails);
        author = findViewById(R.id.tv_author_bfsDetails);
        cat = findViewById(R.id.tv_cat_bfsDetails);
        desc = findViewById(R.id.tv_desc_bfsDetails);
        sellerID = findViewById(R.id.tv_sellerID_bfsDetails);
        bookImg = findViewById(R.id.iv_bookimg_bfsDetails);
        msgSeller = findViewById(R.id.btn_mssSeller_bfsDetail);
        back = findViewById(R.id.iv_back_bfs_detail);
        Intent i = getIntent();
        Book_for_Sale bfs = i.getParcelableExtra("BFS");


        title.setText(bfs.getTitle());
        author.setText(bfs.getAuthor());
        cat.setText(bfs.getCategory());
        desc.setText(bfs.getDescription());
        sellerID.setText(bfs.getStuId());
        Picasso.get().load(bfs.getImageUrl()).fit().into(bookImg);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
