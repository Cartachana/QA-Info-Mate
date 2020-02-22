package com.example.qainfomate;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.qainfomate.View.MainActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class Post_Book extends AppCompatActivity {

    TextView user, logOut, progress;
    ImageView imgBook;
    Button next, upload;
    Uri imageUri; // to save the Image URI in a global variable so we can use it in other methods
    StorageReference sref; //to refer to our Firebase Storage Location
    FirebaseAuth fbAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_book);

        logOut = findViewById(R.id.tv_logOut_postBook);
        user = findViewById(R.id.tv_user_postBook);
        imgBook = findViewById(R.id.iv_img_postBook);
        next = findViewById(R.id.btn_next_postbook);
        upload = findViewById(R.id.btn_upload_postbook);
        sref = FirebaseStorage.getInstance().getReference("Books_for_Sale");
        progress = findViewById(R.id.tv_progress_postBook);
        fbAuth = FirebaseAuth.getInstance();
        user.setText(Session.LiveSession.user.getFname()); //displays the user currently logged in

        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fbAuth.signOut();
                //temporary inner class emptied
                Session.LiveSession.user = null;
                Intent i = new Intent(Post_Book.this, MainActivity.class);
                startActivity(i);
            }
        });

        imgBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.setType(("image/*")); //to filter out all files that are not images
                i.setAction(Intent.ACTION_GET_CONTENT); //Tells the intent to bring into our app what we clicked on
                startActivityForResult(i, 101);
            }
        });

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final DatabaseReference dbref = FirebaseDatabase.getInstance().getReference("Books_for_Sale");
                final String book_id = dbref.push().getKey();
                final StorageReference reference = sref.child(book_id + "." + getExt(imageUri));
                reference.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                String url = uri.toString();
                                String id = book_id;
                                Intent i = new Intent(Post_Book.this, Post_Book2.class);
                                i.putExtra("url", url);
                                i.putExtra("id", id);
                                startActivity(i);
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                            reference.delete();
                            }
                        });
                    }
                });
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 101 && resultCode == RESULT_OK && data.getData() != null){
            Picasso.get().load(data.getData()).fit().into(imgBook);
            upload.setVisibility(View.INVISIBLE);
            next.setVisibility(View.VISIBLE);
        }
    }

    private String getExt(Uri _imageUri){ //Returns file extension
        ContentResolver resolver = getContentResolver();
        MimeTypeMap map = MimeTypeMap.getSingleton();
        return map.getExtensionFromMimeType(resolver.getType(_imageUri));
    }
}
