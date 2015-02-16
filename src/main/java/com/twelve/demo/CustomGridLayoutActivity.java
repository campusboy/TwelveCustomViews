package com.twelve.demo;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import com.twelve.R;
import com.twelve.adapter.GridChildViewContainer;
import com.twelve.custom.CustomViewGroupGridLayout;

/**
 * Created by 刘国权 on 15-1-18.
 */
public class CustomGridLayoutActivity extends Activity {

    private CustomViewGroupGridLayout mCustomViewGroupGridLayout;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_grid_layout);
        mCustomViewGroupGridLayout = (CustomViewGroupGridLayout) findViewById(R.id.list);
        mCustomViewGroupGridLayout.setGridChildViewContainer(new GridChildViewContainer(this,
                new int[]{R.drawable.ic_launcher, R.drawable.actions_comment,
                        R.drawable.actions_order, R.drawable.actions_account,
                        R.drawable.actions_cent, R.drawable.actions_weibo,
                        R.drawable.actions_feedback, R.drawable.actions_about},
                new String[]{"书签", "推荐", "订阅", "账户", "积分", "微博", "反馈", "关于我们"}));

        mCustomViewGroupGridLayout.setOnItemClickListener(new CustomViewGroupGridLayout.OnItemClickListener() {

            @Override
            public void onItemClick(View v, int index) {
                Toast.makeText(getApplicationContext(), "item=" + index, 0).show();
            }
        });
    }
}