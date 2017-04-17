package com.qucrush.android.crushingitapp;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Garry on 2/8/2017.
 */

public class ViewPagerFragment extends Fragment{
    View myView;

    private static final int NUM_PAGES = 3;
    private ViewPager mPager;
    private PagerAdapter mPagerAdapter;

    @Nullable
    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.view_pager_layout, container, false);
        ViewPager mViewPager = (ViewPager) myView.findViewById(R.id.pager);
        mViewPager.setAdapter(new ScreenSliderPagerAdapter(getChildFragmentManager()));
        return myView;
    }

    private class ScreenSliderPagerAdapter extends FragmentStatePagerAdapter {

        private ScreenSliderPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if (position == 0) {
                return new HomePageFragment1();
            } else if (position == 1) {
                return new HomePageFragment2();
            } else {
                return new HomePageFragment3();
            }
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }
    }
}

