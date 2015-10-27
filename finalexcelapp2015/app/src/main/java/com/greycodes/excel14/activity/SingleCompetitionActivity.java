package com.greycodes.excel14.activity;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;


import com.greycodes.excel14.R;
import com.greycodes.excel14.enums.SliderFragmentType;
import com.greycodes.excel14.fragment.SliderFragment;
import com.greycodes.excel14.model.Model;

import com.greycodes.excel14.adapter.SliderPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class SingleCompetitionActivity extends AppCompatActivity {

    private int parentPosition;
    private int pos;
    int viewPagerIndex =0;
    public int dpToPx(int dp) {
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        return (int)(dp * (displayMetrics.densityDpi / 160f));
    }

    SliderPagerAdapter sliderPagerAdapter;
    ViewPager viewPager;
    LinearLayout sliderIndicatorLayout;
    ArrayList<Button> indicatorList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_competition);


        getIntentArguments();
        setUpSliderIndicator();
        setUpPager();
    }
    private void hello(View v){
        if (Model.getInstance(getApplicationContext()).getLoginState()) {
            Toast.makeText(getApplicationContext(), "Registered for this event",
                    Toast.LENGTH_LONG).show();
        }
        else
        {
            Toast.makeText(getApplicationContext(), "sign in to register",
                    Toast.LENGTH_LONG).show();

        }


    }

    private void getIntentArguments() {
        this.parentPosition = getIntent().getExtras().getInt("parentPosition");
        this.pos = getIntent().getExtras().getInt("clickedPosition");
    }

    private void setUpSliderIndicator() {
        int x=dpToPx(15);
        sliderIndicatorLayout = (LinearLayout)findViewById(R.id.slider_indicator_layout);
        for (int i=0;i<Model.getInstance().getCompetitionCategories().get(parentPosition).getEvents().size();i++) {
            Button button = new Button(this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    x,
                    x
            );
            params.setMargins(3, 3, 3, 3);
            button.setLayoutParams(params);
            sliderIndicatorLayout.addView(button);
            indicatorList.add(button);
        }
    }

    private void setUpPager() {
        viewPager = (ViewPager) findViewById(R.id.view_pager_slider);
        List<Fragment> fragments = getFragments();
        sliderPagerAdapter = new SliderPagerAdapter(this.getSupportFragmentManager(), fragments);
        viewPager.setAdapter(sliderPagerAdapter);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                viewPagerIndex = position;
                setViewPagerItem();
                setIndicator();
            }

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        viewPagerIndex = pos;
        setIndicator();
        setViewPagerItem();
    }

    private void setIndicator() {
        for(int i=0;i<Model.getInstance().getCompetitionCategories().get(parentPosition).getEvents().size();i++) {
            indicatorList.get(i).setBackgroundResource(R.drawable.indicator_circle_normal);
        }
        indicatorList.get(viewPagerIndex).setBackgroundResource(R.drawable.indicator_circle_selected);

    }

    private List<Fragment> getFragments(){
        List<Fragment> fList = new ArrayList<>();
        for(int i =0;i< Model.getInstance().getCompetitionCategories().get(parentPosition).getEvents().size();i++)
        {
            try {
                SliderFragment fragment =new SliderFragment(getApplicationContext(),i, SliderFragmentType.SingleCompetitionFragmentType);
                fragment.setParentPosition(parentPosition);
                fList.add(i,fragment);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return fList;
    }

    private void setViewPagerItem() {
        if (viewPagerIndex >= Model.getInstance().getCompetitionCategories().get(parentPosition).getEvents().size()) {
            viewPagerIndex = 0;
        }
        viewPager.setCurrentItem(viewPagerIndex, true);
    }
}
