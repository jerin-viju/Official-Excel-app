package com.greycodes.excel14.network;


import retrofit.Callback;
import retrofit.client.Response;
import retrofit.http.GET;

/**
 * Created by jincy on 16/8/15.
 */
public interface MenuApi {
    @GET("/menu.json")
    public void getData(Callback<Response> response);
}
