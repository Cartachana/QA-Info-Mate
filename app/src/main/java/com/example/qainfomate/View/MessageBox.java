package com.example.qainfomate.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import com.example.qainfomate.Adapters.SwipeToDeleteCallback;
import com.example.qainfomate.Models.Message;
import com.example.qainfomate.Adapters.MessageAdapter;
import com.example.qainfomate.Models.Session;
import com.example.qainfomate.R;
import androidx.appcompat.widget.Toolbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MessageBox extends AppCompatActivity implements MessageAdapter.Holder.MsgInterface {

    private RecyclerView msgRecView;
    private RecyclerView.LayoutManager manager;
    private MessageAdapter msgAdapter;
    private ImageView back;
    private ArrayList<Message> msgs = new ArrayList<>();
    private ArrayList<String> keys = new ArrayList<>();
    private DatabaseReference dbref;
    Toolbar toolbar;
    public static ConstraintLayout constraintLayout;
    public static View view;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_box);

        msgRecView = findViewById(R.id.rv_msgs_msgbox);
        manager = new LinearLayoutManager(MessageBox.this);
        msgRecView.setLayoutManager(manager);
        dbref = FirebaseDatabase.getInstance().getReference().child("Messages");
        dbref.addListenerForSingleValueEvent(listener);
        back = findViewById(R.id.iv_back_msgBox);
        //toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        view = findViewById(R.id.coordinatorLayout);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }


    ValueEventListener listener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            msgs.clear();
            for (DataSnapshot dss : dataSnapshot.getChildren()) {
                //FILTERS ONLY MESSAGES FOR THE CURRENT USER
                if((dss.getValue(Message.class).getIDto()).equals(Session.LiveSession.user.getStuID())) {
                    msgs.add(dss.getValue(Message.class));
                    keys.add(dss.getKey());
                }
            }
            msgAdapter = new MessageAdapter(msgs, keys, MessageBox.this);
            msgRecView.setAdapter(msgAdapter);
            ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new SwipeToDeleteCallback(msgAdapter));
            itemTouchHelper.attachToRecyclerView(msgRecView);

        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
        }
    };


    @Override
    public void onItemClick(int i) {
        Intent intent = new Intent(MessageBox.this, MessageDetail.class);
        intent.putExtra("MSG", msgs.get(i));
        dbref.child(keys.get(i)).child("read").setValue(true);
        dbref.addListenerForSingleValueEvent(listener);
        startActivity(intent);
    }


}
