package com.greycodes.excel14.network;

import com.greycodes.excel14.pojo.NewsFeedItem;

import java.util.ArrayList;

import retrofit.Callback;
import retrofit.http.GET;

/**
 * Created by jincy on 14/8/15.
 */
public interface NewsFeedApi {
    @GET("/newsfeed.php")
    public void getData(Callback<ArrayList<NewsFeedItem>> response);
}
