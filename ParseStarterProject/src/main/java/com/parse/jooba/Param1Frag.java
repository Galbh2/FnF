package com.parse.jooba;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Gal on 18/03/2016.
 */
public class Param1Frag extends BaseYNFragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.param_1_frag, container,false);
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}
