package com.hencoder.hencoderpracticedraw6.practice.practice09;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.hencoder.hencoderpracticedraw6.R;

/**
 * Created by lizhiguang on 2017/10/9.
 */

public class Practice09Map extends RelativeLayout {
    private Button animateBt;
    private Practice09MapView view;

    public Practice09Map(Context context) {
        super(context);
    }

    public Practice09Map(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public Practice09Map(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    class MyAnimatorListener implements Animator.AnimatorListener {
        @Override
        public void onAnimationStart(Animator animation) {
        }

        @Override
        public void onAnimationEnd(Animator animation) {
            startNewAnimator();
        }

        @Override
        public void onAnimationCancel(Animator animation) {

        }

        @Override
        public void onAnimationRepeat(Animator animation) {

        }
    }

    private ObjectAnimator animator, animator1, animator2;
    private int i = 0;

    private void startNewAnimator() {
        switch (i) {
            case 0:
                animator.start();
                break;
            case 1:
                animator1.start();
                break;
            case 2:
                animator2.start();
                break;
        }
        i++;
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();

        animateBt = (Button) findViewById(R.id.animateBt);
        view = (Practice09MapView) findViewById(R.id.view);
        animator = ObjectAnimator.ofFloat(view, "upDegree", 0, -45);
        animator1 = ObjectAnimator.ofFloat(view, "rotateDegree", 0, -270);
        animator2 = ObjectAnimator.ofFloat(view, "secondUpDegree", 0, 45);
        animator.setDuration(500);
        animator1.setDuration(1000);
        animator2.setDuration(500);
        animator.addListener(new MyAnimatorListener());
        animator1.addListener(new MyAnimatorListener());
        animator2.addListener(new MyAnimatorListener());
        animateBt.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                i = 0;
                view.setUpDegree(0);
                view.setRotateDegree(0);
                view.setSecondUpDegree(0);
                startNewAnimator();
            }
        });
    }
}
