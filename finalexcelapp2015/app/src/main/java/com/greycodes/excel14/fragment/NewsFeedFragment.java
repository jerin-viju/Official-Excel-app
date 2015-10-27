package com.greycodes.excel14.fragment;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import com.greycodes.excel14.R;
import com.greycodes.excel14.adapter.FlipAdapter;
import com.greycodes.excel14.network.NewsFeedApi;
import com.greycodes.excel14.pojo.NewsFeedItem;
import com.greycodes.excel14.listener.BackButtonClickListener;

import java.util.ArrayList;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import se.emilsjolander.flipview.FlipView;
import se.emilsjolander.flipview.OverFlipMode;


@SuppressLint("ValidFragment")
public class NewsFeedFragment extends Fragment implements FlipAdapter.Callback, FlipView.OnFlipListener, FlipView.OnOverFlipListener {
    private BackButtonClickListener listener;

    private FlipView mFlipView;
    private FlipAdapter mAdapter;
    int i=0;


    @SuppressLint("ValidFragment")
    public NewsFeedFragment(BackButtonClickListener listener) {
        this.listener = listener;
    }

    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
         view = inflater.inflate(R.layout.fragment_news_feed, container, false);
        setBackButtonOverriding(view);
        setUpViews(view);
        setUpNewsApi();
        return view;
    }

    private void setBackButtonOverriding(View view) {
        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
                    listener.BackButtonClicked(0);
//                    getFragmentManager().popBackStack();
//                    getFragmentManager().popBackStack("home", FragmentManager.POP_BACK_STACK_INCLUSIVE);
                    return true;
                } else {
                    return false;
                }
            }
        });
    }


    private void setUpViews(View view) {
        mFlipView = (FlipView) view.findViewById(R.id.flip_view);
        mFlipView.setEmptyView(view.findViewById(R.id.empty_view));

    }


    private void setUpNewsApi() {
        final RestAdapter restadapter = new RestAdapter.Builder().setEndpoint("http://balumenon.net63.net/app/").build();

        NewsFeedApi newsFeedApi = restadapter.create(NewsFeedApi.class);
        newsFeedApi.getData(new Callback<ArrayList<NewsFeedItem>>() {
            @Override
            public void success(ArrayList<NewsFeedItem> newsFeedItems, Response response) {
                setUpNewsAdapter(newsFeedItems);
            }

            @Override
            public void failure(RetrofitError error) {
                Toast.makeText(getActivity().getApplicationContext(), "Failed to connect to internet", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setUpNewsAdapter(ArrayList<NewsFeedItem> newsFeedItems) {
        mAdapter = new FlipAdapter(getActivity(),newsFeedItems,getActivity().getAssets());
        mAdapter.setCallback(this);
        mFlipView.setAdapter(mAdapter);
        mFlipView.setOnFlipListener(this);
        mFlipView.peakNext(false);
        mFlipView.setOverFlipMode(OverFlipMode.RUBBER_BAND);
        mFlipView.setOnOverFlipListener(this);
    }

    @Override
    public void onPageRequested(int page) {
        mFlipView.smoothFlipTo(page);
    }
    @Override
    public void onFlippedToPage(FlipView v, int position, long id) {
        Log.i("pageflip", "Page: " + position);
    }

    @Override
    public void onOverFlip(FlipView v, OverFlipMode mode,
                           boolean overFlippingPrevious, float overFlipDistance,
                           float flipDistancePerPage) {
        Log.i("overflip", "overFlipDistance = " + overFlipDistance);
    }
}
