package com.example.qainfomate.View;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.qainfomate.R;
import com.example.qainfomate.Application.Session;
import com.google.firebase.auth.FirebaseAuth;

public class Floors extends AppCompatActivity {

    private ImageView floor1img, back;
    private TextView logout, user, title, loc;
    private FirebaseAuth fbAuth;
    private Bundle extras;
    private String extra;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_floors);

        floor1img = findViewById(R.id.img_floor_floors);
        logout = findViewById(R.id.tv_logOut_floors);
        user = findViewById(R.id.tv_user_floors);
        back = findViewById(R.id.iv_back_floors);
        title = findViewById(R.id.tv_title_floors);
        loc = findViewById(R.id.tv_lib_location);
        user.setText(Session.LiveSession.user.getFname());
        fbAuth = FirebaseAuth.getInstance();

        extras = getIntent().getExtras();
        extra = extras.getString("floor");

        switch(extra){
            case "F102":
                loc.setVisibility(View.INVISIBLE);
                floor1img.setImageResource(R.drawable.floor1);
                title.setText("First Floor");
                title.setTextSize(34);
                break;
            case "G02":
                loc.setVisibility(View.INVISIBLE);
                floor1img.setImageResource(R.drawable.groundfloor);
                title.setText("Ground Floor");
                title.setTextSize(34);
                break;
            case "LG01":
                loc.setVisibility(View.INVISIBLE);
                floor1img.setImageResource(R.drawable.lowerground1);
                title.setText("Lower Ground Floor");
                title.setTextSize(24);
                break;
            case "Library":
                loc.setVisibility(View.VISIBLE);
                floor1img.setImageResource(R.drawable.library);
                title.setText("Library");
                title.setTextSize(34);
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
                Intent i = new Intent(Floors.this, MainActivity.class);
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
