package com.van.recyclerview.custom.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.HorizontalScrollView;

public class FlexibleHorizontalScrollView extends HorizontalScrollView {

    public FlexibleHorizontalScrollView(Context context) {
        super(context);
    }

    public FlexibleHorizontalScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FlexibleHorizontalScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected boolean overScrollBy(int deltaX, int deltaY, int scrollX, int scrollY, int
            scrollRangeX, int scrollRangeY, int maxOverScrollX, int maxOverScrollY, boolean
                                           isTouchEvent) {
        return super.overScrollBy(deltaX, deltaY, scrollX, scrollY, scrollRangeX, scrollRangeY,
                200, maxOverScrollY, isTouchEvent);
    }
}
