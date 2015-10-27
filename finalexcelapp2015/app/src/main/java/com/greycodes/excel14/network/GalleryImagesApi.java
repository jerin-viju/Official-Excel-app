package com.greycodes.excel14.network;

/**
 * Created by jincy on 31/7/15.
 */


import com.greycodes.excel14.pojo.GalleryImages;

import retrofit.Callback;
import retrofit.http.GET;

/**
 * Created by Gokul Balakrishnan on 4/4/2015.
 */
public interface GalleryImagesApi {

    @GET("/uploadreq.php")
    public void getData(Callback<GalleryImages> response);
}
