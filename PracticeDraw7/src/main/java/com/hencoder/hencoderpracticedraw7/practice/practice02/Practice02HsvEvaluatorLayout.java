package com.hencoder.hencoderpracticedraw7.practice.practice02;

import android.animation.ObjectAnimator;
import android.animation.TypeEvaluator;
import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.hencoder.hencoderpracticedraw7.R;

public class Practice02HsvEvaluatorLayout extends RelativeLayout {
    Practice02HsvEvaluatorView view;
    Button animateBt;

    public Practice02HsvEvaluatorLayout(Context context) {
        super(context);
    }

    public Practice02HsvEvaluatorLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public Practice02HsvEvaluatorLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();

        view = (Practice02HsvEvaluatorView) findViewById(R.id.objectAnimatorView);
        animateBt = (Button) findViewById(R.id.animateBt);

        animateBt.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ObjectAnimator animator = ObjectAnimator.ofInt(view, "color", 0xff00ffff, 0xffff00ff);
                animator.setEvaluator(new HsvEvaluator()); // 使用自定义的 HsvEvaluator
                animator.setInterpolator(new LinearInterpolator());
                animator.setDuration(2000);
                animator.start();
            }
        });
    }

    private class HsvEvaluator implements TypeEvaluator<Integer> {
        float[] start = new float[3];
        float[] end = new float[3];
        float[] result = new float[3];
        float fend;

        // 重写 evaluate() 方法，让颜色按照 HSV 来变化
        @Override
        public Integer evaluate(float fraction, Integer startValue, Integer endValue) {
            Color.colorToHSV(startValue, start);
            Color.colorToHSV(endValue, end);
            fend = end[0];
            if (end[0] - start[0] > 180) {
                fend = end[0] - 360;
            } else if (start[0] - end[0] > 180) {
                fend = end[0] + 360;
            }
            result[0] = (start[0] + (fend - start[0]) * fraction + 360) % 360;
            result[1] = start[1] + (end[1] - start[1]) * fraction;
            result[2] = start[2] + (end[2] - start[2]) * fraction;
            Log.d("myDebug", String.format("result=%f,%f,%f.fra=%f", result[0], start[0], end[0], fraction));

            return Color.HSVToColor(result);
        }
    }
}
