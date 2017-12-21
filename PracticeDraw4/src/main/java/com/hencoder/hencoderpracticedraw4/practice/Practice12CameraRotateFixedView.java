package com.hencoder.hencoderpracticedraw4.practice;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.hencoder.hencoderpracticedraw4.R;

public class Practice12CameraRotateFixedView extends View {
    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    Bitmap bitmap;
    Point point1 = new Point(100, 200);
    Point point2 = new Point(400, 200);

    public Practice12CameraRotateFixedView(Context context) {
        super(context);
    }

    public Practice12CameraRotateFixedView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public Practice12CameraRotateFixedView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    {
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.maps);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.save();
        canvas.translate(point1.x + bitmap.getWidth() / 2, point1.y + bitmap.getHeight() / 2);
        Camera c1 = new Camera();
        c1.setLocation(0, 0, -15);
        c1.rotateX(45);
        c1.applyToCanvas(canvas);
        canvas.translate(-point1.x - bitmap.getWidth() / 2, -point1.y - bitmap.getHeight() / 2);
        canvas.drawBitmap(bitmap, point1.x, point1.y, paint);
        canvas.restore();

        canvas.save();
        canvas.translate(point2.x + bitmap.getWidth() / 2, point2.y + bitmap.getHeight() / 2);
        Camera c2 = new Camera();
        c2.setLocation(0, 0, -15);
        c2.rotateY(45);
        c2.applyToCanvas(canvas);
        canvas.translate(-point2.x - bitmap.getWidth() / 2, -point2.y - bitmap.getHeight() / 2);
        canvas.drawBitmap(bitmap, point2.x, point2.y, paint);
        canvas.restore();
    }
}
