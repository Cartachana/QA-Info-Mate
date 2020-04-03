package com.example.qainfomate.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.qainfomate.Adapters.LibraryAdapter;
import com.example.qainfomate.Models.Library_Book;
import com.example.qainfomate.Application.Session;
import com.example.qainfomate.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;


public class Library extends AppCompatActivity implements LibraryAdapter.Holder.recInterface {

    private ImageView closeMenu, back;
    private TextView mine, all, programming, business, economics, web, maths, ux, backDash;
    private FloatingActionButton fab;

    private RecyclerView RecView;
    private LinearLayout leftMenu;
    private RecyclerView.LayoutManager manager;
    private LibraryAdapter libAdapter;
    private ArrayList<Library_Book> list = new ArrayList<>();
    private ArrayList<String> keys = new ArrayList<>();

    private Animation animLeft, animRight;
    private DatabaseReference dbref;
    private Intent i;
    private Query dbQuery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_library);

        back = findViewById(R.id.iv_back_library);
        mine = findViewById(R.id.tv_mine_library);
        all = findViewById(R.id.tv_ALL_library);
        programming = findViewById(R.id.tv_programming_library);
        business = findViewById(R.id.tv_business_library);
        economics = findViewById(R.id.tv_economics_library);
        web = findViewById(R.id.tv_web_library);
        maths = findViewById(R.id.tv_maths_library);
        ux = findViewById(R.id.tv_UX_library);
        backDash = findViewById(R.id.tv_back_library);
        fab = findViewById(R.id.floating_button_library);

        RecView = findViewById(R.id.rv_rv_library);
        manager = new GridLayoutManager(this, 3);
        RecView.setLayoutManager(manager);

        leftMenu = findViewById(R.id.left_menu);
        animLeft = AnimationUtils.loadAnimation(this, R.anim.animleft);
        animRight = AnimationUtils.loadAnimation(this, R.anim.animright);
        closeMenu = findViewById(R.id.iv_closeMenu_library);

        dbref = FirebaseDatabase.getInstance().getReference().child("Library_Books");
        dbref.addListenerForSingleValueEvent(listener);

        //opens left slider menu with animation
        fab.setOnClickListener(v -> {
            leftMenu.setVisibility(View.VISIBLE);
            leftMenu.setAnimation(animRight);
            fab.setVisibility(View.GONE);
        });

        //closes left slider menu with animation
        closeMenu.setOnClickListener(v -> {
            leftMenu.setAnimation(animLeft);
            leftMenu.setVisibility(View.INVISIBLE);
            fab.setVisibility(View.VISIBLE);
        });

        //takes user back to dashboard
        back.setOnClickListener(v -> {
            i = new Intent(Library.this, Dashboard.class);
            startActivity(i);
        });
        backDash.setOnClickListener(v -> {
            i = new Intent(Library.this, Dashboard.class);
            startActivity(i);
        });



        //BOOK CATEGORIES filters books being shown depending on user's
        // touch changing the Db query and attaching the listener to it every time
        programming.setOnClickListener(v -> {
            leftMenu.setAnimation(animLeft);
            leftMenu.setVisibility(View.INVISIBLE);
            fab.setVisibility(View.VISIBLE);
            dbQuery = FirebaseDatabase.getInstance().getReference().child("Library_Books").orderByChild("category").equalTo("Programming");
            dbQuery.addListenerForSingleValueEvent(listener);
        });
        business.setOnClickListener(v -> {
            leftMenu.setAnimation(animLeft);
            leftMenu.setVisibility(View.INVISIBLE);
            fab.setVisibility(View.VISIBLE);
            dbQuery = FirebaseDatabase.getInstance().getReference().child("Library_Books").orderByChild("category").equalTo("Business");
            dbQuery.addListenerForSingleValueEvent(listener);
        });
        economics.setOnClickListener(v -> {
            leftMenu.setAnimation(animLeft);
            leftMenu.setVisibility(View.INVISIBLE);
            fab.setVisibility(View.VISIBLE);
            dbQuery = FirebaseDatabase.getInstance().getReference().child("Library_Books").orderByChild("category").equalTo("Economics");
            dbQuery.addListenerForSingleValueEvent(listener);
        });
        web.setOnClickListener(v -> {
            leftMenu.setAnimation(animLeft);
            leftMenu.setVisibility(View.INVISIBLE);
            fab.setVisibility(View.VISIBLE);
            dbQuery = FirebaseDatabase.getInstance().getReference().child("Library_Books").orderByChild("category").equalTo("Web Development");
            dbQuery.addListenerForSingleValueEvent(listener);
        });
        maths.setOnClickListener(v -> {
            leftMenu.setAnimation(animLeft);
            leftMenu.setVisibility(View.INVISIBLE);
            fab.setVisibility(View.VISIBLE);
            dbQuery = FirebaseDatabase.getInstance().getReference().child("Library_Books").orderByChild("category").equalTo("Mathematics");
            dbQuery.addListenerForSingleValueEvent(listener);
        });
        ux.setOnClickListener(v -> {
            leftMenu.setAnimation(animLeft);
            leftMenu.setVisibility(View.INVISIBLE);
            fab.setVisibility(View.VISIBLE);
            dbQuery = FirebaseDatabase.getInstance().getReference().child("Library_Books").orderByChild("category").equalTo("User Experience");
            dbQuery.addListenerForSingleValueEvent(listener);
        });
        //shows logged user his book reservations
        mine.setOnClickListener(v -> {
            leftMenu.setAnimation(animLeft);
            leftMenu.setVisibility(View.INVISIBLE);
            fab.setVisibility(View.VISIBLE);
            dbQuery = FirebaseDatabase.getInstance().getReference().child("Library_Books").orderByChild("loanedTo").equalTo(Session.LiveSession.user.getStuID());
            dbQuery.addListenerForSingleValueEvent(listener);
        });
        //shows all books in the library
        all.setOnClickListener(v -> {
            leftMenu.setAnimation(animLeft);
            leftMenu.setVisibility(View.INVISIBLE);
            fab.setVisibility(View.VISIBLE);
            dbref = FirebaseDatabase.getInstance().getReference().child("Library_Books");
            dbref.addListenerForSingleValueEvent(listener);
        });
    }

    ValueEventListener listener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            list.clear();
            keys.clear();
            for(DataSnapshot dss: dataSnapshot.getChildren()){
                list.add(dss.getValue(Library_Book.class));
                keys.add(dss.getKey());
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
        Intent in = new Intent(this, LibraryBookDetail.class);
        in.putExtra("LB", list.get(i));
        in.putExtra("KEY", keys.get(i));
        startActivity(in);
    }
}
