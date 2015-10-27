package com.greycodes.excel14.activity;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;


import com.andexert.library.RippleView;

import com.greycodes.excel14.R;
import com.greycodes.excel14.adapter.SliderPagerAdapter;
import com.greycodes.excel14.enums.SliderFragmentType;
import com.greycodes.excel14.fragment.SliderFragment;
import com.greycodes.excel14.model.Model;


import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class DemoSliderActivity extends AppCompatActivity {

    private SliderPagerAdapter sliderPagerAdapter;
    ViewPager pager;
    Button signInButton,visitButton;
    LinearLayout sliderIndicatorLayout;
    Timer timer;
    RemindTask remindTask;
    Resources r;
    public int dpToPx(int dp) {
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        return (int)(dp * (displayMetrics.densityDpi / 160f));
    }

    private int page = 0;
    ArrayList<Button> indicatorList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo_slider);

        linkViews();
        setUpSliderIndicator();
        setUpPager();
    }

    private void linkViews()
    {
        pager = (ViewPager)findViewById(R.id.view_pager_slider_initial);
        signInButton = (Button)findViewById(R.id.sign_in_button);
        visitButton = (Button)findViewById(R.id.visit_button);
        Typeface type = Typeface.createFromAsset(getAssets(),"fonts/Roboto-Light.ttf");
        signInButton.setTypeface(type);
        visitButton.setTypeface(type);
    }

    private void setUpPager()
    {
        try {
            List<Fragment> fragments = getFragments();
            sliderPagerAdapter = new SliderPagerAdapter(getSupportFragmentManager(), fragments);
            pager.setAdapter(sliderPagerAdapter);
            pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageSelected(int position) {
                    page = position;
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
            pageSwitcher(3);
        }
        catch(Exception e){

        }
    }

    private List<Fragment> getFragments(){
        List<Fragment> fList = new ArrayList<>();
        fList.add(new SliderFragment(R.drawable.img4, SliderFragmentType.InitialSliderFragmentType));
        fList.add(new SliderFragment( R.drawable.img1, SliderFragmentType.InitialSliderFragmentType));
        fList.add(new SliderFragment( R.drawable.img2,SliderFragmentType.InitialSliderFragmentType));
        fList.add(new SliderFragment( R.drawable.img3,SliderFragmentType.InitialSliderFragmentType));


        return fList;
    }

    private void pageSwitcher(int seconds) {
        timer = new Timer();
        remindTask = new RemindTask();
        timer.scheduleAtFixedRate(remindTask, 0, seconds * 1000);
    }

    public void signInButtonClicked(View view)
    {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("startingFragment",11);
        startActivity(intent);
        finish();
    }

    public void visitButtonClicked(View view)
    {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("startingFragment",0);
        Model.getInstance().setLoginState(false, "Guest", " ", "0");
        startActivity(intent);
        finish();
    }

    private void setUpSliderIndicator() {
        int x=dpToPx(15);
        sliderIndicatorLayout = (LinearLayout)findViewById(R.id.slider_indicator_layout);
        for (int i=0;i<4;i++) {
            Button button = new Button(this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    x,
                    x
            );
            params.setMargins(5, 5, 5, 5);
            button.setLayoutParams(params);
            sliderIndicatorLayout.addView(button);
            indicatorList.add(button);
        }
    }

    private void setIndicator(int page) {
        for(int i=0;i<4;i++) {
            indicatorList.get(i).setBackgroundResource(R.drawable.indicator_circle_orange);
        }
        indicatorList.get(page).setBackgroundResource(R.drawable.indicator_circle_dark_orange);

    }

    class RemindTask extends TimerTask {

        @Override
        public void run() {
            runOnUiThread(new Runnable() {
                public void run() {
                    setViewPagerItem();
                    page++;
                }
            });

        }
    }

    private void setViewPagerItem() {
        if (page >= sliderPagerAdapter.getCount())
        {
            page = 0;
        }
        pager.setCurrentItem(page, true);
        setIndicator(page);
    }

        @Override
    protected void onDestroy() {
        super.onDestroy();
        if(timer!=null) {
            timer.cancel();
            timer.purge();
            remindTask.cancel();
        }
    }
}



