package com.example.qainfomate;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

public class Timetable extends AppCompatActivity {
    private TextView g02_1, g02_2, lg104, f102, g02_3, g02_4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timetable);

        g02_1 = findViewById(R.id.tv_G02r1c2_timetable);
        g02_2 = findViewById(R.id.tv_G02r1c3_timetable);
        lg104 = findViewById(R.id.tv_LG0104r1c5_timetable);
        f102 = findViewById(R.id.tv_102r10c2_timetable);
        g02_3 = findViewById(R.id.tv_G02r10c3_timetable);
        g02_4 = findViewById(R.id.tv_G02r10c5_timetable);

        f102.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateTo(Floor1.class);
            }
        });

    }

    public void navigateTo(Class goTo){
        Intent i = new Intent(this, goTo);
        startActivity(i);
    }
}
