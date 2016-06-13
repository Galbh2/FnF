package com.parse.jooba;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

import java.util.List;
import java.util.concurrent.TimeUnit;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

/**
 * Created by Gal on 12/2/2015.
 */
public class LaunchNotificationService extends Service {

    IBinder mBinder;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Offer");
        // query.whereEqualTo("playerName", "Dan Stemkoski");

        query.orderByAscending("priority");
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> offerList, ParseException e) {
                if (e == null) {
                    Log.d("offers", "Retrieved " + offerList.size() + " offers");

                    if (!offerList.isEmpty()) {
                        ParseObject firstOffer = offerList.get(0);
                        pushNotification(firstOffer);
                    }
                } else {
                    Log.d("score", "Error: " + e.getMessage());
                }
            }
        });

        return super.onStartCommand(intent, flags, startId);
    }

    private void pushNotification(ParseObject object) {

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this);

        mBuilder.setSmallIcon(R.mipmap.ic_launcher);
        mBuilder.setContentTitle(object.getString("title"));
        mBuilder.setContentText(object.getString("description"));

        // Creates an explicit intent for an Activity in your app
        Intent resultIntent = new Intent(this, MainActivity.class);

        // The stack builder object will contain an artificial back stack for the
        // started Activity.
        // This ensures that navigating backward from the Activity leads out of
        // your application to the Home screen.
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);

        // Adds the back stack for the Intent (but not the Intent itself)
        stackBuilder.addParentStack(MainActivity.class);

        // Adds the Intent that starts the Activity to the top of the stack
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        mBuilder.setContentIntent(resultPendingIntent);
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        // Sets the next notification
        PendingIntent nextNotificationIntnet = PendingIntent.getBroadcast(this, 0,
                new Intent(this, LaunchNotificationBroadcast.class) , PendingIntent.FLAG_ONE_SHOT);
        setAlarm(nextNotificationIntnet);


        // mId allows you to update the notification later on.
        mNotificationManager.notify(0, mBuilder.build());



    }

    private void setAlarm(PendingIntent nextNotificationIntnet) {

        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC,
                System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(1), nextNotificationIntnet);
    }
}
