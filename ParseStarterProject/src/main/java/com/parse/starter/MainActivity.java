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
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements PlaceAdapter.PlaceClickListener {

    final static int LIMIT = 50;
    private Toolbar toolBar;
    private RecyclerView recyclerView;
    public static ArrayList<Place> placesList = new ArrayList<Place>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ParseAnalytics.trackAppOpenedInBackground(getIntent());
        setToolBar();
        setRecyclerView();
        testData();

        //ParseObject testObject = new ParseObject("TestObject");
        //testObject.put("foo", "bar");
        //testObject.saveInBackground();

        //Intent intent = new Intent(this, LaunchNotificationBroadcast.class);
        //sendBroadcast(intent);

    }

    private void testData() {

        if (placesList.size() == 0) {
            Toast.makeText(this, "Something went wrong, please check your internet connection", Toast.LENGTH_LONG).show();
        }

    }

    /**
     *This method fetches the next 50 Places from the cloud.
     */
    private void getPlacesFromCloud() {

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Place");
        query.setSkip(placesList.size() + LIMIT);
        query.setLimit(LIMIT);
        query.orderByDescending("grade");


        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {

                if (e == null) {
                    bindDataFromCloud(objects);
                } else {
                    Log.d("Fetch err" ,e.getMessage());
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
            placesList.add(new Place(o.getObjectId(), o.getString("name"), o.getString("address"),
                    o.getNumber("grade").floatValue(),
                    o.getBoolean("openJobs")));
        }
        recyclerView.getAdapter().notifyDataSetChanged();
    }

    private void reset(){
        getPlacesFromCloud();
    }

    private void setRecyclerView() {

        recyclerView = (RecyclerView) findViewById(R.id.places_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new PlaceAdapter(this, placesList));

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

        Place p = placesList.get(position);

        Bundle bundle = new Bundle();
        bundle.putString("ID", p.getId());
        bundle.putString("NAME", p.getName());
        bundle.putDouble("GRADE", p.getGrade());
        bundle.putBoolean("JOB", p.isOpenJobs());
        bundle.putString("ADDRESS", p.getAddress());


        Intent intent = new Intent(this, ProfileActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);

    }
}
