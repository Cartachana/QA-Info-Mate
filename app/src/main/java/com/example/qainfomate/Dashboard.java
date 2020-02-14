package com.example.qainfomate;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import java.sql.Time;

public class Dashboard extends AppCompatActivity {

    //
    private TextView logOut, studentName;
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

        studentName.setText(Session.LiveSession.user.getFname());
        //set  OnClickListener on buttons to trigger events, such that the user will navigate through different activities

        // set an event on Log Out TextView where the user is logged out and redirected to the main Login activity
        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fbAuth.signOut();
                //TEMPORARY INNER CLASS EMPTIED
                Session.LiveSession.user = null;
                navigateTo(MainActivity.class);

            }

        });

        // when the user clicks on the Timetable button they will be directed to the Timetable activity
        timetable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //call method to navigate to the Timetable activity
                //navigateToTimetable();
                navigateTo(Timetable.class);
            }
        });

        //when clicked, the user will be directed to an external browser to the Roehampton Moodle page
        moodle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //call navgateToMoodle method
                navigateToMoodle();
            }
        });

        /* these will be added once the other activities will be created


        //when clicked, the user will be directed to the Library activity
        library.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //call method navigateToLibrary
                navigateToLibrary();
            }
        });

        //when clicked, the user will be directed to the Forum activity
        forum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //call method navigateToForum
                navigateToForum();
            }
        });

        //when clicked, the user will be directed to the Market activity
        market.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //call method navigateToMarket
                navigateToMarket();
            }
        });

         //when clicked, the user will be directed to Help activity
        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //call method navigateToHelp
                navigateToHelp();
            }
        });
        */
    }

    //declare method to navigate to the Main Activity

    public void navigateToMainActivity()
    {
        i = new Intent(this, MainActivity.class);
        startActivity(i);
    }


    //declare method to navigate to the Timetable Activity

    /*public void navigateToTimetable()
    {
        i = new Intent(this, Timetable.class);
        startActivity(i);
    }*/

    public void navigateToMoodle()
    {
        i = new Intent(Intent.ACTION_VIEW, Uri.parse("https://partnerships.moodle.roehampton.ac.uk"));
        startActivity(i);
    }

    public void navigateTo(Class goTo){
        i = new Intent(this, goTo);
        startActivity(i);
    }

    /* these will be made public once the other activities are created



    //declare method to navigate to the Library Activity
    public void navigateToLibrary()
    {
        i = new Intent(this, Library.class);
        startActivity(i);
    }

    //declare method to navigate to the Forum Activity
    public void navigateToForum()
    {
        i = new Intent(this, Forum.class);
        startActivity(i);
    }

    //declare method to navigate to the Market Activity
    public void navigateToMarket()
    {
        i = new Intent(this, Market.class);
        startActivity(i);
    }

    //declare method to navigate to the Help Activity
    public void navigateToHelp()
    {
        i = new Intent(this, Help.class);
        startActivity(i);
    }

     */


}
