package com.example.qainfomate.View;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.example.qainfomate.Models.Library_Book;
import com.example.qainfomate.Models.Review;
import com.example.qainfomate.Models.Session;
import com.example.qainfomate.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Post_review extends AppCompatActivity {

    Button post;
    EditText rev;
    TextView error;
    DatabaseReference dbref;
    Library_Book lb;
    Review rv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_review);

        // This section is the set up and size of the pop up "window"
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width*.7),(int)(height*.6));
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.gravity = Gravity.CENTER;
        params.x = 0;
        params.y = -20;
        getWindow().setAttributes(params);
        //Until here

        post = findViewById(R.id.btn_post_postRev);
        rev = findViewById(R.id.et_review_postRev);
        error = findViewById(R.id.tv_error_postRev);
        Bundle extra = getIntent().getExtras();
        lb = extra.getParcelable("LB");
        dbref = FirebaseDatabase.getInstance().getReference().child("Reviews");

    post.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(rev.getText().toString().isEmpty()){
                error.setVisibility(View.VISIBLE);
            }else {
                rv = new Review(Session.LiveSession.user.getStuID(), lb.getISBN(), rev.getText().toString());
                dbref.push().setValue(rv);
                Intent i = new Intent(Post_review.this, LibraryBookDetail.class);
                i.putExtra("LB", lb);
                startActivity(i);
            }
        }
    });
    }

}
