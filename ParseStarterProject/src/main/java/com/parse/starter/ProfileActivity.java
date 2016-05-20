package com.parse.starter;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * This class represents a profile page for a given MyPlace.
 */
public class ProfileActivity extends AppCompatActivity implements ProfileAdapter.InfoClickListener, View.OnClickListener {

    private Toolbar toolBar;
    RecyclerView recyclerView;
    public ArrayList<Param> paramList = new ArrayList<Param>();

    private FloatingActionButton fab;
    private ImageView logoImageView;
    private TextView placeNameTextView, placeAdressTextView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_layout);

        setToolBar();
        setViews();
        //setRecyclerView();
       // testData();
    }

    /**
     *shows the info dialog in the area that was clicked
     */

    public void showInfoDialog (int index, int[] position) {

        Dialog d = new Dialog(this);
        d.requestWindowFeature(Window.FEATURE_NO_TITLE);
        WindowManager.LayoutParams wmlp = d.getWindow().getAttributes();

        d.setContentView(R.layout.single_place_layout);

        wmlp.gravity = Gravity.TOP | Gravity.LEFT;
        wmlp.x = position[0];
        wmlp.y = position[1];

        Log.d("x", String.valueOf(position[0]));
        Log.d("y", String.valueOf(position[1]));
        d.show();

    }

    /**
     * shows the info dialog in the middle of the screen
     * @param index
     */
    public void showInfoDialog (int index) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(Utils.getParamInfo(index, this))
                .setNeutralButton("Got it!", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

        builder.create().show();
    }

    private void setViews(){

        Bundle extras = getIntent().getExtras();

        if (extras != null) {

            logoImageView = (ImageView) findViewById(R.id.logo_image_view);
            placeNameTextView = (TextView) findViewById(R.id.place_name_text_view);
            fab = (FloatingActionButton) findViewById(R.id.fab);
            placeAdressTextView = (TextView) findViewById(R.id.address_text_view);

            //Sets listeners

            fab.setOnClickListener(this);

            //Sets the logo
            int imgId = getResources().getIdentifier("p".concat(extras.getString("ID").toLowerCase()), "mipmap", getPackageName());

            if (imgId != 0) {
                logoImageView.setImageResource(imgId);
            }
            //Sets the name
            placeNameTextView.setText(extras.getString("NAME"));

            // Sets the adress

            placeAdressTextView.setText(extras.getString("ADDRESS"));

            //Sets the job icon
            //    if (!extras.getBoolean("JOB")) {
            //      openJobImageView.setVisibility(View.INVISIBLE);
            //}



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

    @Override
    public void onClick(View v) {

        if (v == fab) {

            Bundle bundle = getIntent().getExtras();
            Intent intent = new Intent(this, FeedbackActivity.class);
            intent.putExtras(bundle);
            startActivity(intent);
        }
    }
}


