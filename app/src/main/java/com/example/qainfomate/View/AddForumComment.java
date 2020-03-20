package com.example.qainfomate.View;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.qainfomate.Models.Session;
import com.example.qainfomate.Models.Thread;
import com.example.qainfomate.Models.Topic;
import com.example.qainfomate.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AddForumComment extends AppCompatActivity {

    private EditText comment;
    private Button publish;
    private DatabaseReference dbref;
    private String tpkey;
    private Thread thread;
    private Topic topic;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_forum_comment);

        Intent i = getIntent();

        tpkey = i.getStringExtra("KEY");
        topic = i.getParcelableExtra("TP");
        // This section is the set up and size of the pop up "window"
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width*.7),(int)(height*.6));
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.gravity = Gravity.CENTER;
        params.x = 0;
        params.y = -20;
        getWindow().setAttributes(params);
        //Until here

        comment = findViewById(R.id.et_comment_newCom);
        publish = findViewById(R.id.btn_publish_newCom);
        dbref = FirebaseDatabase.getInstance().getReference().child("Threads");

        publish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(comment.getText().toString().isEmpty()) {
                    comment.setTextColor(Color.parseColor("#FF1B00"));
                }else{
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy_HH:mm", Locale.getDefault());
                    String time = sdf.format(new Date());
                    thread = new Thread(tpkey, Session.LiveSession.user.getStuID(), time, comment.getText().toString());
                    dbref.push().setValue(thread);
                    Toast.makeText(AddForumComment.this, "Comment Added", Toast.LENGTH_LONG).show();
                    Intent i = new Intent(AddForumComment.this, TopicDetail.class);
                    i.putExtra("TP", topic);
                    i.putExtra("KEY", tpkey);
                    startActivity(i);
                }
            }
        });
    }
}
