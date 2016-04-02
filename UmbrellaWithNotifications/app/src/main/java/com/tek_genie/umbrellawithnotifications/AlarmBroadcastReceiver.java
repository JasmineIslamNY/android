package com.tek_genie.umbrellawithnotifications;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.WakefulBroadcastReceiver;

/**
 * Created by jasmineislam on 4/1/16.
 */
public class AlarmBroadcastReceiver extends WakefulBroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent service = new Intent(context, NotifyService.class);
        service.putExtra("LatLongArrayService", intent.getStringArrayExtra("LatLongArray"));
        startWakefulService(context, service);
    }
}
