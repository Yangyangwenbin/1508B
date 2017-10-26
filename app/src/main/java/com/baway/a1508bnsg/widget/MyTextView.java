package com.baway.a1508bnsg.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.TextView;

import com.baway.a1508bnsg.R;

/**
 * Created by peng on 2017/9/28.
 */

public class MyTextView extends TextView {

    private int color;
    private Paint paint;
    private String text = "1508B";
    private Rect textRect;

    public MyTextView(Context context) {
        this(context, null);
    }

    public MyTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MyTextView);
        color = typedArray.getColor(R.styleable.MyTextView_textColor, Color.CYAN);
        typedArray.recycle();

        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStrokeWidth(2);

        //获取字宽高
        textRect = new Rect();
        paint.getTextBounds(text, 0, text.length(), textRect);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //画背景
        paint.setColor(Color.YELLOW);
        paint.setStyle(Paint.Style.FILL);
        Rect rect = new Rect(0, 0, 100,50);
        canvas.drawRect(rect, paint);
        //写字
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.STROKE);
        paint.setTextSize(15);
        int bgWidth = rect.width();
        int bgHeight = rect.height();
        int textWidth = textRect.width();
        int textHeight = textRect.height();
        int w = bgWidth/ 2 - textWidth/ 2;
        int h = bgHeight/ 2 + textHeight/ 2 ;
        canvas.drawText(text, w, h, paint);
//        canvas.drawText(text, 248, 370, paint);
    }

    public void changeText(String str) {
        this.text = str;
        invalidate();
    }
}
