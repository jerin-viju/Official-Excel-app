package com.greycodes.excel14.adapter;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.greycodes.excel14.R;
import com.greycodes.excel14.pojo.NavigationDrawerItem;

import java.util.Collections;
import java.util.List;

public class NavigationDrawerAdapter extends RecyclerView.Adapter<NavigationDrawerAdapter.NavigationItemViewHolder>
{
    private List<NavigationDrawerItem> itemList = Collections.emptyList();

    private final LayoutInflater inflater;
    private AssetManager assets;

    public NavigationDrawerAdapter(Context context, List<NavigationDrawerItem> itemList, AssetManager assets)
    {
        inflater = LayoutInflater.from(context);
        this.itemList = itemList;
        this.assets = assets;
    }

    @Override
    public NavigationItemViewHolder onCreateViewHolder(ViewGroup viewGroup, int position)
    {
        View view;

        view = inflater.inflate(R.layout.navigation_drawer_row, viewGroup, false);


        return new NavigationItemViewHolder(view);
    }



    @Override
    public void onBindViewHolder(NavigationItemViewHolder myViewHolder, int position)
    {
        NavigationDrawerItem current = itemList.get(position);
        myViewHolder.title.setText(current.getTitle());
        myViewHolder.imageView.setImageResource(current.getImageID());
        //myViewHolder.sectionDivider.setBackgroundResource(current.getSectionDividerColor());
    }

    @Override
    public int getItemCount()
    {
        return itemList.size();
    }

    class NavigationItemViewHolder extends RecyclerView.ViewHolder
    {
        final TextView title;
        final ImageView imageView;
        //final TextView sectionDivider;

        public NavigationItemViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title);
            Typeface type = Typeface.createFromAsset(assets, "fonts/Roboto-Light.ttf");
            title.setTypeface(type);
            imageView = (ImageView) itemView.findViewById(R.id.drawer_image);
            //sectionDivider = (TextView) itemView.findViewById(R.id.drawer_section_divider);
        }
    }
}
