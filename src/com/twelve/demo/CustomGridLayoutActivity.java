package com.twelve.demo;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.twelve.R;
import com.twelve.custom.CustomViewGroupGridLayout;
import com.twelve.custom.CustomViewGroupGridLayout.GridAdapter;

/**
 * Created by Administrator on 15-1-18.
 */
public class CustomGridLayoutActivity extends Activity {

    private CustomViewGroupGridLayout mCustomViewGroupGridLayout;
    int[] sources = { R.drawable.actions_book_tag, R.drawable.actions_comment,
            R.drawable.actions_order, R.drawable.actions_account,
            R.drawable.actions_cent, R.drawable.actions_weibo,
            R.drawable.actions_feedback, R.drawable.actions_about };
    String titles[] = { "书签", "推荐", "订阅", "账户", "积分", "微博", "反馈", "关于我们" };
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_grid_layout);
        mCustomViewGroupGridLayout = (CustomViewGroupGridLayout) findViewById(R.id.list);
        mCustomViewGroupGridLayout.setGridAdapter(new GridAdapter() {

            @Override
            public View getView(int index) {
                View view = getLayoutInflater().inflate(R.layout.demo_grid_layout_actions_item,
                        null);
                ImageView iv = (ImageView) view.findViewById(R.id.iv);
                TextView tv = (TextView) view.findViewById(R.id.tv);
                iv.setImageResource(sources[index]);
                tv.setText(titles[index]);
                return view;
            }

            @Override
            public int getCount() {
                // TODO Auto-generated method stub
                return titles.length;
            }
        });
        mCustomViewGroupGridLayout.setOnItemClickListener(new CustomViewGroupGridLayout.OnItemClickListener() {

            @Override
            public void onItemClick(View v, int index) {
                // TODO Auto-generated method stub
                Toast.makeText(getApplicationContext(), "item=" + index, 0).show();
            }
        });
    }
}