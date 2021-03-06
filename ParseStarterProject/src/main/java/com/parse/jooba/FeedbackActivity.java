package com.parse.jooba;

import android.content.Intent;
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
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ParseACL;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;

/**
 * Created by Gal on 18/03/2016.
 */
public class FeedbackActivity extends AppCompatActivity implements BaseFragment.ParentActions, View.OnClickListener {

    public static final int NUM_OF_PARAMS = 7;
    private Toolbar toolBar;
    ViewPager viewPager;
    final Param[] params = new Param[NUM_OF_PARAMS];
    private ImageView logo;
    private Button m_SendButton;
    private TextView nameTextView;
    private Button[] circles = new Button[NUM_OF_PARAMS];


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

      //  logo = (ImageView) findViewById(R.id.mini_header_logo_image_view);
      //  nameTextView = (TextView) findViewById(R.id.mini_header_place_name_text_view);

        circles[0] = (Button) findViewById(R.id.circle_a);
        circles[1] = (Button) findViewById(R.id.circle_b);
        circles[2] = (Button) findViewById(R.id.circle_c);
        circles[3] = (Button) findViewById(R.id.circle_d);
        circles[4] = (Button) findViewById(R.id.circle_e);
        circles[5] = (Button) findViewById(R.id.circle_f);
        circles[6] = (Button) findViewById(R.id.circle_g);

        m_SendButton = (Button) findViewById(R.id.send_button);
        m_SendButton.setOnClickListener(this);
        //Bundle b = getIntent().getExtras();

    }

    private void setToolBar() {
        toolBar = (Toolbar) findViewById(R.id.appBar);
        setSupportActionBar(toolBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
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

            private int lastPosition = -1;

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                // send button logic

                if (position == 6) {
                    m_SendButton.setVisibility(View.VISIBLE);
                } else if (lastPosition == 6) {
                    m_SendButton.setVisibility(View.INVISIBLE);
                }


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

        circles[0].setEnabled(true);
    }

    private void showMsg() {
        Toast.makeText(this, "תודה !", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.send_button:
                sendFeedback();
                Intent intent = new Intent(this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                break;
            default:
                break;
        }

    }

    private void sendFeedback() {

        Bundle b = getIntent().getExtras();

        ParseObject feedback = new ParseObject("Feedback");

        feedback.put("gID", b.getString("gID"));
        feedback.put("name", b.getString("NAME"));

        feedback.put("param_1_bool", params[0].getBoolData());
        feedback.put("param_2_bool", params[1].getBoolData());
        feedback.put("param_5_bool", params[4].getBoolData());
        feedback.put("param_6_bool", params[5].getBoolData());

        feedback.put("param_3_text", params[2].getBody());

        feedback.put("param_4_num", params[3].getNumData());

        // comment

        feedback.put("param_7_text", params[6].getBody());

        ParseACL postAcl = new ParseACL(ParseUser.getCurrentUser());
        postAcl.setPublicReadAccess(true);
        feedback.setACL(postAcl);

        feedback.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e != null) {
                    Log.e("save_error", e.getMessage());
                }
            }
        });

        Toast.makeText(this, "תודה !", Toast.LENGTH_SHORT).show();

    }

    //Adapter for the view pager
    private static class PageAdapter extends FragmentPagerAdapter {

        private Fragment frag1;
        private Fragment frag2;
        private Fragment frag3;
        private Fragment frag4;
        private Fragment frag5;
        private Fragment frag6;
        private Fragment frag7;

        public PageAdapter(FragmentManager fm) {
            super(fm);

            frag2 = new Param1Frag();
            frag3 = new Param2Frag();
            frag1 = new Param3Frag();
            frag6 = new Param4Frag();
            frag5 = new Param5Frag();
            frag4 = new Param6Frag();
            frag7 = new Param7Frag();

            Bundle b1 = new Bundle();
            Bundle b2 = new Bundle();
            Bundle b3 = new Bundle();
            Bundle b4 = new Bundle();
            Bundle b5 = new Bundle();
            Bundle b6 = new Bundle();
            Bundle b7 = new Bundle();

            b1.putInt("index", 0);
            b2.putInt("index", 1);
            b3.putInt("index", 2);
            b4.putInt("index", 3);
            b5.putInt("index", 4);
            b6.putInt("index", 5);
            b7.putInt("index", 6);

            frag1.setArguments(b1);
            frag2.setArguments(b2);
            frag3.setArguments(b3);
            frag4.setArguments(b4);
            frag5.setArguments(b5);
            frag6.setArguments(b6);
            frag7.setArguments(b7);


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
                case 5:
                    return frag6;
                case 6:
                    return frag7;
            }

            return null;
        }

        @Override
        public int getCount() {
            return FeedbackActivity.NUM_OF_PARAMS;
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
                case 5:
                    return "6";
                case 6:
                    return "7";
                default:
                    return "n/a";
            }

        }


    }

    @Override
    public void onBackPressed() {

    }
}
