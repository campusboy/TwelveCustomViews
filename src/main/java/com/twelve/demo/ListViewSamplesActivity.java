package com.twelve.demo;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.twelve.R;

/**
 * Created by 刘国权 on 15-4-8.
 */
public class ListViewSamplesActivity extends Activity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LinearLayout viewContainer = new LinearLayout(this);
        viewContainer.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        setContentView(viewContainer);

        final ListView listView = new ListView(this);
        listView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        viewContainer.addView(listView);
        /**
         * 设置空列表提示
         */
        TextView empty = new TextView(this);
        AbsListView.LayoutParams params = new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        empty.setLayoutParams(params);
        empty.setGravity(Gravity.CENTER_HORIZONTAL);
        empty.setText("列表为空，点我添加");
        empty.setTextColor(Color.RED);
        empty.setTextSize(TypedValue.COMPLEX_UNIT_DIP,30);
        listView.setEmptyView(empty);
        viewContainer.addView(empty);

        /**
         * 设置新增数据后，自动滚动到尾部显示
         */
        listView.setTranscriptMode(AbsListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
        /**
         * 设置表头图像
         */
        TextView headerView = new TextView(ListViewSamplesActivity.this);
        headerView.setLayoutParams(params);
        headerView.setGravity(Gravity.CENTER_HORIZONTAL);
        headerView.setTextColor(Color.BLUE);
        headerView.setTextSize(TypedValue.COMPLEX_UNIT_DIP,30);
        headerView.setText("清除列表");
        listView.addHeaderView(headerView);
        /**
         * 设置表尾图像
         */
        ImageView footerView = new ImageView(ListViewSamplesActivity.this);
        footerView.setImageResource(R.drawable.ic_launcher);
        listView.addFooterView(footerView,"底部视图",true);
        /**
         * 添加列表数据
         */
        empty.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /**
                 * 设置列表数据，放在表头/表尾图像设置之后：这里之后的意思是只要前面有一个表头表尾的设置即可，
                 * 其余的表头表尾设置可以放在后面执行
                 */
                listView.setAdapter(new ArrayAdapter<String>(ListViewSamplesActivity.this,
                        android.R.layout.simple_list_item_checked,
                        new String[]{"孙悟空","猪八戒","沙僧","唐僧","孙悟空","猪八戒","沙僧","唐僧",
                                "孙悟空","猪八戒","沙僧","唐僧"}));

                /**
                 * 设置选择图像
                 */
                listView.setSelector(R.drawable.selector_gren);
                /**
                 * 设置选择图像显示覆盖列表项视图
                 */
                listView.setDrawSelectorOnTop(false);
                /**
                 * 设置列表项分割高度和背景
                 */
                listView.setDivider(getResources().getDrawable(R.drawable.selector_red));
                listView.setDividerHeight(15);

                /**
                 * 设置列表项点击事件
                 */
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        if(listView.getAdapter().getItem(i) instanceof String){
                            /**
                             * 设置列表过滤
                             */
                            listView.setTextFilterEnabled(true);
                            listView.setFilterText(listView.getAdapter().getItem(i).toString());
                        }else{
                            listView.clearTextFilter();
                            listView.setAdapter(new ArrayAdapter<String>(ListViewSamplesActivity.this,
                                    android.R.layout.simple_list_item_checked,
                                    new String[]{}));
                        }
                    }
                });
            }
        });
    }
}