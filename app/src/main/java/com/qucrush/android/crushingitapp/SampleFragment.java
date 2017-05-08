package com.qucrush.android.crushingitapp;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * SampleFragment
 *
 * Contains contents for the example fragment page used to help make more fragment pages.
 */

//Don't edit this page's contents, as it is not functional in the app,
//and serves the purpose of showing how to use fragments

public class SampleFragment extends Fragment{//has to extent Fragment
    //Has to call myView
    View myView;

    //Must contain a method "onCreateView", that returns myView
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //Inflate the page's associated .xml file
        myView = inflater.inflate(R.layout.sample_layout, container, false);
        //Return
        return myView;
    }//onCreateView
}//SampleFragment
