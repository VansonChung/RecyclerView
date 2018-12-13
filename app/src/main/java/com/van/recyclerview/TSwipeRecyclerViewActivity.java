package com.van.recyclerview;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.van.recyclerview.custom.adapter.LinearDelAdapter;
import com.van.recyclerview.custom.unknow.ItemDecoration;
import com.van.recyclerview.custom.unknow.ItemTouchCallBack;
import com.van.recyclerview.custom.view.SwipeRecyclerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TSwipeRecyclerViewActivity extends AppCompatActivity {

    private static final String[] WORDS = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K",
            "L"};

    private static final String[] SPORTS = {"BASKETBALL", "BASEBALL", "SOCCER", "VOLLEYBALL",
            "FOOTBALL", "BILLIARD"};

    private Handler mHandler = new Handler();

    private boolean mFlag;

    private List<String> mList = new ArrayList<>();

    private LinearDelAdapter mAdapter;

    ItemTouchHelper mItemTouchHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swipe_two);
        mList.addAll(Arrays.asList(WORDS));
        setUpOne();
    }

    private void setUpOne() {
        SwipeRecyclerView swipeRecyclerView = findViewById(R.id.recyclerView);
        swipeRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        swipeRecyclerView.setDeleteListener(new SwipeRecyclerView.OnDeleteListener() {

            @Override
            public void onDelete(int position) {

            }
        });
        swipeRecyclerView.setLoadMoreListener(new SwipeRecyclerView.OnLoadMoreListener() {
            @Override
            public void onLoadMore(int total) {
                // 需另起線程.
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mList.addAll(Arrays.asList(SPORTS));
                        mAdapter.setData(mList);
                    }
                }, 500);
            }
        });
        mAdapter = new LinearDelAdapter();
        mAdapter.setData(mList);
        swipeRecyclerView.setAdapter(mAdapter);
        ItemDecoration itemDecoration = new ItemDecoration(this, LinearLayoutManager.VERTICAL);
        swipeRecyclerView.addItemDecoration(itemDecoration);

        ItemTouchCallBack itemTouchCallBack = new ItemTouchCallBack();
        itemTouchCallBack.setAnimation(true);
        itemTouchCallBack.setMoveListener(new ItemTouchCallBack.OnMoveListener() {

            @Override
            public void onItemMove(int position, int target) {
                mAdapter.onItemMove(position, target);
            }
        });

        mItemTouchHelper = new ItemTouchHelper(itemTouchCallBack);
        mItemTouchHelper.attachToRecyclerView(swipeRecyclerView);

        final SwipeRefreshLayout swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mList.clear();
                if (mFlag) {
                    mList.addAll(Arrays.asList(WORDS));
                } else {
                    mList.addAll(Arrays.asList(SPORTS));
                }
                mAdapter.setData(mList);
                mFlag = !mFlag;
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }
}
