package com.example.qainfomate.View;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.qainfomate.Models.Book_for_Sale;
import com.example.qainfomate.Models.Message;
import com.example.qainfomate.Models.Session;
import com.example.qainfomate.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class SendMsg extends Activity {

    private TextView msgTo;
    private EditText msg;
    private Button send;
    private DatabaseReference dbref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_msg);
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

        msgTo = findViewById(R.id.tv_msgTo_sendMsg);
        msg = findViewById(R.id.et_message_sendMsg);
        send = findViewById(R.id.btn_send_sendMsg);
        dbref = FirebaseDatabase.getInstance().getReference().child("Messages");

        Intent i = getIntent();
        final Book_for_Sale bfs = i.getParcelableExtra("BFS");
        final Message mess = i.getParcelableExtra("MSG");
        if(bfs!=null){msgTo.setText("Message to: " + bfs.getStuId());}
        if(mess!=null){
            msgTo.setText(("Message to: " + mess.getIDfrom()));

        }

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(msg.getText().toString().isEmpty()){
                    msg.setTextColor(Color.parseColor("#FF1B00"));
                }else{
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy_HH:mm", Locale.getDefault());
                    String time = sdf.format(new Date());
                    if(bfs!=null){Message message = new Message(Session.LiveSession.user.getStuID(), bfs.getStuId(),
                            bfs.getTitle(), msg.getText().toString(), time, false);
                    dbref.push().setValue(message);}
                    if(mess!=null){
                        Message message = new Message(Session.LiveSession.user.getStuID(), mess.getIDfrom(),
                                mess.getBookTitle(), msg.getText().toString(), time, false);
                        dbref.push().setValue(message);
                    }
                    Toast.makeText(SendMsg.this, "Message Sent", Toast.LENGTH_LONG).show();
                    finish();

                }

            }
        });
    }
}
