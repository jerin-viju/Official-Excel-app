package com.greycodes.excel14.fragment;

import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;



import android.graphics.Typeface;



import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import android.view.WindowManager;


import android.widget.Button;
import android.widget.TextView;


import com.andexert.library.RippleView;

import com.greycodes.excel14.R;


import com.greycodes.excel14.listener.BackButtonClickListener;





public class HomeFragment extends Fragment {


    public HomeFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);
        hideKeyboard();
        final RippleView rippleView = (RippleView) v.findViewById(R.id.more);
        Button btn=(Button)v.findViewById(R.id.mailid);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String link = "http://excelmec.org/excel2015/";
            /*   if (link.equals("") || link.equals(" ")) {

                } else {
                    Intent internetIntent = new Intent(Intent.ACTION_VIEW,
                            Uri.parse(link));
                    internetIntent.setComponent(new ComponentName("com.android.browser", "com.android.browser.BrowserActivity"));
                    internetIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                   startActivity(internetIntent);
                }*/
                Uri uri = Uri.parse("http://excelmec.org/excel2015/"); // missing 'http://' will cause crashed
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);

            }
        });
        TextView text = (TextView) v.findViewById(R.id.mailid);
        Typeface face1 = Typeface.createFromAsset(getActivity().getApplicationContext().getAssets(), "fonts/Roboto-Thin.ttf");
        text.setTypeface(face1);
        setBackButtonOverriding(v);
        rippleView.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
            @Override
            public void onComplete(RippleView rippleView) {
                Fragment fragment = new MapFragment(new BackButtonClickListener() {
                    @Override
                    public void BackButtonClicked(int position) {
                        getFragmentManager().popBackStack();
                    }
                });
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.container_body, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });


        return v;
    }
    private void setBackButtonOverriding(View view) {
        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
                    myalert1 myAlert = new myalert1();
                    myAlert.show(getActivity().getFragmentManager(), "A");
                    return true;
                } else {
                    return false;
                }
            }
        });
    }


    private void hideKeyboard() {
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

    }
}
