package com.greycodes.excel14.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.greycodes.excel14.R;
import com.greycodes.excel14.model.Model;

import com.greycodes.excel14.adapter.ViewPagerAdapter;
import com.greycodes.excel14.listener.BackButtonClickListener;


@SuppressLint("ValidFragment")
public class ScheduleFragment extends Fragment {

    private BackButtonClickListener listener;

    ViewPager pager;
    ViewPagerAdapter adapter;
    SlidingTabLayout tabs;
    int Numboftabs =3;

    @SuppressLint("ValidFragment")
    public ScheduleFragment(BackButtonClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view;
        view= inflater.inflate(R.layout.fragment_shedule2, container, false);
        setBackButtonOverriding(view);
        if(Model.getInstance().getScheduleDownloadedStatus()) {
            view= inflater.inflate(R.layout.fragment_schedule, container, false);
            setUpViews(view);
            setPagerAndAdapter();
        }
        return view;
    }

    private void setBackButtonOverriding(View view) {
        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
                    listener.BackButtonClicked(0);
//                    getFragmentManager().popBackStack();
//                    getFragmentManager().popBackStack("home", FragmentManager.POP_BACK_STACK_INCLUSIVE);
                    return true;
                } else {
                    return false;
                }
            }
        });
    }

    private void setUpViews(View view) {
        pager = (ViewPager) view.findViewById(R.id.pager);
        tabs = (SlidingTabLayout) view.findViewById(R.id.tabs);
    }

    private void setPagerAndAdapter() {
        adapter =  new ViewPagerAdapter(getFragmentManager(),Numboftabs);
        pager.setAdapter(adapter);

        tabs.setDistributeEvenly(true); // To make the Tabs Fixed set this true, This makes the tabs Space Evenly in Available width
        tabs.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return getResources().getColor(R.color.tabsScrollColor);
            }
        });
        tabs.setViewPager(pager);
    }
}
