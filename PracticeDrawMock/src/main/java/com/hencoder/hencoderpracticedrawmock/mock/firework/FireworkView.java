package com.hencoder.hencoderpracticedrawmock.mock.firework;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.hencoder.hencoderpracticedrawmock.mock.util.DisplayUtil;

/**
 * Created by lizhiguang on 17/12/19.
 */

public class FireworkView extends View {
    public static final short STATUS_STRAGGLY = 0;
    public static final short STATUS_CIRCLE_SCALE = 1;
    public static final short STATUS_CIRCLE = 2;
    private int width, height;

    private Paint paint;
    private int mBig = 1000;
    private int mCircleOffset[];
    private int mCircleWidth = 10;
    private int mScaleCircleWidth = mCircleWidth * 3;
    private int mPadding = 50;
    private int mFoot = 2274;
    private int mMales = 15;
    private int mKBL = 34;
    private float mCurDegree = 45;
    private int mMaxPointDegree = 40;
    private int mMaxPointLong = 200;
    private short mCircleStatus = STATUS_STRAGGLY;
    private int mCircleRadio = mBig / 2 - mPadding - mScaleCircleWidth / 2;

    public FireworkView(Context context) {
        this(context, null);
    }

    public FireworkView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FireworkView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setColor(Color.WHITE);
        paint.setStyle(Paint.Style.STROKE);

        mCircleOffset = new int[20];
        for (int i = 0; i < mCircleOffset.length; i += 2) {
            mCircleOffset[i] = (int) getRandomOffset(mCircleWidth, -mCircleWidth);
            mCircleOffset[i + 1] = (int) getRandomOffset(mCircleWidth, -mCircleWidth);
        }
    }

    public void setDegree(float degree) {
        mCurDegree = degree;
        invalidate();
    }

    public void setRadio(int radio) {
        mCircleRadio = radio;
    }

    public int getRadio() {
        return mCircleRadio;
    }

    public void setStatus(short status) {
        mCircleStatus = status;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int mode = MeasureSpec.getMode(widthMeasureSpec);
        switch (mode) {
            case MeasureSpec.UNSPECIFIED:
            case MeasureSpec.AT_MOST:
                setMeasuredDimension(mBig, mBig);
                break;
        }
        width = getMeasuredWidth();
        height = getMeasuredHeight();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.parseColor("#87CEEB"));
        drawMiddle(canvas);
        switch (mCircleStatus) {
            case STATUS_STRAGGLY:
                drawStraggly(canvas);
                drawPoints(canvas);
                break;
            case STATUS_CIRCLE_SCALE:
                drawScaleCircle(canvas);
                break;
            case STATUS_CIRCLE:
                drawCircle(canvas);
                drawCircleShadow(canvas);
                drawScaleCircle(canvas);
                break;
        }
    }

    private void drawCircleShadow(Canvas canvas) {
        float centerX, centerY;
        int oldAlpha = paint.getAlpha();
        paint.setStrokeWidth(mScaleCircleWidth);
        Path path = new Path();
        float alpha[] = new float[]{0.25f, 0.4f, 0.55f, 0.7f, 0.85f};
        float lastX = width / 2, lastY = height / 2;
        for (int i = 1; i <= 5; i++) {
            centerX = getLinePointX(width / 2, mScaleCircleWidth * i / 5, mCurDegree);
            centerY = getLinePointY(height / 2, mScaleCircleWidth * i / 5, mCurDegree);
            paint.setAlpha((int) (alpha[alpha.length - i] * 0xff));
            canvas.save();
            path.reset();
            path.setFillType(Path.FillType.INVERSE_WINDING);
            path.addCircle(lastX, lastY, height / 2 - mPadding, Path.Direction.CW);
            canvas.clipPath(path);
            canvas.drawCircle(centerX, centerY, mCircleRadio, paint);
            canvas.restore();
            lastX = centerX;
            lastY = centerY;
        }
        paint.setAlpha(oldAlpha);
    }

    private void drawScaleCircle(Canvas canvas) {
        paint.setStrokeWidth(mScaleCircleWidth);
        Shader shader = new SweepGradient(width / 2, height / 2, new int[]{Color.parseColor("#FFFFFFFF"),
                Color.parseColor("#80FFFFFF"), Color.parseColor("#FFFFFFFF")}, new float[]{0, 0.5f, 1});
        Matrix rotate = new Matrix();
        rotate.setRotate(mCurDegree - 90, width / 2, height / 2);
        shader.setLocalMatrix(rotate);
        paint.setShader(shader);
        canvas.drawCircle(width / 2, height / 2, mCircleRadio, paint);

        paint.setShader(null);
    }

    private void drawCircle(Canvas canvas) {
        paint.setStrokeWidth(5f);
        int inRadio = mCircleRadio - 40;
        PathEffect pathEffect = new DashPathEffect(new float[]{2, 8}, 0);
        paint.setPathEffect(pathEffect);
        Path path = new Path();
        path.addCircle(width / 2, height / 2, inRadio, Path.Direction.CW);
        canvas.drawPath(path, paint);
        paint.setPathEffect(null);
        canvas.drawArc(new RectF(width / 2 - inRadio, height / 2 - inRadio, width / 2 + inRadio, height / 2 + inRadio), 270, 270, false, paint);
    }

    private void drawStraggly(Canvas canvas) {
        paint.setStrokeWidth(2f);
        Shader shader = new SweepGradient(width / 2, height / 2, Color.parseColor("#00FFFFFF"), Color.parseColor("#FFFFFFFF"));
        Matrix rotate = new Matrix();
        paint.setShader(shader);
        for (int i = 0; i < mCircleOffset.length; i += 2) {
            rotate.setRotate(mCurDegree - 90 + mCircleOffset[i] - mCircleWidth, width / 2, height / 2);
            shader.setLocalMatrix(rotate);
            canvas.drawCircle(width / 2 + mCircleOffset[i], height / 2 + mCircleOffset[i + 1], width / 2 - mCircleWidth * 2 - mPadding, paint);
        }
        paint.setShader(null);
    }

    private void drawMiddle(Canvas canvas) {
        Paint.Style oldStyle = paint.getStyle();
        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeWidth(2);
        paint.setTextSize(DisplayUtil.px2sp(getContext(), height / 2));
        float bigW = paint.measureText(String.valueOf(mFoot));
        Rect textRect = new Rect();
        paint.getTextBounds(String.valueOf(mFoot), 0, 1, textRect);
        float bigH = textRect.bottom - textRect.top;//paint.getTextBounds()
        canvas.drawText(String.valueOf(mFoot), width / 2 - bigW / 2, height / 2 + bigH / 2, paint);

        String smallText = String.format("%d.%d 公里 | %d千卡", mMales / 10, mMales % 10, mKBL);
        paint.setTextSize(paint.getTextSize() / 4);
        float smallW = paint.measureText(smallText);
        canvas.drawText(smallText, width / 2 - smallW / 2, height / 2 + bigH / 2 + paint.getFontSpacing(), paint);
        paint.setStyle(oldStyle);
    }

    private void drawPoints(Canvas canvas) {
        Paint.Style oldStyle = paint.getStyle();
        paint.setStyle(Paint.Style.FILL);
        int oldAlpha = paint.getAlpha();
        paint.setStrokeWidth(15);
        float pointAX = getLinePointX(width / 2, width / 2 - mCircleWidth - mPadding, mCurDegree);
        float pointAY = getLinePointY(height / 2, height / 2 - mCircleWidth - mPadding, mCurDegree);
        float pointBX = getLinePointX(pointAX, mMaxPointLong, (mCurDegree + 270) % 360);
        float pointBY = getLinePointY(pointAY, mMaxPointLong, (mCurDegree + 270) % 360);
        float pointCX = getLinePointX(pointAX, mMaxPointLong, (mCurDegree + 270 - mMaxPointDegree) % 360);
        float pointCY = getLinePointY(pointAY, mMaxPointLong, (mCurDegree + 270 - mMaxPointDegree) % 360);
        float u, v;
        float pX, pY;
        for (int i = 0; i < 20; i++) {
            u = getRandomOffset(1f, 0);
            v = getRandomOffset(1f - u, 0);
            pX = u * (pointAX - pointBX) + v * (pointCX - pointBX) + pointBX;
            pY = u * (pointAY - pointBY) + v * (pointCY - pointBY) + pointBY;
            paint.setAlpha((int) (u * 0xFF));
            canvas.drawPoint(pX, pY, paint);
        }
        paint.setAlpha(oldAlpha);
        paint.setStyle(oldStyle);
    }

    private float getRandomOffset(float max, float min) {
        double random = Math.random();
        return (float) (random * (max - min) + min);
    }

    private float getLinePointX(double beginX, int length, float degree) {
        if (degree >= 0 && degree <= 90) {
            return (float) (beginX + length * Math.sin(Math.PI * degree / 180));
        } else if (degree <= 180) {
            return (float) (beginX + length * Math.sin(Math.PI * (180 - degree) / 180));
        } else if (degree <= 270) {
            return (float) (beginX - length * Math.sin(Math.PI * (degree - 180) / 180));
        } else if (degree <= 360) {
            return (float) (beginX - length * Math.sin(Math.PI * (360 - degree) / 180));
        } else
            return -1;
    }

    private float getLinePointY(double beginY, int length, float degree) {
        if (degree >= 0 && degree <= 90) {
            return (float) (beginY - length * Math.cos(Math.PI * degree / 180));
        } else if (degree <= 180) {
            return (float) (beginY + length * Math.cos(Math.PI * (180 - degree) / 180));
        } else if (degree <= 270) {
            return (float) (beginY + length * Math.cos(Math.PI * (degree - 180) / 180));
        } else if (degree <= 360) {
            return (float) (beginY - length * Math.cos(Math.PI * (360 - degree) / 180));
        } else
            return -1;
    }
}
