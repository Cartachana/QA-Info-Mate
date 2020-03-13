package com.example.qainfomate.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.qainfomate.Adapters.LibraryAdapter;
import com.example.qainfomate.Models.Library_Book;
import com.example.qainfomate.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Library extends AppCompatActivity implements LibraryAdapter.Holder.recInterface {

    private RecyclerView RecView;
    private RecyclerView.LayoutManager manager;
    private FloatingActionButton fab;
    private LibraryAdapter libAdapter;
    private ArrayList<Library_Book> list = new ArrayList<>();
    private DatabaseReference dbref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_library);

        RecView = findViewById(R.id.rv_rv_library);
        manager = new GridLayoutManager(this, 3);
        RecView.setLayoutManager(manager);
        fab = findViewById(R.id.floating_button_library);
        dbref = FirebaseDatabase.getInstance().getReference().child("Library_Books");
        dbref.addListenerForSingleValueEvent(listener);
    }

    ValueEventListener listener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            list.clear();
            for(DataSnapshot dss: dataSnapshot.getChildren()){
                list.add(dss.getValue(Library_Book.class));
            }
            libAdapter = new LibraryAdapter(list, Library.this);
            RecView.setAdapter(libAdapter);
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };

    @Override
    public void onItemClick(int i) {
        //Intent i = new Intent(this, )
    }
}
