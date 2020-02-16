package com.example.qainfomate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    private EditText id, email, pass;
    private Button btnLogin;
    private TextView register, please;
    private FirebaseAuth fbAuth;
    private Query dbref;
    private Intent i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        id = findViewById(R.id.et_id_main);
        email = findViewById(R.id.et_email_main);
        pass = findViewById(R.id.et_pass_main);
        btnLogin = findViewById(R.id.btn_Login_main);
        register = findViewById(R.id.tv_reg_main);
        please = findViewById(R.id.tv_please_main);
        fbAuth = FirebaseAuth.getInstance();

    }

    @Override
    protected void onStart() {
        super.onStart();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //HERE THE APP WILL NOT THE USER LEAVE EMPTY FIELDS and will notify the user to fill
                if(id.getText().toString().isEmpty()||email.getText().toString().isEmpty()||pass.getText().toString().isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please fill in all fields!", Toast.LENGTH_LONG).show();
                    please.setVisibility(View.VISIBLE);
                }

                else{
                    //IF NO FIELD IS EMPTY THE APP WILL ATTEMPT TO SIGN THE USER IN
                fbAuth.signInWithEmailAndPassword(email.getText().toString(), pass.getText().toString()).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {

                        //attempted STUDENT ID will be captured and compared with database
                        ValueEventListener listener = new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                for(DataSnapshot dss:dataSnapshot.getChildren())
                                {
                                  Session.LiveSession.user = dss.getValue(User.class);
                                }
                                if(Session.LiveSession.user != null)
                                {
                                    Toast.makeText(MainActivity.this, Session.LiveSession.user.getFname() + " " +
                                            "" + Session.LiveSession.user.getSname() + " " + "Logged in", Toast.LENGTH_LONG).show();
                                    i = new Intent(MainActivity.this, Dashboard.class);
                                    startActivity(i);
                            //if the ID does not match the email
                                } else{
                                    please.setText("Wrong Student ID entered");
                                    please.setVisibility(View.VISIBLE);
                                    fbAuth.signOut();
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        };
                        //here the entered ID and the ID in the database will be compared using the "listener"
                        dbref = FirebaseDatabase.getInstance().getReference("_user_").orderByChild("stuID").equalTo(id.getText().toString());
                        dbref.addListenerForSingleValueEvent(listener);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //TO CODE depending on the log in problem for now I will leave a toast of nonexistant user
                        Toast.makeText(MainActivity.this, "Wrong Credentials.\nHave you registered?", Toast.LENGTH_LONG).show();
                    }
                });
            }}
        });


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i = new Intent(MainActivity.this, Register.class);
                startActivity(i);
            }
        });
    }
}
