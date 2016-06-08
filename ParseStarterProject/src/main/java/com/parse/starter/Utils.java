package com.parse.starter;

import android.content.Context;
import android.widget.ImageView;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.places.Places;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

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

    public static ArrayList<MyPlace> getMock(Context context) {

        ArrayList <MyPlace> list = new ArrayList<>(10);

        MyPlace max = new MyPlace(
                "1",
                "מקס ברנר",

                "המנופים 3, הרצליה",
                null,
                getImgId("max", context)
        );

        MyPlace mack = new MyPlace(
                "2",
                "מקדונלדס",
                "סינמה סיטי",
                null,
                getImgId("mack", context)
                );

        MyPlace candy = new MyPlace(
                "3",
                "עולם הממתקים",
                "בן גוריון 12, הרצליה",
                null,
                getImgId("candy", context)
        );

        MyPlace aroma = new MyPlace(
                "4",
                "ארומה",
                "שדרות חן, הרצליה",
                null,
                getImgId("aroma", context)
        );

        MyPlace araz = new MyPlace(
                "5",
                "לחם ארז",
            "הנדיב 71, הרצליה",
                null,
                getImgId("araz", context)
        );

        MyPlace greg = new MyPlace(
                "6",
                "קפה גרג",
                "השונית 2, הרצליה",
                null,
                getImgId("greg", context)
        );

        GradeCalc.calcParams(
                false,
                false,
                true,
                true,
                20,
                0.75,
                max
        );

        GradeCalc.calcParams(
                true,
                false,
                true,
                true,
                18.82,
                0.75,
                mack
        );

        GradeCalc.calcParams(
                false,
                false,
                false,
                true,
                22,
                0.49,
                candy
        );

        GradeCalc.calcParams(
                true,
                true,
                true,
                true,
                18.82,
                0.70,
                aroma
        );

        GradeCalc.calcParams(
                true,
                true,
                false,
                true,
               23,
                0.70,
                araz
        );

        GradeCalc.calcParams(
                true,
                true,
                false,
                false,
                19,
                0.40,
                greg
        );

        list.add(max);
        list.add(mack);
        list.add(candy);
        list.add(araz);
        list.add(greg);
        list.add(aroma);

        Collections.sort(list, new PlaceComparator().sortByGrade());

        return list;
    }

    public static int getImgId(String i_Name, Context i_Context) {
        return i_Context.getResources().getIdentifier(i_Name, "mipmap", i_Context.getPackageName());
    }

}
