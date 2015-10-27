package com.greycodes.excel14.fragment;

import android.annotation.SuppressLint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;


import com.greycodes.excel14.R;
import com.greycodes.excel14.listener.BackButtonClickListener;

/**
 * Created by jerin on 2/8/15.
 */
@SuppressLint("ValidFragment")
public class proshow_fragment extends Fragment {
    ListView listView;
    private BackButtonClickListener listener;

    @SuppressLint("ValidFragment")
    public proshow_fragment(BackButtonClickListener listener) {
        this.listener = listener;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.proshowfragment,container,false);
        setBackButtonOverriding(v);
//        setUpProshowList(v);
        TextView view1=(TextView)v.findViewById(R.id.content);
        TextView view2=(TextView)v.findViewById(R.id.proname);
        TextView view3=(TextView)v.findViewById(R.id.content1);
        TextView view4=(TextView)v.findViewById(R.id.proname1);
        TextView view5=(TextView)v.findViewById(R.id.content2);
        TextView view6=(TextView)v.findViewById(R.id.proname3);
        TextView view7=(TextView)v.findViewById(R.id.content3);
        TextView view8=(TextView)v.findViewById(R.id.proname4);
        TextView view9=(TextView)v.findViewById(R.id.content4);
        TextView view0=(TextView)v.findViewById(R.id.proname5);
        Typeface face1= Typeface.createFromAsset(getActivity().getApplicationContext().getAssets(), "fonts/Roboto-Thin.ttf");
        view0.setTypeface(face1);
        view1.setTypeface(face1);
        view2.setTypeface(face1);

        view3.setTypeface(face1);
        view4.setTypeface(face1);
        view5.setTypeface(face1);
        view6.setTypeface(face1);
        view7.setTypeface(face1);
        view8.setTypeface(face1);
        view9.setTypeface(face1);



        return v;
    }

//    private void setUpProshowList(View view) {
//        listView = (ListView)view.findViewById(R.id.proshow_list);
//        listView.setAdapter(new ProshowAdapter(getActivity().getApplicationContext()));
//    }

    private void setBackButtonOverriding(View view) {
        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
                    listener.BackButtonClicked(0);
//                    getFragmentManager().popBackStack("home", FragmentManager.POP_BACK_STACK_INCLUSIVE);
                    return true;
                } else {
                    return false;
                }
            }
        });
    }


}
