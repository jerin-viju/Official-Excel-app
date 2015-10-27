package com.greycodes.excel14.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.greycodes.excel14.R;
import com.greycodes.excel14.listener.BackButtonClickListener;
import com.greycodes.excel14.model.Model;


@SuppressLint("ValidFragment")
public class LoginFragment extends Fragment {
    FragmentManager manager;
    private BackButtonClickListener listener;


    @SuppressLint("ValidFragment")
    public LoginFragment(BackButtonClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FragmentManager manager = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction=manager.beginTransaction();


        if(Model.getInstance().getLoginState()){
            SignedInFragment signedInFragment =new SignedInFragment();
            transaction.add(R.id.main, signedInFragment,"B");
            transaction.commit();
        }
        else {
            SignInFragment signInFragment =new SignInFragment();
            transaction.add(R.id.main, signInFragment,"A");
            transaction.commit();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        manager=getFragmentManager();
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        setBackButtonOverriding(view);
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
}