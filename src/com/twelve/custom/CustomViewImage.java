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
    private String mTitle;
    private int mTextColor;
    private int mTextSize;
    private int mImageScale;
    private Bitmap mImage;
    private Paint mPaint;
    private Rect mTextBound;
    private Rect rect;

    private int mWidth;
    private int mHeight;

    public CustomViewImage(Context context) {
        this(context, null);
    }

    public CustomViewImage(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomViewImage(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        rect = new Rect();
        mPaint = new Paint();
        mTextBound = new Rect();

        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.CustomViewImage, defStyle, 0);

        int n = a.length();
        for (int i = 0; i < n; i++) {
            int attr = a.getIndex(i);
            switch (attr) {
                case R.styleable.CustomViewImage_image:
                    mImage = BitmapFactory.decodeResource(getResources(), a.getResourceId(attr, 0));
                    break;
                case R.styleable.CustomViewImage_imageScaleType:
                    mImageScale = a.getInt(attr, 0);
                    break;
                case R.styleable.CustomViewImage_titleText:
                    mTitle = a.getString(attr);
                    break;
                case R.styleable.CustomViewImage_titleTextColor:
                    mTextColor = a.getColor(attr, Color.BLACK);
                    break;
                case R.styleable.CustomViewImage_titleTextSize:
                    mTextSize = a.getDimensionPixelSize(attr, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                            16, getResources().getDisplayMetrics()));
                    break;
            }
        }
        a.recycle();
        // 计算了描绘字体需要的范围
        mPaint.setTextSize(mTextSize);
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
            // match_parent , accurate
            mWidth = widthSize;
        } else {
            // 由图片决定的宽
            int desireByImg = getPaddingLeft() + getPaddingRight() + mImage.getWidth();
            // 由字体决定的宽
            int desireByTitle = getPaddingLeft() + getPaddingRight() + mTextBound.width();

            if (widthMode == MeasureSpec.AT_MOST){
                // wrap_content
                int desire = Math.max(desireByImg, desireByTitle);
                mWidth = Math.min(desire, widthSize);
            }
        }
        /**
         * 设置高度
         */
        if (heightMode == MeasureSpec.EXACTLY){
            // match_parent , accurate
            mHeight = heightSize;
        } else {
            int desire = getPaddingTop() + getPaddingBottom() + mImage.getHeight() + mTextBound.height();
            // wrap_content
            mHeight = Math.min(desire, heightSize);
        }
        setMeasuredDimension(mWidth, mHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        /**
         * 边框：最外边，和里面的内容之间的距离--padding
         */
        mPaint.setStrokeWidth(4);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.RED);
        canvas.drawRect(0, 0, getWidth(), getHeight(), mPaint);

        rect.left = getPaddingLeft();
        rect.right = mWidth - getPaddingRight();
        rect.top = getPaddingTop();
        rect.bottom = mHeight - getPaddingBottom();

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
            //正常情况，将字体居中
            canvas.drawText(mTitle, mWidth / 2 - mTextBound.width() * 1.0f / 2, mHeight - getPaddingBottom(), mPaint);
        }

        //取消使用掉的快
        rect.bottom -= mTextBound.height();

        if (mImageScale == IMAGE_SCALE_FIT_XY) {
            canvas.drawBitmap(mImage, null, rect, mPaint);
        } else {
            //计算居中的矩形范围
            rect.left = mWidth / 2 - mImage.getWidth() / 2;
            rect.right = mWidth / 2 + mImage.getWidth() / 2;
            rect.top = (mHeight - mTextBound.height()) / 2 - mImage.getHeight() / 2;
            rect.bottom = (mHeight - mTextBound.height()) / 2 + mImage.getHeight() / 2;
            canvas.drawBitmap(mImage, null, rect, mPaint);
        }
    }
}
