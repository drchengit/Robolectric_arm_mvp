package me.jessyan.mvparms.demo.mvp.ui.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.Html;
import android.text.Spanned;
import android.util.AttributeSet;
import android.widget.TextView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 *  改变数字颜色 的自定义控件
 */
public class CustomIntColorChangeTextView extends android.support.v7.widget.AppCompatTextView{
    public CustomIntColorChangeTextView(Context context) {
        this(context,null);
    }

    public CustomIntColorChangeTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CustomIntColorChangeTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }



     public void setTextIntColor(String msg){



         String REGEX = "[^\\+\\-\\D]*(\\-*\\+*\\s*\\d+\\.*\\d*)";

         Pattern p = Pattern.compile(REGEX);
            // 获取 matcher 对象
         Matcher m = p.matcher(msg);
         StringBuffer sb = new StringBuffer();
         while(m.find()){
             m.appendReplacement(sb,"<font color = #F66440> "+m.group()+"</font>");

         }
         m.appendTail(sb);


         Spanned result;
         if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
             result = Html.fromHtml(sb.toString(),Html.FROM_HTML_MODE_LEGACY);
         } else {
             result = Html.fromHtml(sb.toString());
         }
         setText(result);
     }

    public void setTextIntColor(String msg,String color){
        String REGEX = "[^\\+\\-\\D]*(\\-*\\+*\\s*\\d+\\.*\\d*)";

        Pattern p = Pattern.compile(REGEX);
        // 获取 matcher 对象
        Matcher m = p.matcher(msg);
        StringBuffer sb = new StringBuffer();
        while(m.find()){
            m.appendReplacement(sb,"<font color = "+color+"> "+m.group()+"</font>");

        }
        m.appendTail(sb);

        Spanned result;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            result = Html.fromHtml(sb.toString(),Html.FROM_HTML_MODE_LEGACY);
        } else {
            result = Html.fromHtml(sb.toString());
        }
        setText(result);
    }

    public void setTextSignIntColor(String msg, String subColor, String cumColor) {
        String REGEX = "[^\\+\\-\\D]*(\\-*\\+*\\s*\\d+\\.*\\d*)";

        Pattern p = Pattern.compile(REGEX);
        // 获取 matcher 对象
        Matcher m = p.matcher(msg);
        StringBuffer sb = new StringBuffer();
        while(m.find()){
            String color;
            if(m.group().contains("-")){
                color = subColor;
            }else {
                color = cumColor;
            }
            m.appendReplacement(sb,"<font color = "+color+"> "+m.group()+"</font>");

        }
        m.appendTail(sb);

        Spanned result;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            result = Html.fromHtml(sb.toString(),Html.FROM_HTML_MODE_LEGACY);
        } else {
            result = Html.fromHtml(sb.toString());
        }
        setText(result);
    }
}
