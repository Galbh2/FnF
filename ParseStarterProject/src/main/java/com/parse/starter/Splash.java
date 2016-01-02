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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_layout);

        circles[0] = (ImageView) findViewById(R.id.circle_a);
        circles[1] = (ImageView) findViewById(R.id.circle_b);
        circles[2] = (ImageView) findViewById(R.id.circle_c);

        getPlacesFromCloud(new AnimationTask().execute());

    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
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
     * @param delay
     * @param animation
     */
    private void launchMain (final int delay, final AsyncTask animation) {

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

    private void getPlacesFromCloud(final AsyncTask animation) {

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
                animation.cancel(true);
                Intent intent = new Intent(Splash.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }

    private void bindDataFromCloud (List<ParseObject> dataFromCloud) {
        for (ParseObject o : dataFromCloud) {
            MainActivity.placesList.add(new Place(o.getObjectId(), o.getString("name"), o.getString("address"),
                    o.getNumber("grade").floatValue(),
                    o.getBoolean("openJobs")));
        }
    }
}

