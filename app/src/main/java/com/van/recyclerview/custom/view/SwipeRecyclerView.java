package com.van.recyclerview.custom.view;

import android.content.Context;
import android.graphics.Rect;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.animation.LinearInterpolator;
import android.widget.LinearLayout;
import android.widget.Scroller;
import android.widget.TextView;

import com.van.recyclerview.R;
import com.van.recyclerview.custom.adapter.LinearDelAdapter;

public class SwipeRecyclerView extends RecyclerView {

    private static final String TAG = "SwipeRecyclerView";

    private Scroller mScroller;

    private int mTouchSlop;

    private int mDownX, mDownY;

    private int mSelectPosition;

    private LinearLayout mItemLayout, mLastItemLayout;

    private int mHiddenWidth;

    private boolean mShowHidden;

    private boolean mFirst = true;

    private OnDeleteListener mDeleteListener;

    public interface OnDeleteListener {
        void onDelete(int position);
    }

    private OnLoadMoreListener mLoadMoreListener;

    public interface OnLoadMoreListener {
        void onLoadMore(int total);
    }

    public SwipeRecyclerView(Context context) {
        this(context, null);
    }

    public SwipeRecyclerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SwipeRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mScroller = new Scroller(context, new LinearInterpolator(context, null));
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        int x = (int) e.getX();
        int y = (int) e.getY();
        switch (e.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mDownX = x;
                mDownY = y;
                // 獲取當前點擊 item
                int firstPosition = ((LinearLayoutManager) getLayoutManager())
                        .findFirstVisibleItemPosition();
                Rect itemRect = new Rect();
                View selectItem = null;
                for (int i = 0; i < getChildCount(); i++) {
                    final View child = getChildAt(i);
                    if (child.getVisibility() == View.VISIBLE) {
                        child.getHitRect(itemRect);
                        if (itemRect.contains(x, y)) {
                            mSelectPosition = firstPosition + i;
                            selectItem = getChildAt(mSelectPosition - firstPosition);
                            break;
                        }
                    }
                }

                if (mFirst) {
                    mFirst = false;
                } else {
                    // 恢復上一點擊 item 狀態
                    if (mLastItemLayout != null && mShowHidden) {
                        mLastItemLayout.scrollTo(0, 0);
                        mHiddenWidth = 0;
                        mShowHidden = false;
                    }
                }

                if (selectItem != null) {
                    LinearDelAdapter.ItemViewHolder viewHolder = (LinearDelAdapter
                            .ItemViewHolder) getChildViewHolder(selectItem);
                    mItemLayout = viewHolder.mLinearLayout;
                    LinearLayout hiddenLayout = mItemLayout.findViewById(R.id.view_hidden);
                    TextView tvDel = hiddenLayout.findViewById(R.id.tv_delete);
                    TextView tvEdit = hiddenLayout.findViewById(R.id.tv_edit);
                    tvDel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Log.d(TAG, "onClick Delete");
                            if (mDeleteListener != null) {
                                mDeleteListener.onDelete(mSelectPosition);
                            }
                        }
                    });
                    tvEdit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Log.d(TAG, "onClick Edit");
                        }
                    });
                    mHiddenWidth = hiddenLayout.getWidth();
                }
                break;
            case MotionEvent.ACTION_MOVE:
                int dx = x - mDownX; // 負左滑, 正右滑.
                int dy = y - mDownY;
                if (dx < 0 && Math.abs(dx) > mTouchSlop && Math.abs(dy) < mTouchSlop) {
                    int scrollX = Math.abs(dx);
                    if (scrollX > mHiddenWidth) {
                        break;
                    }
                    mItemLayout.scrollTo(scrollX, 0);
                } else if (dx > 0) {
                    mItemLayout.scrollTo(0, 0);
                }
                break;
            case MotionEvent.ACTION_UP:
                int scrollX = mItemLayout.getScrollX();
                if (mHiddenWidth > scrollX) {
                    if (scrollX > mHiddenWidth / 2) { //超過一半鬆開則自動滑到左側
                        mItemLayout.scrollTo(mHiddenWidth, 0);
                        mShowHidden = true;
                    } else { //不到一半, 恢復原狀
                        mItemLayout.scrollTo(0, 0);
                        mShowHidden = false;
                    }
                }
                mLastItemLayout = mItemLayout;
                break;
        }
        return super.onTouchEvent(e);
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            Log.e(TAG, "computeScroll getCurrX ->" + mScroller.getCurrX());
            mItemLayout.scrollBy(mScroller.getCurrX(), 0);
            invalidate();
        }
    }

    @Override
    public void onScrolled(int dx, int dy) {
        super.onScrolled(dx, dy);
        if (dy > 0) { // 上滑, 非常簡化...應該會有問題...
            if (!canScrollVertically(1)) {
                mLoadMoreListener.onLoadMore(getLayoutManager().getItemCount());
            }
        }
    }

    public void setDeleteListener(OnDeleteListener listener) {
        mDeleteListener = listener;
    }

    public void setLoadMoreListener(OnLoadMoreListener listener) {
        mLoadMoreListener = listener;
    }
}
