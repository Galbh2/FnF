/*
 * Copyright (c) 2015-present, Parse, LLC.
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree. An additional grant
 * of patent rights can be found in the PATENTS file in the same directory.
 */
package com.parse.jooba;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.location.places.ui.SupportPlaceAutocompleteFragment;
import com.parse.FindCallback;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;


public class MainActivity extends AppCompatActivity implements PlaceAdapter.PlaceClickListener,
        GoogleApiClient.OnConnectionFailedListener, ConnectionCallbacks {

    final static int LIMIT = 50;
    private Toolbar toolBar;
    private RecyclerView recyclerView;
    public static ArrayList<MyPlace> placesList = new ArrayList<MyPlace>();
    public ArrayList<MyPlace> m_PlaceListFromGoogle;
    protected GoogleApiClient mGoogleApiClient;
    private final Fire m_NetManager = new Fire();
    private ParseQuery<ParseObject> m_ParseQuery;
    private DownloadImageTask m_DownloadImageTask;
    private DownloadTask m_DownloadTask;
    private String mLatitude = "32.185533";
    private String mLongitud = "34.854347";
    private AtomicBoolean m_UpdatedLocation = new AtomicBoolean(false);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ParseAnalytics.trackAppOpenedInBackground(getIntent());

        buildGoogleApiClient();

        setToolBar();

        m_DownloadTask = new DownloadTask();
        m_DownloadTask.execute();
        setAutoCompleteFrag();

        //ParseObject testObject = new ParseObject("TestObject");
        //testObject.put("foo", "bar");
        //testObject.saveInBackground();

        //Intent intent = new Intent(this, LaunchNotificationBroadcast.class);
        //sendBroadcast(intent);

    }

    private void stopDownloadTask() {
        if (m_DownloadTask != null &&
                m_DownloadTask.getStatus() == AsyncTask.Status.RUNNING) {

            m_DownloadTask.cancel(true);
        }
    }

    @Override
    protected void onStart() {
        mGoogleApiClient.connect();
        super.onStart();
    }

    @Override
    protected void onStop() {
        mGoogleApiClient.disconnect();
        stopParseWork();
        stopImgWork();
        stopDownloadTask();
        m_UpdatedLocation.set(false);
        super.onStop();
    }

    private void setAutoCompleteFrag() {

        SupportPlaceAutocompleteFragment autocompleteFragment = (SupportPlaceAutocompleteFragment)
                getSupportFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);

        autocompleteFragment.setHint("בדוק את הג'וב...");
        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                // TODO: Get info about the selected place.
                stopImgWork();
                stopParseWork();
                new DownloadSinglePlaceTask().execute(place.getId().toString());

            }

            @Override
            public void onError(Status status) {
                // TODO: Handle the error.
                Log.i("msg", "An error occurred: " + status);
            }
        });
    }

    private void stopImgWork() {
        if (m_DownloadImageTask != null &&
            m_DownloadImageTask.getStatus() == AsyncTask.Status.RUNNING) {

            m_DownloadImageTask.cancel(true);
        }
    }

    private void stopParseWork() {
        if (m_ParseQuery != null) {
            m_ParseQuery.cancel();
        }

    }

    private void testData() {

        if (m_PlaceListFromGoogle.size() == 0) {
            Toast.makeText(this, "Something went wrong, please check your internet connection", Toast.LENGTH_LONG).show();
        }

    }

    /**
     *This method fetches the next 50 Places from the cloud.
     */
    private void getPlacesFromCloud() {

        ParseQuery<ParseObject> query = ParseQuery.getQuery("MyPlace");
        query.setSkip(placesList.size() + LIMIT);
        query.setLimit(LIMIT);
        query.orderByDescending("grade");


        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {

                if (e == null) {
                    bindDataFromCloud(objects);
                } else {
                    Log.d("Fetch err", e.getMessage());
                }
            }
        });

    }

    /**
     * This method adds the object from the cloud to the placeList and calls
     * for an update of the view.
     * @param dataFromCloud
     */
    private void bindDataFromCloud(List<ParseObject> dataFromCloud) {

        for (ParseObject o : dataFromCloud) {
            placesList.add(new MyPlace(o.getObjectId(), o.getString("name"), o.getString("address"),
                    o.getNumber("grade").floatValue(),
                    o.getBoolean("openJobs")));
        }
        recyclerView.getAdapter().notifyDataSetChanged();
    }

    private void reset() {
        this.onConnected(null);
        m_DownloadTask = new DownloadTask();
        m_DownloadTask.execute();
    }

    private void setRecyclerView() {

        recyclerView = (RecyclerView) findViewById(R.id.places_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new PlaceAdapter(this, m_PlaceListFromGoogle));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        } else if (id == R.id.action_refresh) {
            reset();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /*
    Sets up the top toolbar
     */
    private void setToolBar() {
        toolBar = (Toolbar) findViewById(R.id.appBar);
        setSupportActionBar(toolBar);
    }

    @Override
    public void itemClicked(View view, int position) {

        MyPlace p = m_PlaceListFromGoogle.get(position);

        Bundle bundle = new Bundle();
        bundle.putString("gID", p.getId());
        bundle.putString("NAME", p.getName());
        bundle.putDouble("GRADE", p.getGrade());
        bundle.putBoolean("JOB", p.isOpenJobs());
        bundle.putString("ADDRESS", p.getAddress());
        bundle.putParcelable("IMAGE", p.getImage());
        bundle.putIntArray("RESULTS", p.getResults());

        bundle.putStringArrayList("COMMETNS", p.getComments());

        Intent intent = new Intent(this, ProfileActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        stopDownloadTask();
        Log.d("loading_error", "connection_failed");
        Toast.makeText(this, "הג'י פי אס שלי מחובר ?", Toast.LENGTH_LONG);
    }

    protected void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient
                .Builder(this)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .addApi(LocationServices.API)
                .enableAutoManage(this, this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();

    }


    @Override
    public void onConnected(Bundle bundle) {

        try {
            Location lastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
            if (lastLocation != null) {
                mLatitude = String.valueOf(lastLocation.getLatitude());
                mLongitud = String.valueOf(lastLocation.getLongitude());
                Log.d("lati", mLatitude);
                Log.d("long", mLongitud);


            }
        } catch (SecurityException e) {
            e.printStackTrace();
        }

        m_UpdatedLocation.set(true);
    }

    @Override
    public void onConnectionSuspended(int i) {
        Toast.makeText(this, "הגי' פי אס שלי עובד ?", Toast.LENGTH_SHORT);
    }


    private class DownloadTask extends AsyncTask<Void, Integer, ArrayList<MyPlace>> {

        @Override
        protected ArrayList<MyPlace> doInBackground(Void... params) {

            while (!m_UpdatedLocation.get()) {
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            Log.d("DownloadTask", "started");
            Log.d("lati", mLatitude);
            Log.d("long", mLongitud);
            String data = m_NetManager.doRequest("2000", "restaurant", mLatitude, mLongitud);

            if (data == null) {
                return new ArrayList<MyPlace>();
            }

            return Fire.fromJsonToObjects(data);
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(ArrayList<MyPlace> myPlaces) {
            m_PlaceListFromGoogle = myPlaces;
            Log.d("listSize", String.valueOf(myPlaces.size()));

            setRecyclerView();
            testData();
            recyclerView.getAdapter().notifyDataSetChanged();

            m_DownloadImageTask = new DownloadImageTask();
            m_DownloadImageTask.execute();
            calcGrades();
        }
    }

    private void calcGrades() {

        for (int i = 0; i < m_PlaceListFromGoogle.size(); i++) {

            final MyPlace place = m_PlaceListFromGoogle.get(i);
            final int index = i;

            m_ParseQuery = ParseQuery.getQuery("Feedback");
            m_ParseQuery.whereEqualTo("gID", place.getId());


            m_ParseQuery.findInBackground(new FindCallback<ParseObject>() {
                @Override
                public void done(List<ParseObject> objects, ParseException e) {
                    if (e != null){
                        Log.e("getGradeError", e.getMessage());
                    } else {
                        GradeCalc.calcParams(objects, place);
                        setGrades(index);
                    }
                }
            });
        }
    }

    public void setGrades(int i_Index) {

        recyclerView.getAdapter().notifyItemChanged(i_Index);

    }

    private class DownloadSinglePlaceTask extends AsyncTask<String, Void, MyPlace> {

        @Override
        protected MyPlace doInBackground(String... params) {
            String j = m_NetManager.doRequest(params[0]);
            return Fire.fromJsonToOneObject(j);
        }

        @Override
        protected void onPostExecute(MyPlace myPlace) {
            m_PlaceListFromGoogle.clear();
            m_PlaceListFromGoogle.add(myPlace);
            recyclerView.getAdapter().notifyDataSetChanged();
            calcGrades();
            m_DownloadImageTask = new DownloadImageTask();
            m_DownloadImageTask.execute();

        }
    }

    private class DownloadImageTask extends AsyncTask<Void, Integer, Bitmap> {

        @Override
        protected Bitmap doInBackground(Void... params) {

            String ref;
            InputStream in = null;
            for (int i = 0; i < m_PlaceListFromGoogle.size(); i++) {
                try {
                    if (isCancelled()) {
                        break;
                    }
                    MyPlace p = m_PlaceListFromGoogle.get(i);
                    ref = p.getPhotoRef();

                    if (ref != null && !ref.equals("")) {
                        in = m_NetManager.downloadImage(ref);
                        p.setImage(BitmapFactory.decodeStream(in));
                    }
                } catch (Exception e) {
                    Log.e("ImageError", e.getMessage());
                    e.printStackTrace();
                } finally {
                    try {
                        if (in != null) {
                            in.close();
                            in = null;
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                if (in != null) {
                    try {
                        in.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                publishProgress(i);
            }

            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            recyclerView.getAdapter().notifyItemChanged(values[0]);
        }


        protected void onPostExecute(Bitmap result) {
            //bmImage.setImageBitmap(result);
        }
    }
}
