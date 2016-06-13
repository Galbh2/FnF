package com.parse.jooba;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by Gal on 12/2/2015.
 */
public class PrefUtils {

    private Context context;
    private static PrefUtils pref;


    private PrefUtils(Context context){

        this.context = context;
    }

    public static PrefUtils getInstance (Context context){

        if (pref == null) {
            pref = new PrefUtils(context);
        }

        return pref;

    }


    /**
     * Provides the ability to retrieve data from the SharedPref
     */

    private int getSharedPrefInt (String value, int defaultValue){

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        return sp.getInt(value, defaultValue);
    }

    /**
     * Provides the ability to store String values in the
     * shared preferences
     * @param key
     * The key for accessing the data
     * @param value
     * The actual String data
     */

    private void setSharedPrefInt (String key, int value) {

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        //In order to edit the data in the SharedPref we need to call the editor
        SharedPreferences.Editor edit = sp.edit();
        edit.putInt(key, value);
        edit.commit();
    }

}
