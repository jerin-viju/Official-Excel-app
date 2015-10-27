package com.greycodes.excel14.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.greycodes.excel14.R;
import com.greycodes.excel14.enums.SliderFragmentType;

import com.greycodes.excel14.adapter.SliderPagerAdapter;

import java.util.ArrayList;
import java.util.List;


public class FloorMapFragment extends Fragment {

    SliderPagerAdapter sliderPagerAdapter;
    ViewPager viewPager;
    int imageIndex = 0;
    public FloorMapFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_floor_map, container, false);
        setBackButtonOverriding(view);
        setUpPager(view);
        return view;
    }

    private void setBackButtonOverriding(View view) {
        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    getFragmentManager().popBackStack();

                    return true;
                } else {
                    return false;
                }
            }
        });
    }

    private void setUpPager(View view) {
        List<Fragment> fragments = getFragments();
        viewPager = (ViewPager)view.findViewById(R.id.view_pager_slider_floormap);
        sliderPagerAdapter = new SliderPagerAdapter(getActivity().getSupportFragmentManager(), fragments);
        viewPager.setAdapter(sliderPagerAdapter);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                imageIndex = position;
                setViewPagerItem();
            }

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        setViewPagerItem();
    }

    private List<Fragment> getFragments(){
        List<Fragment> fList = new ArrayList<>();
        fList.add(new SliderFragment(R.drawable.floor1, SliderFragmentType.FloorMapSliderFragment));
        fList.add(new SliderFragment(R.drawable.floor2,SliderFragmentType.FloorMapSliderFragment));
        fList.add(new SliderFragment(R.drawable.floor3,SliderFragmentType.FloorMapSliderFragment));
        fList.add(new SliderFragment(R.drawable.floor4,SliderFragmentType.FloorMapSliderFragment));
        fList.add(new SliderFragment(R.drawable.floor5,SliderFragmentType.FloorMapSliderFragment));
        return fList;
    }

    private void setViewPagerItem() {
        viewPager.setCurrentItem(imageIndex, true);
    }
}
