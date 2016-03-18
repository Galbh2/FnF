package com.parse.starter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.security.KeyStore;

/**
 * Created by Gal on 18/03/2016.
 */
public class FeedbackActivity extends AppCompatActivity implements BaseFragment.ParentActions, View.OnClickListener {

    private Toolbar toolBar;
    ViewPager viewPager;
    final Param[] params = new Param[5];
    private ImageView right, left;
    private Button[] circles = new Button[5];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.feedback_layout);

        setToolBar();
        setUpViews();
        fillParams(params);

        setUpPager();

    }

    private void fillParams(Param[] params){
        for (int i = 0; i < params.length; i++) {
            params[i] = new Param();
        }
    }

    @Override
    public Param getParam(int index) {
        return params[index];
    }

    private void setUpViews() {

        right = (ImageView) findViewById(R.id.right_arrow);
        left = (ImageView) findViewById((R.id.left_arrow));

        circles[0] = (Button) findViewById(R.id.circle_a);
        circles[1] = (Button) findViewById(R.id.circle_b);
        circles[2] = (Button) findViewById(R.id.circle_c);
        circles[3] = (Button) findViewById(R.id.circle_d);
        circles[4] = (Button) findViewById(R.id.circle_f);


        right.setOnClickListener(this);
        left.setOnClickListener(this);
    }

    private void setToolBar() {
        toolBar = (Toolbar) findViewById(R.id.appBar);
        setSupportActionBar(toolBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    public void onClick(View v) {

        if (v == left) {
            down();
        } else if (v == right) {
            up();
        }

    }

    private void up() {

        viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
    }

    private void down() {


        viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);

    }

    private void setUpPager() {

        viewPager = (ViewPager) findViewById(R.id.pager);
        viewPager.setHorizontalScrollBarEnabled(false);
        PageAdapter pageAdapter = new PageAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pageAdapter);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            private int lastPosition = 0;

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {


            }

            @Override
            public void onPageSelected(int position) {

              if (position > lastPosition) {
                    circles[position].setEnabled(true);
              } else {
                    circles[lastPosition].setEnabled(false);
              }

                lastPosition = position;

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    //Adapter for the view pager
    private static class PageAdapter extends FragmentPagerAdapter {

        private Fragment frag1 = new Param1Frag();
        private Fragment frag2 = new Param2Frag();
        private Fragment frag3 = new Param3Frag();
        private Fragment frag4 = new Param4Frag();
        private Fragment frag5 = new Param5Frag();


        public PageAdapter(FragmentManager fm) {
            super(fm);

        }

        @Override
        public Fragment getItem(int position) {

            switch (position) {
                case 0:
                    return frag1;
                case 1:
                    return frag2;
                case 2:
                    return frag3;
                case 3:
                    return frag4;
                case 4:
                    return frag5;
            }

            return null;
        }

        @Override
        public int getCount() {
            return 5;
        }

        @Override
        public CharSequence getPageTitle(int position) {

            switch (position) {
                case 0:
                    return "1";
                case 1:
                    return "2";
                case 2:
                    return "3";
                case 3:
                    return "4";
                case 4:
                    return "5";
                default:
                    return "n/a";
            }

        }
    }
}
