package com.example.qainfomate.View;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.qainfomate.R;
import com.example.qainfomate.Session;
import com.google.firebase.auth.FirebaseAuth;

public class Floor extends AppCompatActivity {

    private ImageView floor1img, back;
    private TextView logout, user, title;
    private FirebaseAuth fbAuth;
    private Bundle extras;
    private String extra;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_floor1);

        floor1img = findViewById(R.id.img_floor1_floor1);
        logout = findViewById(R.id.tv_logOut_floor1);
        user = findViewById(R.id.tv_user_floor1);
        back = findViewById(R.id.iv_back_floor1);
        title = findViewById(R.id.tv_title_floor);
        user.setText(Session.LiveSession.user.getFname());
        fbAuth = FirebaseAuth.getInstance();

        extras = getIntent().getExtras();
        extra = extras.getString("floor");

        switch(extra){
            case "F102":
                floor1img.setImageResource(R.drawable.floor1);
                title.setText("First Floor");
                title.setTextSize(34);
                break;
            case "G02":
                floor1img.setImageResource(R.drawable.groundfloor);
                title.setText("Ground Floor");
                title.setTextSize(34);
                break;
            case "LG01":
                floor1img.setImageResource(R.drawable.lowerground1);
                title.setText("Lower Ground Floor");
                title.setTextSize(24);
                break;
            default:
                break;
        }

        // set an event on Log Out TextView where the user is logged out and redirected to the main Login activity
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fbAuth.signOut();
                //TEMPORARY INNER CLASS EMPTIED
                Session.LiveSession.user = null;
                Intent i = new Intent(Floor.this, MainActivity.class);
                startActivity(i);

            }

        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              finish();
            }
        });
    }



}
