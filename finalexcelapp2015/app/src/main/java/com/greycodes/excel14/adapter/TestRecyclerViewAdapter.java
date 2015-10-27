package com.greycodes.excel14.adapter;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.net.Uri;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import com.greycodes.excel14.R;
import com.greycodes.excel14.enums.FragmentType;
import com.greycodes.excel14.helper.ImageHelper;
import com.greycodes.excel14.model.Model;
import com.greycodes.excel14.pojo.CommonCategoryItem;

import java.util.ArrayList;

public class TestRecyclerViewAdapter extends RecyclerView.Adapter<TestRecyclerViewAdapter.ViewHolder> {

    ArrayList<CommonCategoryItem> contents;
    FragmentType fragmentType;
   static Context context;
    public AssetManager assets;

    int parentPosition=0;

    public TestRecyclerViewAdapter(ArrayList<CommonCategoryItem> contents,FragmentType fragmentType,Context context,AssetManager assets,int parentPosition) {
        this.contents = contents;
        this.fragmentType = fragmentType;
        this.context = context;
        this.assets = assets;
        this.parentPosition = parentPosition;
    }

    @Override
    public int getItemCount() {
        return contents.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view  = null;
        try {

            switch (fragmentType) {
                case InfoFragmentType:
                    view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_info_card, parent, false);
                    return new ViewHolder(view, fragmentType, assets) {
                    };

                default:
                    view = LayoutInflater.from(parent.getContext())
                            .inflate(R.layout.list_item_card_big, parent, false);
                    return new ViewHolder(view, fragmentType, assets) {
                    };
            }
        }catch (Exception e) {

        }
        return null;
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        try {
            CommonCategoryItem item = contents.get(position);
            holder.title.setText(item.getTitle());

            holder.description.setText(item.getDescription());
            if(fragmentType == FragmentType.ExcelProFragmentType){
                holder.btn.setText("Go to WebPage");
                holder.btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String link = "http://excelmec.org/excel2015/";
                        if (link.equals("") || link.equals(" ")) {

                        } else {
                            Intent internetIntent = new Intent(Intent.ACTION_VIEW,
                                    Uri.parse(link));
                            internetIntent.setComponent(new ComponentName("com.android.browser", "com.android.browser.BrowserActivity"));
                            internetIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            context.startActivity(internetIntent);
                        }

                    }
                });
            }
            if (fragmentType == FragmentType.InfoFragmentType) {
                int j = Model.getInstance().getInfoCategories().get(parentPosition).getList().size();
                if(position>=j)
                {
                    holder.infocardView.setVisibility(View.INVISIBLE);
                }
                else {
                    holder.infocardView.setVisibility(View.VISIBLE);
                    int resId = ImageHelper.getResourceId(context, item.getImage());
                    if (resId != 0) {
                        holder.imageView.setImageResource(resId);
                    }
                }
            }
        }catch (Exception e) {

        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        protected TextView title;
        protected TextView description;
        protected ImageView imageView;
        protected CardView infocardView;
        protected  Button btn;

        public ViewHolder(View itemView,FragmentType fragmentType,AssetManager assets) {
            super(itemView);
            title = (TextView)itemView.findViewById(R.id.bigTitle);
            description = (TextView)itemView.findViewById(R.id.bigDescription);


                btn = (Button) itemView.findViewById((R.id.button2));
            if(fragmentType==FragmentType.  ProShowFragmentType) {
                btn.setText("vjdb");
                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String link = "http://excelmec.org/excel2015/";
                        if (link.equals("") || link.equals(" ")) {

                        } else {
                            Uri uri = Uri.parse(link); // missing 'http://' will cause crashed
                            Intent intent = new Intent(Intent.ACTION_VIEW, uri);

                           // Intent internetIntent = new Intent(Intent.ACTION_VIEW,
                            //        Uri.parse(link));
                           // internetIntent.setComponent(new ComponentName("com.android.browser", "com.android.browser.BrowserActivity"));
                           // internetIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            context.startActivity(intent);
                        }


                    }
                });
            }
                    //     Typeface type = Typeface.createFromAsset(assets,"fonts/Roboto-Regular.ttf");
            //     title.setTypeface(type);
            //      description.setTypeface(type);
            if(fragmentType==FragmentType.InfoFragmentType)
                infocardView = (CardView)itemView.findViewById(R.id.infoCardView);
                imageView = (ImageView)itemView.findViewById(R.id.img_thumbnail);
        }
    }
}