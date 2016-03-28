package com.tek_genie.notifications;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.WakefulBroadcastReceiver;

/**
 * Created by jasmineislam on 3/28/16.
 */
public class AlarmBroadcastReceiver extends WakefulBroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {
        Intent service = new Intent(context, NotifyService.class);
        startWakefulService(context, service);
    }
}
