package com.greycodes.excel14.fragment;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;


import com.greycodes.excel14.R;
import com.greycodes.excel14.listener.BackButtonClickListener;
import com.greycodes.excel14.model.Model;

/**
 * Created by jerin on 21/7/15.
 */
public class SignedInFragment extends Fragment {
    private BackButtonClickListener listener;
    NavigationDrawerFragment drawerFragment;
    TextView userNameTextView,emailTextView;
    Button logoutButton;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.signed_in,container,false);
        //setBackButtonOverriding(view);
        setUpViews(view);
        hideKeyboard();
        setButtonClickListener();
        return view;
    }

//    private void setBackButtonOverriding(View view) {
//        view.setFocusableInTouchMode(true);
//        view.requestFocus();
//        view.setOnKeyListener(new View.OnKeyListener() {
//            @Override
//            public boolean onKey(View v, int keyCode, KeyEvent event) {
//                if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
//                    getFragmentManager().popBackStack();
////                    getFragmentManager().popBackStack("home", FragmentManager.POP_BACK_STACK_INCLUSIVE);
//                    return true;
//                } else {
//                    return false;
//                }
//            }
//        });
//    }

    private void setUpViews(View view) {
        drawerFragment = (NavigationDrawerFragment)getFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);

        userNameTextView = (TextView)view.findViewById(R.id.usernameTextView);
        emailTextView = (TextView)view.findViewById(R.id.emailTextView);
        logoutButton = (Button)view.findViewById(R.id.logout_button);

        userNameTextView.setText(Model.getInstance().getLoginUserKey());
        emailTextView.setText(Model.getInstance().getLoginUserEmailKey());

        Typeface type = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Roboto-Regular.ttf");
        userNameTextView.setTypeface(type);
        logoutButton.setTypeface(type);
        emailTextView.setTypeface(type);
    }

    private void setButtonClickListener() {
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.support.v4.app.FragmentManager manager = getFragmentManager();
                SignInFragment f3 = new SignInFragment();
                Model.getInstance().setLoginState(false, "Guest", " ", "0");
                drawerFragment.setNavHeaderUserNameAndEmail("Guest", " ");
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.replace(R.id.main, f3,"B");
                transaction.commit();
            }
        });

    }

    private void hideKeyboard() {
        //getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        View view = this.getActivity().getCurrentFocus();
        if (view != null) {
            InputMethodManager inputManager = (InputMethodManager) this.getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }
}

