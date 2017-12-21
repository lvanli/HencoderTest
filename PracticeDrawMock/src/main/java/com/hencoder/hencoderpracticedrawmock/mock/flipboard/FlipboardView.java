package com.hencoder.hencoderpracticedrawmock.mock.flipboard;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.hencoder.hencoderpracticedrawmock.R;

public class FlipboardView extends View {
    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    Camera camera = new Camera();
    float upDegree = 0;
    float rotateDegree = 0;
    float secondUpDegree = 0;

    public float getUpDegree() {
        return upDegree;
    }

    public void setUpDegree(float upDegree) {
        this.upDegree = upDegree;
        invalidate();
    }

    public float getRotateDegree() {
        return rotateDegree;
    }

    public void setRotateDegree(float rotateDegree) {
        this.rotateDegree = rotateDegree;
        invalidate();
    }

    public float getSecondUpDegree() {
        return secondUpDegree;
    }

    public void setSecondUpDegree(float secondUpDegree) {
        this.secondUpDegree = secondUpDegree;
        invalidate();
    }

    public void init() {
        upDegree = 0;
        rotateDegree = 0;
        secondUpDegree = 0;
        invalidate();
    }

    public FlipboardView(Context context) {
        super(context);
    }

    public FlipboardView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public FlipboardView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private Bitmap bitmap;

    {
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.maps);
    }


    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.GRAY);
        int bitmapWidth = bitmap.getWidth();
        int bitmapHeight = bitmap.getHeight();
        int centerX = getWidth() / 2;
        int centerY = getHeight() / 2;
        int x = centerX - bitmapWidth / 2;
        int y = centerY - bitmapHeight / 2;

        canvas.save();
        camera.save();
        canvas.translate(centerX, centerY);
        canvas.rotate(rotateDegree);
        camera.rotateY(upDegree);
        canvas.clipRect(0, 0 - bitmapHeight * 2, bitmapWidth * 2, bitmapHeight * 2);
        camera.applyToCanvas(canvas);
        canvas.rotate(-rotateDegree);
        canvas.translate(-centerX, -centerY);
        camera.restore();
        canvas.drawBitmap(bitmap, x, y, paint);
        canvas.restore();

        canvas.save();
        camera.save();
        canvas.translate(centerX, centerY);
        canvas.rotate(rotateDegree);
        camera.rotateY(secondUpDegree);
        canvas.clipRect(0 - bitmapWidth * 2, 0 - bitmapHeight * 2, 0, bitmapHeight * 2);
        camera.applyToCanvas(canvas);
        canvas.rotate(-rotateDegree);
        canvas.translate(-centerX, -centerY);
        camera.restore();
        canvas.drawBitmap(bitmap, x, y, paint);
        canvas.restore();
    }
}
