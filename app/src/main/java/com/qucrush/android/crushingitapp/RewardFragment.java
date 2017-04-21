package com.qucrush.android.crushingitapp;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
    private List<Badge> badgeList,cBadgeList,uBadgeList;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.reward_screen, container, false);
        gView = (GridView) myView.findViewById(R.id.badgeView);
        badgeList = new ArrayList<Badge>();
        cBadgeList = new ArrayList<Badge>();
        uBadgeList = new ArrayList<Badge>();
        badgeList = MainActivity.bm.getAllBadges();

        for(int i = 0; i < badgeList.size(); i++){
            if(badgeList.get(i).getIsEarned().equals("no")){
                uBadgeList.add(badgeList.get(i));
            }else{
                cBadgeList.add(badgeList.get(i));
            }
        }
        cBadgeList.addAll(uBadgeList);
        dataAdapter = new BadgeAdapter(getActivity(),R.layout.reward_screen,cBadgeList);
        gView.setAdapter(dataAdapter);
        return myView;
    }
}
