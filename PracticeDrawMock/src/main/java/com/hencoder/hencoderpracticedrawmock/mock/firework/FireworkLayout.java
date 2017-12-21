package com.hencoder.hencoderpracticedrawmock.mock.firework;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.LinearLayout;

import com.hencoder.hencoderpracticedrawmock.R;

/**
 * Created by lizhiguang on 17/12/19.
 */

public class FireworkLayout extends LinearLayout {
    private static final int STATUS_NONE = 0;
    private static final int STATUS_CIRCLE = 1;
    private static final int STATUS_END = 2;
    private static final int STATUS_MAX = 3;
    private Button button;
    private FireworkView mFireworkView;
    private int mStatus = STATUS_NONE;
    private ObjectAnimator mCircleAnimator;
    private ObjectAnimator mSuccessAnimator;

    public FireworkLayout(Context context) {
        this(context, null);
    }

    public FireworkLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FireworkLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        button = (Button) findViewById(R.id.firework_button);
        mFireworkView = (FireworkView) findViewById(R.id.firework_view);

        mCircleAnimator = ObjectAnimator.ofFloat(mFireworkView, "degree", 0, 360);
        mCircleAnimator.setDuration(4000);
        mCircleAnimator.setInterpolator(new LinearInterpolator());
        mCircleAnimator.setRepeatCount(ValueAnimator.INFINITE);

        int radio = mFireworkView.getRadio();
        mSuccessAnimator = ObjectAnimator.ofInt(mFireworkView, "radio", (int) (radio * 0.75), radio, (int) (radio * 1.25), radio);
        mSuccessAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                mFireworkView.setStatus(FireworkView.STATUS_CIRCLE);
            }

            @Override
            public void onAnimationCancel(Animator animation) {
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
            }
        });

        button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (mStatus) {
                    case STATUS_NONE:
                        mFireworkView.setStatus(FireworkView.STATUS_STRAGGLY);
                        mCircleAnimator.start();
                        button.setText("连接");
                        break;
                    case STATUS_CIRCLE:
                        mFireworkView.setStatus(FireworkView.STATUS_CIRCLE_SCALE);
                        mSuccessAnimator.start();
                        button.setText("连接成功");
                        break;
                    default:
                        mCircleAnimator.cancel();
                        mSuccessAnimator.cancel();
                        button.setText("开始");
                }
                mStatus = (mStatus + 1) % STATUS_MAX;
            }
        });
    }
}
