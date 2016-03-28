package com.tek_genie.notifications;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

/**
 * Created by jasmineislam on 3/28/16.
 */
public class Notifier {
    public Notifier(){}

    Alarm alarm = new Alarm();
    private int notificationID = 100;

    protected void createNotification(Context context) {
        Log.i("Notify", "Displaying Notification");
        NotificationCompat.Builder nBuilder = new NotificationCompat.Builder(context);
        nBuilder.setContentTitle("Notification");
        nBuilder.setContentText("This is a Notification");
        nBuilder.setSmallIcon(R.drawable.ic_thinkful);

        nBuilder.setContentIntent(alarm.getMainActivityPendingIntent(context));
        nBuilder.setAutoCancel(true);

        Notification notification = nBuilder.build();

        NotificationManager mNotificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        mNotificationManager.notify(notificationID, notification);
    }
}
