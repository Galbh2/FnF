package com.parse.starter;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

/**
 * Created by Gal on 02/01/2016.
 */
public class Splash extends AppCompatActivity {

    ImageView[] circles = new ImageView[3];
    AsyncTask animation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_layout);

        Log.d("callbackg", "onCreate");

        circles[0] = (ImageView) findViewById(R.id.circle_a);
        circles[1] = (ImageView) findViewById(R.id.circle_b);
        circles[2] = (ImageView) findViewById(R.id.circle_c);

       // animation = new AnimationTask().execute();
        getPlacesFromCloud();

    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("callbackg", "onResume");
        //finish();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("callbackg", "onDestroy");
        //finish();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("callbackg", "onPause");

        /*
        if (animation != null && animation.getStatus() == AsyncTask.Status.RUNNING) {
            animation.cancel(true);
        }
        finish();
        */
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("callbackg", "onStop");
        //finish();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("callbackg", "onRestart");
        Intent intent = new Intent(Splash.this, MainActivity.class);
        startActivity(intent);
    }
    public class AnimationTask extends AsyncTask<Void,Integer,Void> {

        @Override
        protected Void doInBackground(Void... params) {

            while (true) {

                for (int i = 0; i < 3; i++) {
                   publishProgress(i, 1);
                    try {
                        Thread.sleep(250);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    publishProgress(i, 0);
                }
            }
        }

        @Override
        protected void onProgressUpdate(Integer... progress) {

            if (progress[1] == 1) {
                circles[progress[0]].setVisibility(View.VISIBLE);
            } else {
                circles[progress[0]].setVisibility(View.INVISIBLE);
            }
        }

    }


    /**
     * This method isn't in use

     */
    private void launchMain (final int delay) {

        new Thread() {
            @Override
            public void run() {

                try {
                    Thread.sleep(delay);


                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }.start();
    }

    private void getPlacesFromCloud() {

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Place");
        query.setLimit(MainActivity.LIMIT);
        query.orderByDescending("grade");


        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null) {
                    bindDataFromCloud(objects);
                } else {
                    Log.d("Fetch err", e.getMessage());
                }
                Intent intent = new Intent(Splash.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }

    private void bindDataFromCloud (List<ParseObject> dataFromCloud) {

        if (!MainActivity.placesList.isEmpty()) {
            MainActivity.placesList.clear();
        }

        for (ParseObject o : dataFromCloud) {
            MainActivity.placesList.add(new MyPlace(o.getObjectId(), o.getString("name"), o.getString("address"),
                    o.getNumber("grade").floatValue(),
                    o.getBoolean("openJobs")));
        }
    }
}

