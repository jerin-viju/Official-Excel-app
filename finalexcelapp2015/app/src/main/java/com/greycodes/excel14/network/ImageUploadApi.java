package com.greycodes.excel14.network;

import com.greycodes.excel14.pojo.GalleryImages;

import retrofit.Callback;
import retrofit.http.Multipart;
import retrofit.http.POST;
import retrofit.http.Part;

/**
 * Created by jincy on 9/8/15.
 */
public interface ImageUploadApi {
    @Multipart
    @POST("/up.php")
    void postUserImage(@Part("image") String image, Callback<GalleryImages> callback);
}
