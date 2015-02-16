package com.twelve.custom;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 刘国权 on 15-1-17.
 * 第一个自定义容器，不是我自己原创的
 * 原文地址[http://blog.csdn.net/lmj623565791/article/details/38352503]
 * 流式布局
 */
public class CustomViewGroupFlowLayout extends ViewGroup{

    public CustomViewGroupFlowLayout(Context context) {
        super(context);
    }

    public CustomViewGroupFlowLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomViewGroupFlowLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    /**
     * 负责设置子控件的测量模式和大小，根据所有子控件设置自己的宽和高
     * 并完成按行分组
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        /**
         * 获得它的父容器为它设置的测量模式及高度和宽度
         */
        int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
        int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);
        int modeWidth = MeasureSpec.getMode(widthMeasureSpec);
        int modeHeight = MeasureSpec.getMode(heightMeasureSpec);
        /**
         * 计算由子控件决定的高度和宽度：
         * (1)子控件按顺序累加宽度到lineWidth，当lineWidth的值加上当前控件宽度大于sizeWidth，
         *      取width = Max(lineWidth,width),并计算该行最大高度，累加到height；
         * (2)然后，另起一行，从上一行的位置开始，重新累加lineWidth；
         */
        /**
         * 计算结果的宽度
         */
        int width = 0;
        /**
         * 计算结果的高度
         */
        int height = 0;
        /**
         * 记录每一行的宽度，width不断取最大宽度
         */
        int lineWidth = 0;
        /**
         * 每一行的高度，累加至height
         */
        int lineHeight = 0;
        /**
         * 子控件个数
         */
        int childCount = getChildCount();

        /**
         * 遍历每个子元素
         */
        for (int index = 0; index < childCount; index++){
            /**
             * 当前子控件child
             */
            View child = getChildAt(index);
            /**
             * 测量并设置child的宽和高
             */
            measureChild(child, widthMeasureSpec, heightMeasureSpec);
            /**
             * 得到child的布局参数
             */
            MarginLayoutParams lp = (MarginLayoutParams) child
                    .getLayoutParams();
            /**
             * 当前子控件实际占据的宽度：实际宽度 + 左右两边的距离
             */
            int childWidth = child.getMeasuredWidth() + lp.leftMargin
                    + lp.rightMargin;
            /**
             * 当前子控件实际占据的高度：实际高度 + 上下两方向的距离
             */
            int childHeight = child.getMeasuredHeight() + lp.topMargin
                    + lp.bottomMargin;
            /**
             * 如果加入当前child，宽度依然在sizeWidth内，
             * 则的到目前最大高度给lineHeight，宽度类加lineWidth，
             * 然后测量下一个子控件
             */
            if (lineWidth + childWidth <= sizeWidth){
                /**
                 * 累加childWidth值到lineWidth,lineHeight取最大高度
                 */
                lineWidth += childWidth;
                lineHeight = Math.max(lineHeight, childHeight);
            } else{
                /**
                 * 如果加入当前child，则超出最大宽度，则的到目前最大宽度给width，类加height 然后开启新行
                 */

                width = Math.max(lineWidth, childWidth);
                /**
                 * 重新开启新行，开始记录：因为lineWidth + childWidth > sizeWidth
                 * 参与Max计算的lineWidth没有加入childWidth，所以，child是下一行的第一个值，
                 * 即lineWidth 的初始值；
                 */
                lineWidth = childWidth;
                /**
                 * 叠加当前高度
                 */
                height += lineHeight;
                /**
                 * 开启记录下一行的高度
                 */
                lineHeight = childHeight;
            }
            /**
             * 如果是最后一个，则将当前记录的最大宽度和当前lineWidth做比较
             */
            if (index == childCount - 1){
                width = Math.max(width, lineWidth);
                height += lineHeight;
            }
        }
        /**
         * 判断高度模式和宽度模式
         * （1）EXACTLY：指定高度
         * 如果是指定高度，丢弃上述计算结果
         */
        setMeasuredDimension(
                (modeWidth == MeasureSpec.EXACTLY) ? sizeWidth: width,
                (modeHeight == MeasureSpec.EXACTLY) ? sizeHeight: height);

    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
    }

    /**
     * 存储所有的View，按行记录
     */
    private List<List<View>> mAllViews = new ArrayList<List<View>>();
    /**
     * 记录每一行的最大高度
     */
    private List<Integer> mLineHeight = new ArrayList<Integer>();

    /**
     * 摆子控件:
     * @param changed 是否更改
     * @param left 左
     * @param top 上
     * @param right 右
     * @param bottom 底
     */
    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom){
        mAllViews.clear();
        mLineHeight.clear();
        /**
         * onMeasure的测量结果
         */
        int width = getWidth();
        /**
         * 行宽度
         */
        int lineWidth = 0;
        /**
         * 行高度
         */
        int lineHeight = 0;
        /**
         * 存储一行的childView
         */
        List<View> lineViews = new ArrayList<View>();
        /**
         * 子控件个数
         */
        int childCount = getChildCount();
        /**
         * 遍历所有子控件
         */
        for (int i = 0; i < childCount; i++){
            /**
             * 当前子控件
             */
            View child = getChildAt(i);
            MarginLayoutParams lp = (MarginLayoutParams) child
                    .getLayoutParams();
            int childWidth = child.getMeasuredWidth();
            int childHeight = child.getMeasuredHeight();

            /**
             * 如果lineWidth + 控件宽度 + 控件与两边距离 > 容器测量宽度width
             * 需要换行，记录本行的高度和带有本行所有控件的列表，
             * 然后重置行宽lineWidth和lineViews
             */
            if (childWidth + lp.leftMargin + lp.rightMargin + lineWidth > width){
                /**
                 * 记录这一行所有的View的最大高度
                 */
                mLineHeight.add(lineHeight);
                /**
                 * 将当前行的childView保存
                 */
                mAllViews.add(lineViews);
                /**
                 * 重置行宽
                 */
                lineWidth = 0;
                /**
                 * 开启新的ArrayList保存下一行的childView
                 */
                lineViews = new ArrayList<View>();
            }

            /**
             * 累加行宽：如果已经换行，累加到新的行；如果没有换行，累加到原来的行
             */
            lineWidth += childWidth + lp.leftMargin + lp.rightMargin;
            /**
             * 记录当前行的最大高度：
             * 如果已经换行，记录的是新的行的第一个控件的高度；
             * 如果没有换行，记录的是原来行的最大高度
             */
            lineHeight = Math.max(lineHeight, childHeight + lp.topMargin
                    + lp.bottomMargin);
            lineViews.add(child);
        }
        /**
         * 记录最后一行的最大高度
         */
        mLineHeight.add(lineHeight);
        /**
         * 最后一行控件列表
         */
        mAllViews.add(lineViews);
        /**
         * 得到总行数
         */
        int countLines = mAllViews.size();
        /**
         * 遍历处理每一行的控件
         */
        /**
         * 当前控件(包含Margin)在容器中的x轴起始坐标
         */
        int leftPointer = 0;
        /**
         * 当前控件(包含Margin)在容器中的y轴起始坐标
         */
        int topPointer= 0;
        for (int lineIndex = 0; lineIndex < countLines; lineIndex++){
            /**
             * 获取当前行的控件列表
             */
            lineViews = mAllViews.get(lineIndex);
            /**
             * 获取当前行的最大高度
             */
            lineHeight = mLineHeight.get(lineIndex);

            /**
             * 遍历当前行所有控件
             */
            for (int viewIndex = 0; viewIndex < lineViews.size(); viewIndex++){
                /**
                 * 当前处理控件
                 */
                View child = lineViews.get(viewIndex);
                /**
                 * 处理可视参数
                 */
                if (child.getVisibility() == View.GONE){
                    continue;
                }
                MarginLayoutParams lp = (MarginLayoutParams) child
                        .getLayoutParams();

                /**
                 * 计算控件在容器中的矩形位置区域
                 * (lc,tc)左上角位置，(rc,bc)右下角位置
                 */
                int lc = leftPointer + lp.leftMargin;
                int tc = topPointer + lp.topMargin;
                int rc =lc + child.getMeasuredWidth();
                int bc = tc + child.getMeasuredHeight();
                /**
                 * 摆控件
                 */
                child.layout(lc, tc, rc, bc);
                /**
                 * 向左移动x轴起始位置：因为同一行的y轴起始位置相同
                 */
                leftPointer += child.getMeasuredWidth() + lp.rightMargin
                        + lp.leftMargin;
            }
            /**
             * 一行处理结束后，另起一行
             * x轴起始位置清零；
             * y轴起始位置向下移动，移动距离是上一行的行高
             */
            leftPointer = 0;
            topPointer += lineHeight;
        }
    }

    /**
     * 因为我们只支持margin，所以直接使用系统的MarginLayoutParams
     */
    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }
}
