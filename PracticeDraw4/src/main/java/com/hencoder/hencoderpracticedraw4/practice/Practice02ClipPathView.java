package com.hencoder.hencoderpracticedraw4.practice;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.hencoder.hencoderpracticedraw4.R;

public class Practice02ClipPathView extends View {
    Paint paint = new Paint();
    Bitmap bitmap;
    Point point1 = new Point(100, 200);
    Point point2 = new Point(400, 200);

    public Practice02ClipPathView(Context context) {
        super(context);
    }

    public Practice02ClipPathView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public Practice02ClipPathView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    {
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.maps);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.save();
        Path fPath = new Path();
        fPath.addCircle(point1.x + bitmap.getWidth(), point1.y + bitmap.getHeight(), 100, Path.Direction.CW);
        Path sPath = new Path();
        sPath.setFillType(Path.FillType.INVERSE_WINDING);
        sPath.addCircle(point2.x + bitmap.getWidth(), point2.y + bitmap.getHeight(), 100, Path.Direction.CW);
        canvas.clipPath(fPath);
        canvas.drawBitmap(bitmap, point1.x, point1.y, paint);
        canvas.restore();
        canvas.save();
        canvas.clipPath(sPath);
        canvas.drawBitmap(bitmap, point2.x, point2.y, paint);
        canvas.restore();
    }
}
