package com.greycodes.excel14.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.greycodes.excel14.R;
import com.greycodes.excel14.enums.FragmentType;
import com.greycodes.excel14.model.Model;
import com.greycodes.excel14.pojo.CommonCategoryItem;
import com.greycodes.excel14.pojo.CompetitionCategory;
import com.greycodes.excel14.pojo.InfoCategory;
import com.greycodes.excel14.pojo.TalkCategory;
import com.github.florent37.materialviewpager.MaterialViewPager;
import com.github.florent37.materialviewpager.header.HeaderDesign;

import com.greycodes.excel14.helper.ImageHelper;
import com.greycodes.excel14.listener.BackButtonClickListener;

import java.util.ArrayList;

public class MaterialPagerFragment extends Fragment {

    private BackButtonClickListener listener;

    MaterialViewPager mViewPager;
    ImageView headerImageView;
    Context context;
    FragmentType fragmentType =null;

    ArrayList<String> titles;
    ArrayList<String> images;
//    ArrayList<String> colors;
    ArrayList<Object> objectlist;

    public static MaterialPagerFragment newInstance() {
        MaterialPagerFragment fragment = new MaterialPagerFragment();
        return fragment;
    }

    public MaterialPagerFragment() {
        // Required empty public constructor
    }

    @SuppressLint("ValidFragment")
    public MaterialPagerFragment(Context context,ArrayList<String> titles,ArrayList<String> images,ArrayList<Object> objectlist,BackButtonClickListener listener) {
        this.context = context;
        this.titles = titles;
        this.images = images;
//        this.colors = colors;
        this.objectlist = objectlist;
        this.listener = listener;
        setList(0);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_material_pager, container, false);
        setBackButtonOverriding(view);
        setUpViews(view);
        setPager();
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
                   // getFragmentManager().popBackStack("home", FragmentManager.POP_BACK_STACK_INCLUSIVE);
                    return true;
                } else {
                    return false;
                }
            }
        });
    }

    private void setUpViews(View view) {
        mViewPager = (MaterialViewPager) view.findViewById(R.id.materialViewPager);
        headerImageView = (ImageView) view.findViewById(R.id.materialviewpager_imageHeader);

        int resId = ImageHelper.getResourceId(context, images.get(0));
        headerImageView.setImageResource(resId);
        Drawable drawable = headerImageView.getDrawable();
        HeaderDesign.fromColorAndDrawable(R.color.transparent, drawable);
//        HeaderDesign.fromColorAndDrawable(ImageHelper.getColorResId(context,colors.get(0)), drawable);

        Toolbar toolbar = mViewPager.getToolbar();
        if (toolbar != null) {
            AppCompatActivity activity = (AppCompatActivity) getActivity();
            activity.setSupportActionBar(toolbar);
            ActionBar actionBar = activity.getSupportActionBar();
            assert actionBar != null;
            actionBar.hide();
//            actionBar.setTitle(null);
//            actionBar.setDisplayShowTitleEnabled(false);
//            actionBar.setDisplayHomeAsUpEnabled(true);
//            actionBar.setDisplayShowHomeEnabled(true);
//            actionBar.setDisplayShowTitleEnabled(true);
//            actionBar.setDisplayUseLogoEnabled(false);
//            actionBar.setHomeButtonEnabled(true);
        }
    }

    private void setPager() {
        mViewPager.getViewPager().setAdapter(new FragmentStatePagerAdapter(this.getFragmentManager()) {

            @Override
            public Fragment getItem(int position) {

                ArrayList<CommonCategoryItem> objectList = setList(position);
                RecyclerViewFragment fragment = RecyclerViewFragment.newInstance();
                fragment.setItems(context, objectList, fragmentType, position);
                return fragment;
            }

            @Override
            public int getCount() {
                return titles.size();
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return titles.get(position);
//
            }
        });

        mViewPager.getViewPager().setOffscreenPageLimit(mViewPager.getViewPager().getAdapter().getCount());
        mViewPager.getPagerTitleStrip().setViewPager(mViewPager.getViewPager());
        mViewPager.getPagerTitleStrip().setBackgroundResource(R.color.transparent);
        mViewPager.setMaterialViewPagerListener(new MaterialViewPager.Listener() {
            @Override
            public HeaderDesign getHeaderDesign(int page) {
                mViewPager.getPagerTitleStrip().setTabBackground(R.color.transparent);
                int resId = ImageHelper.getResourceId(context, images.get(page));
                if(resId!=0) {
                    headerImageView.setImageResource(resId);
                    Drawable drawable = headerImageView.getDrawable();
                    return HeaderDesign.fromColorAndDrawable(R.color.transparent,drawable);
                }
                return null;
//                return HeaderDesign.fromColorAndDrawable(ImageHelper.getColorResId(context,colors.get(page)), drawable);

            }
        });
    }

    public ArrayList setList(int position) {
        ArrayList<CommonCategoryItem>objectList = new ArrayList<>();

        CommonCategoryItem  item;
        if(objectlist.get(position).getClass()==CompetitionCategory.class) {
            fragmentType = FragmentType.CompetitionsFragmentType;
            CompetitionCategory category = (CompetitionCategory) objectlist.get(position);
            int i;

            for(i=0;i<category.getEvents().size();i++) {
                item = new CommonCategoryItem();
                item.setTitle(category.getEvents().get(i).getTitle());
                item.setImage(category.getEvents().get(i).getImage());
                if(i==0)
                    objectList.add(item);
                objectList.add(item);
            }
            for(i=0;i<2;i++) {
                item = new CommonCategoryItem();
                objectList.add(item);
            }

        }
        else if(objectlist.get(0).getClass()==TalkCategory.class)
        {
            fragmentType =FragmentType.TalksFragmentType;
            TalkCategory category = (TalkCategory) objectlist.get(position);
            item = new CommonCategoryItem();
            item.setTitle(category.getTitle());
            item.setImage(category.getImageId());

            item.setDescription(category.getAbout());
            objectList.add(item);
        }
        else if(objectlist.get(0).getClass()==InfoCategory.class)
        {
            fragmentType =FragmentType.InfoFragmentType;
            InfoCategory category = (InfoCategory) objectlist.get(position);
            for(int i=0;i<category.getList().size();i++) {
                item = category.getList().get(i);
                objectList.add(item);
            }
            for(int i=0;i<2;i++) {
                item = category.getList().get(0);
                objectList.add(item);
            }
        }
        else {
            fragmentType = FragmentType.CommonFragmentType;
            item = (CommonCategoryItem) objectlist.get(position);
            objectList.add(item);
        }
        return objectList;
    }
}

