package com.twelve.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.twelve.R;

/**
 * Created by 刘国权 on 15-1-27.
 * 给定一组图像和一组文字描述，
 * 组合成一组包含文字和图像的视图
 */
public class GridChildViewContainer {
    private Context mContext;
    /**
     * 布局加载器
     */
    private LayoutInflater mLayoutInflater;
    /**
     * 给定的一组图像资源
     */
    private int[] mIntImageResources;
    /**
     * 给定的一组图像描述
     */
    private String [] mStrTitles;

    /**
     * 构造方法
     * @param context 上下文
     * @param mIntImageResources 图像资源
     * @param mStrTitles 文字描述
     */
    public GridChildViewContainer(Context context, int[] mIntImageResources, String[] mStrTitles) {
        this.mContext = context;
        this.mLayoutInflater = LayoutInflater.from(mContext);
        this.mIntImageResources = mIntImageResources;
        this.mStrTitles = mStrTitles;
    }

    /**
     * 组合方法：把对应的图像和文字组合成视图
     * @param index 数组下标
     * @return 组合后的View对象
     */
    public View getView(int index){
        View view = mLayoutInflater.inflate(R.layout.demo_grid_layout_actions_item,
                null);
        String title = "";
        if(mStrTitles != null && index < mStrTitles.length){
            title = mStrTitles[index];
        }
        ImageView iv = (ImageView) view.findViewById(R.id.iv);
        TextView tv = (TextView) view.findViewById(R.id.tv);
        iv.setImageResource(mIntImageResources[index]);
        tv.setText(title);
        return view;
    }

    /**
     * 获取携带View对象的个数
     * @return 返回图像的个数
     */
    public int getCount(){
        if(this.mIntImageResources == null){
            return 0;
        }
        return mIntImageResources.length;
    }
}
