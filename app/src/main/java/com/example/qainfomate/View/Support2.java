package com.example.qainfomate.View;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.qainfomate.R;

public class Support2 extends AppCompatActivity {
    private TextView title, email, phoneno, floor;
    private ImageView dash, timetable, library, market, forum, moodle;
    private String extra;
    private Bundle extras;
    Intent i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_support2);

        title = findViewById(R.id.tv_title_support2);
        email = findViewById(R.id.tv_contact_email_support2);
        phoneno = findViewById(R.id.tv_contact_number_support2);
        floor = findViewById(R.id.tv_see_capmpus_location_support2);
        dash = findViewById(R.id.iv_dashboard_bottom_support2);
        timetable = findViewById(R.id.iv_timetable_bottom_support2);
        library = findViewById(R.id.iv_library_bottom_support2);
        market = findViewById(R.id.iv_market_bottom_support2);
        forum = findViewById(R.id.iv_forum_bottom_support2);
        moodle = findViewById(R.id.iv_moodle_bottom_support2);

        extras = getIntent().getExtras();
        extra = extras.getString("DEP");

        switch (extra){
            case("StudentFinance"):
                title.setText("Finance");
                email.setText("student.finance.support@qa.ac.uk");
                phoneno.setText("+44-2076568450");
                floor.setText("Press here to see our location on campus");
                floor.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        navigateTo(Floors.class, "G02");
                    }
                });
                break;
            case("StudentServices"):
                title.setText("Services");
                email.setText("student.services@qa.ac.uk");
                phoneno.setText("+44-2076 568 460");
                floor.setText("Press here to see our location on campus");
                floor.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        navigateTo(Floors.class, "G02");
                    }
                });
                break;
            case("Welfare"):
                title.setText("Student Welfare");
                email.setText("student.welfare@qa.ac.uk");
                phoneno.setText("+44-2076 568 420");
                floor.setText("Press here to see our location on campus");
                floor.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        navigateTo(Floors.class, "F102");
                    }
                });
                break;
            case("ACE"):
                title.setText("ACE Team");
                email.setText("ace.team.support@qa.ac.uk");
                phoneno.setText("+44-2076 568 446");
                floor.setText("Press here to see our location on campus");
                floor.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        navigateTo(Floors.class, "LG01");
                    }
                });
                break;
            case("Library"):
                title.setText("Library");
                email.setText("london.library@qa.ac.uk");
                phoneno.setText("+44-2076 568 448");
                floor.setText("Press here to see where we are");
                floor.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        navigateTo(Floors.class, "Library");
                    }
                });
                break;
            case("Internships"):
                title.setText("Internships");
                email.setText("admin@qainternships.com");
                phoneno.setText("+44-7720 291 896");
                floor.setText("Find us here");
                floor.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        i = new Intent(Intent.ACTION_VIEW, Uri.parse("https://qainternships.com/"));
                        startActivity(i);
                    }
                });
                break;
            case("Careers"):
                title.setText("Careers");
                email.setText("yourcareers@qa.ac.uk");
                phoneno.setText("+44-207 656 8458");
                floor.setText("Find us here");
                floor.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        i = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.qa.com/careers/"));
                        startActivity(i);
                    }
                });
                break;
            default:
                break;
        }

        //navigate user to the Help activity
        dash.setOnClickListener(v -> navigateTo(Dashboard.class, null));

        // direct the user to the Timetable activity
        timetable.setOnClickListener(v -> navigateTo(Timetable.class, null));

        // direct the user to the Library activity
        library.setOnClickListener(v -> navigateTo(Library.class, null));

        //navigate the user to the Market activity
        market.setOnClickListener(v -> {
            Intent i = new Intent(Support2.this, ItemListClass.class);
            i.putExtra("ITEM", "Books_for_Sale");
            i.putExtra("ITEM2", 1);
            startActivity(i);
        });

        //navigate the user to Forum
        forum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Support2.this, ItemListClass.class);
                i.putExtra("ITEM", "Topics");
                i.putExtra("ITEM2", 1);
                startActivity(i);
            }
        });

        //direct the user to an external browser to the Roehampton Moodle page
        moodle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i = new Intent(Intent.ACTION_VIEW, Uri.parse("https://partnerships.moodle.roehampton.ac.uk"));
                startActivity(i);
            }
        });
    }

    public void navigateTo(Class goTo, String floor){
        i = new Intent(this, goTo);
        i.putExtra("floor", floor);
        startActivity(i);
    }
}
