package com.van.recyclerview;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.van.recyclerview.custom.adapter.LinearAdapter;
import com.van.recyclerview.custom.unknow.ItemDecoration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FlexibleActivity extends AppCompatActivity {

    private static final String[] SPORTS = {"BASKETBALL", "BASEBALL", "SOCCER", "VOLLEYBALL",
            "FOOTBALL", "BILLIARD"};

    private List<String> mList = new ArrayList<>();

    // Horizontal 使用最簡便的效果似乎比 'me.everything:overscroll-decor-android:1.0.4' 好
    // Vertical 無法使用簡便...

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flexible);
        mList.addAll(Arrays.asList(SPORTS));
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(layoutManager);
        LinearAdapter adapter = new LinearAdapter();
        adapter.setData(mList);
        adapter.setItemLayout(R.layout.item_h_rectangle_text);
        recyclerView.setAdapter(adapter);
        ItemDecoration itemDecoration = new ItemDecoration(this, LinearLayoutManager.HORIZONTAL);
        itemDecoration.setDivideDrawable(null);
        itemDecoration.setItemOffsets(40, 60, 40, 60);
        recyclerView.addItemDecoration(itemDecoration);
    }
}
