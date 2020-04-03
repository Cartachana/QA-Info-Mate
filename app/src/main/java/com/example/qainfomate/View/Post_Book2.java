package com.example.qainfomate.View;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.qainfomate.Models.Book_for_Sale;
import com.example.qainfomate.R;
import com.example.qainfomate.Application.Session;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Post_Book2 extends AppCompatActivity {

    private Spinner spinner;
    private ArrayAdapter<String> spinnerAdapter;
    private EditText title, description, author;
    private Button post;
    private TextView error;
    private String url, id;
    private DatabaseReference dbref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_book2);

        title = findViewById(R.id.et_title_postbook2);
        description = findViewById(R.id.et_desc_postbook2);
        author = findViewById(R.id.et_author_postbook2);
        post = findViewById(R.id.btn_post_postbook2);
        Bundle extras = getIntent().getExtras(); //to get extras from previous activity
        url = extras.getString("url");
        id = extras.getString("id");
        spinner = findViewById(R.id.sp_category_postbook2);
        error = findViewById(R.id.tv_error_postbook2);
        Resources res = getResources();


        //here we use an arrayadapter to send the values of the array to the spinner
        spinnerAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item, res.getStringArray(R.array.categories));
        spinner.setAdapter(spinnerAdapter);


            post.setOnClickListener(v -> {
                if (title.getText().toString().isEmpty() || description.getText().toString().isEmpty() || author.getText().toString().isEmpty() || spinner.getSelectedItemId() == 0) {
                    error.setText("Please fill in all fields!!");
                    error.setVisibility(View.VISIBLE);
                } else {
                dbref = FirebaseDatabase.getInstance().getReference("Books_for_Sale");
                Book_for_Sale bookfs = new Book_for_Sale(title.getText().toString(), author.getText().toString(),
                        description.getText().toString(), spinner.getSelectedItem().toString(), url, "BFS",
                        Session.LiveSession.user.getStuID(), true);
                dbref.child(id).setValue(bookfs);
                Toast.makeText(Post_Book2.this, "Book Successfully Posted", Toast.LENGTH_LONG).show();
                Intent i = new Intent(Post_Book2.this, ItemListClass.class);
                i.putExtra("ITEM", "Books_for_Sale");
                i.putExtra("ITEM2", 2);
                startActivity(i);
            }});

    }
}
