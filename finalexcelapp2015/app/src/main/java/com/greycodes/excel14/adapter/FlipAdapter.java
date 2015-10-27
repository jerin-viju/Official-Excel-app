package com.greycodes.excel14.adapter;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.greycodes.excel14.R;
import com.greycodes.excel14.pojo.NewsFeedItem;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class FlipAdapter extends BaseAdapter  {
    public interface Callback{
        public void onPageRequested(int page);
    }
//    static class Item {
//        static long id = 0;
//        long mId;
//        public Item() {
//            mId = id++;
//        }
//        long getId(){
//            return mId;
//        }
//    }


    private LayoutInflater inflater;
    private Context context;
    private AssetManager assets;
    private Callback callback;
    private List<NewsFeedItem> newsFeedItems = new ArrayList<NewsFeedItem>();

    public FlipAdapter(Context context,ArrayList<NewsFeedItem> newsItems,AssetManager assets) {
        inflater = LayoutInflater.from(context);
        this.newsFeedItems = newsItems;
        this.context = context;
        this.assets = assets;
    }
    public void setCallback(Callback callback) {
        this.callback = callback;
    }
    @Override
    public int getCount() {
        return newsFeedItems.size();
    }
    @Override
    public Object getItem(int position) {
        return position;
    }
    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public boolean hasStableIds() {
        return true;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView == null){
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.page, parent, false);
            holder.title = (TextView) convertView.findViewById(R.id.title);
            holder.author = (TextView)convertView.findViewById(R.id.author);
            holder.description=(TextView) convertView.findViewById(R.id.description);
            holder.headerImage=(ImageView) convertView.findViewById(R.id.headerImage);

            Typeface type1 = Typeface.createFromAsset(assets,"fonts/Roboto-Regular.ttf");
            holder.title.setTypeface(type1);
            Typeface type2 = Typeface.createFromAsset(assets,"fonts/Roboto-Thin.ttf");
            holder.author.setTypeface(type2);
            holder.description.setTypeface(type2);

            holder.title.setText(newsFeedItems.get(position).getHeading());
            holder.author.setText(newsFeedItems.get(position).getAuthor());
            holder.description.setText(newsFeedItems.get(position).getDescription());
            Picasso.with(context).load(newsFeedItems.get(position).getImageurl()).fit().into(holder.headerImage);
//              holder.firstPage = (Button) convertView.findViewById(R.id.first_page);
//              holder.lastPage = (Button) convertView.findViewById(R.id.last_page);
//              holder.firstPage.setOnClickListener(this);
//              holder.lastPage.setOnClickListener(this);
              convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }

        return convertView;
    }
    static class ViewHolder{
        TextView title;
        TextView author;
        TextView description;
        ImageView headerImage;
    }
    /*  @Override
      public void onClick(View v) {
          switch(v.getId()){
              case R.id.first_page:
                  if(callback != null){
                      callback.onPageRequested(0);
                  }
                  break;
              case R.id.last_page:
                  if(callback != null){
                      callback.onPageRequested(getCount()-1);
                  }
                  break;
          }
      }*/

//    public void addItems(int amount) {
//        TextView text;
//        for(int i = 0 ; i<amount ; i++){
//            items.add(new Item());
//
//        }
//
//        notifyDataSetChanged();
//    }
//    public void addItemsBefore(int amount) {
//        for(int i = 0 ; i<amount ; i++){
//            items.add(0, new Item());
//        }
//        notifyDataSetChanged();
//    }
}