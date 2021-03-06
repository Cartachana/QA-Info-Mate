package com.example.qainfomate.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.qainfomate.R;
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

public class Post_lib_Book extends AppCompatActivity {

    private TextView progressText;
    private ProgressBar mProgressBar;
    private int mProgressStatus = 0;
    private ImageView imgBook;
    private Button next, upload;
    private Uri imageUri; // to save the Image URI in a global variable so we can use it in other methods
    private StorageReference sref; //to refer to our Firebase Storage Location
    private FirebaseAuth fbAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_lib_book);


        imgBook = findViewById(R.id.iv_img_postLib);
        next = findViewById(R.id.btn_next_postLib);
        upload = findViewById(R.id.btn_upload_postLib);
        sref = FirebaseStorage.getInstance().getReference("Library_Books");
        mProgressBar = findViewById(R.id.pb_postLib);
        progressText = findViewById(R.id.tv_progress_PostLib);
        fbAuth = FirebaseAuth.getInstance();

        Animation animation = new AlphaAnimation(1, (float) .4); // Change alpha from fully visible to partially visible
        animation.setDuration(1000); // duration - half a second
        animation.setInterpolator(new LinearInterpolator()); // do not alter animation rate
        animation.setRepeatCount(Animation.INFINITE); // Repeat animation infinitely
        animation.setRepeatMode(Animation.REVERSE); // Reverse animation at the end so the button will fade back in

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
                final DatabaseReference dbref = FirebaseDatabase.getInstance().getReference("Library_Books");
                final String book_id = dbref.push().getKey();
                final StorageReference reference = sref.child(book_id + "." + getExt(imageUri));
                reference.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                final String url = uri.toString();
                                final String id = book_id;
                                progressText.setText("Upload Complete");
                                upload.setVisibility(View.INVISIBLE);
                                next.setVisibility(View.VISIBLE);
                                next.startAnimation(animation);
                                //BUTTON NEXT PROGRAMMED HERE TO TAKE US TO THE NEXT ACTIVITY
                                next.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Intent i = new Intent(Post_lib_Book.this, Post_lib_Book2.class);
                                        i.putExtra("url", url);
                                        i.putExtra("id", id);
                                        startActivity(i);
                                    }
                                });

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(Post_lib_Book.this, "IMAGE FAILED TO UPLOAD", Toast.LENGTH_LONG).show();
                                reference.delete();
                            }
                        });
                    }
                }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                        double status = 100.0 * ((float) (taskSnapshot.getBytesTransferred()) / (float) (taskSnapshot.getTotalByteCount()));
                        mProgressStatus = (int) status;
                        mProgressBar.setProgress(mProgressStatus);
                        progressText.setVisibility(View.VISIBLE);
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
            imageUri = data.getData();
            upload.setVisibility(View.VISIBLE);
        }
    }



    private String getExt(Uri _imageUri){ //Returns file extension
        ContentResolver resolver = getContentResolver();
        MimeTypeMap map = MimeTypeMap.getSingleton();
        return map.getExtensionFromMimeType(resolver.getType(_imageUri));
    }
    }

