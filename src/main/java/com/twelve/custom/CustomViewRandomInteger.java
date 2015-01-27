package com.twelve.custom;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import com.twelve.R;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 * Created by 刘国权 on 15-1-15.
 * 第一个自定义控件，不是我自己原创的
 * 原文地址[http://blog.csdn.net/lmj623565791/article/details/24252901#]
 * 显示4位随机数字，点击跟新数字
 */
public class CustomViewRandomInteger extends View{

    /**
     * 文本
     */
    private String mTitleText;
    /**
     * 文本的颜色
     */
    private int mTitleTextColor;
    /**
     * 文本的大小
     */
    private int mTitleTextSize;

    /**
     * 绘制时控制文本绘制的范围
     */
    private Rect mBound;
    /**
     * 画笔
     */
    private Paint mPaint;

    public CustomViewRandomInteger(Context context) {
        this(context, null);
    }

    public CustomViewRandomInteger(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    /**
     * 获得我自定义的样式属性
     *
     * @param context
     * @param attrs
     * @param defStyle
     */
    public CustomViewRandomInteger(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        mPaint = new Paint();
        mBound = new Rect();

        /**
         * 获得我们所定义的自定义样式属性
         */
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs,
                R.styleable.CustomViewRandomInteger, defStyle, 0);
        int n = a.length();
        for (int i = 0; i < n; i++)
        {
            int attr = a.getIndex(i);
            if (attr == R.styleable.CustomViewRandomInteger_titleTextColor){
                // 默认颜色设置为黑色
                mTitleTextColor = a.getColor(attr, Color.BLACK);
                /**
                 * 获得绘制文本的颜色
                 */
                mPaint.setColor(mTitleTextColor);
            }else if (attr == R.styleable.CustomViewRandomInteger_titleTextSize){
                // 默认设置为16sp，TypeValue也可以把sp转化为px
                mTitleTextSize = a.getDimensionPixelSize(attr,
                        (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 16,
                                getResources().getDisplayMetrics()));
                /**
                 * 获得绘制文本的字号
                 */
                mPaint.setTextSize(mTitleTextSize);
            }
        }
        a.recycle();

        /**
         * 获得绘制文本
         */
        mTitleText = randomText();
        /**
         * 获取文本占据上控件画布的大小
         */
        mPaint.getTextBounds(mTitleText, 0, mTitleText.length(), mBound);
        this.setOnClickListener(new OnClickListener(){

            @Override
            public void onClick(View v)
            {
                mTitleText = randomText();
                postInvalidate();
            }

        });
    }

    /**
     * 产生一个有4个数字的字符串
     */
    private String randomText()
    {
        Random random = new Random();
        Set<Integer> set = new HashSet<Integer>();
        while (set.size() < 4)
        {
            int randomInt = random.nextInt(10);
            set.add(randomInt);
        }
        StringBuffer sb = new StringBuffer();
        for (Integer i : set)
        {
            sb.append("" + i);
        }

        return sb.toString();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int width;
        int height ;
        if (widthMode == MeasureSpec.EXACTLY)
        {
            width = widthSize;
        } else
        {
            float textWidth = mBound.width();
            int desired = (int) (getPaddingLeft() + textWidth + getPaddingRight());
            width = desired;
        }

        if (heightMode == MeasureSpec.EXACTLY)
        {
            height = heightSize;
        } else{
            float textHeight = mBound.height();
            int desired = (int) (getPaddingTop() + textHeight + getPaddingBottom());
            height = desired;
        }
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        mPaint.setColor(Color.YELLOW);
        mPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
        canvas.drawRect(0, 0, getMeasuredWidth(), getMeasuredHeight(), mPaint);

        mPaint.setColor(mTitleTextColor);
        if(mTitleText !=null){
            canvas.drawText(mTitleText, getWidth() / 2 - mBound.width() / 2, getHeight() / 2 + mBound.height() / 2, mPaint);
        }
    }
}