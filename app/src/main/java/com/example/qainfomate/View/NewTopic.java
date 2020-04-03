package com.example.qainfomate.View;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.example.qainfomate.Application.Session;
import com.example.qainfomate.Models.Topic;
import com.example.qainfomate.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class NewTopic extends AppCompatActivity {

    private EditText subject, desc;
    private TextView error;
    private Button pub;
    DatabaseReference dbref;
    Topic topic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_topic);

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

        subject = findViewById(R.id.et_subject_newTopic);
        desc = findViewById(R.id.et_desc_newTopic);
        pub = findViewById(R.id.btn_publish_newTopic);
        dbref = FirebaseDatabase.getInstance().getReference().child("Topics");
        error = findViewById(R.id.tv_error_newTopic);

        pub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //shows error if one of the fields is empty
                if((subject.getText().toString().isEmpty()) || (desc.getText().toString().isEmpty())){
                    error.setVisibility(View.VISIBLE);
                }else{ //on success new topic is stored with system date, and user's inputs
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy_HH:mm", Locale.getDefault());
                    String time = sdf.format(new Date());
                    topic = new Topic(Session.LiveSession.user.getStuID(),
                            subject.getText().toString(), desc.getText().toString(), time, 0);
                    dbref.push().setValue(topic);

                    Toast.makeText(NewTopic.this, "New Topic Published", Toast.LENGTH_LONG).show();
                    Intent i = new Intent(NewTopic.this, ItemListClass.class);
                    i.putExtra("ITEM", "Topics");
                    i.putExtra("ITEM2", 1);
                    startActivity(i);
                }
            }
        });
    }
}
