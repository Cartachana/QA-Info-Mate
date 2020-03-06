package com.example.qainfomate.View;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.example.qainfomate.Models.Message;
import com.example.qainfomate.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MessageDetail extends AppCompatActivity {

    private TextView msgFrom, time, msg, book;
    private Button reply;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_detail);

        // This section is the set up and size of the pop up "window"
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width*.7),(int)(height*.7));
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.gravity = Gravity.CENTER;
        params.x = 0;
        params.y = -20;
        getWindow().setAttributes(params);
        //Until here

        msgFrom = findViewById(R.id.tv_msgFrom_msgDetail);
        time = findViewById(R.id.tv_time_msgDetail);
        msg = findViewById(R.id.tv_msg_msgDetail);
        reply = findViewById(R.id.btn_reply_msgDetail);
        book = findViewById(R.id.tv_book_msgDetail);


        Intent i = getIntent();
        final Message message = i.getParcelableExtra("MSG");
        msgFrom.setText("Message from: " + message.getIDfrom());
        time.setText(message.getDate());
        msg.setText(message.getMessage());
        book.setText("Regarding: " + message.getBookTitle());


        reply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MessageDetail.this, SendMsg.class);
                i.putExtra("MSG", message);
                startActivity(i);
            }
        });

    }
}
