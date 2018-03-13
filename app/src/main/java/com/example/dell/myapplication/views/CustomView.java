package com.example.dell.myapplication.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.example.dell.myapplication.R;

/**
 * Created by Vinh Nguyen on 3/9/2018.
 */

public class CustomView extends View{
    private static final int SIZE_DEF = 15;
    private Rect mRectSquare;
    private Paint mSquarePaint;
    private int x, y;

    private void init(@Nullable AttributeSet set){
        mRectSquare = new Rect();
        mSquarePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        if (set == null){
            return;
        }

        TypedArray ta = getContext().obtainStyledAttributes(set, R.styleable.MenuView);



        ta.recycle();
    }
    public CustomView(Context context){
        super(context);

        init(null);
    }
    public CustomView(Context context, AttributeSet attrs) {
        super(context, attrs);

        init(attrs);
    }
    public void getXY(int a, int b){
        x = a;
        y = b;
        postInvalidate();
    }
    @Override
    protected void onDraw(Canvas canvas){
        canvas.drawColor(Color.RED);
        mSquarePaint.setColor(Color.GREEN);
        mRectSquare.left = 30;
        mRectSquare.top = 75 * 3;
        mRectSquare.right = mRectSquare.left + SIZE_DEF;
        mRectSquare.bottom = mRectSquare.top + SIZE_DEF;
        canvas.drawRect(mRectSquare,mSquarePaint);
        mRectSquare.left = 60;
        mRectSquare.top = 130 * 3;
        mRectSquare.right = mRectSquare.left + SIZE_DEF;
        mRectSquare.bottom = mRectSquare.top + SIZE_DEF;
        canvas.drawRect(mRectSquare,mSquarePaint);
        mRectSquare.left = 60;
        mRectSquare.top = 40 * 3;
        mRectSquare.right = mRectSquare.left + SIZE_DEF;
        mRectSquare.bottom = mRectSquare.top + SIZE_DEF;
        canvas.drawRect(mRectSquare,mSquarePaint);
        mRectSquare.left = x*30;
        mRectSquare.top = y*30;
        mRectSquare.right = mRectSquare.left + SIZE_DEF;
        mRectSquare.bottom = mRectSquare.top + SIZE_DEF;
        mSquarePaint.setColor(Color.BLUE);
        canvas.drawRect(mRectSquare,mSquarePaint);
    }

}
