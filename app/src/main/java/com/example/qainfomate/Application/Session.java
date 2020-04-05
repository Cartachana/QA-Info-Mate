package com.example.qainfomate.Application;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.app.TaskStackBuilder;
import com.example.qainfomate.Models.Message;
import com.example.qainfomate.Models.User;
import com.example.qainfomate.R;
import com.example.qainfomate.View.ItemListClass;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class Session {

    public static class LiveSession
    {
        public static User user;
        private static Context context;
        private static Query dbref;
        private static DatabaseReference vrsref;
        private static NotificationManagerCompat nmc;


        public static void setContext(Context _context){

            context = _context;
            nmc = NotificationManagerCompat.from(context);


            //Database reference to version number node in the database
            vrsref = FirebaseDatabase.getInstance().getReference("Version").child("updates");

            Notification updateNotification = new NotificationCompat.Builder(context, Uni._UPDATE)
                    .setSmallIcon(R.drawable.ic_file_download_black_24dp)
                    .setContentTitle("New Version of the App Available")
                    .setContentText("*New Features*")
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .build();

           vrsref.addChildEventListener(new ChildEventListener() {
               @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                    if(!dataSnapshot.getValue().toString().equals(Uni.version)) {
                        nmc.notify(102, updateNotification);
                    }
                }

                @Override
                public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                    if(!dataSnapshot.getValue().toString().equals(Uni.version)){
                        nmc.notify(102, updateNotification);
                    }
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

            //Database query to filter messages by user ID
            dbref = FirebaseDatabase.getInstance().getReference("Messages")
                    .orderByChild("idto").equalTo(user.getStuID());

            //setting intent for Messages activity
            Intent intent = new Intent(context, ItemListClass.class);
            intent.putExtra("ITEM", "Messages");
            intent.putExtra("ITEM2", 1);


            TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
            //putting our intent on "standby" in a stackBuilder
            stackBuilder.addNextIntentWithParentStack(intent);
            //we define a pending intent that will get our previously defined intent
            PendingIntent pendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);


            dbref.addChildEventListener(new ChildEventListener() {
                @Override // checks for added messages
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                    //second check if the message was not read yet
                    if(!dataSnapshot.getValue(Message.class).getRead()) {
                        Notification notification = new NotificationCompat.Builder(context, Uni._MESSAGE)
                                //if tapped, takes user to messages activity from pendingIntent
                                .setContentIntent(pendingIntent)
                                .setSmallIcon(R.drawable.ic_mail_outline_black_24dp)
                                .setContentTitle("You have a new Message")
                                .setContentText("Message regarding " + dataSnapshot.getValue(Message.class).getBookTitle())
                                .setPriority(NotificationCompat.PRIORITY_HIGH)
                                .build();
                        nmc.notify(101, notification);
                    }
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
