package com.greycodes.excel14.adapter;

/**
 * Created by jerin on 23/7/15.
 */

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.greycodes.excel14.fragment.Tab1;

/**
 * Created by hp1 on 21-01-2015.
 */
public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    CharSequence Titles[]; // This will Store the Titles of the Tabs which are Going to be passed when ViewPagerAdapter is created
    int NumbOfTabs; // Store the number of tabs, this will also be passed when the ViewPagerAdapter is created


    // Build a Constructor and assign the passed Values to appropriate values in the class
    public ViewPagerAdapter(FragmentManager fm , int mNumbOfTabsumb) {
        super(fm);


        this.NumbOfTabs = mNumbOfTabsumb;

    }

    //This method return the fragment for the every position in the View Pager
    @Override
    public Fragment getItem(int position) {

       Tab1 tab1=new Tab1();
       tab1.getinstance(position);
        return tab1;
    }

    @Override
    public int getCount() {
        return NumbOfTabs;
    }
}
