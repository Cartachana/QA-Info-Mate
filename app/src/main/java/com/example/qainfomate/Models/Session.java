package com.example.qainfomate.Models;

import android.app.Notification;
import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import com.example.qainfomate.R;
import com.example.qainfomate.Uni;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class Session {

    public static class LiveSession
    {
        public static User user;
        private static Context context;
        private static Query dbref;
        private static NotificationManagerCompat nmc;

        public static void setContext(Context _context){

            context = _context;
            nmc = NotificationManagerCompat.from(context);
            dbref = FirebaseDatabase.getInstance().getReference("Messages").orderByChild("idto").equalTo(user.getStuID());

            Notification notification = new NotificationCompat.Builder(context, Uni._MESSAGE)
                    .setSmallIcon(R.drawable.ic_mail_outline_black_24dp)
                    .setContentTitle("You have a new Message")
                    .setContentText("")
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .build();

            dbref.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                    nmc.notify(101, notification);
                }

                @Override
                public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                }

                @Override
                public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

                }

                @Override
                public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }

    }

}
