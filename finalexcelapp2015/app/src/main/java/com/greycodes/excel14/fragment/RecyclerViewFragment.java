package com.greycodes.excel14.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.greycodes.excel14.R;
import com.greycodes.excel14.activity.SingleCompetitionActivity;
import com.greycodes.excel14.adapter.GridAdapter;
import com.greycodes.excel14.adapter.TestRecyclerViewAdapter;
import com.greycodes.excel14.enums.FragmentType;
import com.greycodes.excel14.pojo.CommonCategoryItem;
import com.github.florent37.materialviewpager.MaterialViewPagerHelper;
import com.github.florent37.materialviewpager.adapter.RecyclerViewMaterialAdapter;

import java.util.ArrayList;

public class RecyclerViewFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager mLayoutManager;

    Context context;
    int ITEM_COUNT ;
    FragmentType fragmentType;
    private int parentPosition;

    public ArrayList<CommonCategoryItem> mContentItems = new ArrayList<>();

    public static RecyclerViewFragment newInstance() {
        return new RecyclerViewFragment();
    }

    public void setItems(Context con,ArrayList<CommonCategoryItem> list,FragmentType fragmentType,int parentPosition)
    {
        ITEM_COUNT = list.size();
        mContentItems = list;
        context = con;
        this.fragmentType = fragmentType;
        this.parentPosition = parentPosition;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_recyclerview, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Calling the RecyclerView
        mRecyclerView = (RecyclerView)view.findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);


        switch (fragmentType) {
            case CompetitionsFragmentType:
                mLayoutManager = new GridLayoutManager(context, 2);
                mRecyclerView.setLayoutManager(mLayoutManager);
                GridAdapter gridAdapter = new GridAdapter(context, mContentItems,getActivity().getAssets(),parentPosition);
                mAdapter = new RecyclerViewMaterialAdapter(gridAdapter);
                mRecyclerView.setAdapter(mAdapter);
                gridAdapter.setOnItemClickListener(new GridAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {

                        int pos = position - 2;
                        Intent intent = new Intent(getActivity(), SingleCompetitionActivity.class);
                        intent.putExtra("parentPosition",parentPosition);
                        intent.putExtra("clickedPosition",pos);
                        startActivity(intent);
                    }
                });

                break;
            default:
                mLayoutManager = new LinearLayoutManager(getActivity());
                mRecyclerView.setLayoutManager(mLayoutManager);
                mAdapter = new RecyclerViewMaterialAdapter(new TestRecyclerViewAdapter(mContentItems,fragmentType,context,getActivity().getAssets(),parentPosition));
                mRecyclerView.setAdapter(mAdapter);
                break;
        }
        MaterialViewPagerHelper.registerRecyclerView(getActivity(), mRecyclerView, null);
    }
}