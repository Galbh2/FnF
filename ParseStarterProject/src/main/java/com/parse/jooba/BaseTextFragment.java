package com.parse.jooba;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

/**
 * Created by Gal on 23/03/2016.
 */
public  abstract  class BaseTextFragment extends BaseFragment {

    protected EditText mEditText;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        /**
         * sets the radio buttons defined in BaseYNFragment class
         * and set up the listeners
         */

        setUpView(view);

    }

    private void setUpView(final View view) {


        mEditText = (EditText) view.findViewById(R.id.param_edit_text);

        mEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                String string = s.toString();
                //Toast.makeText(getActivity(), string, Toast.LENGTH_LONG).show();

                try {
                    double d = Double.valueOf(string);
                    param.setNumData(d);
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    param.setNumData(0.0);
                }



            }
        });
    }
}
