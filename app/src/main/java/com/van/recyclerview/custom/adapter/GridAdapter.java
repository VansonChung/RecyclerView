package com.van.recyclerview.custom.adapter;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.van.recyclerview.R;

public class GridAdapter extends RecyclerView.Adapter<GridAdapter.GridViewHolder> {

    private static final String TAG = "GridAdapter";

    private static final String[] COLOR = {"#585757", "#3F51B5", "#FF4081", "#00962D",
            "#601006", "#A06158", "#007BCA", "#FF0000", "#0BBE4A", "#806AAA", "#AC719F", "#CC6E91"};

    private int mLayout;

    private int mCurrentPos;

    @NonNull
    @Override
    public GridAdapter.GridViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(mLayout, parent, false);
        return new GridViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final GridViewHolder holder, int position) {
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setColor(Color.parseColor(COLOR[position]));
        gradientDrawable.setShape(GradientDrawable.OVAL);
        gradientDrawable.setSize(40, 40);
        holder.mImageView.setImageDrawable(gradientDrawable);
        if (mCurrentPos == position) {
            holder.itemView.setSelected(true);
        } else {
            holder.itemView.setSelected(false);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                Log.d(TAG, "onClick : " + COLOR[position]);
                int tmp = mCurrentPos;
                mCurrentPos = position;
                notifyItemChanged(tmp);
                holder.itemView.setSelected(true);
            }
        });
    }

    @Override
    public int getItemCount() {
        return COLOR.length;
    }

    public void setLayout(int layout) {
        mLayout = layout;
    }

    class GridViewHolder extends RecyclerView.ViewHolder {

        ImageView mImageView;

        GridViewHolder(View v) {
            super(v);
            mImageView = v.findViewById(R.id.imageView);
        }
    }
}
