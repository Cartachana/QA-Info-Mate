package com.example.qainfomate;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class Floor2 extends AppCompatActivity {

    private ImageView floor2img;
    private TextView logout, user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_floor2);

        floor2img = findViewById(R.id.img_floor2_floor2);
        logout = findViewById(R.id.tv_logOut_floor2);
        user = findViewById(R.id.tv_user_floor2);
    }
}
