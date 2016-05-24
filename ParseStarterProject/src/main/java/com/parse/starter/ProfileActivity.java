package com.parse.starter;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
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

    private ImageView m_Param_1_Bool, m_Param_2_Bool, m_Param_4_Bool, m_Param_5_Bool, m_Param_6_Bool;
    private TextView m_Param_3_Text;



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

            // Header

            logoImageView = (ImageView) findViewById(R.id.logo_image_view);
            placeNameTextView = (TextView) findViewById(R.id.place_name_text_view);
            fab = (FloatingActionButton) findViewById(R.id.fab);
            placeAdressTextView = (TextView) findViewById(R.id.address_text_view);

            int results[] = extras.getIntArray("RESULTS");
            logoImageView.setImageBitmap((Bitmap) extras.getParcelable("IMAGE"));

            // Body

            m_Param_1_Bool = (ImageView) findViewById(R.id.param_1_bool);
            m_Param_2_Bool = (ImageView) findViewById(R.id.param_2_bool);
            m_Param_4_Bool = (ImageView) findViewById(R.id.param_4_bool);
            m_Param_5_Bool = (ImageView) findViewById(R.id.param_5_bool);
            m_Param_6_Bool = (ImageView) findViewById(R.id.param_6_bool);

            ImageView[] boolParamsArray = {m_Param_1_Bool, m_Param_2_Bool, m_Param_4_Bool,
                                            m_Param_5_Bool, m_Param_6_Bool};

            m_Param_3_Text = (TextView) findViewById(R.id.param_3_text);

            if (results != null) {
                setBoolImage(m_Param_1_Bool, GradeCalc.intToBool(results[0]));
                setBoolImage(m_Param_2_Bool, GradeCalc.intToBool(results[1]));
                setBoolImage(m_Param_4_Bool, GradeCalc.intToBool(results[3]));
                setBoolImage(m_Param_5_Bool, GradeCalc.intToBool(results[4]));
                setBoolImage(m_Param_6_Bool, GradeCalc.intToBool(results[5]));

                m_Param_3_Text.setText(String.valueOf(results[2]));
            }

            //Sets listeners

            fab.setOnClickListener(this);

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

    private void setBoolImage(ImageView i_Image, boolean i_Value) {
        if (i_Value) {
            i_Image.setImageResource(R.mipmap.v);
        } else {
            i_Image.setImageResource(R.mipmap.x);
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


