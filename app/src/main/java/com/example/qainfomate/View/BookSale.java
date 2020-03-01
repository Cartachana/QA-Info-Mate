package com.example.qainfomate.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.qainfomate.Models.Adapter;
import com.example.qainfomate.Models.Book_for_Sale;
import com.example.qainfomate.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class BookSale extends AppCompatActivity implements Adapter.Holder.recInterface {

    private RecyclerView booksRecView;
    RecyclerView.LayoutManager manager;
    Adapter adapter;
    private Button sell;
    private ArrayList<Book_for_Sale> list = new ArrayList<>();
    private DatabaseReference dbref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_sale);

        booksRecView = findViewById(R.id.rv_books_booksale);
        sell = findViewById(R.id.btn_sellBook_booksale);
        manager = new LinearLayoutManager(BookSale.this);
        booksRecView.setLayoutManager(manager);
        dbref = FirebaseDatabase.getInstance().getReference().child("Books_for_Sale");
        dbref.addListenerForSingleValueEvent(listener);
    }

        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot dss: dataSnapshot.getChildren()){
                    Book_for_Sale bfs = dss.getValue(Book_for_Sale.class);
                    list.add(bfs);
                }
                adapter = new Adapter(list, BookSale.this);
                booksRecView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };


    @Override
    public void onItemClick(int i) {
        Intent intent = new Intent(BookSale.this, Book_for_sale_detail.class);
        intent.putExtra("BFS", list.get(i));
        startActivity(intent);
    }
}
