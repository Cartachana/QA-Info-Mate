package com.example.qainfomate.View;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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
import com.example.qainfomate.Models.Session;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class Post_Book2 extends AppCompatActivity {

    private Spinner spinner;
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

        //Here I create an arraylist to send values to our Spinner (dropdown menu)
        final ArrayList<String> categories = new ArrayList<>();
        categories.add("Book Category");
        categories.add("Programming");
        categories.add("Management");
        categories.add("Biology");
        categories.add("Web Development");
        categories.add("Networking");
        categories.add("User Experience");

        //here we use an arrayadapter to send the values of the array to the spinner
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item, categories);
        spinner.setAdapter(spinnerAdapter);

        if (title.getText().toString().isEmpty() || description.getText().toString().isEmpty() || author.getText().toString().isEmpty() || spinner.getSelectedItemId() == 0) {
            error.setText("Please fill in all fields!!");
            error.setVisibility(View.VISIBLE);
        } else {
            post.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dbref = FirebaseDatabase.getInstance().getReference("Books_for_Sale");
                    Book_for_Sale bookfs = new Book_for_Sale(title.getText().toString(), author.getText().toString(),
                            description.getText().toString(), spinner.getSelectedItem().toString(), url,
                            Session.LiveSession.user.getStuID(), true);
                    dbref.child(id).setValue(bookfs);
                    Toast.makeText(Post_Book2.this, "Book Successfully Posted", Toast.LENGTH_LONG).show();
                    Intent i = new Intent(Post_Book2.this, BookSale.class);
                    startActivity(i);
                }
            });
        }
    }
}
