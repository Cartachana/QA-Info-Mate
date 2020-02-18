package com.example.qainfomate;

import androidx.appcompat.app.AppCompatActivity;

import android.media.Image;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

public class Floor1 extends AppCompatActivity {

    private ImageView floor1img;
    private TextView logout, user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_floor1);

        floor1img = findViewById(R.id.img_floor1_floor1);
        logout = findViewById(R.id.tv_logOut_floor1);
        user = findViewById(R.id.tv_user_floor1);
    }
}
