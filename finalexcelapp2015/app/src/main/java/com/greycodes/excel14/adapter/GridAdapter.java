package com.greycodes.excel14.adapter;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Point;
import android.graphics.Typeface;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;


import com.greycodes.excel14.R;
import com.greycodes.excel14.helper.ImageHelper;
import com.greycodes.excel14.model.Model;
import com.greycodes.excel14.pojo.CommonCategoryItem;

import java.util.ArrayList;
import java.util.List;


public class GridAdapter  extends RecyclerView.Adapter<GridAdapter.ViewHolder> {

    List<CommonCategoryItem> mItems;
    Context context;
    int parentPosition;
    private OnItemClickListener listener;
    private AssetManager assets;

    public interface OnItemClickListener {
        void onItemClick(View itemView, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public GridAdapter(Context context,ArrayList<CommonCategoryItem> list,AssetManager assets,int parentPosition) {
        super();
        mItems = new ArrayList<>();
        mItems = list;
        this.context = context;
        this.assets = assets;
        this.parentPosition = parentPosition;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        final View view= LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.list_item_card_small, viewGroup, false);
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        int width=display.getWidth();
        CardView card=(CardView)view.findViewById(R.id.smallCardView);
        ImageView image=(ImageView)view.findViewById(R.id.img_thumbnail);
        ViewGroup.LayoutParams params = card.getLayoutParams();
        params.width = (width/2)-16;
        card.setLayoutParams(params);
        ViewGroup.LayoutParams params1 = image.getLayoutParams();
        params.width = (width/2)-16;
        image.setLayoutParams(params1);

        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        switch (i) {
            case 0:viewHolder.cardView.setVisibility(View.GONE);
                break;
            default:
                int j=Model.getInstance().getCompetitionCategories().get(parentPosition).getEvents().size();
                if(i> j) {
                    viewHolder.cardView.setVisibility(View.INVISIBLE);
                    break;
                }
                else {
                    viewHolder.cardView.setVisibility(View.VISIBLE);
                    CommonCategoryItem item = mItems.get(i);
                    viewHolder.title.setText(item.getTitle());
                    int resId = ImageHelper.getResourceId(context, mItems.get(i).getImage());
                    if (resId != 0)

                    {
                        viewHolder.imageView.setImageResource(resId);
                    }
                }

        }
    }

    @Override
    public int getItemCount() {

        return mItems.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{

        public ImageView imageView;
        public TextView title;
        public CardView cardView;

        public ViewHolder(final View itemView) {
            super(itemView);
            imageView = (ImageView)itemView.findViewById(R.id.img_thumbnail);
            title = (TextView)itemView.findViewById(R.id.title);
            Typeface type = Typeface.createFromAsset(assets,"fonts/Roboto-Regular.ttf");
            title.setTypeface(type);
            cardView = (CardView)itemView.findViewById(R.id.smallCardView);
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Triggers click upwards to the adapter on click
                    if(listener!=null & getLayoutPosition()!=0)
                        listener.onItemClick(itemView, getLayoutPosition());
                }
            });
        }

    }

}