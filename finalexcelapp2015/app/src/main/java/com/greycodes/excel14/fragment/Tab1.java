package com.greycodes.excel14.fragment;

/**
 * Created by jerin on 23/7/15.
 */
import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.greycodes.excel14.R;
import com.greycodes.excel14.model.Model;
import com.greycodes.excel14.pojo.DaySchedule;


public class Tab1 extends Fragment {

    public TextView date;
    public TextView day;
    public ImageView left;
    public ImageView right;

    View mcontainerview;
    ObjectAnimator fade;
    int height_image;

    private DaySchedule daySchedule;
    ListView parentList,childList;
    View headerView;

    Resources r;
    int ps;
    int pos;

    public  void getinstance(int position){
        pos=position;
        this.daySchedule = Model.getInstance().getDayScheduleCategories().get(position);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.tab_1,container,false);
        setUpViews(view);
        setUpHeader();
        populate();
        return view;

    }

    private void setUpViews(View view) {
        parentList=(ListView)view.findViewById(R.id.listView);
        headerView=LayoutInflater.from(getActivity()).inflate(R.layout.schedule_header,parentList,false);
        date=(TextView) headerView.findViewById(R.id.date);
        day=(TextView)headerView.findViewById(R.id.day);
        left=(ImageView)headerView.findViewById(R.id.left);
        right=(ImageView)headerView.findViewById(R.id.right);
    }


    private void setUpHeader() {
        mcontainerview=headerView.findViewById(R.id.lin);
        parentList.addHeaderView(headerView);

        fade = ObjectAnimator.ofFloat(mcontainerview, "alpha", 0f, 1f);
        fade.setInterpolator(new DecelerateInterpolator());
        fade.setDuration(400);
        parentList.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (view != null && view.getChildCount() > 0 && firstVisibleItem == 0) {
                    if (view.getChildAt(0).getTop() < -dpToPx(100)) {
                        toggleHeader(false, false);
                    } else {
                        toggleHeader(true, true);
                    }
                } else {
                    toggleHeader(false, false);
                }
            }
        });
        r=getResources();
        float px= TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 250, r.getDisplayMetrics());
        height_image=Math.round(px);

        date.setText(daySchedule.getDate());
        day.setText(daySchedule.getDay());
        switch (pos) {
            case 0:
                right.setImageResource(R.drawable.keyboard53);
                break;
            case 1:
                left.setImageResource(R.drawable.left216);
                right.setImageResource(R.drawable.keyboard53);
                break;
            case 2:
                left.setImageResource(R.drawable.left216);
                break;
        }
    }


    private void toggleHeader(boolean visible, boolean force) {
        if ((force && visible) || (visible && mcontainerview.getAlpha() == 0f)) {
            fade.setFloatValues(mcontainerview.getAlpha(), 1f);
            fade.start();
        } else if (force || (!visible && mcontainerview.getAlpha() == 1f)){
            fade.setFloatValues(mcontainerview.getAlpha(), 0f);
            fade.start();
        }

    }
    public int dpToPx(int dp) {
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        return (int)(dp * (displayMetrics.densityDpi / 160f));
    }

    public static boolean isLollipop() {
        return android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;
    }

    public void populate(){
        String[] time = new String[daySchedule.getTimingsArrayList().size()];
        for(int i=0;i<daySchedule.getTimingsArrayList().size();i++) {
            time[i] = daySchedule.getTimingsArrayList().get(i).getTime();
        }
        ListAdapter adapter=new listviewbaseadapter(getActivity(),time);

        r=getResources();
        float px= TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 40, r.getDisplayMetrics());
        ps=Math.round(px);
        ViewGroup.LayoutParams params=parentList.getLayoutParams();
        params.height = height_image;
        for(int i=0;i<daySchedule.getTimingsArrayList().size();i++) {
            params.height+=ps*daySchedule.getTimingsArrayList().get(i).getScheduleEvents().size();
        }
        parentList.setAdapter(adapter);
    }

    public void populate2(View customview,final int parentPosition){
        String[] competitionNames = new String[daySchedule.getTimingsArrayList().get(parentPosition).getScheduleEvents().size()];
        for (int i=0;i<daySchedule.getTimingsArrayList().get(parentPosition).getScheduleEvents().size();i++) {
            competitionNames[i] = daySchedule.getTimingsArrayList().get(parentPosition).getScheduleEvents().get(i).getTitle();
        }
        ListAdapter adapter2=new listadapter2(getActivity(),competitionNames,parentPosition);
        childList=(ListView)customview.findViewById(R.id.listView2);

        ViewGroup.LayoutParams params=childList.getLayoutParams();
        params.height=ps*competitionNames.length;
        childList.setAdapter(adapter2);
    }

    public class listviewbaseadapter extends ArrayAdapter<String>{

        public listviewbaseadapter(Context context, String[] resorces) {
            super(context,R.layout.test ,resorces);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater =LayoutInflater.from(getContext());
            View customview=inflater.inflate(R.layout.test, parent, false);
            populate2(customview,position);
            String single=getItem(position);
            TextView text=(TextView)customview.findViewById(R.id.time);
            text.setText(single);
            return customview;

        }
    }

    public class listadapter2 extends ArrayAdapter<String> {
        private int parentPosition;

        public listadapter2(Context context, String[] resource, int parentPosition) {
            super(context, R.layout.second_list, resource);
            this.parentPosition = parentPosition;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            LayoutInflater inflate = LayoutInflater.from(getContext());
            View customview2 = inflate.inflate(R.layout.second_list, parent, false);
            String second = getItem(position);
            TextView text = (TextView) customview2.findViewById(R.id.text2);
            final ImageView imageView = (ImageView) customview2.findViewById(R.id.goingImageView);
            RelativeLayout layout = (RelativeLayout) customview2.findViewById(R.id.rowLayout);
            text.setText(second);
            if (daySchedule.getTimingsArrayList().get(parentPosition).getScheduleEvents().get(position).getGoing()) {
                imageView.setImageResource(R.drawable.left216);
            } else {
                imageView.setImageResource(R.drawable.banner);
            }

            layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View view) {
                    MyAlert myAlert = new MyAlert();
                    myAlert.setButtonClickListener(new MyAlert.ButtonClickListener() {
                        @Override
                        public void onClick(int option) {
                            switch(option)
                            {
                                case 0 :
                                    if(!Model.getInstance().getDayScheduleCategories().get(pos).getTimingsArrayList().get(parentPosition).getScheduleEvents().get(position).getGoing()) {
                                        Model.getInstance().isScheduleChanged = true;
                                    }
                                    Model.getInstance().getDayScheduleCategories().get(pos).getTimingsArrayList().get(parentPosition).getScheduleEvents().get(position).setGoing(true);
                                    imageView.setImageResource(R.drawable.left216);
                                    break;
                                case 1 :
                                    if(Model.getInstance().getDayScheduleCategories().get(pos).getTimingsArrayList().get(parentPosition).getScheduleEvents().get(position).getGoing()) {
                                        Model.getInstance().isScheduleChanged = true;
                                    }
                                    Model.getInstance().getDayScheduleCategories().get(pos).getTimingsArrayList().get(parentPosition).getScheduleEvents().get(position).setGoing(false);
                                    imageView.setImageResource(R.drawable.banner);
                            }
                        }
                    });
                    myAlert.show(getActivity().getFragmentManager(), "A");
                }
            });
            return customview2;
        }
    }

}