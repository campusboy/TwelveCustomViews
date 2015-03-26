package com.twelve.demo;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import com.twelve.R;

/**
 * Created by lgq on 15-3-26.
 * 参考博客：http://blog.csdn.net/vqqyuan/article/details/44648683#
 */
public class ButtonViewSampleActivity extends Activity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_view_samples);

        LinearLayout viewContainer = (LinearLayout) findViewById(R.id.view_container);
        Button button = new Button(this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.CENTER;
        button.setLayoutParams(params);
        button.setBackgroundResource(R.drawable.button_selector);
        viewContainer.addView(button);
    }
}