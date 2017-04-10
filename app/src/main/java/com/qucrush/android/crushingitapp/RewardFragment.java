package com.qucrush.android.crushingitapp;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Garry on 2/8/2017.
 */

public class RewardFragment extends Fragment{
    View myView;
    private BadgeAdapter dataAdapter;
    private GridView gView;
    private List<Badge> badgeList;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.reward_screen, container, false);
        gView = (GridView) myView.findViewById(R.id.badgeView);
        badgeList = new ArrayList<Badge>();
        badgeList = MainActivity.bm.getAllBadges();

        for(int i = 0; i < badgeList.size(); i++){
            System.out.println(badgeList.get(i).getName());
        }
        dataAdapter = new BadgeAdapter(getActivity(),R.layout.reward_screen,badgeList);
        gView.setAdapter(dataAdapter);
        return myView;
    }
}
