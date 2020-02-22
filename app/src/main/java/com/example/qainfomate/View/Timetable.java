package com.example.qainfomate.View;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.qainfomate.R;
import com.example.qainfomate.Session;
import com.google.firebase.auth.FirebaseAuth;


public class Timetable extends AppCompatActivity {


    private TextView logOut, studentName, G02_1,G02_2, G02_3, G02_4, LG1_04, F102;
    private ImageView dashboard, library, forum, market, moodle, help;
    private Intent i;
    private FirebaseAuth fbAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timetable);


        //link our Java TextViews and Buttons to their XML counterparts
        logOut = findViewById(R.id.tv_logOut_timetable);
        studentName = findViewById(R.id.tv_user_timetable);
        G02_1 = findViewById(R.id.tv_G02r1c2_timetable);
        G02_2 = findViewById(R.id.tv_G02r1c3_timetable);
        G02_3 = findViewById(R.id.tv_G02r10c3_timetable);
        G02_4 = findViewById(R.id.tv_G02r10c5_timetable);
        LG1_04 = findViewById(R.id.tv_LG0104r1c5_timetable);
        F102 = findViewById(R.id.tv_102r10c2_timetable);
        dashboard = findViewById(R.id.iv_dashboard_bottom_timetable);
        library = findViewById(R.id.iv_library_bottom_timetable);
        forum = findViewById(R.id.iv_forum_bottom_timetable);
        market = findViewById(R.id.iv_market_bottom_timetable);
        moodle = findViewById(R.id.iv_moodle_bottom_timetable);
        help = findViewById(R.id.iv_help_bottom_timetable);
        fbAuth = FirebaseAuth.getInstance();

        studentName.setText(Session.LiveSession.user.getFname()); //displays the user currently logged in

        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fbAuth.signOut();
                //temporary inner class emptied
                Session.LiveSession.user = null;
                navigateTo(MainActivity.class, "0");
            }
        });

        //navigate user to Ground floor map
        G02_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
            navigateTo(Floor.class, "G02");
            }
        });
        G02_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                navigateTo(Floor.class, "G02");
            }
        });
        G02_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                navigateTo(Floor.class, "G02");
            }
        });
        G02_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                navigateTo(Floor.class, "G02");
            }
        });
        //navigate to Lower Ground floor map
        LG1_04.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateTo(Floor.class, "LG01");
            }
        });

        //navigate user to Floor 1 map
        F102.setOnClickListener(new View.OnClickListener() {     

            @Override
            public void onClick(View v) {
                navigateTo(Floor.class, "F102");
            }
        });

        // direct the user to Dashboard
        dashboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateTo(Dashboard.class, "0");
            }
        });

        /*
        // direct the user to the Library activity
        library.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateTo(Library.class);
            }
        });
        //navigate the user to Forum
        forum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateTo(Forum.class);
            }
        });
        //navigate the user to the Market activity
        market.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateTo(Market.class);
            }
        });
        //navigate user to the Help activity
        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateTo(Help.class);
            }
        });

         */

        moodle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToMoodle();
            }
        });

    }

    public void navigateTo(Class goTo, String floor){
        i = new Intent(this, goTo);
        i.putExtra("floor", floor);
        startActivity(i);
    }

    public void navigateToMoodle() {
        i = new Intent(Intent.ACTION_VIEW, Uri.parse("https://partnerships.moodle.roehampton.ac.uk"));
        startActivity(i);
    }

}
