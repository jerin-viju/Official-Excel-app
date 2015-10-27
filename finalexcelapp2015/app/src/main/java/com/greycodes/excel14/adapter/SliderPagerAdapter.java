package com.greycodes.excel14.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by jincy on 19/7/15.
 */
public class SliderPagerAdapter extends FragmentPagerAdapter
{
    private List<Fragment> fragments;

    public SliderPagerAdapter(FragmentManager fragmentManager, List<Fragment> fragments)
    {
        super(fragmentManager);
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return this.fragments.get(position);
    }

    @Override
    public int getCount() {
        return this.fragments.size();
    }
}