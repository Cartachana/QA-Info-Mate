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
import com.example.qainfomate.Adapters.ForumAdapter;
import com.example.qainfomate.Adapters.MessageAdapter;
import com.example.qainfomate.Adapters.MyBooksAdapter;
import com.example.qainfomate.Adapters.SwipeToDeleteCallback;
import com.example.qainfomate.Models.Book_for_Sale;
import com.example.qainfomate.Models.GenericList;
import com.example.qainfomate.Models.Message;
import com.example.qainfomate.Application.Session;
import com.example.qainfomate.Models.Topic;
import com.example.qainfomate.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ItemListClass extends AppCompatActivity implements BookAdapter.Holder.recInterface, MessageAdapter.Holder.MsgInterface, MyBooksAdapter.Holder.recInterface, ForumAdapter.Holder.ForumInterface {

    private RecyclerView RecView;
    private ImageView dash, timetable, forum, lib, moodle, help, market;
    private Button sell, btnmsgs, myBooks, booksforSale, topic;
    private TextView title;

    private RecyclerView.LayoutManager manager;
    private BookAdapter bookAdapter;
    private MyBooksAdapter myBooksAdapter;
    private ForumAdapter forumAdapter;
    private MessageAdapter msgAdapter;

    private Bundle Extras;
    private Intent i;
    private String idbref;
    private Integer item2;
    private DatabaseReference dbref;

    private ArrayList<GenericList> list = new ArrayList<>();
    private ArrayList<String> keys = new ArrayList<>();
    private ArrayList<Boolean> available = new ArrayList<>();

    public static ConstraintLayout constraintLayout;
    public static View view;

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list);

        //DESIGN COMPONENTS
        title = findViewById(R.id.tv_title_itemList);
        sell = findViewById(R.id.btn_sellBook_itemList);
        btnmsgs = findViewById(R.id.btn_messages_itemList);
        myBooks = findViewById(R.id.btn_myBooks_itemList);
        booksforSale = findViewById(R.id.btn_bookSale_itemList);
        topic = findViewById(R.id.btn_topic_itemList);

        //RECYCLER VIEW COMPONENTS
        RecView = findViewById(R.id.rv_books_itemList);
        manager = new LinearLayoutManager(ItemListClass.this);
        RecView.setLayoutManager(manager);

        dash = findViewById(R.id.iv_dashboard_bottom_itemList);
        timetable = findViewById(R.id.iv_timetable_bottom_itemList);
        forum = findViewById(R.id.iv_forum_bottom_itemList);
        market = findViewById(R.id.iv_market_itemlist);
        lib = findViewById(R.id.iv_library_bottom_itemList);
        moodle = findViewById(R.id.iv_moodle_bottom_itemList);
        help = findViewById(R.id.iv_help_bottom_itemList);

        //Database Reference gets node name from last activity
        Extras = getIntent().getExtras();
        idbref = Extras.getString("ITEM");
        item2 = Extras.getInt("ITEM2");
        dbref = FirebaseDatabase.getInstance().getReference().child(idbref);
        dbref.addListenerForSingleValueEvent(listener);

        //TOOL BAR FOR MESSAGE DELETE UNDO
        setSupportActionBar(toolbar);
        view = findViewById(R.id.coordinatorLayout);


        sell.setOnClickListener(v -> {
            Intent i = new Intent(ItemListClass.this, Post_Book.class);
            startActivity(i);
        });
        btnmsgs.setOnClickListener(v -> {
            Intent i = new Intent(ItemListClass.this, ItemListClass.class);
            i.putExtra("ITEM", "Messages");
            i.putExtra("ITEM2", 1);
            startActivity(i);
        });
        myBooks.setOnClickListener(v -> {
            Intent i = new Intent(ItemListClass.this, ItemListClass.class);
            i.putExtra("ITEM", "Books_for_Sale");
            i.putExtra("ITEM2", 2);
            startActivity(i);
        });
        booksforSale.setOnClickListener(v -> {
            Intent i = new Intent(ItemListClass.this, ItemListClass.class);
            i.putExtra("ITEM", "Books_for_Sale");
            i.putExtra("ITEM2", 1);
            startActivity(i);
        });

        //navigate the user to the New Topic activity
        topic.setOnClickListener(v -> navigateTo(NewTopic.class));

        //navigate the user to the Dashboard activity
        dash.setOnClickListener(v -> navigateTo(Dashboard.class));

        //navigate the user to the Timetable activity
        timetable.setOnClickListener(v -> navigateTo(Timetable.class));

        // direct the user to the Library activity
        lib.setOnClickListener(v -> navigateTo(Library.class));

        //navigate the user to Market Activity
        market.setOnClickListener(v -> {
            Intent i = new Intent(ItemListClass.this, ItemListClass.class);
            i.putExtra("ITEM", "Books_for_Sale");
            i.putExtra("ITEM2", 1);
            startActivity(i);
        });

        //navigate the user to Forum
        forum.setOnClickListener(v -> {
            Intent i = new Intent(ItemListClass.this, ItemListClass.class);
            i.putExtra("ITEM", "Topics");
            i.putExtra("ITEM2", 1);
            startActivity(i);
        });

        //navigate user to the Help activity
        help.setOnClickListener(v -> navigateTo(Support.class));


        //direct the user to an external browser to the Roehampton Moodle page
        moodle.setOnClickListener(v -> {
            i = new Intent(ItemListClass.this, WebViewer.class);
            i.putExtra("site", "Moodle");
            startActivity(i);
        });


    }

        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list.clear();
                if (item2 == 2) {
                    forum.setVisibility(View.VISIBLE);
                    topic.setVisibility(View.INVISIBLE);
                    market.setVisibility(View.INVISIBLE);
                    title.setText("My Books");
                    booksforSale.setVisibility(View.VISIBLE);
                    btnmsgs.setVisibility(View.VISIBLE);
                    sell.setVisibility(View.VISIBLE);
                    myBooks.setVisibility(View.INVISIBLE);
                    for(DataSnapshot dss: dataSnapshot.getChildren()) {
                        //shows all my books
                        if ((dss.getValue(Book_for_Sale.class).getStuId()).equals(Session.LiveSession.user.getStuID())) {
                            list.add(dss.getValue(Book_for_Sale.class));
                            available.add(dss.getValue(Book_for_Sale.class).getAvaiable());
                            keys.add(dss.getKey());
                        }
                    }
                    myBooksAdapter = new MyBooksAdapter(list, ItemListClass.this);
                    RecView.setAdapter(myBooksAdapter);
                } else {
                    switch (idbref) {
                        case "Books_for_Sale":
                            forum.setVisibility(View.VISIBLE);
                            topic.setVisibility(View.INVISIBLE);
                            market.setVisibility(View.INVISIBLE);
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
                        case "Topics":
                            topic.setVisibility(View.VISIBLE);
                            booksforSale.setVisibility(View.INVISIBLE);
                            myBooks.setVisibility(View.INVISIBLE);
                            sell.setVisibility(View.INVISIBLE);
                            btnmsgs.setVisibility(View.INVISIBLE);
                            forum.setVisibility(View.INVISIBLE);
                            market.setVisibility(View.VISIBLE);
                            for (DataSnapshot dss : dataSnapshot.getChildren()) {
                                title.setText("Forum");
                                booksforSale.setVisibility(View.INVISIBLE);
                                myBooks.setVisibility(View.INVISIBLE);
                                sell.setVisibility(View.INVISIBLE);
                                btnmsgs.setVisibility(View.INVISIBLE);
                                list.add(dss.getValue(Topic.class));
                                keys.add(dss.getKey());
                            }
                            forumAdapter = new ForumAdapter(list, ItemListClass.this);
                            RecView.setAdapter(forumAdapter);
                            break;
                        case "Messages":
                            topic.setVisibility(View.INVISIBLE);
                            forum.setVisibility(View.VISIBLE);
                            market.setVisibility(View.INVISIBLE);
                            title.setText("Messages");
                            booksforSale.setVisibility(View.VISIBLE);
                            myBooks.setVisibility(View.VISIBLE);
                            sell.setVisibility(View.INVISIBLE);
                            btnmsgs.setVisibility(View.INVISIBLE);
                            for (DataSnapshot dss : dataSnapshot.getChildren()) {
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
        if(item2==2) {intent = new Intent(this, MyBookDetail.class);
            intent.putExtra("BFS", (Parcelable) list.get(i));
            intent.putExtra("isAvailable", available.get(i));
            intent.putExtra("KEY", keys.get(i));
            startActivity(intent);
        }else{
            switch (idbref) {
                case "Books_for_Sale":
                    intent = new Intent(this, Book_for_sale_detail.class);
                    intent.putExtra("BFS", (Parcelable) list.get(i));
                    startActivity(intent);
                    break;
                case "Topics":
                    intent = new Intent(this, TopicDetail.class);
                    intent.putExtra("TP", (Parcelable) list.get(i));
                    intent.putExtra("KEY", keys.get(i));
                    startActivity(intent);
                    break;
                case "Messages":
                    intent = new Intent(this, MessageDetail.class);
                    intent.putExtra("MSG", (Parcelable) list.get(i));
                    dbref.child(keys.get(i)).child("read").setValue(true);
                    dbref.addListenerForSingleValueEvent(listener);
                    startActivity(intent);
                    break;
            }
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
