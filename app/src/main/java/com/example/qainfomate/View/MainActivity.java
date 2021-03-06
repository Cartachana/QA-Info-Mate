package com.example.qainfomate.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.example.qainfomate.R;
import com.example.qainfomate.Application.Session;
import com.example.qainfomate.Models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    private EditText id, email, pass;
    private Button btnLogin;
    private TextView please, reset, SignUp;
    private FirebaseAuth fbAuth;
    private Query dbref;
    private Intent i;
    private static Context appContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        id = findViewById(R.id.et_id_main);
        email = findViewById(R.id.et_email_main);
        pass = findViewById(R.id.et_pass_main);
        btnLogin = findViewById(R.id.btn_Login_main);
        SignUp = findViewById(R.id.tv_SignUp_main);
        reset = findViewById(R.id.tv_reset_main);
        please = findViewById(R.id.tv_please_main);
        fbAuth = FirebaseAuth.getInstance();

        //getting Application Context to use in another activity
        appContext = getApplicationContext();

        // if user in Static Class is not null app jumps to Dashboard
        if(Session.LiveSession.user != null){
            i = new Intent(MainActivity.this, Dashboard.class);
            startActivity(i);
        }

    }

    public static Context getAppContext() {
        return appContext;
    }

    @Override
    protected void onStart() {
        super.onStart();

        btnLogin.setOnClickListener(v -> {
            //HERE THE APP WILL NOT THE USER LEAVE EMPTY FIELDS and will notify the user to fill
            if(id.getText().toString().isEmpty()||email.getText().toString().isEmpty()||pass.getText().toString().isEmpty()) {
                Toast.makeText(MainActivity.this, "Please fill in all fields!", Toast.LENGTH_LONG).show();
                please.setText("Please fill in all Fields!");
                please.setVisibility(View.VISIBLE);
            }

            else{
                //IF NO FIELD IS EMPTY THE APP WILL ATTEMPT TO SIGN THE USER IN
                //if sign in fails, text is shown to user saying credentials were not recognized and asking if he registered
                fbAuth.signInWithEmailAndPassword(email.getText().toString().trim(), pass.getText().toString().trim()).addOnSuccessListener(authResult -> {
                    ValueEventListener listener = new ValueEventListener() {
                        @Override
                        //the listener will get every record with the same ID as the ID entered
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for(DataSnapshot dss:dataSnapshot.getChildren())
                            {//but will only get the one with the same primary key as the UID in the Authentication
                                if(fbAuth.getCurrentUser().getUid().matches(dss.getKey())) {
                                    Session.LiveSession.user = dss.getValue(User.class);
                                    Session.LiveSession.setContext(appContext);
                                }
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
                    //our database query will get all users stuID in the databse that are equal to the ID entered
                    dbref = FirebaseDatabase.getInstance().getReference("_user_").orderByChild("stuID").equalTo(id.getText().toString().trim());
                    dbref.addListenerForSingleValueEvent(listener);
                }).addOnFailureListener(e -> {
                    Toast.makeText(MainActivity.this, "Wrong Credentials.\nHave you registered?", Toast.LENGTH_LONG).show();
                    please.setText("Wrong Student ID entered");
                    please.setVisibility(View.VISIBLE);
                });
            }});

        reset.setOnClickListener(v -> {

            String enteredEm = email.getText().toString().trim();

            if (TextUtils.isEmpty(enteredEm)) {
                please.setText("Enter your registered email address");
                please.setVisibility(View.VISIBLE);
                return;
            }

            fbAuth.sendPasswordResetEmail(enteredEm)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Toast.makeText(MainActivity.this, "Password Reset Email Sent!", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(MainActivity.this, "Failed to send reset email!", Toast.LENGTH_SHORT).show();
                        }
                    });
        });

        SignUp.setOnClickListener(v -> {
            i = new Intent(MainActivity.this, Register.class);
            startActivity(i);
        });
    }
}
