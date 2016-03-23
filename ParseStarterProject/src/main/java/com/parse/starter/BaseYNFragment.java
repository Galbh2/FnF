package com.parse.starter;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

/**
 * Created by Gal on 23/03/2016.
 */
public abstract class BaseYNFragment extends BaseFragment implements RadioGroup.OnCheckedChangeListener {

    protected RadioGroup radio;
    protected RadioButton yesButton;
    protected RadioButton noButton;
    protected boolean mState = false;


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        /**
         * sets the radio buttons defined in BaseYNFragment class
         * and set up the listeners
         */
        setRadioButtons(view);
        register();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    protected void setRadioButtons(View layout) {

        radio = (RadioGroup) layout.findViewById(R.id.radioGroup);
        yesButton = (RadioButton) radio.findViewById(R.id.param_yes);
        noButton = (RadioButton) radio.findViewById(R.id.param_no);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {

        Log.d("onChecked", String.valueOf(checkedId));

        switch (checkedId) {
            case R.id.param_yes:
                if (!mState) {
                    Toast.makeText(getActivity(), "yes", Toast.LENGTH_LONG).show();
                    param.setBoolData(true);
                   mState = true;
                }
                break;
            case R.id.param_no:
                if (mState) {
                    Toast.makeText(getActivity(), "no", Toast.LENGTH_LONG).show();
                    param.setBoolData(false);
                    break;
                }
        }
    }

    protected void register(){
        radio.setOnCheckedChangeListener(this);
    }

    protected void unregister(){
        radio.setOnCheckedChangeListener(null);
    }
}
