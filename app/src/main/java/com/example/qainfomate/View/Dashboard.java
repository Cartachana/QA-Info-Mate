package com.example.qainfomate.View;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.qainfomate.R;
import com.example.qainfomate.Models.Session;
import com.google.firebase.auth.FirebaseAuth;

public class Dashboard extends AppCompatActivity {

    //
    private TextView logOut, studentName, user;
    private Button   timetable, library, forum, market, moodle, help;
    private Intent i;
    private FirebaseAuth fbAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        //link our Java TextViews and Buttons to their XML counterparts
        logOut=findViewById(R.id.tv_logOut_dashboard);
        studentName=findViewById(R.id.tv_user_name_dashboard);
        timetable=findViewById(R.id.btn_timetable_dashboard);
        library=findViewById(R.id.btn_library_dashboard);
        forum=findViewById(R.id.btn_forum_dashboard);
        market=findViewById(R.id.btn_market_dashboard);
        moodle=findViewById(R.id.btn_moodle_dashboard);
        help=findViewById(R.id.btn_help_dashboard);
        fbAuth = FirebaseAuth.getInstance();
        user = findViewById(R.id.tv_user_dashboard);



        user.setText(Session.LiveSession.user.getFname());
        studentName.setText(Session.LiveSession.user.getFname() + " " + Session.LiveSession.user.getSname()); //displays the user currently logged in

        // set an event on Log Out TextView where the user is logged out and redirected to the main Login activity
        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fbAuth.signOut();
                //temporary inner class emptied
                Session.LiveSession.user = null;
                navigateTo(MainActivity.class);
            }

        });

        // direct the user to the Timetable activity
        timetable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateTo(Timetable.class);
            }
        });

        //navigate the user to the Market activity
        market.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Dashboard.this, ItemListClass.class);
                i.putExtra("ITEM", "Books_for_Sale");
                i.putExtra("ITEM2", 1);
                startActivity(i);
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

        //navigate user to the Help activity
        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateTo(Help.class);
            }
        });

         */

        //direct the user to an external browser to the Roehampton Moodle page
        moodle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToMoodle();
            }
        });

    }

    public void navigateToMoodle() {
        i = new Intent(Intent.ACTION_VIEW, Uri.parse("https://partnerships.moodle.roehampton.ac.uk"));
        startActivity(i);
    }

    public void navigateTo(Class goTo){
        i = new Intent(this, goTo);
        startActivity(i);
    }


}
