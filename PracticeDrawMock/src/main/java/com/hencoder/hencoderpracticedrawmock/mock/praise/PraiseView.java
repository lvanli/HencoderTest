package com.hencoder.hencoderpracticedrawmock.mock.praise;

import android.animation.AnimatorSet;
import android.animation.Keyframe;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.hencoder.hencoderpracticedrawmock.R;
import com.hencoder.hencoderpracticedrawmock.mock.util.DisplayUtil;
import com.lizhiguang.utils.log.LogUtil;

/**
 * Created by lizhiguang on 2017/10/18.
 */

public class PraiseView extends View {
    public PraiseView(Context context) {
        this(context, null);
    }

    public PraiseView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PraiseView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private Bitmap mOriBitmap, mPressBitmap;
    private boolean press = false;
    private float zoom = 1;
    private int duration = 200;
    private Paint paint;
    private int mWidth, mHeight;
    private int mImgWidth, mImgHeight;
    private float lineAlpha;
    private ObjectAnimator zoomOutAnimator, zoomInAnimator;
    private AnimatorSet lineOutAnimation, lineInAnimation;
    private float scale = 1;

    public float getZoom() {
        return zoom;
    }

    public void setZoom(float zoom) {
        this.zoom = zoom;
        invalidate();
    }

    public float getLineAlpha() {
        return lineAlpha;
    }

    public void setLineAlpha(float lineAlpha) {
        this.lineAlpha = lineAlpha;
        invalidate();
    }

    private void init() {
        mOriBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.praise);
        mPressBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.praise_press);
        zoomOutAnimator = ObjectAnimator.ofFloat(this, "zoom", 1f, 0.85f);
        zoomOutAnimator.setDuration(duration);
        zoomInAnimator = ObjectAnimator.ofFloat(this, "zoom", 0.85f, 1f);
        zoomInAnimator.setDuration(duration);
        ObjectAnimator lineOutAlpha = ObjectAnimator.ofFloat(this, "lineAlpha", 0, 1);
        lineOutAnimation = new AnimatorSet();
        lineOutAnimation.playTogether(lineOutAlpha);
        lineOutAnimation.setDuration(duration);
        ObjectAnimator lineInAlpha = ObjectAnimator.ofFloat(this, "lineAlpha", 1, 0);
        lineInAnimation = new AnimatorSet();
        lineInAnimation.playTogether(lineInAlpha);
        lineInAnimation.setDuration(duration);

        Keyframe first = Keyframe.ofFloat(0f, 0.85f);
        Keyframe second = Keyframe.ofFloat(0.5f, 1.1f);
        Keyframe third = Keyframe.ofFloat(1f, 1f);
        PropertyValuesHolder holder = PropertyValuesHolder.ofKeyframe("zoom", first, second, third);
        zoomInAnimator = ObjectAnimator.ofPropertyValuesHolder(this, holder);
        zoomInAnimator.setDuration(duration);

        paint = new Paint();
        paint.setStrokeWidth(10f);
        paint.setAntiAlias(true);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeCap(Paint.Cap.ROUND);
        mImgWidth = mOriBitmap.getWidth();
        mImgHeight = mOriBitmap.getHeight();

        paint.setTextSize(DisplayUtil.px2sp(getContext(), mImgHeight * 2));
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth = getMeasuredWidth();
        mHeight = getMeasuredHeight();
        int mode = MeasureSpec.getMode(widthMeasureSpec);

        float numberWidth = paint.measureText(String.valueOf(mNumber + 1));
        float needWidth = numberWidth + mImgWidth;
        float needHeight = mImgHeight + mLineLength;

        switch (mode) {//200*200
            case MeasureSpec.UNSPECIFIED:
            case MeasureSpec.AT_MOST:
                mWidth = (int) needWidth;
                mHeight = (int) needHeight;
                break;
            case MeasureSpec.EXACTLY:
                float widthScale = mWidth / needWidth;
                float heightScale = mHeight / needHeight;
                scale = widthScale > heightScale ? heightScale : widthScale;
                break;
        }
        LogUtil.d("mode=" + mode + ",scale=" + scale);
        setMeasuredDimension(mWidth, mHeight);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    private int mImgScaleCenterX = 105, mImgScaleCenterY = 30;
    private int mImgCenterX, mImgCenterY;
    private int mImgPaddingLeft, mImgPaddingTop;
    private int mLineLength = 50;
    private int mNumber = 99;

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mImgCenterX = mImgScaleCenterX;
        mImgCenterY = mImgHeight / 2;
        mImgPaddingLeft = getPaddingLeft();
        mImgPaddingTop = getPaddingTop() + mLineLength;
        drawLines(canvas);
        drawBitmap(canvas);
        drawNumber(canvas);
    }

    private void drawBitmap(Canvas canvas) {
        canvas.save();
        canvas.scale(scale, scale);
        paint.setAlpha(0xff);
        canvas.translate(mImgPaddingLeft, mImgPaddingTop);
        canvas.translate(mImgCenterX, mImgCenterY);
        canvas.scale(zoom, zoom);
        canvas.translate(-mImgCenterX, -mImgCenterY);
        if (!press)
            canvas.drawBitmap(mOriBitmap, 0, 0, paint);
        else
            canvas.drawBitmap(mPressBitmap, 0, 0, paint);
        canvas.translate(-mImgPaddingLeft, -mImgPaddingTop);

        canvas.restore();
    }

    private void drawLines(Canvas canvas) {
        canvas.save();
        canvas.scale(scale, scale);
        if (press)
            paint.setColor(Color.RED);
        else
            paint.setColor(Color.BLACK);
        paint.setAlpha((int) (0xff * lineAlpha));
        int lineCenterX = mImgScaleCenterX + mImgPaddingLeft;
        int lineCenterY = mImgScaleCenterY + mImgPaddingTop;
        Path mLineCenterPath = new Path();
        mLineCenterPath.setFillType(Path.FillType.INVERSE_WINDING);
        mLineCenterPath.addCircle(lineCenterX, lineCenterY, mLineLength / 2, Path.Direction.CW);
        canvas.clipPath(mLineCenterPath);
        Path mLineOutCenterPath = new Path();
        mLineOutCenterPath.addCircle(lineCenterX, lineCenterY, mLineLength * (lineAlpha / 2 + 0.6f), Path.Direction.CW);
        canvas.clipPath(mLineOutCenterPath);
        canvas.drawLine(lineCenterX, lineCenterY, lineCenterX, lineCenterY - mLineLength, paint);
        canvas.drawLine(lineCenterX, lineCenterY, lineCenterX - mLineLength * 2 / 3, lineCenterY - mLineLength * 2 / 3, paint);
        canvas.drawLine(lineCenterX, lineCenterY, lineCenterX + mLineLength * 2 / 3, lineCenterY - mLineLength * 2 / 3, paint);
        canvas.drawLine(lineCenterX, lineCenterY, lineCenterX - mLineLength * 4 / 5, lineCenterY + mLineLength * 1 / 5, paint);

        canvas.restore();
    }

    private void drawNumber(Canvas canvas) {
        float textHeight;
        int newNumber = mNumber + 1;
        int normalSize;
        int numberLength = getNumberLength(mNumber);

        normalSize = numberLength - getLastNumberSize(newNumber, '0');
        if (normalSize < 0)
            normalSize = 0;
        paint.setColor(Color.BLACK);

        textHeight = paint.getFontSpacing();

        paint.setAlpha(0xff);
        canvas.save();
        canvas.scale(scale, scale);
        canvas.drawText(String.valueOf(mNumber), 0, normalSize, mImgWidth + getPaddingLeft(), mImgHeight * 3 / 4 + mLineLength - 2, paint);

        float normalWidth = paint.measureText(String.valueOf(mNumber), 0, normalSize);
        float textWidth = paint.measureText(String.valueOf(mNumber + 1));
        canvas.clipRect(mImgWidth + getPaddingLeft() + normalWidth, 0, mImgWidth + getPaddingLeft() + textWidth, mImgHeight + mLineLength);
        paint.setAlpha((int) (0xff * (1.0 - lineAlpha)));
        canvas.drawText(String.valueOf(mNumber), 0, numberLength, mImgWidth + getPaddingLeft(), mImgHeight * 3 / 4 + mLineLength - 2 - textHeight * lineAlpha, paint);
        paint.setAlpha((int) (0xff * lineAlpha));
        canvas.drawText(String.valueOf(newNumber), 0, getNumberLength(newNumber), mImgWidth + getPaddingLeft(), mImgHeight * 3 / 4 + mLineLength - 2 + textHeight * (1 - lineAlpha), paint);
        canvas.restore();
    }

    private int getChangedNumber(int number, int increaseSize) {
        int mask = (int) Math.pow(10, increaseSize);
        return number % mask;
    }

    private int getLastNumberSize(int number, char src) {
        int count = 1;
        int p = 0;
        String sNumber = String.valueOf(number);
        while (p < sNumber.length()) {
            if (sNumber.charAt(sNumber.length() - p - 1) == src) {
                count++;
            } else
                break;
            p++;
        }
        return count;
    }

    private int getNumberLength(int number) {
        return String.valueOf(number).length();
    }

    private void onClick() {
        press = !press;
        if (zoomOutAnimator.isRunning())
            zoomOutAnimator.cancel();
        zoomInAnimator.start();
        if (press) {
            lineOutAnimation.start();
        } else {
            lineInAnimation.start();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                LogUtil.d("ACTION_DOWN");
                zoomOutAnimator.start();
                break;
            case MotionEvent.ACTION_UP:
                LogUtil.d("ACTION_UP");
                onClick();
                break;
        }
        return true;
    }

    public void setNumber(int number) {
        mNumber = number;
        requestLayout();
    }

    public void setDuration(int duration) {
        this.duration = duration;
        zoomInAnimator.setDuration(duration);
        zoomOutAnimator.setDuration(duration);
        lineOutAnimation.setDuration(duration);
        lineInAnimation.setDuration(duration);
    }
}
