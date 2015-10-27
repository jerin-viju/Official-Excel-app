package com.greycodes.excel14.network;

import com.greycodes.excel14.pojo.LoginResponse;

import java.util.Map;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.QueryMap;

/**
 * Created by jincy on 9/8/15.
 */
public interface LoginApi {
    @GET("/login.php")
    public void getData(@QueryMap Map<String, String> params,Callback<LoginResponse> response);

}
