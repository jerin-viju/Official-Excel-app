package com.greycodes.excel14.fragment;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

/*
import com.greycodes.excel14.model.Model;
import com.greycodes.excel14.network.SignUpApi;
import com.greycodes.excel14.pojo.LoginResponse;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
*/
import com.github.siyamed.shapeimageview.CircularImageView;
import com.greycodes.excel14.R;
import com.greycodes.excel14.listener.BackButtonClickListener;
import com.greycodes.excel14.model.Model;
import com.greycodes.excel14.network.SignUpApi;
import com.greycodes.excel14.pojo.LoginResponse;

import java.util.HashMap;
import java.util.Map;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by jerin on 21/7/15.
 */
public class SignUpFragment extends Fragment {

    private static final int SELECT_PICTURE = 1;
    private String selectedImagePath;
    private BackButtonClickListener listener;
    //LoginButton fbLoginButton;
    Button signUpButton;
    CircularImageView imageView;
    RadioGroup radioGroup;
    EditText firstnameTextView,passwordTextView,emailTextView,collegeTextView,departmentTextView,phnoTextView;
    NavigationDrawerFragment drawerFragment;
    //private  AccessTokenTracker tracker;
    //private  ProfileTracker profileTracker;
    //private CallbackManager mcallbackmanager;

    /*private FacebookCallback<LoginResult> mcallback=new FacebookCallback<LoginResult>() {
        @Override
        public void onSuccess(LoginResult loginResult) {
            AccessToken accessToken=loginResult.getAccessToken();
            Profile profile=Profile.getCurrentProfile();
            if(profile!=null) {
                firstnameTextView.setText(profile.getName());
                imageView.setImageURI(profile.getProfilePictureUri(50,50));
//                displaymessage(profile);
//                drawerFragment.setNavHeaderUserNameAndEmail(profile.getName()," ");
//                drawerFragment.setNavHeaderImage(profile);
            }
        }

        @Override
        public void onCancel() {

        }
*//*
        @Override
        public void onError(FacebookException e) {
            Toast.makeText(getActivity().getApplicationContext(),"Error Logging in",Toast.LENGTH_SHORT);
        }
    };*/
    /*public void displaymessage(Profile profile){
        if(profile!=null){
            Toast.makeText(getActivity().getApplicationContext(),"welcome "+profile.getName(),Toast.LENGTH_SHORT );
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);FacebookSdk.sdkInitialize(getActivity().getApplicationContext());
        FacebookSdk.sdkInitialize(getActivity().getApplicationContext());
        mcallbackmanager= CallbackManager.Factory.create();
        tracker=new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken old, AccessToken newtoken) {

            }
        };
         profileTracker=new ProfileTracker() {
            @Override
            protected void onCurrentProfileChanged(Profile oldprofile, Profile newprofile) {
                if(newprofile!=null) {
                    firstnameTextView.setText(newprofile.getName());
                    imageView.setImageURI(newprofile.getProfilePictureUri(50,50));
//                displaymessage(profile);
//                drawerFragment.setNavHeaderUserNameAndEmail(profile.getName()," ");
//                drawerFragment.setNavHeaderImage(profile);
                }
//                drawerFragment.setNavHeaderUserNameAndEmail(newprofile.getName()," ");
//                drawerFragment.setNavHeaderImage(newprofile);
//                displaymessage(newprofile);

            }
        };
        tracker.startTracking();
        profileTracker.startTracking();

    }
*/
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sign_up,container,false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setBackButtonOverriding(view);
        setUpViews(view);
        //      setFbLoginButtonSpecs();
        setLoginButtonClickListener();
    }

    private void setBackButtonOverriding(View view) {
        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
                    getFragmentManager().popBackStack();
//                    getFragmentManager().popBackStack("home", FragmentManager.POP_BACK_STACK_INCLUSIVE);
                    return true;
                } else {
                    return false;
                }
            }
        });
    }


    private void setUpViews(View view) {
        // radioGroup = (RadioGroup) view.findViewById(R.id.radioGroup1);
        drawerFragment = (NavigationDrawerFragment)getFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        imageView = (CircularImageView)view.findViewById(R.id.profile_image_view);
        firstnameTextView = (EditText)view.findViewById(R.id.firstNameTextView);
        passwordTextView = (EditText)view.findViewById(R.id.passwordTextView);
        emailTextView = (EditText)view.findViewById(R.id.emailTextView);
        collegeTextView = (EditText)view.findViewById(R.id.collegeTextView);
        departmentTextView = (EditText)view.findViewById(R.id.departmentTextView);
        phnoTextView = (EditText)view.findViewById(R.id.phoneNumTextView);
        signUpButton = (Button)view.findViewById(R.id.sign_up_button);
        //      fbLoginButton=(LoginButton)view.findViewById(R.id.fb_login_button);

//        emailTextView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//                if (hasFocus){
//                    Toast.makeText(getActivity(),"Use Email id used for Facebook",Toast.LENGTH_LONG).show();
//                }
//            }
//        });
    }
    /*
        private void setFbLoginButtonSpecs() {
            fbLoginButton.setReadPermissions("user_friends");
            fbLoginButton.setFragment(this);
            fbLoginButton.registerCallback(mcallbackmanager, mcallback);
        }
    */
    private void setLoginButtonClickListener() {
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String  firstName, password, email, college, department, phno;
                firstName = firstnameTextView.getText().toString();
                password = passwordTextView.getText().toString();
                email = emailTextView.getText().toString();
                college = collegeTextView.getText().toString();
                department = departmentTextView.getText().toString();
                phno = phnoTextView.getText().toString();

                final RestAdapter restadapter = new RestAdapter.Builder().setEndpoint("http://balumenon.net63.net/app").build();
                final SignUpApi signUpApi = restadapter.create(SignUpApi.class);
                Map<String, String> params = new HashMap<String, String>();
                params.put("user_name", firstName);
                params.put("college", college);
                params.put("department", department);
                params.put("email", email);
                params.put("phone", phno);
                params.put("password", password);
                params.put("submit", "Sign Up");
                signUpApi.getData(params, new Callback<LoginResponse>() {
                    @Override
                    public void success(LoginResponse loginResponse, Response response) {
                        if (loginResponse.getStatus() == 1) {
                            Model.getInstance().setLoginState(true, firstName, email, loginResponse.getUser_id());
                            drawerFragment.setNavHeaderUserNameAndEmail(firstName, email);
                            drawerFragment.setNavHeaderImage(null);
                            Toast.makeText(getActivity().getApplicationContext(), "Welcome  " + firstName, Toast.LENGTH_SHORT).show();
                            gotoSignedInFragment();
                        } else if(loginResponse.getStatus() == 2)
                            Toast.makeText(getActivity().getApplicationContext(),"check all fields", Toast.LENGTH_SHORT).show();
                        else if(loginResponse.getStatus() == 3)
                            Toast.makeText(getActivity().getApplicationContext(),"check your emailid and username", Toast.LENGTH_SHORT).show();
                        else
                            Toast.makeText(getActivity().getApplicationContext(),"Backend issues try again after some time", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Toast.makeText(getActivity().getApplicationContext(), "Failed to connect to internet", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    private void gotoSignedInFragment() {
        Fragment fragment = new SignedInFragment();
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container_body, fragment);
        fragmentTransaction.commit();
    }
    /*
        @Override
        public void onResume() {
            super.onResume();
            Profile profile=Profile.getCurrentProfile();
            displaymessage(profile);
        }
    */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        mcallbackmanager.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
                Uri selectedImageUri = data.getData();
                selectedImagePath = getPath(selectedImageUri);
                System.out.println("Image Path : " + selectedImagePath);
                imageView.setImageURI(selectedImageUri);
            }
        }
    }

    public String getPath(Uri uri) {
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = getActivity().managedQuery(uri, projection, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }
/*
    @Override
    public void onStop() {
        super.onStop();
        tracker.stopTracking();
        profileTracker.stopTracking();
    }*/
}