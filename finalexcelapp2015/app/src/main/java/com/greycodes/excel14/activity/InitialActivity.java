package com.greycodes.excel14.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.greycodes.excel14.model.Model;


public class InitialActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checkLogin();
    }

    private void checkLogin()
    {
        Intent intent;
        if (Model.getInstance(getApplicationContext()).getLoginState())
       {
            intent = new Intent(this, MainActivity.class);
            intent.putExtra("startingFragment",0);
        }
        else
       {
          intent = new Intent(this, DemoSliderActivity.class);
       }
        startActivity(intent);
        finish();
    }
}
