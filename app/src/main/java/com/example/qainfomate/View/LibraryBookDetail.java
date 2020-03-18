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
import com.example.qainfomate.Models.Session;
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
    private TextView title, author, desc, unv;
    private Button rev, reserve, cancel;
    private Intent intent;
    private RecyclerView.LayoutManager manager;
    private ArrayList<Review> list = new ArrayList<>();
    private Library_Book libbook;
    private ReviewAdapter adapter;
    private DatabaseReference revref, bkref;
    private String key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_library_book_detail);

        title = findViewById(R.id.tv_booktitle_libBookDetail);
        author = findViewById(R.id.tv_author_libBookDetail);
        desc = findViewById(R.id.tv_description_libBookDetail);
        unv = findViewById(R.id.tv_unv_libBookDetail);
        back = findViewById(R.id.iv_back_libBookDetail);
        rev = findViewById(R.id.btn_leaveRev_libBookDetail);
        reserve = findViewById(R.id.btn_Reserve_libBookDetail);
        cancel = findViewById(R.id.btn_cancel_libBookDetail);
        RecView = findViewById(R.id.rv_rv_books_libBookDetail);
        bookImg = findViewById(R.id.iv_bookImg_libBookDetail);
        manager = new LinearLayoutManager(LibraryBookDetail.this);
        RecView.setLayoutManager(manager);

        Bundle extra = getIntent().getExtras();
        libbook = extra.getParcelable("LB");
        key = extra.getString("KEY");

        //Book Avaiable, show button to reserve
        if(libbook.getLoanedTo().equals("Available")){
            reserve.setVisibility(View.VISIBLE);
            cancel.setVisibility(View.INVISIBLE);

        //book reserved by user, show button to cancel reservation
        }else if(libbook.getLoanedTo().equals(Session.LiveSession.user.getStuID())){
            reserve.setVisibility(View.INVISIBLE);
            cancel.setVisibility(View.VISIBLE);

        }else{      //Book Reserved to Another Student
            reserve.setVisibility(View.INVISIBLE);
            cancel.setVisibility(View.INVISIBLE);
            unv.setVisibility(View.VISIBLE);
        }

        title.setText(libbook.getTitle());
        author.setText(libbook.getAuthor());
        desc.setText(libbook.getDescription());
        Picasso.get().load(libbook.getImageUrl()).fit().into(bookImg);
        revref = FirebaseDatabase.getInstance().getReference("Reviews");
        bkref = FirebaseDatabase.getInstance().getReference("Library_Books");
        revref.addListenerForSingleValueEvent(listener);

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
                intent.putExtra("LB", libbook);
                startActivity(intent);
            }
        });
        reserve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                libbook.setLoanedTo(Session.LiveSession.user.getStuID());
                bkref.child(key).setValue(libbook);
                reserve.setVisibility(View.INVISIBLE);
                cancel.setVisibility(View.VISIBLE);
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                libbook.setLoanedTo("Available");
                bkref.child(key).setValue(libbook);
                reserve.setVisibility(View.VISIBLE);
                cancel.setVisibility(View.INVISIBLE);
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
