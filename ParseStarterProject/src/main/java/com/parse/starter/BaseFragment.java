package com.parse.starter;


import android.support.v4.app.Fragment;

/**
 * Created by Gal on 18/03/2016.
 */
public abstract class BaseFragment extends Fragment {

    protected ParentActions parent = (ParentActions) getActivity();

    public interface ParentActions{

        Param getParam(int index);
    }
}
