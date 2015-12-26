package com.parse.starter;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by Gal on 12/2/2015.
 */
public class LaunchNotificationBroadcast extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {


        context.startService(new Intent(context, LaunchNotificationService.class));

    }
}
