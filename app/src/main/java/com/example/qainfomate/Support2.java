package com.example.qainfomate;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class Support2 extends AppCompatActivity {
    private TextView title, email, phoneno;
    private String extra;
    private Bundle extras;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_support2);

        title=findViewById(R.id.tv_title_support2);
        email=findViewById(R.id.tv_contact_email_support2);
        phoneno=findViewById(R.id.tv_contact_number_support2);
        extras = getIntent().getExtras();
        extra = extras.getString("department");

        switch (extra){
            case("Student Finance"):
                title.setText("Finance");
                title.setTextSize(30);
                email.setText("student.finance.support@qa.ac.uk");
                email.setTextSize(15);
                phoneno.setText("0164 111 111");
                phoneno.setTextSize(15);
                break;
            case("Student Services"):
                title.setText("Services");
                title.setTextSize(30);
                email.setText("student.services@qa.ac.uk");
                email.setTextSize(15);
                phoneno.setText("0164 111 112");
                phoneno.setTextSize(15);
                break;
            case("Welfare"):
                title.setText("Student Welfare");
                title.setTextSize(30);
                email.setText("student.welfare@qa.ac.uk");
                email.setTextSize(15);
                phoneno.setText("0164 111 113");
                phoneno.setTextSize(15);
                break;
            case("ACE"):
                title.setText("ACE Team");
                title.setTextSize(30);
                email.setText("ace.team.support@qa.ac.uk");
                email.setTextSize(15);
                phoneno.setText("0164 111 114");
                phoneno.setTextSize(15);
                break;
            case("Library"):
                title.setText("Library");
                title.setTextSize(30);
                email.setText("london.library@qa.ac.uk");
                email.setTextSize(15);
                phoneno.setText("0164 111 114");
                phoneno.setTextSize(15);
                break;
            default:
                break;
        }
    }
}
