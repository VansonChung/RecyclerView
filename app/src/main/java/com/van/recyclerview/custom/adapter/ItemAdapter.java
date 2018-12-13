package com.van.recyclerview.custom.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.van.recyclerview.R;
import com.van.recyclerview.custom.view.ItemView;

public class ItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int NORMAL = 1, OFFSET = 2, RECTANGLE = 3;

    private int[] TYPE_LINEAR = {NORMAL, OFFSET, RECTANGLE};

    private static final int GRID_V1 = 1, GRID_V2 = 2, GRID_H1 = 3, GRID_H2 = 4;

    private int[] TYPE_GRID = {GRID_V1, GRID_V2, GRID_H1, GRID_H2};

    private boolean mGrid;

    private int mOrientation;

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int
            viewType) {
//        if (viewType == xxx) {
//            return new xxxHolder(...);
//        } else if (viewType == yyy) {
//            return new yyyHolder(...);
//        }
        return new ItemViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_view, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, int position) {
        int type = getItemViewType(position);
//        if (type == xxx) {
//            ((xxxHolder) holder).func();
//        } else if (type == yyy) {
//            ((yyyHolder) holder).func();
//        }
        ((ItemViewHolder) holder).mView.setType(mGrid, mOrientation, type);
    }

    @Override
    public int getItemCount() {
        if (mGrid) {
            return TYPE_GRID.length;
        } else {
            return TYPE_LINEAR.length;
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (mGrid && position < TYPE_GRID.length) {
            return TYPE_GRID[position];
        } else if (position < TYPE_LINEAR.length) {
            return TYPE_LINEAR[position];
        }
        return super.getItemViewType(position);
    }

    public void setGrid(boolean grid) {
        mGrid = grid;
    }

    public void setOrientation(int orientation) {
        mOrientation = orientation;
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {

        ItemView mView;

        ItemViewHolder(View v) {
            super(v);
            mView = v.findViewById(R.id.itemView);
        }
    }
}
