package com.hencoder.hencoderpracticedraw1.practice;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;

public class Practice11PieChartView extends View {

    public Practice11PieChartView(Context context) {
        super(context);
    }

    public Practice11PieChartView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public Practice11PieChartView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint p = new Paint();
        p.setColor(Color.YELLOW);
        p.setStyle(Paint.Style.FILL);
        canvas.drawArc(50, 20, 300, 270, -45, 40, true, p);
        p.setColor(Color.parseColor("#006615"));
        canvas.drawArc(50, 20, 300, 270, 0, 10, true, p);
        p.setColor(Color.GRAY);
        canvas.drawArc(50, 20, 300, 270, 15, 10, true, p);
        p.setColor(Color.GREEN);
        canvas.drawArc(50, 20, 300, 270, 30, 45, true, p);
        p.setColor(Color.BLUE);
        canvas.drawArc(50, 20, 300, 270, 80, 90, true, p);
        p.setColor(Color.RED);
        int off = 5;
        canvas.drawArc(50 - off, 20 - off, 300 - off, 270 - off, 180, 130, true, p);


//        综合练习
//        练习内容：使用各种 Canvas.drawXXX() 方法画饼图
    }
}
