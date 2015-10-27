package com.greycodes.excel14.fragment;

import android.app.FragmentManager;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.os.Build;

import com.greycodes.excel14.R;
import com.greycodes.excel14.adapter.NavigationDrawerAdapter;
import com.greycodes.excel14.model.Model;
import com.greycodes.excel14.pojo.NavigationDrawerItem;
//import com.facebook.Profile;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.github.siyamed.shapeimageview.CircularImageView;

import java.util.ArrayList;
import java.util.List;

public class NavigationDrawerFragment extends Fragment
{
    private ActionBarDrawerToggle mDrawerToggle;

    private DrawerLayout mDrawerLayout;

    private View containerView;
    TextView usernameTextView;
    TextView emailTextView;
    ImageView profileBackgroundImageView;
    CircularImageView circularProfileImageView;

    private static String[] titles = null;
    private static TypedArray resIds;
    static int[] sectionDividers;

    private NavigationDrawerFragmentListener drawerListener;

    public NavigationDrawerFragment() {
    }

    public void setDrawerListener(NavigationDrawerFragmentListener listener) {
        this.drawerListener = listener;
    }

    private static List<NavigationDrawerItem> getItems() {
        List<NavigationDrawerItem> data = new ArrayList<>();

        // preparing navigation drawer items
        /*for (String title : titles) {

        }*/

        for(int i=0;i<titles.length;i++)
        {
            NavigationDrawerItem navItem = new NavigationDrawerItem();
            navItem.setTitle(titles[i]);
            navItem.setImageID(resIds.getResourceId(i, 0));
            navItem.setSectionDividerColor(sectionDividers[i]);
            data.add(navItem);
        }
        return data;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        titles = getActivity().getResources().getStringArray(R.array.drawer_titles);
        resIds = getResources().obtainTypedArray(R.array.random_imgs);
        sectionDividers = getSectionDividerColors();
    }

    private int[] getSectionDividerColors() {
        int[] array = {R.color.white,
                R.color.white,
                R.color.white,
                R.color.white,
                R.color.white,
                R.color.half_black,
                R.color.white,
                R.color.white,
                R.color.half_black,
                R.color.white,
                R.color.white,
                R.color.white,
                R.color.white
        };
        return array;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflating view layout
        View view = inflater.inflate(R.layout.fragment_navigation_drawer, container, false);
        setUpViews(view);
        setNavHeaderImage(null);
        setNavHeaderView();
        setBackButtonOverriding(view);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.drawerList);
        NavigationDrawerAdapter adapter = new NavigationDrawerAdapter(getActivity(), getItems(),getActivity().getAssets());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                drawerListener.onDrawerItemSelected(position);
                mDrawerLayout.closeDrawer(containerView);
            }
        }));

        return view;
    }
    private void setBackButtonOverriding(View view) {
        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    mDrawerLayout.closeDrawer(containerView);
                  //  getFragmentManager().popBackStack();
                   // getFragmentManager().popBackStack("home", FragmentManager.POP_BACK_STACK_INCLUSIVE);

                    return true;
                } else {
                    return false;
                }
            }
        });
    }

    public void setUp(int fragmentId, DrawerLayout drawerLayout, final Toolbar toolbar) {
        containerView = getActivity().findViewById(fragmentId);
        mDrawerLayout = drawerLayout;
        mDrawerToggle = new ActionBarDrawerToggle(getActivity(), drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                drawerListener.onDrawerOpen();
                getActivity().supportInvalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                drawerListener.onDrawerClose();
                getActivity().supportInvalidateOptionsMenu();
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
                {
                    toolbar.setAlpha(1 - slideOffset / 2);
                }
            }
        };

        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerToggle.syncState();
            }
        });
    }

    public void setUpViews(View view) {
        usernameTextView = (TextView)view.findViewById(R.id.profile_name_drawer);
        emailTextView = (TextView)view.findViewById(R.id.emailid);
        circularProfileImageView = (CircularImageView)view.findViewById(R.id.profile_image_view_drawer);
        profileBackgroundImageView = (ImageView)view.findViewById(R.id.header_background_image);
    }

    public void setNavHeaderView() {
        Typeface type = Typeface.createFromAsset(getActivity().getAssets(),"fonts/Roboto-Light.ttf");
        usernameTextView.setTypeface(type);
        emailTextView.setTypeface(type);
        setNavHeaderUserNameAndEmail(Model.getInstance().getLoginUserKey(), Model.getInstance().getLoginUserEmailKey());

    }

    public void setNavHeaderUserNameAndEmail(String username,String email) {
        usernameTextView.setText(username);
        emailTextView.setText(email);
    }

    public void setNavHeaderImage(ContactsContract.Profile profile) {
        if(profile==null)
        {
            circularProfileImageView.setImageResource(R.drawable.signin2);
        }
        else
        {
    //        circularProfileImageView.setImageURI(profile.getProfilePictureUri(60,60));
        }

        profileBackgroundImageView.setImageResource(R.drawable.backk);
    }

    public interface ClickListener {
        void onClick(View view, int position);
    }

    static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener
    {
        private final GestureDetector gestureDetector;

        private final ClickListener clickListener;

        public RecyclerTouchListener(Context context, final ClickListener clickListener) {
            this.clickListener = clickListener;
            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView recyclerView, MotionEvent e) {

            View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
            if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)) {
                clickListener.onClick(child, recyclerView.getChildAdapterPosition(child));
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean b) {
        }
    }

    public interface NavigationDrawerFragmentListener {
        void onDrawerItemSelected(int position);

        void onDrawerOpen();

        void onDrawerClose();
    }
}
