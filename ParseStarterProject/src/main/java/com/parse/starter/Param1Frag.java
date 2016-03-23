package com.parse.starter;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

/**
 * Created by Gal on 18/03/2016.
 */
public class Param1Frag extends BaseFragment {

    RadioGroup radio;
    RadioButton yesButton;
    RadioButton noButton;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setFragIndex(0);
        setParent();
        getParam(fragIndex);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View layout = inflater.inflate(R.layout.param_1_frag, container,false);
        setRadioButtons(layout);



        return layout;
    }

    private void setRadioButtons(View layout){

        radio = (RadioGroup) layout.findViewById(R.id.radioGroup);
        yesButton = (RadioButton) radio.findViewById(R.id.param_1_yes);
        noButton = (RadioButton) radio.findViewById(R.id.param_1_no);

        radio.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                Log.d("onChecked", String.valueOf(checkedId));

                switch (checkedId) {
                    case R.id.param_1_yes:
                        Toast.makeText(getActivity(), "yes", Toast.LENGTH_LONG).show();
                        param.setBoolData(true);
                        break;
                    case R.id.param_1_no:
                        Toast.makeText(getActivity(), "no", Toast.LENGTH_LONG).show();
                        param.setBoolData(false);
                        break;
                }
            }
        });
    }
}
