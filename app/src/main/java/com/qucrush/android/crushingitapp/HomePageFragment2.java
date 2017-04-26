package com.qucrush.android.crushingitapp;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by Garry on 2/8/2017.
 */

public class HomePageFragment2 extends Fragment{
    View myView;
    String type = "one";
    Button next1, prev1, next2, prev2, next3, prev3;
    LayoutInflater inflater;
    ViewGroup container;
    @Nullable
    //@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        this.inflater = inflater;
        this.container = container;

        myView = inflater.inflate(R.layout.home_2, container, false);

        return myView;
    }

    public void setParameter(String toType) {
        type = toType;
    }
}
