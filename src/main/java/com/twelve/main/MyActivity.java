package com.twelve.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import com.twelve.R;
import com.twelve.demo.*;

public class MyActivity extends Activity {
    /**
     * Called when the activity is first created.
     */
    /**
     * 测试控件对应的Activity列表，点击列表项跳转
     */
    private ListView mLstViewTestActivities;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        initWidgets();
    }

    private void initWidgets(){
        mLstViewTestActivities = (ListView)findViewById(R.id.lv_test);
        // 添加ListItem，设置事件响应
        mLstViewTestActivities.setAdapter(new DemoListAdapter());
        mLstViewTestActivities.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> arg0, View v, int index, long arg3) {
                onListItemClick(index);
            }
        });
    }

    void onListItemClick(int index) {
        Intent intent = null;
        intent = new Intent(MyActivity.this, demos[index].demoClass);
        this.startActivity(intent);
    }

    private static final DemoInfo[] demos = {
            new DemoInfo(R.string.custom_random_integer_title,R.string.custom_random_integer_desc, CustomTitleViewActivity.class),
            new DemoInfo(R.string.custom_image_view_title,R.string.custom_image_view_desc, CustomImageViewActivity.class),
            new DemoInfo(R.string.custom_flow_layout_title,R.string.custom_flow_layout_desc, CustomFlowLayoutActivity.class),
            new DemoInfo(R.string.custom_grid_layout_title,R.string.custom_grid_layout_desc, CustomGridLayoutActivity.class),
            new DemoInfo(R.string.text_view_samples_title,R.string.text_view_samples_desc, TextViewSamplesActivity.class)
    };

    private class DemoListAdapter extends BaseAdapter {
        public DemoListAdapter() {
            super();
        }

        @Override
        public View getView(int index, View convertView, ViewGroup parent) {
            convertView = View.inflate(MyActivity.this, R.layout.demo_info_item, null);
            TextView title = (TextView) convertView.findViewById(R.id.title);
            TextView desc = (TextView) convertView.findViewById(R.id.desc);
            title.setText(demos[index].title);
            desc.setText(demos[index].desc);
            return convertView;
        }

        @Override
        public int getCount() {
            return demos.length;
        }

        @Override
        public Object getItem(int index) {
            return demos[index];
        }

        @Override
        public long getItemId(int id) {
            return id;
        }
    }

    private static class DemoInfo {
        private final int title;
        private final int desc;
        private final Class<? extends Activity> demoClass;

        public DemoInfo(int title, int desc, Class<? extends Activity> demoClass) {
            this.title = title;
            this.desc = desc;
            this.demoClass = demoClass;
        }
    }
}
