package com.hencoder.hencoderpracticedraw1.practice;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class Practice10HistogramView extends View {

    public Practice10HistogramView(Context context) {
        super(context);
    }

    public Practice10HistogramView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public Practice10HistogramView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint p = new Paint();
        p.setColor(Color.GREEN);
        canvas.drawRect(30, 140, 50, 150, p);
        canvas.drawRect(70, 140, 90, 150, p);
        canvas.drawRect(110, 140, 130, 150, p);
        canvas.drawRect(150, 90, 170, 150, p);
        canvas.drawRect(190, 50, 210, 150, p);
        canvas.drawRect(230, 20, 250, 150, p);
        canvas.drawRect(270, 120, 290, 150, p);
        p.setColor(Color.WHITE);
        canvas.drawLine(10, 10, 10, 150, p);
        canvas.drawLine(10, 150, 310, 150, p);

        Rect rect = new Rect();
        p.getTextBounds("Fy", 0, "Fy".length(), rect);
        int ty = rect.height() + 150 + 5;
        canvas.drawText("Froyo", 30, ty, p);
        canvas.drawText("GB", 70, ty, p);
        canvas.drawText("ICS", 110, ty, p);
        canvas.drawText("JB", 150, ty, p);
        canvas.drawText("KitKat", 190, ty, p);
        canvas.drawText("L", 230, ty, p);
        canvas.drawText("M", 270, ty, p);
//        综合练习
//        练习内容：使用各种 Canvas.drawXXX() 方法画直方图
    }
}
