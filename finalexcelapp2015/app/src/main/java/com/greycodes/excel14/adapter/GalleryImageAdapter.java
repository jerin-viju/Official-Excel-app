package com.greycodes.excel14.adapter;

import android.content.Context;
import android.content.res.TypedArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;

import com.greycodes.excel14.R;
import com.greycodes.excel14.model.Model;
import com.squareup.picasso.Picasso;

/**
 * Created by jincy on 25/7/15.
 */
public class GalleryImageAdapter extends BaseAdapter {
    private Context context;
    private int itemBackground;
    private int galleryHeight;
    public GalleryImageAdapter(Context c, TypedArray typedArray,int height)
    {
        context = c;
        TypedArray a = typedArray;
        itemBackground = a.getResourceId(R.styleable.MyGallery_android_galleryItemBackground, 0);
        a.recycle();
        galleryHeight =height;
    }
    public int getCount() {
        return Model.getInstance().getImageUrls().size();
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView = new ImageView(context);
        Picasso.with(context).load(Model.getInstance().getImageUrls().get(position)).fit().into(imageView);
        imageView.setLayoutParams(new Gallery.LayoutParams(galleryHeight, galleryHeight));
        imageView.setPadding(5,5,5,5);
        imageView.setBackgroundResource(R.color.white);
        return imageView;
    }
}