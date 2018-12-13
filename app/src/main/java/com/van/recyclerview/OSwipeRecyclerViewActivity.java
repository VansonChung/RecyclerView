package com.van.recyclerview;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.van.recyclerview.custom.adapter.LinearAdapter;
import com.van.recyclerview.custom.unknow.ItemDecoration;
import com.van.recyclerview.custom.unknow.ItemTouchCallBack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class OSwipeRecyclerViewActivity extends AppCompatActivity {

    private static final String[] WORDS = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K",
            "L"};

    private List<String> mList = new ArrayList<>();

    ItemTouchHelper mItemTouchHelper1, mItemTouchHelper2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swipe_one);
        mList.addAll(Arrays.asList(WORDS));
        setUpOne();
        setUpTwo();
    }

    private void setUpOne() {
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        final LinearAdapter adapter = new LinearAdapter();
        adapter.setData(mList);
        adapter.setQuickDrag(true);
        adapter.setDragListener(new LinearAdapter.DragListener() {
            @Override
            public void onStartDrag(RecyclerView.ViewHolder holder) {
                mItemTouchHelper1.startDrag(holder);
            }
        });
        adapter.setItemLayout(R.layout.item_v_rectangle_text);
        recyclerView.setAdapter(adapter);
        ItemDecoration itemDecoration = new ItemDecoration(this, LinearLayoutManager.VERTICAL);
        itemDecoration.setDivideDrawable(null);
        itemDecoration.setItemOffsets(0, 40, 0, 40);
        recyclerView.addItemDecoration(itemDecoration);

        ItemTouchCallBack itemTouchCallBack = new ItemTouchCallBack();
        itemTouchCallBack.setAnimation(true);
        itemTouchCallBack.setMoveListener(new ItemTouchCallBack.OnMoveListener() {
            @Override
            public void onItemMove(int position, int target) {
                adapter.onItemMove(position, target);
            }
        });
        itemTouchCallBack.setSwipeListener(new ItemTouchCallBack.OnSwipedListener() {
            @Override
            public void onItemSwiped(RecyclerView.ViewHolder holder, int position) {
                adapter.onItemSwiped(holder, position);
            }
        });
        mItemTouchHelper1 = new ItemTouchHelper(itemTouchCallBack);
        mItemTouchHelper1.attachToRecyclerView(recyclerView);
    }

    private void setUpTwo() {
        RecyclerView recyclerView = findViewById(R.id.recyclerView2);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        final LinearAdapter adapter = new LinearAdapter();
        adapter.setData(mList);
        adapter.setQuickDrag(false);
        adapter.setItemLayout(R.layout.item_v_rectangle_text);
        recyclerView.setAdapter(adapter);
        ItemDecoration itemDecoration = new ItemDecoration(this, LinearLayoutManager.VERTICAL);
        itemDecoration.setDivideDrawable(null);
        itemDecoration.setItemOffsets(0, 40, 0, 40);
        recyclerView.addItemDecoration(itemDecoration);

        ItemTouchCallBack itemTouchCallBack = new ItemTouchCallBack();
        itemTouchCallBack.setAnimation(true);
        itemTouchCallBack.setMoveListener(new ItemTouchCallBack.OnMoveListener() {
            @Override
            public void onItemMove(int position, int target) {
                adapter.onItemMove(position, target);
            }
        });
        itemTouchCallBack.setSwipeListener(new ItemTouchCallBack.OnSwipedListener() {
            @Override
            public void onItemSwiped(RecyclerView.ViewHolder holder, int position) {
                adapter.onItemSwiped(holder, position);
            }
        });

        mItemTouchHelper2 = new ItemTouchHelper(itemTouchCallBack);
        mItemTouchHelper2.attachToRecyclerView(recyclerView);
    }
}
