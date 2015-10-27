package com.greycodes.excel14.activity;

import android.app.Application;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.util.Base64;
import android.util.Log;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by jerin on 31/7/15.
 */
public class Myapplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        printhashkey();
    }
    public void printhashkey(){
        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "org.excelmec.excelapp",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }
    }
}
