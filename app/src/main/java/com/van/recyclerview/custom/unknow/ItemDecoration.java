package com.van.recyclerview.custom.unknow;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

public class ItemDecoration extends RecyclerView.ItemDecoration {

    private static final String TAG = "ItemDecoration";

    private static final int[] ATTRS = new int[]{
            android.R.attr.listDivider
    };

    private final int LEFT = 0, TOP = 1, RIGHT = 2, BOTTOM = 3;

    private int mOrientation;

    private int mLeft, mTop, mRight, mBottom;

    private int mDividePaddingStart, mDividePaddingEnd;

    private Drawable mDivider;
    private int mWidth;
    private int mHeight;

    private final Rect mBounds = new Rect();

    public ItemDecoration(Context context, int orientation) {
        TypedArray a = context.obtainStyledAttributes(ATTRS);
        mDivider = a.getDrawable(0);
        if (mDivider == null) {
            Log.w(TAG, "@android:attr/listDivider was not set in the theme used for this " +
                    "DividerItemDecoration. Please set that attribute all call setDrawable()");
        }

        a.recycle();
        setOrientation(orientation);
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView
            parent, @NonNull RecyclerView.State state) {
        if (mOrientation == LinearLayoutManager.VERTICAL) {
            outRect.set(getOffset(LEFT), getOffset(TOP), getOffset(RIGHT), getOffset
                    (BOTTOM) + getDividerHeight());
        } else {
            outRect.set(getOffset(LEFT), getOffset(TOP), getOffset(RIGHT) + getDividerWidth(),
                    getOffset(BOTTOM));
        }
    }

    @Override
    public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView
            .State state) {
        if (parent.getLayoutManager() != null) {
            if (mDivider == null) {
                super.onDraw(c, parent, state);
            } else {
                if (mOrientation == LinearLayoutManager.VERTICAL) {
                    drawVertical(c, parent);
                } else {
                    drawHorizontal(c, parent);
                }
            }
        }

    }

    public void setOrientation(int orientation) {
        if (orientation != LinearLayoutManager.HORIZONTAL && orientation != LinearLayoutManager.VERTICAL) {
            throw new IllegalArgumentException("invalid orientation");
        }
        mOrientation = orientation;
    }

    // Note : ColorDrawable 需配合 setDividerWidth/Height (Color no w/h)
    public void setDivideDrawable(Drawable drawable) {
        mDivider = drawable;
    }

    // 根據 layout 會擠壓/延展(外部) item
    // ex : Orientation : Vertical.
    //      只要 left/right offset 後所剩長度小於 item layout 長度則壓縮.
    //      top/bottom 則延展 item 外部 (非 item 本身), 相對 recyclerView 變大.
    public void setItemOffsets(int left, int top, int right, int bottom) {
        mLeft = left;
        mTop = top;
        mRight = right;
        mBottom = bottom;
    }

    public void setDividePadding(int paddingStart, int paddingEnd) {
        mDividePaddingStart = paddingStart;
        mDividePaddingEnd = paddingEnd;
    }

    public void setDividerWidth(int width) {
        this.mWidth = width;
    }

    public void setDividerHeight(int height) {
        this.mHeight = height;
    }

    private void drawVertical(Canvas canvas, RecyclerView parent) {
        canvas.save();
        int left;
        int right;
        if (parent.getClipToPadding()) {
            left = parent.getPaddingLeft();
            right = parent.getWidth() - parent.getPaddingRight();
            canvas.clipRect(left, parent.getPaddingTop(), right, parent.getHeight() - parent
                    .getPaddingBottom());
        } else {
            left = 0;
            right = parent.getWidth();
        }

        int childCount = parent.getChildCount();

        for (int i = 0; i < childCount; ++i) {
            View child = parent.getChildAt(i);
            parent.getDecoratedBoundsWithMargins(child, mBounds);
            int bottom = mBounds.bottom + Math.round(child.getTranslationY());
            int top = bottom - getDividerHeight();
            mDivider.setBounds(left + getDividePadding(true), top, right - getDividePadding
                    (false), bottom);
            mDivider.draw(canvas);
        }

        canvas.restore();
    }

    private void drawHorizontal(Canvas canvas, RecyclerView parent) {
        canvas.save();
        int top;
        int bottom;
        if (parent.getClipToPadding()) {
            top = parent.getPaddingTop();
            bottom = parent.getHeight() - parent.getPaddingBottom();
            canvas.clipRect(parent.getPaddingLeft(), top, parent.getWidth() - parent
                    .getPaddingRight(), bottom);
        } else {
            top = 0;
            bottom = parent.getHeight();
        }

        int childCount = parent.getChildCount();

        for (int i = 0; i < childCount; ++i) {
            View child = parent.getChildAt(i);
            parent.getLayoutManager().getDecoratedBoundsWithMargins(child, this.mBounds);
            int right = this.mBounds.right + Math.round(child.getTranslationX());
            int left = right - getDividerWidth();
            this.mDivider.setBounds(left, top + getDividePadding(true), right, bottom -
                    getDividePadding(false));
            this.mDivider.draw(canvas);
        }

        canvas.restore();
    }

    private int getOffset(int path) {
        switch (path) {
            case LEFT:
                return mLeft > 0 ? mLeft : 0;
            case TOP:
                return mTop > 0 ? mTop : 0;
            case RIGHT:
                return mRight > 0 ? mRight : 0;
            case BOTTOM:
                return mBottom > 0 ? mBottom : 0;
            default:
                return 0;
        }
    }

    private int getDividePadding(boolean paddingStart) {
        return paddingStart ? mDividePaddingStart : mDividePaddingEnd;
    }

    private int getDividerWidth() {
        return mDivider == null ? 0 : mWidth > 0 ? mWidth : mDivider.getIntrinsicWidth();
    }

    private int getDividerHeight() {
        return mDivider == null ? 0 : mHeight > 0 ? mHeight : mDivider.getIntrinsicHeight();
    }
}
