package com.tek_genie.umbrellawithnotifications;

/**
 * Created by jasmineislam on 4/1/16.
 */
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

/**
 * Created by jasmineislam on 3/28/16.
 */
public class Notifier {
    public Notifier(){}

    private int notificationID = 100;

    protected void createNotification(Context context, String display) {
        Log.i("Notify", "Displaying Notification");
        NotificationCompat.Builder nBuilder = new NotificationCompat.Builder(context);
        nBuilder.setContentTitle(context.getString(R.string.app_name));
        nBuilder.setContentText(display);
        nBuilder.setSmallIcon(R.drawable.umbrella512);

        nBuilder.setContentIntent(getMainActivityPendingIntent(context));
        nBuilder.setAutoCancel(true);

        Notification notification = nBuilder.build();

        NotificationManager mNotificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        mNotificationManager.notify(notificationID, notification);
    }
    protected PendingIntent getMainActivityPendingIntent(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        return(pendingIntent);
    }
}
