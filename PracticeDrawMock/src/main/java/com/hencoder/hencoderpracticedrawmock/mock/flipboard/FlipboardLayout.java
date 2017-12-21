package com.hencoder.hencoderpracticedrawmock.mock.flipboard;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.hencoder.hencoderpracticedrawmock.R;

public class FlipboardLayout extends RelativeLayout {
    FlipboardView view;
    Button animateBt;

    public FlipboardLayout(Context context) {
        super(context);
    }

    public FlipboardLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FlipboardLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    AnimatorSet animatorSet;
    private ObjectAnimator animator, animator1, animator2;

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();

        view = (FlipboardView) findViewById(R.id.flipboardAnimatorView);
        animateBt = (Button) findViewById(R.id.animateBt);

        animator = ObjectAnimator.ofFloat(view, "upDegree", 0, -45);
        animator1 = ObjectAnimator.ofFloat(view, "rotateDegree", 0, -270);
        animator2 = ObjectAnimator.ofFloat(view, "secondUpDegree", 0, 45);
        animator.setDuration(500);
        animator1.setDuration(1000);
        animator2.setDuration(500);
        animatorSet = new AnimatorSet();
        animatorSet.playSequentially(animator, animator1, animator2);

        animateBt.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (animatorSet.isRunning())
                    animatorSet.cancel();
                view.init();
                animatorSet.start();
            }
        });
    }
}
