package com.example.qainfomate;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class Support extends AppCompatActivity {

    TextView finance, stuServ, welfare, ace, lib, internships, careers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_support);

        finance = findViewById(R.id.tv_student_finance_support);
        stuServ = findViewById(R.id.tv_student_services_support);
        welfare = findViewById(R.id.tv_student_welfare_support);
        ace = findViewById(R.id.tv_ACE_support);
        lib = findViewById(R.id.tv_library_support);
        internships = findViewById(R.id.tv_student_finance_support);

    }
}
