package com.van.recyclerview.custom.view;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.widget.TextView;

import com.van.recyclerview.R;
import com.van.recyclerview.custom.adapter.GridAdapter;
import com.van.recyclerview.custom.adapter.LinearAdapter;
import com.van.recyclerview.custom.unknow.ItemDecoration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import me.everything.android.ui.overscroll.OverScrollDecoratorHelper;

public class ItemView extends ConstraintLayout {

    private static final String[] SPORTS = {"BASKETBALL", "BASEBALL", "SOCCER", "VOLLEYBALL",
            "FOOTBALL", "BILLIARD"};

    private static final int NORMAL = 1, OFFSET = 2, RECTANGLE = 3;

    private static final int GRID_V1 = 1, GRID_V2 = 2, GRID_H1 = 3, GRID_H2 = 4;

    private List<String> mList = new ArrayList<>();

    private Context mContext;

    private TextView mTv;

    private RecyclerView mRecyclerView;

    public ItemView(Context context) {
        super(context);
        mContext = context;
        init();
    }

    public ItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init();
    }

    public ItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        init();
    }

    public void setType(boolean grid, int orientation, int type) {
        if (grid) {
            gridProcess(type);
        } else {
            linearProcess(orientation, type);
        }
    }

    private void init() {
        // 使用 android.support.constraint.ConstraintLayout 會有問題 ...
        inflate(mContext, R.layout.layout_item_view, this);
        mTv = findViewById(R.id.textView);
        mRecyclerView = findViewById(R.id.recyclerView);
        mList.addAll(Arrays.asList(SPORTS));
    }

    private void linearProcess(int orientation, int type) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        layoutManager.setOrientation(orientation);
        mRecyclerView.setLayoutManager(layoutManager);
        LinearAdapter adapter = new LinearAdapter();
        adapter.setData(mList);
        ItemDecoration itemDecoration = new ItemDecoration(mContext, orientation);
        switch (type) {
            case NORMAL:
                mTv.setText("Normal");
                adapter.setItemLayout(orientation == LinearLayoutManager.VERTICAL ? R.layout
                        .item_v_text : R.layout.item_h_text);
                break;
            case OFFSET:
                mTv.setText("Offset");
                adapter.setItemLayout(orientation == LinearLayoutManager.VERTICAL ? R.layout
                        .item_v_text : R.layout.item_h_text);
                itemDecoration.setDivideDrawable(new ColorDrawable(0xFF007BCA));
                if (orientation == LinearLayoutManager.VERTICAL) {
                    itemDecoration.setDividerHeight(8);
                } else {
                    itemDecoration.setDividerWidth(8);
                }
                // Vertical : item 左右擠壓, 外部上下延展 (非 item 本身).
                // Horizontal : item 外部上下左右延展 (非 item 本身).
                itemDecoration.setItemOffsets(200, 100, 50, 50);
                itemDecoration.setDividePadding(40, 100);
                break;
            case RECTANGLE:
                mTv.setText("Rectangle");
                adapter.setItemLayout(orientation == LinearLayoutManager.VERTICAL ? R.layout
                        .item_v_rectangle_text : R.layout.item_h_rectangle_text);
                itemDecoration.setDivideDrawable(null);
                if (orientation == LinearLayoutManager.VERTICAL) {
                    // item 左右全屏, 外部上下延展.
                    itemDecoration.setItemOffsets(0, 50, 0, 50);
                } else {
                    // item 外部上下左右延展
                    itemDecoration.setItemOffsets(16, 24, 16, 24);
                }
                break;
        }
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.addItemDecoration(itemDecoration);
        if (orientation == LinearLayoutManager.HORIZONTAL) {
            OverScrollDecoratorHelper.setUpOverScroll(mRecyclerView, OverScrollDecoratorHelper
                    .ORIENTATION_HORIZONTAL);
        }
    }

    private void gridProcess(int type) {
        GridLayoutManager layoutManager;
        GridAdapter adapter = new GridAdapter();
        ItemDecoration itemDecoration;
        if (type == GRID_V1 || type == GRID_V2) {
            layoutManager = new GridLayoutManager(mContext, 6);
            adapter.setLayout(R.layout.item_v_grid);
            itemDecoration = new ItemDecoration(mContext, LinearLayoutManager.VERTICAL);
        } else {
            layoutManager = new GridLayoutManager(mContext, 3);
            layoutManager.setOrientation(GridLayoutManager.HORIZONTAL);
            adapter.setLayout(R.layout.item_h_grid);
            itemDecoration = new ItemDecoration(mContext, LinearLayoutManager.HORIZONTAL);
        }
        if (type == GRID_V2 || type == GRID_H2) {
            // Vertical : item 左右擠壓, 外部上下延展.
            // Horizontal : item 外部上下左右延展.
            itemDecoration.setItemOffsets(40, 40, 40, 40);
        }
        itemDecoration.setDivideDrawable(null);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.addItemDecoration(itemDecoration);
    }
}
