package com.example.qainfomate.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.qainfomate.Adapters.BookAdapter;
import com.example.qainfomate.Adapters.MessageAdapter;
import com.example.qainfomate.Adapters.MyBooksAdapter;
import com.example.qainfomate.Adapters.SwipeToDeleteCallback;
import com.example.qainfomate.Models.Book_for_Sale;
import com.example.qainfomate.Models.GenericList;
import com.example.qainfomate.Models.Message;
import com.example.qainfomate.Models.Session;
import com.example.qainfomate.MyBookDetail;
import com.example.qainfomate.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ItemListClass extends AppCompatActivity implements BookAdapter.Holder.recInterface, MessageAdapter.Holder.MsgInterface, MyBooksAdapter.Holder.recInterface {

    private RecyclerView RecView;
    private ImageView dash, timetable, forum, lib, moodle, help;
    RecyclerView.LayoutManager manager;
    BookAdapter bookAdapter;
    MyBooksAdapter myBooksAdapter;
    private MessageAdapter msgAdapter;
    Bundle Extras;
    String idbref;
    int item2;
    private Button sell, btnmsgs, myBooks, booksforSale;
    private TextView title;
    private ArrayList<GenericList> list = new ArrayList<>();
    private ArrayList<String> keys = new ArrayList<>();
    private DatabaseReference dbref;
    public static ConstraintLayout constraintLayout;
    public static View view;
    Intent i;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list);

        //DESIGN COMPONENTS
        title = findViewById(R.id.tv_title_itemList);
        RecView = findViewById(R.id.rv_books_itemList);
        sell = findViewById(R.id.btn_sellBook_itemList);
        btnmsgs = findViewById(R.id.btn_messages_itemList);
        myBooks = findViewById(R.id.btn_myBooks_itemList);
        booksforSale = findViewById(R.id.btn_bookSale_itemList);
        manager = new LinearLayoutManager(ItemListClass.this);
        dash = findViewById(R.id.iv_dashboard_bottom_itemList);
        timetable = findViewById(R.id.iv_timetable_bottom_itemList);
        forum = findViewById(R.id.iv_forum_bottom_itemList);
        lib = findViewById(R.id.iv_library_bottom_itemList);
        moodle = findViewById(R.id.iv_moodle_bottom_itemList);
        help = findViewById(R.id.iv_help_bottom_itemList);
        //RECYCLER VIEW COMPONENTS
        RecView.setLayoutManager(manager);

        //Database Reference gets node name from last activity
        Extras = getIntent().getExtras();
        idbref = Extras.getString("ITEM");
        item2 = Extras.getInt("ITEM2");
        dbref = FirebaseDatabase.getInstance().getReference().child(idbref);
        dbref.addListenerForSingleValueEvent(listener);

        //TOOL BAR FOR MESSAGE DELETE UNDO
        setSupportActionBar(toolbar);
        view = findViewById(R.id.coordinatorLayout);


        sell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ItemListClass.this, Post_Book.class);
                startActivity(i);
            }
        });
        btnmsgs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ItemListClass.this, ItemListClass.class);
                i.putExtra("ITEM", "Messages");
                i.putExtra("ITEM2", 1);
                startActivity(i);
            }
        });
        myBooks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ItemListClass.this, ItemListClass.class);
                i.putExtra("ITEM", "Books_for_Sale");
                i.putExtra("ITEM2", 2);
                startActivity(i);
            }
        });
        booksforSale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ItemListClass.this, ItemListClass.class);
                i.putExtra("ITEM", "Books_for_Sale");
                i.putExtra("ITEM2", 1);
                startActivity(i);
            }
        });

        //navigate the user to the Dashboard activity
        dash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateTo(Dashboard.class);
            }
        });

        //navigate the user to the Timetable activity
        timetable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateTo(Timetable.class);
            }
        });

        /*
        // direct the user to the Library activity
        library.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateTo(Library.class);
            }
        });
        //navigate the user to Forum
        forum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateTo(Forum.class);
            }
        });

        //navigate user to the Help activity
        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateTo(Help.class);
            }
        });

         */

        //direct the user to an external browser to the Roehampton Moodle page
        moodle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToMoodle();
            }
        });
    }

        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list.clear();
                if(item2==2){
                    title.setText("My Books");
                    booksforSale.setVisibility(View.VISIBLE);
                    btnmsgs.setVisibility(View.VISIBLE);
                    sell.setVisibility(View.VISIBLE);
                    myBooks.setVisibility(View.INVISIBLE);
                    for(DataSnapshot dss: dataSnapshot.getChildren()) {
                        //shows all my books
                        if ((dss.getValue(Book_for_Sale.class).getStuId()).equals(Session.LiveSession.user.getStuID())) {
                            list.add(dss.getValue(Book_for_Sale.class));
                            keys.add(dss.getKey());
                        }
                    }
                    myBooksAdapter = new MyBooksAdapter(list, ItemListClass.this);
                    RecView.setAdapter(myBooksAdapter);
                }else {

                    switch (idbref) {
                        case "Books_for_Sale":
                            title.setText("Books for Sale");
                            booksforSale.setVisibility(View.INVISIBLE);
                            btnmsgs.setVisibility(View.VISIBLE);
                            sell.setVisibility(View.INVISIBLE);
                            myBooks.setVisibility(View.VISIBLE);
                            for (DataSnapshot dss : dataSnapshot.getChildren()) {
                                //shows only avaiable books
                                if (dss.getValue(Book_for_Sale.class).getAvaiable()) {
                                    list.add(dss.getValue(Book_for_Sale.class));
                                }
                            }
                            bookAdapter = new BookAdapter(list, ItemListClass.this);
                            RecView.setAdapter(bookAdapter);
                            break;
                        case "Messages":
                            for (DataSnapshot dss : dataSnapshot.getChildren()) {
                                title.setText("Messages");
                                booksforSale.setVisibility(View.VISIBLE);
                                myBooks.setVisibility(View.VISIBLE);
                                sell.setVisibility(View.INVISIBLE);
                                btnmsgs.setVisibility(View.INVISIBLE);
                                //FILTERS ONLY MESSAGES FOR THE CURRENT USER
                                if ((dss.getValue(Message.class).getIDto()).equals(Session.LiveSession.user.getStuID())) {
                                    list.add(dss.getValue(Message.class));
                                    keys.add(dss.getKey());
                                }
                            }
                            msgAdapter = new MessageAdapter(list, keys, ItemListClass.this);
                            RecView.setAdapter(msgAdapter);
                            ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new SwipeToDeleteCallback(msgAdapter));
                            itemTouchHelper.attachToRecyclerView(RecView);
                            break;

                    }
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };


    @Override
    public void onItemClick(int i) {
        Intent intent;
        if(item2==1) {
            switch (idbref) {
                case "Books_for_Sale":
                    intent = new Intent(this, Book_for_sale_detail.class);
                    intent.putExtra("BFS", (Parcelable) list.get(i));
                    startActivity(intent);
                case "Messages":
                    intent = new Intent(this, MessageDetail.class);
                    intent.putExtra("MSG", (Parcelable) list.get(i));
                    dbref.child(keys.get(i)).child("read").setValue(true);
                    dbref.addListenerForSingleValueEvent(listener);
                    startActivity(intent);
            }
        }else{
            intent = new Intent(this, MyBookDetail.class);
            intent.putExtra("BFS", (Parcelable) list.get(i));
            intent.putExtra("KEY", keys.get(i));
            startActivity(intent);
        }


    }
    public void navigateToMoodle() {
        i = new Intent(Intent.ACTION_VIEW, Uri.parse("https://partnerships.moodle.roehampton.ac.uk"));
        startActivity(i);
    }

    public void navigateTo(Class goTo){
        i = new Intent(this, goTo);
        startActivity(i);
    }



}
