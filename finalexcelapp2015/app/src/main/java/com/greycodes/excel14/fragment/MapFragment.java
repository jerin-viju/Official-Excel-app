package com.greycodes.excel14.fragment;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.InflateException;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import com.greycodes.excel14.R;

import com.greycodes.excel14.listener.BackButtonClickListener;


import net.i2p.android.ext.floatingactionbutton.FloatingActionButton;
import net.i2p.android.ext.floatingactionbutton.FloatingActionsMenu;


@SuppressLint("ValidFragment")
public class MapFragment extends Fragment {

    private BackButtonClickListener listener;

    private static View view;
    private GoogleMap map;
    private final LatLng LOCATION_MEC = new LatLng(10.02836, 76.328829);
    private final LatLng LOCATION_CITY = new LatLng(10.025140, 76.308970);
    Button floormapbutton;

    @SuppressLint("ValidFragment")
    public MapFragment(BackButtonClickListener listener) {
        this.listener = listener;
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        try {
            setUpMap(inflater, container);
            setUpDifferentMapViews();
        }
        catch(Exception e) {

        }
        return view;
    }

    private void setUpMap(LayoutInflater inflater, ViewGroup container) {
        if (view != null) {
            ViewGroup parent = (ViewGroup) view.getParent();
            if (parent != null)
                parent.removeView(view);
        }
        try {
            view = inflater.inflate(R.layout.fragment_map, container, false);





           // setUpMapButtons();
            setBackButtonOverriding(view);
        } catch (InflateException e) {
		        /* map is already there, just return view as it is */
        }

        map = ((SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map)).getMap();
        map.addMarker(new MarkerOptions().position(LOCATION_MEC).title("Model Engineering College"));
        map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(LOCATION_MEC, 16);
        map.animateCamera(update);

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
//                   getFragmentManager().popBackStack("home", FragmentManager.POP_BACK_STACK_INCLUSIVE);
                    return true;
                } else {
                    return false;
                }
            }
        });
    }

    private void setUpDifferentMapViews() {
        FloatingActionsMenu menuMultipleActions;
        menuMultipleActions = (FloatingActionsMenu) view.findViewById(R.id.multiple_actions);
        FloatingActionButton navigation;
        FloatingActionButton floormap;
        FloatingActionButton street;
        navigation = (FloatingActionButton) view.findViewById(R.id.navigation);
        floormap = (FloatingActionButton) view.findViewById(R.id.floormap);
        street = (FloatingActionButton) view.findViewById(R.id.street);


        navigation.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                try {
                    Intent navigation = new Intent(Intent.ACTION_VIEW, Uri.parse("https://maps.google.co.in/maps?q=Model+Engineering+College+Thrikkakara,+Ernakulam,+Kerala&hl=en&ll=10.028558,76.328748&spn=0.006772,0.009645&sll=10.027935,76.328716&sspn=0.006772,0.009645&t=h&z=17&iwloc=A"));
                    startActivity(navigation);

                } catch (Exception e) {
                }


            }
        });


        street.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                CameraUpdate update = CameraUpdateFactory.newLatLngZoom(LOCATION_CITY, 13);
                map.animateCamera(update);


            }
        });


        floormap.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Fragment fragment = new FloorMapFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.container_body, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

            }
        });
    }

}
