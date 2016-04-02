package com.tek_genie.umbrellawithnotifications;

/**
 * Created by jasmineislam on 4/1/16.
 */

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.SystemClock;
import android.util.Log;
import java.util.Calendar;

public class Alarm {
    public Alarm () {}

    protected PendingIntent getMainActivityPendingIntent(Context context){
        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(
                context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        return(pendingIntent);
    }

    protected PendingIntent getBroadcastActivityPendingIntent(Context context, String [] latlongArrayIntent) {
        Intent intent = new Intent(context, AlarmBroadcastReceiver.class);
        intent.putExtra("LatLongArray", latlongArrayIntent);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0);

        return(pendingIntent);
    }

    protected void setAlarm(Context context, String [] latlongArray) {
        AlarmManager alarmMgr = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        /*
        alarmMgr.set(AlarmManager.ELAPSED_REALTIME,
                SystemClock.elapsedRealtime() + 10 * 1000,
                getMainActivityPendingIntent(context));

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 7);
        calendar.set(Calendar.MINUTE, 00);
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
                getBroadcastActivityPendingIntent(context, latlongArray));

        Log.i("Alarm ", "is set");

    }

    protected void cancelAlarm(Context context, String [] latlongArray) {
        AlarmManager alarmMgr = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        alarmMgr.cancel(getBroadcastActivityPendingIntent(context, latlongArray));

        ComponentName receiver = new ComponentName(context, BootReceiver.class);
        PackageManager pm = context.getPackageManager();

        pm.setComponentEnabledSetting(receiver,
                PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                PackageManager.DONT_KILL_APP);

        Log.i("Alarm ", "is canceled");
    }



}
