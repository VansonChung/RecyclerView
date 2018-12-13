package com.van.recyclerview.custom.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.van.recyclerview.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LinearDelAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<String> mList;

    private int mCurrentPos;

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new LinearDelAdapter.ItemViewHolder(LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_v_text_del, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, int position) {
        ((ItemViewHolder) holder).mTv.setText(mList.get(position));
    }

    @Override
    public int getItemCount() {
        if (mList == null)
            return 0;
        return mList.size();
    }

    public void setData(List<String> data) {
        mList = new ArrayList<>(data);
        notifyDataSetChanged();
    }

    public void onItemMove(int i, int j) {
        Collections.swap(mList, i, j);
        if (mCurrentPos == j) {
            mCurrentPos = i;
        } else if (mCurrentPos == i) {
            mCurrentPos = j;
        }
        notifyItemMoved(i, j);
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {

        public LinearLayout mLinearLayout;

        private TextView mTv;

        ItemViewHolder(View v) {
            super(v);
            mLinearLayout = v.findViewById(R.id.view_item);
            mTv = v.findViewById(R.id.tv_title);
        }
    }
}
