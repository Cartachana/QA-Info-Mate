package com.example.qainfomate;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class Dashboard extends AppCompatActivity {

    private TextView logOut, studentName;
    private Button   timetable, library, forum, market, moodle, help;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        logOut=findViewById(R.id.tv_logOut_dashboard);
        studentName=findViewById(R.id.tv_logOut_dashboard);
        timetable=findViewById(R.id.btn_timetable_dashboard);
        library=findViewById(R.id.btn_library_dashboard);
        forum=findViewById(R.id.btn_forum_dashboard);
        market=findViewById(R.id.btn_market_dashboard);
        moodle=findViewById(R.id.btn_moodle_dashboard);
        help=findViewById(R.id.btn_help_dashboard);
    }
}
