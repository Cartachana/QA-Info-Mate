package com.example.qainfomate.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.qainfomate.Adapters.TopicAdapter;
import com.example.qainfomate.AddForumComment;
import com.example.qainfomate.Models.Session;
import com.example.qainfomate.Models.Thread;
import com.example.qainfomate.Models.Topic;
import com.example.qainfomate.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class TopicDetail extends AppCompatActivity {

    private TextView from, subject, date, desc;
    private ImageView back;
    private Topic topic;
    private String tpkey;
    private EditText comment;
    private Button post;
    private RecyclerView recView;
    private RecyclerView.LayoutManager manager;
    private DatabaseReference dbref;
    private Thread thread;
    private ArrayList<Thread> threads = new ArrayList<>();
    private TopicAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic_detail);

        back = findViewById(R.id.iv_back_topicDetail);
        from = findViewById(R.id.tv_from_topicDetail);
        subject = findViewById(R.id.tv_subject_topicDetail);
        date = findViewById(R.id.tv_date_topicDetail);
        desc = findViewById(R.id.tv_desc_topicDetail);
        post = findViewById(R.id.btn_post_topicDetail);

        recView = findViewById(R.id.rv_threads_topicDetail);
        manager = new LinearLayoutManager(TopicDetail.this);
        recView.setLayoutManager(manager);

        Intent i = getIntent();
        topic = i.getParcelableExtra("TP");
        tpkey = i.getStringExtra("KEY");
        dbref = FirebaseDatabase.getInstance().getReference().child("Threads");
        dbref.addListenerForSingleValueEvent(listener);

        from.setText("From: " + topic.getStuId());
        subject.setText("Subject: " + topic.getSubject());
        date.setText("On: " + topic.getDate());
        desc.setText(topic.getDesc());

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(TopicDetail.this, ItemListClass.class);
                i.putExtra("ITEM", "Topics");
                i.putExtra("ITEM2", 1);
                startActivity(i);
            }
        });

        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(TopicDetail.this, AddForumComment.class);
                i.putExtra("TP", topic);
                i.putExtra("KEY", tpkey);
                startActivity(i);
            }
        });
    }

    ValueEventListener listener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            threads.clear();
            for(DataSnapshot dss: dataSnapshot.getChildren()){
                if(dss.getValue(Thread.class).getFromTopic().equals(tpkey)){
                    threads.add(dss.getValue(Thread.class));
                }
            }
                adapter = new TopicAdapter(threads);
                recView.setAdapter(adapter);

        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };
}
