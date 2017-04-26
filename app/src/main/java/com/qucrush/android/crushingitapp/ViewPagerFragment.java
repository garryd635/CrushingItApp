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
import android.widget.Button;

/**
 * Created by Garry on 2/8/2017.
 */

public class ViewPagerFragment extends Fragment{
    //Instance Variables
    View myView;
    private static final int NUM_PAGES = 3;
    private ViewPager mViewPager;
    private PagerAdapter mPagerAdapter;
    private Button next,prev;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.view_pager_layout, container, false);
        mViewPager = (ViewPager) myView.findViewById(R.id.pager);
        next = (Button) myView.findViewById(R.id.nextButtonHome);
        prev = (Button) myView.findViewById(R.id.prevButtonHome);

        //Change pages forward when next button is pressed
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mViewPager.getCurrentItem() == 2) {
                    mViewPager.setCurrentItem(0);
                } else {
                    // Select previous page.
                    mViewPager.setCurrentItem(mViewPager.getCurrentItem() + 1);
                }
            }
        });//next

        //Changes page backwards when buttons is pressed
        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mViewPager.getCurrentItem() == 0) {
                    mViewPager.setCurrentItem(2);
                } else {
                    // Select previous page.
                    mViewPager.setCurrentItem(mViewPager.getCurrentItem() - 1);
                }
            }
        });//prev

        mViewPager.setAdapter(new ScreenSliderPagerAdapter(getChildFragmentManager()));
        return myView;
    }//onCreateView

    private class ScreenSliderPagerAdapter extends FragmentStatePagerAdapter {

        private ScreenSliderPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        //Set positions for each fragment
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

