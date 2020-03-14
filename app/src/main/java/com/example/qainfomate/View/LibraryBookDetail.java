package com.example.qainfomate.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.qainfomate.Adapters.ReviewAdapter;
import com.example.qainfomate.Models.Library_Book;
import com.example.qainfomate.Models.Review;
import com.example.qainfomate.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class LibraryBookDetail extends AppCompatActivity implements ReviewAdapter.Holder.recInterface {

    private RecyclerView RecView;
    private ImageView bookImg, back;
    private TextView title, author, desc;
    private Button rev, reserve, cancel;
    Intent intent;
    RecyclerView.LayoutManager manager;
    ArrayList<Review> list = new ArrayList<>();
    Library_Book libbook;
    ReviewAdapter adapter;
    DatabaseReference dbref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_library_book_detail);

        title = findViewById(R.id.tv_booktitle_libBookDetail);
        author = findViewById(R.id.tv_author_libBookDetail);
        desc = findViewById(R.id.tv_description_libBookDetail);
        back = findViewById(R.id.iv_back_libBookDetail);
        rev = findViewById(R.id.btn_leaveRev_categBooks);
        reserve = findViewById(R.id.btn_Reserve_categBooks);
        cancel = findViewById(R.id.btn_cancel_categBooks);
        RecView = findViewById(R.id.rv_rv_books_category);
        bookImg = findViewById(R.id.iv_bookImg_libBookDetail);
        manager = new LinearLayoutManager(LibraryBookDetail.this);
        RecView.setLayoutManager(manager);

        Bundle extra = getIntent().getExtras();
        libbook = extra.getParcelable("LB");

        title.setText(libbook.getTitle());
        author.setText(libbook.getAuthor());
        desc.setText(libbook.getDescription());
        Picasso.get().load(libbook.getImageUrl()).fit().into(bookImg);
        dbref = FirebaseDatabase.getInstance().getReference("Reviews");
        dbref.addListenerForSingleValueEvent(listener);

        rev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(LibraryBookDetail.this, Post_review.class);
                intent.putExtra("LB", libbook);
                startActivity(intent);
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(LibraryBookDetail.this, Library.class);
                startActivity(intent);
            }
        });

    }
    ValueEventListener listener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            for(DataSnapshot dss: dataSnapshot.getChildren()){
                Review rev = dss.getValue(Review.class);
                if(rev.getISBN().equals(libbook.getISBN())){
                    list.add(rev);
                }
            }
            adapter = new ReviewAdapter(list, LibraryBookDetail.this);
            RecView.setAdapter(adapter);
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };


    @Override
    public void onItemClick(int i) {

    }
}
