package com.example.qainfomate;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import org.w3c.dom.Text;

public class Floor1 extends AppCompatActivity {

    private ImageView floor1img;
    private TextView logout, user, back;
    private FirebaseAuth fbAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_floor1);

        floor1img = findViewById(R.id.img_floor1_floor1);
        logout = findViewById(R.id.tv_logOut_floor1);
        user = findViewById(R.id.tv_user_floor1);
        back = findViewById(R.id.back_floor1);

        user.setText(Session.LiveSession.user.getFname());

        // set an event on Log Out TextView where the user is logged out and redirected to the main Login activity
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fbAuth.signOut();
                //TEMPORARY INNER CLASS EMPTIED
                Session.LiveSession.user = null;
                navigateTo(MainActivity.class);

            }

        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              navigateTo(Timetable.class);
            }
        });
    }

    public void navigateTo(Class goTo){
        Intent i = new Intent(this, goTo);
        startActivity(i);
    }

}
