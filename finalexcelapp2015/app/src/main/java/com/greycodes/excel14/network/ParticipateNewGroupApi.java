package com.greycodes.excel14.network;

import com.greycodes.excel14.pojo.ParticipateResponse;

import java.util.Map;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.QueryMap;

/**
 * Created by jincy on 12/8/15.
 */
public interface ParticipateNewGroupApi {
    @GET("/groupreg.php")
    public void getData(@QueryMap Map<String, String> params, Callback<ParticipateResponse> response);

}
