package com.parse.starter;


import android.support.v4.app.Fragment;

/**
 * Created by Gal on 18/03/2016.
 */
public abstract class BaseFragment extends Fragment {

    protected ParentActions parent;
    protected Param param = null;
    protected int fragIndex = 0;

    public interface ParentActions{

        Param getParam(int index);
    }

    protected void getParam(int index){

        this.param = parent.getParam(index);
    }

    protected void setFragIndex (int index){
        fragIndex = index;
    }

    protected void setParent() {
        parent = (ParentActions) getActivity();
    }
}
