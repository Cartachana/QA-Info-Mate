package com.example.qainfomate.View;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.qainfomate.Application.Session;
import com.example.qainfomate.R;
import com.google.firebase.auth.FirebaseAuth;

public class Support extends AppCompatActivity {

    TextView user, logout, finance, stuServ, welfare, ace, lib, internships, careers;
    ImageView dash, timetable, library, market, forum, moodle;
    Intent i;
    private FirebaseAuth fbAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_support);

        user = findViewById(R.id.tv_user_support);
        logout = findViewById(R.id.tv_logOut_support);
        finance = findViewById(R.id.tv_student_finance_support);
        stuServ = findViewById(R.id.tv_student_services_support);
        welfare = findViewById(R.id.tv_student_welfare_support);
        ace = findViewById(R.id.tv_ACE_support);
        lib = findViewById(R.id.tv_library_support);
        internships = findViewById(R.id.tv_internships_support);
        careers = findViewById(R.id.tv_post_graduation_jobs_support);
        dash = findViewById(R.id.iv_dashboard_bottom_support);
        timetable = findViewById(R.id.iv_timetable_bottom_support);
        library = findViewById(R.id.iv_library_bottom_support);
        market = findViewById(R.id.iv_market_bottom_support);
        forum = findViewById(R.id.iv_forum_bottom_support);
        moodle = findViewById(R.id.iv_moodle_bottom_support);

        fbAuth = FirebaseAuth.getInstance();

        user.setText(Session.LiveSession.user.getFname());
        logout.setOnClickListener(v -> {
            fbAuth.signOut();
            //temporary inner class emptied
            Session.LiveSession.user = null;
            navigateTo(MainActivity.class, null);
        });

        finance.setOnClickListener(v -> navigateTo(Support2.class, "StudentFinance"));
        stuServ.setOnClickListener(v -> navigateTo(Support2.class, "StudentServices"));
        welfare.setOnClickListener(v -> navigateTo(Support2.class, "Welfare"));
        ace.setOnClickListener(v -> navigateTo(Support2.class, "ACE"));
        lib.setOnClickListener(v -> navigateTo(Support2.class, "Library"));
        internships.setOnClickListener(v -> navigateTo(Support2.class, "Internships"));
        careers.setOnClickListener(v -> navigateTo(Support2.class, "Careers"));

        //navigate user to the Help activity
        dash.setOnClickListener(v -> navigateTo(Dashboard.class, null));

        // direct the user to the Timetable activity
        timetable.setOnClickListener(v -> navigateTo(Timetable.class, null));

        // direct the user to the Library activity
        library.setOnClickListener(v -> navigateTo(Library.class, null));

        //navigate the user to the Market activity
        market.setOnClickListener(v -> {
            Intent i = new Intent(Support.this, ItemListClass.class);
            i.putExtra("ITEM", "Books_for_Sale");
            i.putExtra("ITEM2", 1);
            startActivity(i);
        });

        //navigate the user to Forum
        forum.setOnClickListener(v -> {
            Intent i = new Intent(Support.this, ItemListClass.class);
            i.putExtra("ITEM", "Topics");
            i.putExtra("ITEM2", 1);
            startActivity(i);
        });

        //direct the user to an external browser to the Roehampton Moodle page
        moodle.setOnClickListener(v -> {
            i = new Intent(Support.this, WebViewer.class);
            i.putExtra("site", "Moodle");
            startActivity(i);
        });

    }

    public void navigateTo(Class goTo, String dep){
        i = new Intent(this, goTo);
        i.putExtra("DEP", dep);
        startActivity(i);
    }
}
