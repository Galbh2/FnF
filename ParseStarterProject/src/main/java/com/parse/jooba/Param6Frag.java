package com.parse.jooba;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * Created by Gal on 18/03/2016.
 */
public class Param6Frag extends BaseFragment {

    MyRadioButton[] m_Buttons = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.param_6_frag, container,false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ImageView angel = (ImageView) view.findViewById(R.id.s_angel_img);
        ImageView boss = (ImageView) view.findViewById(R.id.s_boss_img);
        ImageView sunglasses = (ImageView) view.findViewById(R.id.s_sunglesses_img);
        ImageView pile = (ImageView) view.findViewById(R.id.s_pile_img);
        ImageView eye = (ImageView) view.findViewById(R.id.s_eye_img);
        ImageView sad = (ImageView) view.findViewById(R.id.s_sad_img);
        ImageView present = (ImageView) view.findViewById(R.id.s_present_img);
        ImageView sleepy = (ImageView) view.findViewById(R.id.s_sleepy_img);

        m_Buttons = new MyRadioButton[4];
        m_Buttons[0] = new MyRadioButton(angel, boss);
        m_Buttons[1] =  new MyRadioButton(sunglasses, pile);
        m_Buttons[2] = new MyRadioButton(eye, sad);
        m_Buttons[3] =new MyRadioButton(present, sleepy);
    }

    @Override
    public void onPause() {
        super.onPause();

        if (m_Buttons != null) {
            double grade = 1.0 / (double) m_Buttons.length;
            double sum = 0.0;
            for (MyRadioButton b : m_Buttons) {
                if (b.getResult()) {
                    sum += grade;
                }
            }
            param.setNumData(sum);
            Log.d("the sum is", String.valueOf(sum));
        }

    }

    private class MyRadioButton implements View.OnClickListener {

        boolean m_CheckedB1 = false;
        boolean m_CheckedB2 = false;
        ImageView m_B1, m_B2;
        int m_B1_Id, m_B2_Id;
        final int c_AccentColor = ContextCompat.getColor(getContext(), R.color.accentColor);
        final int c_tranperentColor = Color.TRANSPARENT;

        public MyRadioButton (ImageView i_B1, ImageView i_B2) {
            m_B1 = i_B1;
            m_B2 = i_B2;
            m_B1.setOnClickListener(this);
            m_B2.setOnClickListener(this);
            m_B1_Id = i_B1.getId();
            m_B2_Id = i_B2.getId();
        }

        @Override
        public void onClick(View v) {

            int id = v.getId();

            if (id == m_B1_Id) {
                // turn on the background in the first argument
                // turn off the background in the second argument
                switchBackground(m_B1, m_B2);
                m_CheckedB1 = true;
                m_CheckedB2 = false;
            } else if (id == m_B2_Id) {
                switchBackground(m_B2, m_B1);
                m_CheckedB2 = true;
                m_CheckedB1 = false;
            }

        }

        private void switchBackground(ImageView i_B1, ImageView i_B2) {
            i_B1.setBackgroundColor(c_AccentColor);
            i_B2.setBackgroundColor(c_tranperentColor);
        }

        public boolean getResult() {
            if (m_CheckedB1) {
                return true;
            } else {
                return false;
            }
        }

    }
}
