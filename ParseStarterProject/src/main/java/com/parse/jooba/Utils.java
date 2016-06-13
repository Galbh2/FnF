package com.parse.jooba;

import android.content.Context;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.Collections;

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

    public static void setImgFromRcs(Context context, ImageView imageView, String name) {
        int imgId = context.getResources().getIdentifier("p".concat(name.toLowerCase()), "mipmap", context.getPackageName());

        if (imgId != 0) {
            imageView.setImageResource(imgId);
        }
    }

    public static int getImgId(String i_Name, Context i_Context) {
        return i_Context.getResources().getIdentifier(i_Name, "mipmap", i_Context.getPackageName());
    }

}
