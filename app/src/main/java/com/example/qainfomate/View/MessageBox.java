package com.example.qainfomate.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.qainfomate.Models.Message;
import com.example.qainfomate.Adapters.MessageAdapter;
import com.example.qainfomate.Models.Session;
import com.example.qainfomate.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MessageBox extends AppCompatActivity implements MessageAdapter.Holder.MsgInterface {

    private RecyclerView msgRecView;
    RecyclerView.LayoutManager manager;
    MessageAdapter msgAdapter;
    private ImageView back;

    private ArrayList<Message> msgs = new ArrayList<>();
    private DatabaseReference dbref;

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
            for (DataSnapshot dss : dataSnapshot.getChildren()) {
                //FILTERS ONLY MESSAGES FOR THE CURRENT USER
                if((dss.getValue(Message.class).getIDto()).equals(Session.LiveSession.user.getStuID())) {
                    msgs.add(dss.getValue(Message.class));
                }
            }
            msgAdapter = new MessageAdapter(msgs, MessageBox.this);
            msgRecView.setAdapter(msgAdapter);
            
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };



    @Override
    public void onItemClick(int i) {
        Intent intent = new Intent(MessageBox.this, MessageDetail.class);
        intent.putExtra("MSG", msgs.get(i));
        startActivity(intent);
    }
}
