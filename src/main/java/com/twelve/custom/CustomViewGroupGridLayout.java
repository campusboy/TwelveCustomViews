package com.twelve.custom;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import com.twelve.R;
import com.twelve.adapter.GridChildViewContainer;

import static android.view.View.MeasureSpec.EXACTLY;
import static android.view.View.MeasureSpec.makeMeasureSpec;

/**
 * @author allen
 * @email jaylong1302@163.com
 * @date 2013-11-26 下午1:19:35
 * @company 富媒科技
 * @version 1.0
 * @description 格子布局(类似4.0中的gridlayout)
 * http://www.cnblogs.com/Jaylong/p/viewgroup.html
 *
 * 在显示之前需要执行setGridChildViewContainer方法，
 * 获取容器所包含的View对象
 */
public class CustomViewGroupGridLayout extends ViewGroup {
    /**
     * 每个格子的水平和垂直间隔
     */
	private int mIntMargin = 2;
    /**
     * 列数
     */
    private int mIntColumns = 2;
    /**
     * 容器包含的View对象的个数
     */
	private int mIntViewCount = 0;
    /**
     * View对象携带工具
     */
	private GridChildViewContainer mGridChildViewContainer;

    /**
     * 构造方法：获取自定义属性值
     * ：View对象之间的距离
     * ：View对象列数
     * @param context 上下文
     * @param attrs 属性容器
     * @param defStyle 未知
     */
	public CustomViewGroupGridLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		if (attrs != null) {
            /**
             * 获取自定义属性列表
             */
			TypedArray a = getContext().obtainStyledAttributes(attrs,
					R.styleable.CustomViewGroupGridLayout);
            int n = a.length();
            for (int i = 0; i < n; i++) {
                int attr = a.getIndex(i);
                switch (attr) {
                    case R.styleable.CustomViewGroupGridLayout_numColumns:
                        /**
                         * 获取给定的列数，默认值为2
                         */
                        mIntColumns = a.getInteger(R.styleable.CustomViewGroupGridLayout_numColumns, 2);
                        break;
                    case R.styleable.CustomViewGroupGridLayout_itemMargin:
                        /**
                         * 获取指定的距离，默认值为2
                         */
                        mIntMargin = a.getInteger(R.styleable.CustomViewGroupGridLayout_itemMargin, 2);
                        break;
                }
            }
		}
	}

	public CustomViewGroupGridLayout(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public CustomViewGroupGridLayout(Context context) {
		this(context, null);
	}


	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        /**
         * 获取ChildView的measureMode
         */
        int childModeWidth = 0, childModeHeight = 0;
        if (MeasureSpec.getMode(widthMeasureSpec) != MeasureSpec.UNSPECIFIED)
            childModeWidth = MeasureSpec.UNSPECIFIED;
        if (MeasureSpec.getMode(heightMeasureSpec) != MeasureSpec.UNSPECIFIED)
            childModeHeight = MeasureSpec.UNSPECIFIED;
        /**
         * 生成子控件的childWidthMeasureSpec和childHeightMeasureSpec
         * 这两个参数包含measureWidth&measureModeWidth和measureHeight&measureModeHeight
         * 注意这里的measureWidth和measureHeight初始化值为0，也可以是其他的任意值
         */
        final int childWidthMeasureSpec = MeasureSpec.makeMeasureSpec(0, childModeWidth);
        final int childHeightMeasureSpec = MeasureSpec.makeMeasureSpec(0, childModeHeight);

        mIntViewCount = getChildCount();
        if (mIntViewCount == 0) {
            super.onMeasure(childWidthMeasureSpec, childHeightMeasureSpec);
            return;
        }
        for (int i = 0; i < mIntViewCount; i++) {
            final View child = getChildAt(i);
            if (child.getVisibility() == GONE) {
                continue;
            }
            /**
             * 测量并设置子控件的宽度和高度
             */
            child.measure(childWidthMeasureSpec, childHeightMeasureSpec);
        }
        /**
         * 设置容器的高度和宽度
         */
        setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec),MeasureSpec.getSize(heightMeasureSpec));
	}

    /**
     * 这里传入的四个参数l,t,r,b是onMeasure测量的容器的矩形区域
     * (l,t)是容器的左上角起点坐标，(r,b)是容器的右下角终点坐标
     */
	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
        /**
         * 布局区域高度
         */
		int height = b - t;
        /**
         * 布局区域宽度
         */
		int width =  r - l;
        /**
         * 计算控件摆放需要的行数
         */
		int rows = mIntViewCount % mIntColumns == 0 ? mIntViewCount / mIntColumns
                : mIntViewCount / mIntColumns + 1;
		if (mIntViewCount == 0)
			return;
        /**
         * 计算每个格子的宽度：除去间隔之后控件所占的净宽度
         */
		int gridW = (width - mIntMargin * (mIntColumns - 1)) / mIntColumns;
        /**
         * 计算每个格子的高度：除去间隔之后控件所占的净高度
         */
		int gridH = (height - mIntMargin * rows) / rows;

        /**
         * 开始摆放控件：初始化位置左边顶边界，上面留间隔
         */
		int leftPointer = 0;
		int topPointer = mIntMargin;

        /**
         * 遍历行
         */
		for (int row = 0; row < rows; row++) {
            /**
             * 遍历列
             */
			for (int column = 0; column < mIntColumns; column++) {
                /**
                 * 获取某行某列的子元素
                 */
				View child = this.getChildAt(row * mIntColumns + column);
				if (child == null)
					return;
                /**
                 * 左上角起点x坐标leftPointer由列宽gridW列序号和间隔数决定；
                 * 左上角起点y坐标topPointer由行高gridH行序号和间隔数决定，
                 * 在某一行中所有元素都是相同的；
                 */
				leftPointer = column * gridW + column * mIntMargin;
                /**
                 * 如果当前布局宽度和测量宽度不一样，就直接用当前布局的宽度重新测量
                 */
				if (gridW != child.getMeasuredWidth()
						|| gridH != child.getMeasuredHeight()) {
					child.measure(makeMeasureSpec(gridW, EXACTLY),
							makeMeasureSpec(gridH, EXACTLY));
				}
                /**
                 * 摆放子控件
                 */
				child.layout(leftPointer, topPointer, leftPointer + gridW, topPointer + gridH);
			}
            /**
             * 一行遍历结束，行起点y坐标下移
             */
			topPointer += gridH + mIntMargin;
		}
	}

	/**
     * 从给定的View携带工具中提取View对象
     * */
	public void setGridChildViewContainer(GridChildViewContainer container) {
		this.mGridChildViewContainer = container;
        /**
         * 提取View对象
         */
		int size = container.getCount();
		for (int i = 0; i < size; i++) {
			addView(container.getView(i));
		}
	}

    /**
     * View视图点击事件：适用所有View对象
     */
	public interface OnItemClickListener {
		void onItemClick(View v, int index);
	}

    /**
     * 为容器中每个对象设置点击事件：
     * @param click 容器子View点击事件
     */
	public void setOnItemClickListener(final OnItemClickListener click) {
		if (this.mGridChildViewContainer == null)
			return;
		for (int i = 0; i < mGridChildViewContainer.getCount(); i++) {
			final int index = i;
			View view = getChildAt(i);
			view.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					click.onItemClick(v, index);
				}
			});
		}
	}
}
