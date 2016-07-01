package com.halilibrahimaksoy.borsaalarm.util;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by haksoy on 4/18/2016.
 */
public class NonSwipeableViewPager extends ViewPager {

    public NonSwipeableViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public NonSwipeableViewPager(Context context) {
        super(context);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        // Never allow swiping to switch between pages
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // Never allow swiping to switch between pages
        return false;
    }

}
