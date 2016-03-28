package com.tek_genie.notifications;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.SystemClock;
import android.util.Log;

import java.util.Calendar;

/**
 * Created by jasmineislam on 3/17/16.
 */
public class Alarm {
    public Alarm () {}

    protected PendingIntent getMainActivityPendingIntent(Context context){
        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(
                context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        return(pendingIntent);
    }

    protected PendingIntent getBroadcastActivityPendingIntent(Context context) {
        Intent intent = new Intent(context, AlarmBroadcastReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0);

        return(pendingIntent);
    }

    protected void setAlarm(Context context) {
        AlarmManager alarmMgr = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        /*
        alarmMgr.set(AlarmManager.ELAPSED_REALTIME,
                SystemClock.elapsedRealtime() + 10 * 1000,
                getMainActivityPendingIntent());

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 14);
        calendar.set(Calendar.MINUTE, 45);
        long milliseconds = calendar.getTimeInMillis();

        alarmMgr.setInexactRepeating(AlarmManager.RTC,
                milliseconds,
                AlarmManager.INTERVAL_DAY,
                getMainActivityPendingIntent(context));

         */

        ComponentName bootReceiver = new ComponentName(context, BootReceiver.class);
        PackageManager packageManager = context.getPackageManager();
        packageManager.setComponentEnabledSetting(bootReceiver,
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                PackageManager.DONT_KILL_APP);

        alarmMgr.setInexactRepeating(AlarmManager.ELAPSED_REALTIME,
                SystemClock.elapsedRealtime() + 10 * 1000,
                10 * 1000,
                getBroadcastActivityPendingIntent(context));

        Log.i("Alarm ", "is set");

    }

    protected void cancelAlarm(Context context) {
        AlarmManager alarmMgr = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        alarmMgr.cancel(getBroadcastActivityPendingIntent(context));

        ComponentName receiver = new ComponentName(context, BootReceiver.class);
        PackageManager pm = context.getPackageManager();

        pm.setComponentEnabledSetting(receiver,
                PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                PackageManager.DONT_KILL_APP);

        Log.i("Alarm ", "is canceled");
    }



}
