package me.jessyan.mvparms.demo.mvp.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v7.widget.AppCompatEditText;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextUtils;
import android.text.method.DigitsKeyListener;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.jess.arms.utils.ArmsUtils;

import me.jessyan.mvparms.demo.R;


/**
 * Created by Carson_Ho on 17/8/4.
 */

public class VerifyEditText extends AppCompatEditText {
    /**
     * 没有左边的图片
     */
    private boolean noHasleftDrawble;
    /*
    * 定义属性变量
    * */
    private int  ic_deleteResID; // 删除图标 资源ID
    private Drawable  ic_delete; // 删除图标
    private int delete_x,delete_y,delete_width,delete_height; // 删除图标起点(x,y)、删除图标宽、高（px）

    private int  ic_left_clickResID,ic_left_unclickResID;    // 左侧图标 资源ID（点击 & 无点击）
    private Drawable  ic_left_click,ic_left_unclick; // 左侧图标（点击 & 未点击）
    private int left_x,left_y,left_width,left_height; // 左侧图标起点（x,y）、左侧图标宽、高（px）










    private int maxLength = 4;
    private String digits;


    public VerifyEditText(Context context) {
        super(context);

    }

    public VerifyEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public VerifyEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    /**
     * 步骤1：初始化属性
     */

    private void init(Context context, AttributeSet attrs) {

        // 获取控件资源
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.UserVerifyEditText);

        /**
         * 初始化左侧图标（点击 & 未点击）
         */

        // a. 点击状态的左侧图标
         // 1. 获取资源ID
         ic_left_clickResID = typedArray.getResourceId(R.styleable.UserVerifyEditText_user_ic_left_click, R.mipmap.ic_launcher);
         // 2. 根据资源ID获取图标资源（转化成Drawable对象）
         ic_left_click =  getResources().getDrawable(ic_left_clickResID);
         // 3. 设置图标大小
         // 起点(x，y)、宽= left_width、高 = left_height
         left_x = (int) typedArray.getDimension(R.styleable.UserVerifyEditText_user_left_x, ArmsUtils.dip2px(context,0));
         left_y = (int) typedArray.getDimension(R.styleable.UserVerifyEditText_user_left_y, ArmsUtils.dip2px(context,0));
         left_width = (int) typedArray.getDimension(R.styleable.UserVerifyEditText_user_left_width, ArmsUtils.dip2px(context,24));
         left_height = (int) typedArray.getDimension(R.styleable.UserVerifyEditText_user_left_height, ArmsUtils.dip2px(context,24));

         ic_left_click.setBounds(left_x, left_y,left_width, left_height);
         // Drawable.setBounds(x,y,width,height) = 设置Drawable的初始位置、宽和高等信息
         // x = 组件在容器X轴上的起点、y = 组件在容器Y轴上的起点、width=组件的长度、height = 组件的高度

        // b. 未点击状态的左侧图标
         // 1. 获取资源ID
         ic_left_unclickResID = typedArray.getResourceId(R.styleable.UserVerifyEditText_user_ic_left_unclick, R.mipmap.ic_launcher);
         // 2. 根据资源ID获取图标资源（转化成Drawable对象）
         // 3. 设置图标大小（此处默认左侧图标点解 & 未点击状态的大小相同）
         ic_left_unclick =  getResources().getDrawable(ic_left_unclickResID);
         ic_left_unclick.setBounds(left_x, left_y,left_width, left_height);


        /**
         * 初始化删除图标
         */

         // 1. 获取资源ID
         ic_deleteResID = typedArray.getResourceId(R.styleable.UserVerifyEditText_user_ic_delete,R.drawable.ic_login_et_delete);
         // 2. 根据资源ID获取图标资源（转化成Drawable对象）
         ic_delete =  getResources().getDrawable(ic_deleteResID);
         // 3. 设置图标大小
         // 起点(x，y)、宽= left_width、高 = left_height
         delete_x = (int) typedArray.getDimension(R.styleable.UserVerifyEditText_user_delete_x, ArmsUtils.dip2px(context,0));
         delete_y = (int) typedArray.getDimension(R.styleable.UserVerifyEditText_user_delete_y, ArmsUtils.dip2px(context,0));
         delete_width = (int) typedArray.getDimension(R.styleable.UserVerifyEditText_user_delete_width, ArmsUtils.dip2px(context,24));
         delete_height = (int) typedArray.getDimension(R.styleable.UserVerifyEditText_user_delete_height, ArmsUtils.dip2px(context,24));
         ic_delete.setBounds(delete_x, delete_y, delete_width, delete_height);

        /**
         * 设置EditText左侧 & 右侧的图片（初始状态仅有左侧图片））
         */
        if(noHasleftDrawble){
            setCompoundDrawables(null, null,
                    null, null);
        }else {
            setCompoundDrawables(ic_left_unclick, null,
                    null, null);
        }









         // 消除自带下划线
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            setBackground(null);
        }

        typedArray.recycle();

         initType();




    }

    /**
     * 输出的格式
     */
    private void initType(){


                //验证码号码4位
            maxLength = 4;
            digits = "0123456789 ";
            setInputType(InputType.TYPE_CLASS_NUMBER);
            //只显示一行
            setSingleLine();

        setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxLength)});
    }

    @Override
    public void setInputType(int type) {

        super.setInputType(type);
        /* 非常重要:setKeyListener要在setInputType后面调用，否则无效。*/
        if(!TextUtils.isEmpty(digits)) {
            setKeyListener(DigitsKeyListener.getInstance(digits));
        }
    }


    /**
     * 复写EditText本身的方法：onTextChanged（）
     * 调用时刻：当输入框内容变化时
     */
    @Override
    protected void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter);
        setDeleteIconVisible(hasFocus() && text.length() > 0,hasFocus());
        // hasFocus()返回是否获得EditTEXT的焦点，即是否选中
        // setDeleteIconVisible（） = 根据传入的是否选中 & 是否有输入来判断是否显示删除图标->>关注1

    }




    /**
     * 复写EditText本身的方法：onFocusChanged（）
     * 调用时刻：焦点发生变化时
     */
    @Override
    protected void onFocusChanged(boolean focused, int direction, Rect previouslyFocusedRect) {
        super.onFocusChanged(focused, direction, previouslyFocusedRect);
        setDeleteIconVisible(focused && length() > 0,focused);
        // focused = 是否获得焦点
        // 同样根据setDeleteIconVisible（）判断是否要显示删除图标->>关注1
    }

    /**
     * 作用：对删除图标区域设置为"点击 即 清空搜索框内容"
     * 原理：当手指抬起的位置在删除图标的区域，即视为点击了删除图标 = 清空搜索框内容
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        // 原理：当手指抬起的位置在删除图标的区域，即视为点击了删除图标 = 清空搜索框内容
        switch (event.getAction()) {
            // 判断动作 = 手指抬起时
            case MotionEvent.ACTION_UP:
                Drawable drawable =  ic_delete;

                if (drawable != null && event.getX() <= (getWidth() - getPaddingRight())
                        && event.getX() >= (getWidth() - getPaddingRight() - drawable.getBounds().width())) {

                    // 判断条件说明
                    // event.getX() ：抬起时的位置坐标
                    // getWidth()：控件的宽度
                    // getPaddingRight():删除图标图标右边缘至EditText控件右边缘的距离
                    // 即：getWidth() - getPaddingRight() = 删除图标的右边缘坐标 = X1
                        // getWidth() - getPaddingRight() - drawable.getBounds().width() = 删除图标左边缘的坐标 = X2
                    // 所以X1与X2之间的区域 = 删除图标的区域
                    // 当手指抬起的位置在删除图标的区域（X2=<event.getX() <=X1），即视为点击了删除图标 = 清空搜索框内容
                    setText("");

                }
                break;
        }
        return super.onTouchEvent(event);
    }

    public void setNoHasleftDrawble(boolean noHasleftDrawble) {
        this.noHasleftDrawble = noHasleftDrawble;

         if(noHasleftDrawble){
             setCompoundDrawables(null, null,
                     null, null);
         }
    }

    /**
     * 关注1
     * 作用：判断是否显示删除图标 & 设置分割线颜色,分割线的粗线
     */
    private void setDeleteIconVisible(boolean deleteVisible,boolean leftVisible) {
        if(noHasleftDrawble){
            setCompoundDrawables(null, null,
                    deleteVisible ? ic_delete : null, null);
        }else {

            setCompoundDrawables(leftVisible ? ic_left_click : ic_left_unclick, null,
                    deleteVisible ? ic_delete : null, null);

        }




        invalidate();
    }



}

