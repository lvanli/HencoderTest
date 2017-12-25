package com.hencoder.hencoderpracticedrawmock.mock.tape;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

import com.hencoder.hencoderpracticedrawmock.mock.util.DisplayUtil;
import com.lizhiguang.utils.log.LogUtil;

/**
 * Created by lizhiguang on 17/12/16.
 */

public class TapeView extends View {
    private int mWeight = 1995;
    private int mMaxWeight = 2000;
    private int mMinWeight = 0;
    private int width, height;
    private int mXOffset = 0;
    private int mTapeSpace = 20;
    private int mTextSize = 0;
    private int mMiddleLength = 65;
    private int mLongLength = 60;
    private int mShortLength = 40;
    private int mAnimatorBeginOffset = 0;
    private float mScrollBeginOffset = 0;

    private ObjectAnimator flipAnimator;
    private ObjectAnimator finalAnimator;

    private Paint paint;

    public TapeView(Context context) {
        this(context, null);
    }

    public TapeView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TapeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        paint = new Paint();
        paint.setStrokeWidth(10f);
        paint.setAntiAlias(true);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeCap(Paint.Cap.ROUND);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int mode = MeasureSpec.getMode(widthMeasureSpec);
        switch (mode) {
            case MeasureSpec.UNSPECIFIED:
            case MeasureSpec.AT_MOST:
                setMeasuredDimension(600, 300);
                break;
        }
        width = getMeasuredWidth();
        height = getMeasuredHeight();
        mTextSize = DisplayUtil.px2dp(getContext(), height / 2);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawKG(canvas);
        drawTape(canvas);
    }

    private void drawKG(Canvas canvas) {
        paint.setColor(Color.GREEN);
        paint.setTextSize(mTextSize);
        String sWeight = String.format("%d.%d", mWeight / 10, mWeight % 10);
        float sWeightHeight = paint.getFontSpacing();
        float sWeightWidth = paint.measureText(sWeight);
        canvas.drawText(sWeight, width / 2 - sWeightWidth / 2, height / 2 - sWeightHeight * 2 / 3, paint);
        paint.setTextSize(mTextSize - 10);
        canvas.drawText("kg", width / 2 + sWeightWidth / 2, height / 2 - sWeightHeight * 4 / 5, paint);
    }

    private void drawTape(Canvas canvas) {
        paint.setColor(Color.parseColor("#D3D3D3"));
        canvas.drawRect(0, height / 2, width, height, paint);
        paint.setColor(Color.GRAY);
        //划横线
        paint.setStrokeWidth(1);
        canvas.drawLine(0, height / 2, width, height / 2, paint);
        //画短竖线
        paint.setStrokeWidth(2);
        int rightNumber = (mWeight + 10 - mWeight % 10);
        int leftNumber = rightNumber;
        int beginOffset = mXOffset % mTapeSpace + width / 2;
        int rCount = mXOffset / mTapeSpace + mWeight % 10;
        int lCount = rCount;
        //LogUtil.d("rCount="+rCount+",lCount="+lCount+",beginOff="+beginOffset);
        for (int offset = 0; beginOffset - offset > 0; offset += mTapeSpace) {
            if (rCount % 10 != 0 && mWeight + rCount - mWeight % 10 <= mMaxWeight)
                canvas.drawLine(beginOffset + offset, height / 2, beginOffset + offset, height / 2 + mShortLength, paint);
            if (lCount % 10 != 0 && mWeight + lCount - mWeight % 10 >= mMinWeight && mWeight + lCount - mWeight % 10 <= mMaxWeight)
                canvas.drawLine(beginOffset - offset, height / 2, beginOffset - offset, height / 2 + mShortLength, paint);
            rCount++;
            lCount--;
        }
        //画粗线
        paint.setStrokeWidth(4);
        beginOffset += (10 - mWeight % 10) * mTapeSpace;
        //LogUtil.d("beginO="+beginOffset+",rightNumber＝"+rightNumber+",number="+mWeight);
        for (int offset = 0; beginOffset - offset > 0; offset += mTapeSpace * 10) {
            if (rightNumber <= mMaxWeight) {
                canvas.drawLine(beginOffset + offset, height / 2, beginOffset + offset, height / 2 + mLongLength, paint);
                canvas.drawText(String.valueOf(rightNumber / 10), beginOffset + offset - paint.measureText(String.valueOf(rightNumber)) / 2, height / 2 + mLongLength + paint.getFontSpacing(), paint);
            }
            if (leftNumber >= mMinWeight && leftNumber <= mMaxWeight) {
                canvas.drawLine(beginOffset - offset, height / 2, beginOffset - offset, height / 2 + mLongLength, paint);
                canvas.drawText(String.valueOf(leftNumber / 10), beginOffset - offset - paint.measureText(String.valueOf(rightNumber)) / 2, height / 2 + mLongLength + paint.getFontSpacing(), paint);
            }
            rightNumber += 10;
            leftNumber -= 10;
        }
        //画中间粗竖线
        paint.setColor(Color.GREEN);
        paint.setStrokeWidth(8);
        canvas.drawLine(width / 2, height / 2, width / 2, height / 2 + mMiddleLength, paint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean ret = gestureDetector.onTouchEvent(event);
        if (event.getAction() == MotionEvent.ACTION_UP && flipAnimator == null)
            startFinalAnimator();
        return true;
    }

    private void setMXOffset(int offset) {
        mXOffset = offset - offset / mTapeSpace * mTapeSpace;
        mWeight = mWeight - offset / mTapeSpace;
        if (mWeight >= mMaxWeight) {
            mWeight = mMaxWeight;
            if (mXOffset < 0)
                mXOffset = 0;
        } else if (mWeight < mMinWeight) {
            mWeight = mMinWeight;
            if (mXOffset > 0)
                mXOffset = 0;
        }
 //       LogUtil.d("offset="+offset+","+"mX="+mXOffset+",mWeight="+mWeight);
        invalidate();
    }

    public void setXOffset(int offset) {
        setMXOffset(mXOffset + offset - mAnimatorBeginOffset);
        mAnimatorBeginOffset = offset;
        //LogUtil.d("offset="+offset+","+"mX="+mXOffset+",mWeight="+mWeight);
    }

    private void startAnimator(int begin, int end) {
        flipAnimator = ObjectAnimator.ofInt(this, "xOffset", begin, end);
        flipAnimator.setInterpolator(new DecelerateInterpolator());
        flipAnimator.setDuration(Math.abs((end - begin) / mTapeSpace * 30));
        flipAnimator.addListener(listener);
        flipAnimator.start();
    }

    private void startFinalAnimator() {
        mAnimatorBeginOffset = 0;
        if (mXOffset <= 0.5 * mTapeSpace && mXOffset >= -0.5 * mTapeSpace)
            finalAnimator = ObjectAnimator.ofInt(this, "xOffset", 0, -mXOffset);
        else if (mXOffset < 0)
            finalAnimator = ObjectAnimator.ofInt(this, "xOffset", 0, -mTapeSpace - mXOffset);
        else
            finalAnimator = ObjectAnimator.ofInt(this, "xOffset", 0, mTapeSpace - mXOffset);
        finalAnimator.setInterpolator(new DecelerateInterpolator());
        finalAnimator.start();

    }

    Animator.AnimatorListener listener = new Animator.AnimatorListener() {
        @Override
        public void onAnimationStart(Animator animation) {

        }

        @Override
        public void onAnimationEnd(Animator animation) {
            startFinalAnimator();
        }

        @Override
        public void onAnimationCancel(Animator animation) {

        }

        @Override
        public void onAnimationRepeat(Animator animation) {

        }
    };
    GestureDetector gestureDetector = new GestureDetector(getContext(), new GestureDetector.OnGestureListener() {
        @Override
        public boolean onDown(MotionEvent e) {
            if (flipAnimator != null) {
                flipAnimator.cancel();
                flipAnimator = null;
            }
            if (finalAnimator != null) {
                finalAnimator.cancel();
                finalAnimator = null;
            }
            mScrollBeginOffset = mXOffset;
            return true;
        }

        @Override
        public void onShowPress(MotionEvent e) {

        }

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            return false;
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            LogUtil.d("distanceX=" + distanceX);
            mScrollBeginOffset -= distanceX;
            setXOffset((int)(mScrollBeginOffset + 0.0001));
            return true;
        }

        @Override
        public void onLongPress(MotionEvent e) {

        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            LogUtil.d("offset=" + mXOffset + ",velocity=" + velocityX);
            mAnimatorBeginOffset = mXOffset;
            startAnimator(mXOffset, (int)(velocityX / 5));
            return true;
        }
    });
}
