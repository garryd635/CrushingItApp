package com.qucrush.android.crushingitapp;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

/**
 * Created by Garry on 2/8/2017.
 */

public class HomePageFragment2 extends Fragment{
    View myView;
    LayoutInflater inflater;
    ViewGroup container;
    ListView upcomingList;
    CustomAdapter adapter;
    @Nullable
    //@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.inflater = inflater;
        this.container = container;
        myView = inflater.inflate(R.layout.home_2, container, false);
        upcomingList = (ListView) myView.findViewById(R.id.homeUpcomingList);

        return myView;
    }
}
