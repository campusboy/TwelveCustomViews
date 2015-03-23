package com.twelve.demo;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.method.ScrollingMovementMethod;
import android.text.util.Linkify;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.twelve.R;

/**
 * Created by 刘国权 on 15-3-20.
 * TextView属性设置可以再XML文件中实现，也可以在Java代码中是实现
 * 在MVC模式下，最好是在XML文件中实现，但此处为了方便，在Java中实现
 */
public class TextViewSamplesActivity extends Activity {

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_view_samples);

        LinearLayout viewContainer = (LinearLayout) findViewById(R.id.view_container);

        TextView introduction = new TextView(this);
        introduction.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
        introduction.setTextColor(Color.rgb(0x94,0x00,0xD3));
        introduction.setText("TextView示例程序展示了TextView的一些文字和图像效果，依次包括：" +
                "\n在左侧插入一张图片;\n显示页内错误提醒;\n设置文字阴影，行间距，文字拉伸，文字颜色效果;" +
                "\n跑马灯效果;\n文字滑动效果;\n链接识别:电话，URL，Email;\nHTML填充TextView内容;" +
                "\nTextView加载动画效果.");
        viewContainer.addView(introduction);
        /**
         * 显示图片和文字，设置间隔40px
         */
        TextView imageAndText = new TextView(this);
        Drawable image = getResources().getDrawable(R.drawable.actions_about);
        image.setBounds(0,0,image.getIntrinsicWidth(),image.getIntrinsicHeight());
        imageAndText.setText("显示图片和文字，间隔40像素");
        imageAndText.setCompoundDrawables(image,null,null,null);
        imageAndText.setCompoundDrawablePadding(40);
        viewContainer.addView(imageAndText,1);
        /**
         * 页内错误提示，支持文字和图片
         */
        final TextView error = new TextView(this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.LEFT|Gravity.BOTTOM;
        error.setLayoutParams(params);
        error.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
        Drawable icon = getResources().getDrawable(R.drawable.actions_about);
        icon.setBounds(0, 0, image.getIntrinsicWidth(), image.getIntrinsicHeight());
        error.setError("这是一个显示提醒的TextView", icon);
        error.setText("点击弹出错误提示！");
        // 在同一个活动界面中只有一个控件可以设置：setFocusableInTouchMode(true),
        // 跑马灯和错误提示效果有冲突，所以添加了这个点击事件
        error.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                error.setFocusableInTouchMode(true);
            }
        });
        viewContainer.addView(error);

        /**
         * 设置文字阴影效果，行间距和文字拉伸
         */
        TextView text = new TextView(this);
        text.setText("设置文字阴影效果\n设置文字行间距\n设置文字拉伸");
        text.setShadowLayer(0.3f,-5,-5, Color.BLUE);
        text.setLineSpacing(2,2);
        text.setTextScaleX(4);
        viewContainer.addView(text);

        /**
         * 设置文字滚动，跑马灯效果
         */
        final TextView scroll = new TextView(this);

        scroll.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        scroll.setText("点击显示TextView实现的跑马灯效果,点击显示TextView实现的跑马灯效果，" +
                "点击显示TextView实现的跑马灯效果");
        // 在同一个活动界面中只有一个控件可以设置：setFocusableInTouchMode(true),
        // 跑马灯和错误提示效果有冲突，所以添加了这个点击事件
        scroll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scroll.setFocusableInTouchMode(true);
            }
        });
        scroll.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
        scroll.setSingleLine();// 单行显示
        scroll.setEllipsize(TextUtils.TruncateAt.MARQUEE);// 对超出控件的文字，设置跑马灯值
        scroll.setMarqueeRepeatLimit(-1);// 滚动次数
        viewContainer.addView(scroll);

        TextView flip = new TextView(this);
        flip.setText("请用手指在我身上左右滑动，请用手指在我身上左右滑动，请用手指在我身上左右滑动。");
        flip.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
        //让文字可以滚动显示
        flip.setMovementMethod(ScrollingMovementMethod.getInstance());
        // 启用滑块：否则会报空指针异常
        flip.setHorizontalScrollBarEnabled(true);

        flip.setSingleLine();// 单行显示
        flip.setEllipsize(TextUtils.TruncateAt.MARQUEE);// 对超出控件的文字，设置跑马灯值
        flip.setMarqueeRepeatLimit(-1);// 滚动次数
        viewContainer.addView(flip);
        /**
         * 链接到电话号码，可以点击拨打
         */
        TextView phone = new TextView(this);
        phone.setTextSize(TypedValue.COMPLEX_UNIT_DIP,20);
        phone.setAutoLinkMask(Linkify.PHONE_NUMBERS);
        phone.setLinksClickable(true);
        phone.setLinkTextColor(Color.RED);
        phone.setText("服务号码:10086");
        viewContainer.addView(phone);
        /**
         * 链接到网址，可以点击进入页面
         */
        TextView url = new TextView(this);
        url.setTextSize(TypedValue.COMPLEX_UNIT_DIP,20);
        url.setAutoLinkMask(Linkify.WEB_URLS);
        url.setLinksClickable(true);
        url.setLinkTextColor(Color.BLUE);
        url.setText("GitHub：https://github.com/campusboy/TwelveCustomViews");
        viewContainer.addView(url);
        /**
         * 链接到邮箱，可以点击发送邮件
         */
        TextView email = new TextView(this);
        email.setTextSize(TypedValue.COMPLEX_UNIT_DIP,20);
        email.setAutoLinkMask(Linkify.EMAIL_ADDRESSES);
        email.setClickable(true);
        email.setLinkTextColor(Color.WHITE);
        email.setText("Email: 290804346@qq.com");
        viewContainer.addView(email);
        /**
         * 使用HTML填充TextVew
         */
        TextView html = new TextView(this);
        StringBuffer sb = new StringBuffer();
        sb.append("<h3>HTML填充TextView内容：<font color='#00ff00'>" +
                "<a href='https://github.com/campusboy/TwelveCustomViews'>点击进入GitHub</a>" +
                "</font></h3>");
        html.setText(Html.fromHtml(sb.toString()));
        html.setMovementMethod(LinkMovementMethod.getInstance());// 这句很重要,使超链接<a href>起作用
        viewContainer.addView(html);
        /**
         * TextView加载动画
         */
        final TextView animation = new TextView(this);
        animation.setText("点击按钮，加载动画");
        animation.setTextSize(TypedValue.COMPLEX_UNIT_DIP,20);
        viewContainer.addView(animation);
        /**
         * 触发动画的按钮
         */
        final Animation anim = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.circle);
        Button btnAnimation = new Button(this);
        btnAnimation.setText("加载动画");
        btnAnimation.setTextSize(TypedValue.COMPLEX_UNIT_DIP,20);
        btnAnimation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                animation.startAnimation(anim);
            }
        });
        viewContainer.addView(btnAnimation);
    }
}