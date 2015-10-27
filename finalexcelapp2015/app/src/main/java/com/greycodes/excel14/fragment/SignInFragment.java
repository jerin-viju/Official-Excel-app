package com.greycodes.excel14.fragment;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.greycodes.excel14.R;
import com.greycodes.excel14.listener.BackButtonClickListener;
import com.greycodes.excel14.model.Model;
import com.greycodes.excel14.pojo.LoginResponse;

import com.greycodes.excel14.network.LoginApi;

import java.util.HashMap;
import java.util.Map;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by jerin on 21/7/15.
 */
public class SignInFragment extends Fragment {
    private BackButtonClickListener listener;
    Button signUpButton,signInButton;
    EditText emailTextView,passwordTextView;
    NavigationDrawerFragment drawerFragment;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sign_in,container,false);
        //setBackButtonOverriding(view);
        setUpViews(view);
        setClickListeners();
        return view;
    }

//    private void setBackButtonOverriding(View view) {
//        view.setFocusableInTouchMode(true);
//        view.requestFocus();
//        view.setOnKeyListener(new View.OnKeyListener() {
//            @Override
//            public boolean onKey(View v, int keyCode, KeyEvent event) {
//                if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
//
//                    getFragmentManager().popBackStack();
//
//                    return true;
//                } else {
//                    return false;
//                }
//            }
//        });
//    }

    private void setUpViews(View view) {
        drawerFragment = (NavigationDrawerFragment)getFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);

        emailTextView = (EditText)view.findViewById(R.id.emailTextView);
        passwordTextView = (EditText)view.findViewById(R.id.passwordTextView);
        signInButton=(Button)view.findViewById(R.id.sign_in_button);
        signUpButton=(Button)view.findViewById(R.id.sign_up_button);
        Typeface type1 = Typeface.createFromAsset(getActivity().getAssets(),"fonts/Roboto-Regular.ttf");
        Typeface type2 = Typeface.createFromAsset(getActivity().getAssets(),"fonts/Roboto-Regular.ttf");
        signInButton.setTypeface(type1);
        signUpButton.setTypeface(type1);
        emailTextView.setTypeface(type2);
        passwordTextView.setTypeface(type2);
    }

    private void setClickListeners() {

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signInClicked();
            }
        });


        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUpClicked();
            }
        });
    }

    private void signInClicked() {
        final String email,password;
        email = emailTextView.getText().toString();
        password = passwordTextView.getText().toString();

        final RestAdapter restadapter = new RestAdapter.Builder().setEndpoint("http://balumenon.net63.net/app").build();
        final LoginApi loginApi = restadapter.create(LoginApi.class);
        Map<String, String> params = new HashMap<String, String>();
        params.put("email", email);
        params.put("password", password);
        params.put("login", "login");
        loginApi.getData(params, new Callback<LoginResponse>() {
            @Override
            public void success(LoginResponse loginResponse, Response response) {
                try {
                    if (loginResponse.getStatus() == 1) {
                        Model.getInstance().setLoginState(true, loginResponse.getUser_name(), email, loginResponse.getUser_id());
                        drawerFragment.setNavHeaderUserNameAndEmail(loginResponse.getUser_name(), Model.getInstance().getLoginUserEmailKey());
                        Toast.makeText(getActivity().getApplicationContext(), "Welcome  " + loginResponse.getUser_name(), Toast.LENGTH_SHORT).show();
                        gotoSignedInFragment();
                    } else if (loginResponse.getStatus() == 2)
                        Toast.makeText(getActivity().getApplicationContext(), "Fill all fields", Toast.LENGTH_SHORT).show();
                    else if (loginResponse.getStatus() == 3)
                        Toast.makeText(getActivity().getApplicationContext(), "Check your login details", Toast.LENGTH_SHORT).show();

                }
                catch (Exception e){

                }
            }

            @Override
            public void failure(RetrofitError error) {
                Toast.makeText(getActivity().getApplicationContext(), "Failed to connect to internet", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void gotoSignedInFragment() {
        Fragment fragment = new SignedInFragment();
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.main, fragment);
        fragmentTransaction.commit();
    }

    private void signUpClicked() {
        FragmentManager manager=getFragmentManager();
        SignUpFragment signUpFragment=new SignUpFragment();
        FragmentTransaction transaction=manager.beginTransaction();
        transaction.replace(R.id.main, signUpFragment, "B");
        transaction.commit();
    }

}


