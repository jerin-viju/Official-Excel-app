package com.greycodes.excel14.network;

import com.greycodes.excel14.pojo.ParticipateResponse;

import java.util.Map;

import retrofit.Callback;
import retrofit.client.Response;
import retrofit.http.GET;
import retrofit.http.QueryMap;

/**
 * Created by jincy on 28/8/15.
 */
public interface ParticipateExistingGroupApi {
    @GET("/groupjoin.php")
    public void getData(@QueryMap Map<String, String> params, Callback<Response> response);
}
