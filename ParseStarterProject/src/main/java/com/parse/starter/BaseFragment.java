package com.parse.starter;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Gal on 18/03/2016.
 */
public abstract class BaseFragment extends Fragment {

    protected ParentActions parent;
    protected Param param = null;
    protected int fragIndex = 0;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setFragIndex(getArguments());
        setParent();
        getParam(fragIndex);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d("onCrateView", String.valueOf(fragIndex));
        return null;
    }

    public interface ParentActions{

        Param getParam(int index);
    }

    protected void getParam(int index){

        this.param = parent.getParam(index);
    }

    protected void setFragIndex (Bundle bundle){
        fragIndex = bundle.getInt("index");
    }

    protected void setParent() {
        parent = (ParentActions) getActivity();
    }
}
