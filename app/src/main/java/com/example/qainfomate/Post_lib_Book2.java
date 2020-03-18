package com.example.qainfomate;

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
import com.example.qainfomate.Models.Library_Book;
import com.example.qainfomate.Models.Session;
import com.example.qainfomate.View.Dashboard;
import com.example.qainfomate.View.ItemListClass;
import com.example.qainfomate.View.Post_Book2;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Post_lib_Book2 extends AppCompatActivity {

    private Spinner spinner;
    private ArrayAdapter<String> spinnerAdapter;
    private EditText title, description, author, edition, isbn;
    private Button post;
    private TextView error;
    private String url, id;
    private DatabaseReference dbref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_lib__book2);

        title = findViewById(R.id.et_title_postlibbook2);
        description = findViewById(R.id.et_desc_postlibbook2);
        author = findViewById(R.id.et_author_postlibbook2);
        edition = findViewById(R.id.et_edition_postlibbook2);
        isbn = findViewById(R.id.et_isbn_postlibbook2);
        post = findViewById(R.id.btn_post_postlibbook2);
        Bundle extras = getIntent().getExtras(); //to get extras from previous activity
        url = extras.getString("url");
        id = extras.getString("id");
        spinner = findViewById(R.id.sp_category_postlibbook2);
        error = findViewById(R.id.tv_error_postlibbook2);
        Resources res = getResources();


        //here we use an arrayadapter to send the values of the array to the spinner
        spinnerAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item, res.getStringArray(R.array.categories));
        spinner.setAdapter(spinnerAdapter);


        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (title.getText().toString().isEmpty() || description.getText().toString().isEmpty()||isbn.getText().toString().isEmpty()|| edition.getText().toString().isEmpty() || author.getText().toString().isEmpty() || spinner.getSelectedItemId() == 0) {
                    error.setText("Please fill in all fields!!");
                    error.setVisibility(View.VISIBLE);
                } else {
                    dbref = FirebaseDatabase.getInstance().getReference("Library_Books");
                    Library_Book libbook = new Library_Book(title.getText().toString(), author.getText().toString(),
                            description.getText().toString(), spinner.getSelectedItem().toString(), url, "LB",
                            "Available", edition.getText().toString(), isbn.getText().toString());
                    dbref.child(id).setValue(libbook);
                    Toast.makeText(Post_lib_Book2.this, "Book Successfully Posted", Toast.LENGTH_LONG).show();
                    Intent i = new Intent(Post_lib_Book2.this, Dashboard.class);
                    startActivity(i);
                }}
        });

    }
    }

