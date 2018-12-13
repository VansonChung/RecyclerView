package com.van.recyclerview.custom.adapter;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.van.recyclerview.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LinearAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String TAG = "LinearAdapter";

    private List<String> mList;

    private int mLayout;

    private int mCurrentPos;

    private boolean mQuickDrag;

    private DragListener mDragListener;

    public interface DragListener {
        // 設置可使 ItemTouchHelper 更快觸發 drag,
        // 但會使 swipe 無法觸發且有機會使 list 無法滑動 (因馬上觸發 drag).
        // 不設置則約按住一秒後才觸發 drag, swipe 則是一秒內有效.
        // 不建議設置.
        void onStartDrag(RecyclerView.ViewHolder holder);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new LinearAdapter.ItemViewHolder(LayoutInflater.from(viewGroup.getContext())
                .inflate(mLayout, viewGroup, false));
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, int position) {
        if (mCurrentPos == position) {
            holder.itemView.setSelected(true);
        } else {
            holder.itemView.setSelected(false);
        }
        ((ItemViewHolder) holder).mTv.setText(mList.get(position));
        // 點擊 Text 才做事 (For Vertical Third 點擊白色區域不做事, 因實際 item width 全屏)
        ((ItemViewHolder) holder).mTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                Log.d(TAG, "onClick : " + mList.get(position));
                if (mCurrentPos != position) {
                    int tmp = mCurrentPos;
                    mCurrentPos = position;
                    holder.itemView.setSelected(true);
                    notifyItemChanged(tmp);
                }
            }
        });

        if (mQuickDrag && mDragListener != null) {
            // TextView 設置, ItemView 沒設置, 可點擊比較.
            ((ItemViewHolder) holder).mTv.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        mDragListener.onStartDrag(holder);
                    }
                    return false;
                }
            });
        }
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

    // layout 相近, id 相同所以故意寫在同一個 adapter.
    public void setItemLayout(int layout) {
        mLayout = layout;
    }

    public void setQuickDrag(boolean quickDrag) {
        mQuickDrag = quickDrag;
    }

    public void setDragListener(DragListener listener) {
        mDragListener = listener;
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

    public void onItemSwiped(RecyclerView.ViewHolder holder, int i) {
        mList.remove(i);
        notifyItemRemoved(i);
        if (mCurrentPos == i && i >= mList.size()) {
            mCurrentPos = i - 1;
        }
        notifyItemChanged(mCurrentPos);
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {

        TextView mTv;

        ItemViewHolder(View v) {
            super(v);
            mTv = v.findViewById(R.id.textView);
            mTv.setTextColor(v.getContext().getResources().getColorStateList(R.color
                    .selector_color_text));
        }
    }
}
