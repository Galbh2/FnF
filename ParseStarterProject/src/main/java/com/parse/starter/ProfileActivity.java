package com.parse.starter;

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
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * This class represents a profile page for a given Place.
 */
public class ProfileActivity extends AppCompatActivity {

    private Toolbar toolBar;
    RecyclerView recyclerView;
    public ArrayList<Param> paramList = new ArrayList<Param>();

    private ImageView logoImageView, openJobImageView;
    private TextView placeNameTextView, gradeTextView, addressTextView,
                    phoneTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_layout);

        setToolBar();
        setViews();
        setRecyclerView();
        testData();


    }

    private void setViews(){

        Bundle extras = getIntent().getExtras();

        if (extras != null) {

            logoImageView = (ImageView) findViewById(R.id.logo_image_view);
            placeNameTextView = (TextView) findViewById(R.id.place_name_text_view);
            gradeTextView = (TextView) findViewById(R.id.grade_text_view);
            addressTextView= (TextView) findViewById(R.id.address_text_view);
            phoneTextView = (TextView) findViewById(R.id.phone_text_view);
            openJobImageView = (ImageView) findViewById(R.id.open_job_image_view);


            //Sets the logo
            int imgId = getResources().getIdentifier("p".concat(extras.getString("ID").toLowerCase()), "mipmap", getPackageName());

            if (imgId != 0) {
                logoImageView.setImageResource(imgId);
            }
            //Sets the name
            placeNameTextView.setText(extras.getString("NAME"));

            //Sets the grade
            if (extras.getDouble("GRADE") == 10) {
              gradeTextView.setText(String.format("%.0f", extras.getDouble("GRADE")));
            } else {
                gradeTextView.setText(String.format("%.1f", extras.getDouble("GRADE")));
            }

            //Sets the job icon
            if (!extras.getBoolean("JOB")) {
                openJobImageView.setVisibility(View.INVISIBLE);
            }

            //Sets the address
            addressTextView.setText(extras.getString("ADDRESS"));

        } else{
            Log.d("Profile Activity", "Extras = null");
        }
    }

    private void testData() {

        if (paramList.size() == 0) {
            Toast.makeText(this, "Something went wrong, please check your internet connection", Toast.LENGTH_LONG).show();
        }

    }

    private void setRecyclerView() {

        recyclerView = (RecyclerView) findViewById(R.id.profile_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new ProfileAdapter(this));

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
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }
}


