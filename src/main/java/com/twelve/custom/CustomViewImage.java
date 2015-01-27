package com.twelve.custom;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.*;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import com.twelve.R;

/**
 * Created by 刘国权 on 15-1-15.
 * 第一个自定义控件，不是我自己原创的
 * 原文地址[http://blog.csdn.net/gebitan505/article/details/27679587]
 * 显示文字和图像：文字位于图像的下方
 */
public class CustomViewImage extends View {

    private int IMAGE_SCALE_FIT_XY = 0;
    /**
     * 要显示的文字
     */
    private String mTitle;
    /**
     * 要显示的文字的颜色
     */
    private int mTextColor;
    /**
     * 要显示的文字的字号
     */
    private int mTextSize;
    /**
     * 图像显示模式
     */
    private int mImageScale;
    /**
     * 图像对象
     */
    private Bitmap mImage;
    /**
     * 画笔
     */
    private Paint mPaint;
    /**
     * 文本文字绘制大小
     */
    private Rect mTextBound;
    /**
     * 绘制内容时设置内容的边界
     */
    private Rect rect;
    /**
     * 控件宽度
     */
    private int mWidth;
    /**
     * 控件高度
     */
    private int mHeight;

    public CustomViewImage(Context context) {
        this(context, null);
    }

    public CustomViewImage(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomViewImage(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        /**
         * 初始化控件边界
         */
        rect = new Rect();
        /**
         * 初始化画笔对象
         */
        mPaint = new Paint();
        /**
         * 初始化文本绘制框
         */
        mTextBound = new Rect();

        /**
         * 获取文本和图像属性
         */
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.CustomViewImage, defStyle, 0);
        int n = a.length();
        for (int i = 0; i < n; i++) {
            int attr = a.getIndex(i);
            switch (attr) {
                case R.styleable.CustomViewImage_image:
                    /**
                     * 获取图像对象
                     */
                    mImage = BitmapFactory.decodeResource(getResources(), a.getResourceId(attr, 0));
                    break;
                case R.styleable.CustomViewImage_imageScaleType:
                    /**
                     * 获取图像显示模式
                     */
                    mImageScale = a.getInt(attr, 0);
                    break;
                case R.styleable.CustomViewImage_titleText:
                    /**
                     * 获取文本内容
                     */
                    mTitle = a.getString(attr);
                    break;
                case R.styleable.CustomViewImage_titleTextColor:
                    /**
                     * 获取文本颜色
                     */
                    mTextColor = a.getColor(attr, Color.BLACK);
                    break;
                case R.styleable.CustomViewImage_titleTextSize:
                    /**
                     * 获取文本字号
                     */
                    mTextSize = a.getDimensionPixelSize(attr, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                            16, getResources().getDisplayMetrics()));
                    mPaint.setTextSize(mTextSize);
                    break;
            }
        }
        a.recycle();
        /**
         * 获取文本占据上控件画布的大小
         */
        mPaint.getTextBounds(mTitle, 0, mTitle.length(), mTextBound);
    }

    /**
     * 确定视图的总宽度和总高度
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        /**
         * 设置宽度
         */
        if (widthMode == MeasureSpec.EXACTLY){
            /**
             * 如果显示模式为指定大小，宽度就是给定的宽度
             */
            mWidth = widthSize;
        } else {
            /**
             * 由图片决定的宽度：图片本身的宽度+与边界的距离
             */
            int desireByImg = getPaddingLeft() + getPaddingRight() + mImage.getWidth();
            /**
             * 由文字决定的宽度：文字本身的宽度+与边界的距离
             */
            int desireByTitle = getPaddingLeft() + getPaddingRight() + mTextBound.width();

            /**
             * 可变宽度下，原则上使得内容显示不超出边界。
             */
            int desire = Math.max(desireByImg, desireByTitle);
            mWidth = Math.min(desire, widthSize);
        }
        /**
         * 设置高度
         */
        if (heightMode == MeasureSpec.EXACTLY){
            /**
             * 如果显示模式为指定大小，高度就是给定的高度
             */
            mHeight = heightSize;
        } else {
            /**
             * 由图片和文字决定的高度：图片本身的高度+文字本身高度+与边界的距离
             */
            int desire = getPaddingTop() + getPaddingBottom() + mImage.getHeight() + mTextBound.height();
            /**
             * 可变高度下，原则上使得内容显示不超出边界。
             */
            mHeight = Math.min(desire, heightSize);
        }
        setMeasuredDimension(mWidth, mHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        mPaint.setStrokeWidth(4);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.BLUE);
        /**
         * 边框：View视图最外边框，与内容距离--padding
         */
        canvas.drawRect(0,0,getWidth(),getHeight(), mPaint);

        rect.left = getPaddingLeft();
        rect.right = mWidth - getPaddingRight();
        rect.top = getPaddingTop();
        rect.bottom = mHeight - getPaddingBottom();

        /**
         * 边框：紧贴着文字和图像内容的长方形边框
         */
        mPaint.setColor(Color.RED);
        canvas.drawRect(rect, mPaint);

        mPaint.setColor(mTextColor);
        mPaint.setStyle(Paint.Style.FILL);
        /**
         * 当前设置的宽度小于字体需要的宽度，将字体改为xxx...
         */
        if (mTextBound.width() > mWidth) {
            TextPaint paint = new TextPaint(mPaint);
            String msg = TextUtils.ellipsize(mTitle, paint, (float) mWidth - getPaddingLeft() - getPaddingRight(),
                    TextUtils.TruncateAt.END).toString();
            canvas.drawText(msg, getPaddingLeft(), mHeight - getPaddingBottom(), mPaint);
        } else {
            /**
             * 正常情况，将字体居中
             */
            canvas.drawText(mTitle, mWidth / 2 - mTextBound.width() * 1.0f / 2, mHeight - getPaddingBottom(), mPaint);
        }

        /**
         * 图形显示在文字的上方，图形可使用的位置高度要减去文字已经使用的高度。
         */
        rect.bottom -= mTextBound.height();

        /**
         * 如果图片显示模式为填充模式，图片沾满余下的空间
         */
        if (mImageScale == IMAGE_SCALE_FIT_XY) {
            canvas.drawBitmap(mImage, null, rect, mPaint);
        } else {
            /**
             * 如果不沾满，图形居中显示，有可能图像不能完全显示在给定的范围内
             */
            rect.left = mWidth / 2 - mImage.getWidth() / 2;
            rect.right = mWidth / 2 + mImage.getWidth() / 2;
            rect.top = (mHeight - mTextBound.height()) / 2 - mImage.getHeight() / 2;
            rect.bottom = (mHeight - mTextBound.height()) / 2 + mImage.getHeight() / 2;
            canvas.drawBitmap(mImage, null, rect, mPaint);
        }
    }
}
