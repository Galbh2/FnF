package com.parse.starter;

import android.content.Context;

/**
 * Created by Gal on 13/01/2016.
 */
public class Utils {

    public static CharSequence getParamInfo (int param, Context context) {

        switch (param) {
            case 0 :
                return context.getText(R.string.param_1);
            case 1 :
                return context.getText(R.string.param_2);
            case 2 :
                return context.getText(R.string.param_3);
            case 3 :
                return context.getText(R.string.param_4);
            case 4 :
                return context.getText(R.string.param_5);
            case 5 :
                return context.getText(R.string.param_6);
            default:
                return "default string";
        }
    }
}
