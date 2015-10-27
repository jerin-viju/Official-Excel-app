package com.greycodes.excel14.network;

import com.greycodes.excel14.pojo.LoginResponse;

import java.util.Map;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Query;
import retrofit.http.QueryMap;

/**
 * Created by jincy on 9/8/15.
 */
public interface SignUpApi {
    @GET("/signup.php")
    public void getData(@QueryMap Map<String, String> params,Callback<LoginResponse> response);

}
