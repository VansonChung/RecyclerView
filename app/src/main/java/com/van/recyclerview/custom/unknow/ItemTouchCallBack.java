package com.van.recyclerview.custom.unknow;

import android.graphics.Canvas;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

public class ItemTouchCallBack extends ItemTouchHelper.Callback {

    private boolean mAnimation;

    private OnMoveListener mMoveListener;

    private OnSwipedListener mSwipeListener;

    public interface OnMoveListener {
        void onItemMove(int position, int target);
    }

    public interface OnSwipedListener {
        void onItemSwiped(RecyclerView.ViewHolder holder, int position);
    }

    @Override
    public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView
            .ViewHolder viewHolder) {
        return makeMovementFlags(mMoveListener == null ? 0 : ItemTouchHelper.UP | ItemTouchHelper
                .DOWN, mSwipeListener == null ? 0 : ItemTouchHelper.LEFT | ItemTouchHelper.END);
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView
            .ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        mMoveListener.onItemMove(viewHolder.getAdapterPosition(), target.getAdapterPosition());
        return false;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        mSwipeListener.onItemSwiped(viewHolder, viewHolder.getAdapterPosition());
    }

    @Override
    public boolean isLongPressDragEnabled() {
        return true;
    }

    @Override
    public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull
            RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean
                                    isCurrentlyActive) {
        if (mAnimation) {
            if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
                swipedItem(isCurrentlyActive, dX, viewHolder);
            } else if (actionState == ItemTouchHelper.ACTION_STATE_DRAG) {
                dragItem(isCurrentlyActive, viewHolder);
            }
        }
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState,
                isCurrentlyActive);
    }

    public void setMoveListener(OnMoveListener listener) {
        mMoveListener = listener;
    }

    public void setSwipeListener(OnSwipedListener listener) {
        mSwipeListener = listener;
    }

    public void setAnimation(boolean enable) {
        mAnimation = enable;
    }

    // 自訂效果
    private void swipedItem(boolean active, float dX, RecyclerView.ViewHolder viewHolder) {
        if (active) {
            float alpha = 1 - Math.abs(dX) / viewHolder.itemView.getWidth();
            viewHolder.itemView.setAlpha(alpha);
            viewHolder.itemView.setScaleX(Math.max(0.6f, alpha));
            viewHolder.itemView.setScaleY(Math.max(0.6f, alpha));
            // alpha == 0 滑出
        } else {
            viewHolder.itemView.setAlpha(1.0f);
            viewHolder.itemView.setScaleX(1.0f);
            viewHolder.itemView.setScaleY(1.0f);
        }
    }

    // 自訂效果
    private void dragItem(boolean active, RecyclerView.ViewHolder viewHolder) {
        if (active) {
            viewHolder.itemView.setAlpha(0.8f);
            viewHolder.itemView.setScaleX(1.2f);
            viewHolder.itemView.setScaleY(1.2f);
        } else {
            viewHolder.itemView.setAlpha(1.0f);
            viewHolder.itemView.setScaleX(1.0f);
            viewHolder.itemView.setScaleY(1.0f);
        }
    }
}
