package me.jessyan.mvparms.demo.mvp.ui.widget;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.RadioButton;

/**
 * @author DrChen
 * @Date 2019/2/21 0021.
 * qq:1414355045
 */
public class SizeChangRadioBn extends android.support.v7.widget.AppCompatRadioButton{
    /**
     * 选中前size
     */
    private float beforeSize =14 ;

    /**
     * 选中后size
     */
    private float afterSize = 16;

    private boolean isFirst = true;

    public SizeChangRadioBn(Context context) {
        this(context,null);
    }

    public SizeChangRadioBn(Context context, AttributeSet attrs) {
        super(context, attrs);

        isFirst = false;
    }


    @Override
    public void setChecked(boolean checked) {
        super.setChecked(checked);
        if(isFirst)return;
        if(checked){
            setTextSize(afterSize);
            setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        }else {
            setTextSize(14);
            setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
        }
    }
}
