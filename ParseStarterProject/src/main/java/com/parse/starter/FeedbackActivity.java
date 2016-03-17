package com.parse.starter;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

/**
 * Created by Gal on 18/03/2016.
 */
public class FeedbackActivity extends AppCompatActivity {

    private Toolbar toolBar;
    ViewPager viewPager;
    final Param[] params = new Param[5];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.feedback_layout);

        setToolBar();
        fillParams(params);
    }


    private void fillParams(Param[] params){
        for (int i = 0; i < params.length; i++) {
            params[i] = new Param();
        }
    }

    private void setUpViews() {

    }

    private void setToolBar() {
        toolBar = (Toolbar) findViewById(R.id.appBar);
        setSupportActionBar(toolBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }
}
