package com.greycodes.excel14.activity;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.inputmethod.InputMethodManager;


import com.greycodes.excel14.R;
import com.greycodes.excel14.enums.FragmentType;
import com.greycodes.excel14.fragment.GalleryFragment;
import com.greycodes.excel14.fragment.HomeFragment;
import com.greycodes.excel14.fragment.LoginFragment;
import com.greycodes.excel14.fragment.MapFragment;
import com.greycodes.excel14.fragment.MaterialPagerFragment;

import com.greycodes.excel14.fragment.NavigationDrawerFragment;
import com.greycodes.excel14.fragment.NewsFeedFragment;
import com.greycodes.excel14.fragment.ScheduleFragment;
import com.greycodes.excel14.fragment.proshow_fragment;
import com.greycodes.excel14.model.Model;
import com.greycodes.excel14.listener.BackButtonClickListener;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements NavigationDrawerFragment.NavigationDrawerFragmentListener
{
    private Toolbar mToolbar;
    ArrayList<String> titles;
    ArrayList<String> images;
    int startingFragmentNumber =0;
    NavigationDrawerFragment drawerFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Model.getInstance(getApplicationContext());
        setToolBar();
        startingFragmentNumber = getIntent().getExtras().getInt("startingFragment");
        setNavigationDrawer();
    }

    private void setToolBar()
    {
        mToolbar = (Toolbar) findViewById(R.id.tool_bar_main_activity);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

    }

    private void setNavigationDrawer()
    {
        drawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        drawerFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), mToolbar);
        drawerFragment.setDrawerListener(this);
        displayView(0);
        displayView(startingFragmentNumber);
    }


    private void displayView(int position)
    {
        Fragment fragment;
        switch (position) {
           case 0:
                fragment = new HomeFragment();
                setFragment(fragment);
                break;
            case 1://competition
                setUpPagerArguments(FragmentType.CompetitionsFragmentType);
                fragment = new MaterialPagerFragment(getApplicationContext(), titles, images, (ArrayList) Model.getInstance().getCompetitionCategories(), new BackButtonClickListener() {
                    @Override
                    public void BackButtonClicked(int position) {
                        displayView(0);
                    }
                });
                getFragmentManager().popBackStack();
                setFragment(fragment);
                break;
            case 2://talks
                setUpPagerArguments(FragmentType.TalksFragmentType);
                fragment = new MaterialPagerFragment(getApplicationContext(), titles, images, (ArrayList) Model.getInstance().getTalkCategories(), new BackButtonClickListener() {
                    @Override
                    public void BackButtonClicked(int position) {
                        displayView(0);
                    }
                });
                getFragmentManager().popBackStack();
                setFragment(fragment);
                break;
            case 3:
                fragment =new proshow_fragment(new BackButtonClickListener() {
                    @Override
                    public void BackButtonClicked(int position) {
                        displayView(0);
                    }
                });
                getFragmentManager().popBackStack();
                setFragment(fragment);
                break;
            case 4://excel pro
                setUpPagerArguments(FragmentType.ExcelProFragmentType);
                fragment = new MaterialPagerFragment(getApplicationContext(), titles, images, (ArrayList) Model.getInstance().getExcelProCategories(), new BackButtonClickListener() {
                    @Override
                    public void BackButtonClicked(int position) {
                        displayView(0);
                    }
                });
                getFragmentManager().popBackStack();
                setFragment(fragment);
                break;
            case 5://initiaitves
                setUpPagerArguments(FragmentType.InitiativesFragmentType);
                fragment = new MaterialPagerFragment(getApplicationContext(), titles, images, (ArrayList) Model.getInstance().getInitiativesCategories(), new BackButtonClickListener() {
                    @Override
                    public void BackButtonClicked(int position) {
                        displayView(0);
                    }
                });
                getFragmentManager().popBackStack();
                setFragment(fragment);
                break;
            case 6://news feed
                fragment = new NewsFeedFragment(new BackButtonClickListener() {
                    @Override
                    public void BackButtonClicked(int position) {
                        displayView(0);
                    }
                });
                setFragment(fragment);
                getFragmentManager().popBackStack();
//                title = " ";
                break;

            case 7://map
                fragment = new MapFragment(new BackButtonClickListener() {
                    @Override
                    public void BackButtonClicked(int position) {
                        displayView(0);
                    }
                });
                getFragmentManager().popBackStack();
                setFragment(fragment);
                break;
            case 8://info
                setUpPagerArguments(FragmentType.InfoFragmentType);
                fragment = new MaterialPagerFragment(getApplicationContext(), titles, images, (ArrayList) Model.getInstance().getInfoCategories(), new BackButtonClickListener() {
                    @Override
                    public void BackButtonClicked(int position) {
                        displayView(0);
                    }
                });
                getFragmentManager().popBackStack();
                setFragment(fragment);
                break;
            case 9://schedule
                fragment = new ScheduleFragment(new BackButtonClickListener() {
                    @Override
                    public void BackButtonClicked(int position) {
                        displayView(0);
                    }
                });
                getFragmentManager().popBackStack();
                setFragment(fragment);
                break;
            case 10://gallery
                TypedArray typedArray =obtainStyledAttributes(R.styleable.MyGallery);
                fragment = new GalleryFragment(getApplicationContext(), typedArray, new BackButtonClickListener() {
                    @Override
                    public void BackButtonClicked(int position) {
                        displayView(0);
                    }
                });

                getFragmentManager().popBackStack();
                setFragment(fragment);
                break;
            case 11://accounts
                fragment = new LoginFragment(new BackButtonClickListener() {
                    @Override
                    public void BackButtonClicked(int position) {
                        displayView(0);
                    }
                });
                getFragmentManager().popBackStack();
                setFragment(fragment);
                break;
            default:
                fragment = new HomeFragment();
                setFragment(fragment);
                break;
        }
        getSupportActionBar().setTitle(" ");
    }

    private void setUpPagerArguments(FragmentType fragmentType) {
        titles = null;
        images = null;
//        colors = null;
        titles = new ArrayList<>();
        images = new ArrayList<>();
//        colors = new ArrayList<>();

        switch (fragmentType) {
            case CompetitionsFragmentType:
                for(int i=0;i<Model.getInstance().getCompetitionCategories().size();i++)
                {
                    titles.add(Model.getInstance().getCompetitionCategories().get(i).getTitle());
                    images.add(Model.getInstance().getCompetitionCategories().get(i).getImageURL());
//                    colors.add(Model.getInstance().getCompetitionCategories().get(i).getColor());
                }
                break;
            case TalksFragmentType:
                for(int i=0;i<Model.getInstance().getTalkCategories().size();i++)
                {
                    titles.add(Model.getInstance().getTalkCategories().get(i).getTitle());
                    images.add(Model.getInstance().getTalkCategories().get(i).getImageId());
//                    colors.add(Model.getInstance().getTalkCategories().get(i).getColor());
                }
                break;
            case ExcelProFragmentType:
                for(int i=0;i<Model.getInstance().getExcelProCategories().size();i++)
                {
                    titles.add(Model.getInstance().getExcelProCategories().get(i).getTitle());
                    images.add(Model.getInstance().getExcelProCategories().get(i).getImage());
//                    colors.add(Model.getInstance().getExcelProCategories().get(i).getColor());
                }
                break;
            case InitiativesFragmentType:
                for(int i=0;i<Model.getInstance().getInitiativesCategories().size();i++)
                {
                    titles.add(Model.getInstance().getInitiativesCategories().get(i).getTitle());
                    images.add(Model.getInstance().getInitiativesCategories().get(i).getImage());
//                    colors.add(Model.getInstance().getInitiativesCategories().get(i).getColor());
                }
                break;
            case InfoFragmentType:
                for(int i=0;i<Model.getInstance().getInfoCategories().size();i++)
                {
                    titles.add(Model.getInstance().getInfoCategories().get(i).getTitle());
                    images.add(Model.getInstance().getInfoCategories().get(i).getImage());
//                    colors.add(Model.getInstance().getInfoCategories().get(i).getColor());
                }
                break;
        }
    }

    private void setFragment(Fragment fragment)
    {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container_body, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    public void onDrawerItemSelected(int position) {
        displayView(position);
    }

    @Override
    public void onDrawerOpen() {

    }

    @Override
    public void onDrawerClose() {

    }

    @Override
    public void onResume() {
        super.onResume();
        getSupportActionBar().setTitle(" ");
    }

    @Override
    public void onBackPressed() {
        Model.getInstance().appExited();
        finish();
    }
}