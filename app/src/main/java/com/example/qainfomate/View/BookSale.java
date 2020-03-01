package com.example.qainfomate.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.qainfomate.Models.Book_for_Sale;
import com.example.qainfomate.Models.FirebaseViewHolder;
import com.example.qainfomate.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class BookSale extends AppCompatActivity {

    private RecyclerView booksRecView;
    private Button sell;
    private ArrayList<Book_for_Sale> arrayList;
    private FirebaseRecyclerOptions<Book_for_Sale> options;
    private FirebaseRecyclerAdapter<Book_for_Sale, FirebaseViewHolder> adapter;
    private DatabaseReference dbref;
    private Bitmap icon;
    private String URL;

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_sale);

        booksRecView = findViewById(R.id.rv_books_booksale);
        sell = findViewById(R.id.btn_sellBook_booksale);
        booksRecView.setHasFixedSize(true);
        booksRecView.setLayoutManager(new LinearLayoutManager(this));
        arrayList = new ArrayList<Book_for_Sale>();
        dbref = FirebaseDatabase.getInstance().getReference().child("Books_for_Sale");
        dbref.keepSynced(true);
        options = new FirebaseRecyclerOptions.Builder<Book_for_Sale>().setQuery(dbref, Book_for_Sale.class).build();

        adapter = new FirebaseRecyclerAdapter<Book_for_Sale, FirebaseViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull FirebaseViewHolder holder, int i, @NonNull Book_for_Sale book_for_sale) {
               if(book_for_sale.getAvaiable()==true){
                   holder.title.setText("Title: " + book_for_sale.getTitle());
                   holder.author.setText("Author: " + book_for_sale.getAuthor());
                   holder.category.setText("Category: "+ book_for_sale.getCategory());
                   holder.description.setText(book_for_sale.getCategory());
                   URL = book_for_sale.getImageUrl();
                   Picasso.get().load(URL).into(holder.bookimg);
               }
            }

            @NonNull
            @Override
            public FirebaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                return new FirebaseViewHolder(LayoutInflater.from(BookSale.this).inflate(R.layout.listitem_book_rec_view, parent, false));
            }

        };
booksRecView.addOnItemTouchListener(new RecyclerView.SimpleOnItemTouchListener());

        sell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(BookSale.this, Post_Book.class);
                startActivity(i);
            }
        });

            booksRecView.setAdapter(adapter);

    }
}
