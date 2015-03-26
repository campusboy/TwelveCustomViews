package com.twelve.demo;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.text.*;
import android.text.style.*;
import android.text.util.Linkify;
import android.util.TypedValue;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.twelve.R;

/**
 * Created by lgq on 15-3-24.
 */
public class EditTextSamplesActivity extends Activity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_view_samples);

        LinearLayout viewContainer = (LinearLayout) findViewById(R.id.view_container);
        TextView introduction = new TextView(this);
        introduction.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
        introduction.setTextColor(Color.rgb(0x94,0x00,0xD3));
        introduction.setText("EditText示例程序展示了EditText的一些文字和图像效果，依次包括：" +
                "\n设置文字选中;\n文本的中间插入和末尾追加;\nEditText设置Ellipse无效;" +
                "\nEditText设置AutoLink无效;\nEditText使用Span对象(这些设置适合所有可以加载文字的控件)：" +
                "图文混排，文字样式，颜色，背景设置");
        viewContainer.addView(introduction);

        /**
         * 设置选中文字
         */
        EditText selection = new EditText(this);
        selection.setText("这是一个测试选择的EditText控件");
        selection.extendSelection(3);
        viewContainer.addView(selection);

        /**
         * 文字的插入和追加
         */
        EditText editable = new EditText(this);
        editable.setTextColor(Color.RED);
        editable.setText("这是一个测试Editable元素插入/追加字符串操作的EditText控件");
        Editable e = editable.getText();
        e.insert(5,getTextAppearanceText("[这是中间插入的字符串]",android.R.style.TextAppearance_Large));
        e.append(getTextAppearanceText("[这是尾部追加的字符串]",android.R.style.TextAppearance_Small));
        viewContainer.addView(editable);
        /**
         * 设置Ellipse无效
         */
        EditText ellipse = new EditText(this);
        ellipse.setSingleLine();
        ellipse.setText("这是一个测试Ellipse的EditText控件，这是一个测试Ellipse的EditText控件，" +
                "这是一个测试Ellipse的EditText控件");
        ellipse.setEllipsize(TextUtils.TruncateAt.START);
        viewContainer.addView(ellipse);
        /**
         * 设置AutoLink无效
         */
        EditText url = new EditText(this);
        url.setTextSize(TypedValue.COMPLEX_UNIT_DIP,20);
        url.setAutoLinkMask(Linkify.WEB_URLS);
        url.setLinksClickable(true);
        url.setLinkTextColor(Color.BLUE);
        url.setText("EditText设置AutoLink无法点击：https://github.com/campusboy/TwelveCustomViews");
        viewContainer.addView(url);
        /**
         * Span对象的使用
         */
        EditText imageText = new EditText(this);
        imageText.setTextColor(Color.RED);
        imageText.setText("图文混排\n");
        imageText.append(getSpannableStringFromBitmap(Environment.getExternalStorageDirectory() +
                "/BabyCare/20153/11/a5492.png"));
        imageText.append("\n图片后文字");
        imageText.append(getColorfulText("\n这是一段设置文字颜色，背景颜色，字体大小的文字"));
        imageText.append(getTextAppearanceText("\n这是一段设置文字样式的文字",
                android.R.style.TextAppearance_DeviceDefault_Medium));
        viewContainer.addView(imageText);
    }

    /**
     * 获取带图片的Spannable对象
     * @param imagePath 图片路径
     */
    private SpannableString getSpannableStringFromBitmap(String imagePath){
        Bitmap originalBitmap = BitmapFactory.decodeFile(imagePath);
        SpannableString ss = new SpannableString(imagePath);
        ImageSpan span = new ImageSpan(this, originalBitmap);
        ss.setSpan(span, 0, imagePath.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return ss;
    }

    /**
     * 设置文字颜色，字体，背景色
     * @param text 目标文字
     */
    private SpannableString getColorfulText(String text){
        SpannableString span  = new SpannableString(text);
        span.setSpan(new AbsoluteSizeSpan(58), 0, text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        span.setSpan(new ForegroundColorSpan(Color.BLUE), 0, text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        span.setSpan(new BackgroundColorSpan(Color.YELLOW), 0, text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return span;
    }

    /**
     * 使用系统样式设置文字
     * @param text 目标文字
     */
    private SpannableString getTextAppearanceText(String text,int appearance){
        SpannableString span  = new SpannableString(text);
        span.setSpan(new TextAppearanceSpan(this,appearance),0, text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return span;
    }
}