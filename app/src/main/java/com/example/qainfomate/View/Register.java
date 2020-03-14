package com.example.qainfomate.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.qainfomate.Models.Session;
import com.example.qainfomate.R;
import com.example.qainfomate.Models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class Register extends AppCompatActivity {

    private EditText stuID, fn, sn, em, pass, confpass;
    private String check1, check2;
    private Button btnReg;
    private TextView error;
    //Declare object of Firebase Auth
    private FirebaseAuth fbAuth;
    private DatabaseReference dbref;
    private Query dbquery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //Initialise our FB object
        fbAuth = FirebaseAuth.getInstance();
        stuID = findViewById(R.id.et_id_register);
        fn = findViewById(R.id.et_fname_register);
        sn = findViewById(R.id.et_lname_register);
        em = findViewById(R.id.et_email_register);
        pass = findViewById(R.id.et_pass_register);
        confpass = findViewById(R.id.et_confpass_register);
        btnReg = findViewById(R.id.btn_register);
        error = findViewById(R.id.tv_error_register);

    }

    @Override
    protected void onStart() {
        super.onStart();

        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(stuID.getText().toString().isEmpty()||fn.getText().toString().isEmpty()||sn.getText().toString().isEmpty()||em.getText().toString().isEmpty()||pass.getText().toString().isEmpty()||confpass.getText().toString().isEmpty()) {
                    error.setText("Please fill in all fields!!");
                    error.setVisibility(View.VISIBLE);
                }else{
                    check1 = pass.getText().toString();
                    check2 = confpass.getText().toString();
                    if (check1.equals(check2)) {
                        //Registering user if passwords match
                        ValueEventListener listener = new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if(dataSnapshot.exists()){
                                    for(DataSnapshot dss:dataSnapshot.getChildren())
                                    {
                                        error.setText("Wrong Student ID entered");
                                        error.setVisibility(View.VISIBLE);
                                        fbAuth.signOut();
                                    }
                                }
                                else{fbAuth.createUserWithEmailAndPassword(em.getText().toString(), pass.getText().toString()).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                    @Override
                                    public void onSuccess(AuthResult authResult) {
                                        //logging in user in the background to get his details
                                        fbAuth.signInWithEmailAndPassword(em.getText().toString(), pass.getText().toString()).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                            @Override
                                            public void onSuccess(AuthResult authResult) {
                                                dbref = FirebaseDatabase.getInstance().getReference("_user_");
                                                //Storing user's details in Realtime database
                                                User u = new User(stuID.getText().toString(), fn.getText().toString(), sn.getText().toString());
                                                dbref.child(fbAuth.getUid()).setValue(u);
                                                fbAuth.signOut();
                                                //directing to splash page
                                                Intent i = new Intent(Register.this, Splash.class);
                                                i.putExtra("name", u.getFname());
                                                i.putExtra("surname", u.getSname());
                                                startActivity(i);
                                            }
                                        });
                                    }


                                }).addOnFailureListener(e -> {
                                    error.setText(e.toString());
                                    error.setVisibility(View.VISIBLE);
                                    //Toast.makeText(Register.this, e.toString(), Toast.LENGTH_LONG).show();
                                });
                                }

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        };
                        dbquery = FirebaseDatabase.getInstance().getReference("_user_").orderByChild("stuID").equalTo(stuID.getText().toString());
                        dbquery.addListenerForSingleValueEvent(listener);

                    } else {
                        error.setText("Passwords do not Match!");
                        error.setVisibility(View.VISIBLE);
                    }

                }
            }
        });
    }

}

