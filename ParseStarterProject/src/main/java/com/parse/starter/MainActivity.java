/*
 * Copyright (c) 2015-present, Parse, LLC.
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree. An additional grant
 * of patent rights can be found in the PATENTS file in the same directory.
 */
package com.parse.starter;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
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
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements PlaceAdapter.PlaceClickListener, GoogleApiClient.OnConnectionFailedListener{

    final static int LIMIT = 50;
    private Toolbar toolBar;
    private RecyclerView recyclerView;
    public static ArrayList<MyPlace> placesList = new ArrayList<MyPlace>();
    public ArrayList<MyPlace> m_PlaceListFromGoogle;
    protected GoogleApiClient mGoogleApiClient;
    private final Fire m_NetManager = new Fire();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ParseAnalytics.trackAppOpenedInBackground(getIntent());

        new DownloadTask().execute(new String[2]);
        setToolBar();



        buildGoogleApiClient();
        setAutoCompleteFrag();

        //ParseObject testObject = new ParseObject("TestObject");
        //testObject.put("foo", "bar");
        //testObject.saveInBackground();

        //Intent intent = new Intent(this, LaunchNotificationBroadcast.class);
        //sendBroadcast(intent);

    }

    private void setAutoCompleteFrag() {

        SupportPlaceAutocompleteFragment autocompleteFragment = (SupportPlaceAutocompleteFragment)
                getSupportFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);



        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                // TODO: Get info about the selected place.
                Log.i("msg", "Place: " + place.getName());
            }

            @Override
            public void onError(Status status) {
                // TODO: Handle the error.
                Log.i("msg", "An error occurred: " + status);
            }
        });
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
    private void bindDataFromCloud (List<ParseObject> dataFromCloud) {

        for (ParseObject o : dataFromCloud) {
            placesList.add(new MyPlace(o.getObjectId(), o.getString("name"), o.getString("address"),
                    o.getNumber("grade").floatValue(),
                    o.getBoolean("openJobs")));
        }
        recyclerView.getAdapter().notifyDataSetChanged();
    }

    private void reset(){
       // getPlacesFromCloud();
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
        bundle.putString("ID", p.getId());
        bundle.putString("NAME", p.getName());
        bundle.putDouble("GRADE", p.getGrade());
        bundle.putBoolean("JOB", p.isOpenJobs());
        bundle.putString("ADDRESS", p.getAddress());
        bundle.putParcelable("IMAGE", p.getImage());


        Intent intent = new Intent(this, ProfileActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    protected void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient
                .Builder(this)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .enableAutoManage(this, this)
                .build();

    }

    private class DownloadTask extends AsyncTask<String[], Integer, ArrayList<MyPlace>> {

        @Override
        protected ArrayList<MyPlace> doInBackground(String[]... params) {
            Log.d("DownloadTask", "started");
            String data = m_NetManager.doRequest("3000", "restaurant", "333");
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
            recyclerView.getAdapter().notifyDataSetChanged();
            testData();
            new DownloadImageTask().execute();
        }
    }

    private class DownloadImageTask extends AsyncTask<Void, Integer, Bitmap> {

        @Override
        protected Bitmap doInBackground(Void... params) {


            String ref;
            InputStream in = null;
            for (int i = 0; i < m_PlaceListFromGoogle.size(); i++) {
                try {
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
                        }
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
