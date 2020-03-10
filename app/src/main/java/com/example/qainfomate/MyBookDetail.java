package com.example.qainfomate;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.qainfomate.Models.Book;
import com.example.qainfomate.Models.Book_for_Sale;
import com.example.qainfomate.View.ItemListClass;
import com.example.qainfomate.View.Post_Book2;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

public class MyBookDetail extends AppCompatActivity {

    private ImageView bookImg;
    private TextView error;
    private EditText title, author, desc;
    private Spinner spinner;
    private CheckBox available;
    private Button update, delete;
    public ArrayAdapter<String> spinnerAdapter;
    private DatabaseReference dbref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_book_detail);

        // This section is the set up and size of the pop up "window"
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width*.7),(int)(height*.7));
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.gravity = Gravity.CENTER;
        params.x = 0;
        params.y = -20;
        getWindow().setAttributes(params);

        Resources res = getResources();

        bookImg = findViewById(R.id.iv_bookimg_mybookdetail);
        title = findViewById(R.id.et_title_mybookdetails);
        author = findViewById(R.id.et_author_mybookdetails);
        desc = findViewById(R.id.et_desc_mybookdetails);
        update = findViewById(R.id.btn_update_mybookdetail);
        delete = findViewById(R.id.btn_delete_mybookdetail);
        available = findViewById(R.id.cb_available_mybookdetails);
        error = findViewById(R.id.tv_error_mybookdetails);

        spinner = findViewById(R.id.sp_cat_mybookdetail);
        spinnerAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item, res.getStringArray(R.array.categories));
        spinner.setAdapter(spinnerAdapter);
        dbref = FirebaseDatabase.getInstance().getReference("Books_for_Sale");

        Intent i = getIntent();
        Book_for_Sale bfs = i.getParcelableExtra("BFS");
        String key = i.getStringExtra("KEY");
        Picasso.get().load(bfs.getImageUrl()).fit().into(bookImg);
        title.setText(bfs.getTitle());
        author.setText(bfs.getAuthor());
        desc.setText(bfs.getDescription());
        if(bfs.getAvaiable()){
            available.setChecked(true);
        }


        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (title.getText().toString().isEmpty() || desc.getText().toString().isEmpty() || author.getText().toString().isEmpty() || spinner.getSelectedItemId() == 0) {
                    error.setText("Please fill in all fields!!");
                    error.setVisibility(View.VISIBLE);
                } else {
                   bfs.setTitle(title.getText().toString());
                   bfs.setAuthor(author.getText().toString());
                   bfs.setDescription(desc.getText().toString());
                   bfs.setCategory(spinner.getSelectedItem().toString());
                   bfs.setAvaiable(available.isChecked());
                   dbref.child(key).setValue(bfs);
                    Toast.makeText(MyBookDetail.this, "Book Successfully Updated", Toast.LENGTH_LONG).show();
                    Intent i = new Intent(MyBookDetail.this, ItemListClass.class);
                    i.putExtra("ITEM", "Books_for_Sale");
                    i.putExtra("ITEM2", 2);
                    startActivity(i);
                }
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbref.child(key).removeValue();
                Intent i = new Intent(MyBookDetail.this, ItemListClass.class);
                i.putExtra("ITEM", "Books_for_Sale");
                i.putExtra("ITEM2", 2);
                startActivity(i);
            }
        });
    }
}
