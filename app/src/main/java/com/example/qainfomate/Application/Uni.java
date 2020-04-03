package com.example.qainfomate.Application;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

public class Uni extends Application {
    public static final String _MESSAGE = "MSG";
    public static final String _UPDATE = "UPDATE";
    public static final String version = "1.0.0";

    @Override
    public void onCreate() {
        super.onCreate();
        //Check if Android OS version is Oreo or above
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel msg_channel = new NotificationChannel(_MESSAGE,
                    "Message", NotificationManager.IMPORTANCE_HIGH);
            msg_channel.setDescription("QA InfoMate");
            NotificationManager messageManager = getSystemService(NotificationManager.class);
            messageManager.createNotificationChannel(msg_channel);

            NotificationChannel update_channel = new NotificationChannel(_UPDATE,
                    "Update", NotificationManager.IMPORTANCE_HIGH);
            update_channel.setDescription("QA InfoMate");
            NotificationManager updateManager = getSystemService(NotificationManager.class);
            updateManager.createNotificationChannel(update_channel);
        }
    }
}
