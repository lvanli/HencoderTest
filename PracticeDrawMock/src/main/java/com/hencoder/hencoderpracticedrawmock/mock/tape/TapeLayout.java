package com.hencoder.hencoderpracticedrawmock.mock.tape;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.LinearLayout;

/**
 * Created by lizhiguang on 17/12/16.
 */

public class TapeLayout extends LinearLayout {
    public TapeLayout(Context context) {
        this(context, null);
    }

    public TapeLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TapeLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        boolean ret = super.dispatchTouchEvent(ev);
        if (ret) {
            getParent().requestDisallowInterceptTouchEvent(true);
        } else
            getParent().requestDisallowInterceptTouchEvent(false);
        return ret;
    }
}
