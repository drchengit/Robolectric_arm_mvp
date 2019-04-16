package me.jessyan.mvparms.demo.mvp.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.DigitsKeyListener;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.jess.arms.utils.ArmsUtils;

import me.jessyan.mvparms.demo.R;


/**
 * Created by Carson_Ho on 17/8/4.
 */

public class PhoneEditText extends AppCompatEditText {

    /*
    * 定义属性变量
    * */
    private Paint mPaint; // 画笔

    private int  ic_deleteResID; // 删除图标 资源ID
    private Drawable  ic_delete; // 删除图标
    private int delete_x,delete_y,delete_width,delete_height; // 删除图标起点(x,y)、删除图标宽、高（px）

    private int  ic_left_clickResID,ic_left_unclickResID;    // 左侧图标 资源ID（点击 & 无点击）
    private Drawable  ic_left_click,ic_left_unclick; // 左侧图标（点击 & 未点击）
    private int left_x,left_y,left_width,left_height; // 左侧图标起点（x,y）、左侧图标宽、高（px）



    // 分割线变量
    private int lineColor_click,lineColor_unclick;// 点击时 & 未点击颜色
    private int color;
    private int linePosition;
    private int lineStrokeWith;

    private int start, count,before;

    private int maxLength = 13;
    private String digits;


    public PhoneEditText(Context context) {
        super(context);

    }

    public PhoneEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public PhoneEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    /**
     * 步骤1：初始化属性
     */

    private void init(Context context, AttributeSet attrs) {

        // 获取控件资源
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.UserPhoneEditText);

        /**
         * 初始化左侧图标（点击 & 未点击）
         */



         // 3. 设置图标大小
         // 起点(x，y)、宽= left_width、高 = left_height
         left_x = (int) typedArray.getDimension(R.styleable.UserPhoneEditText_user_left_x, ArmsUtils.dip2px(context,0));
         left_y = (int) typedArray.getDimension(R.styleable.UserPhoneEditText_user_left_y, ArmsUtils.dip2px(context,0));
         left_width = (int) typedArray.getDimension(R.styleable.UserPhoneEditText_user_left_width, ArmsUtils.dip2px(context,24));
         left_height = (int) typedArray.getDimension(R.styleable.UserPhoneEditText_user_left_height, ArmsUtils.dip2px(context,24));

        // a. 点击状态的左侧图标
        // 1. 获取资源ID
        ic_left_clickResID = typedArray.getResourceId(R.styleable.UserPhoneEditText_user_ic_left_click, -1);
        // 2. 根据资源ID获取图标资源（转化成Drawable对象）
        if(ic_left_clickResID==-1){
            ic_left_click = null;
        }else {
            ic_left_click =  getResources().getDrawable(ic_left_clickResID);
            ic_left_click.setBounds(left_x, left_y,left_width, left_height);
        }

         // Drawable.setBounds(x,y,width,height) = 设置Drawable的初始位置、宽和高等信息
         // x = 组件在容器X轴上的起点、y = 组件在容器Y轴上的起点、width=组件的长度、height = 组件的高度

        // b. 未点击状态的左侧图标
         // 1. 获取资源ID
         ic_left_unclickResID = typedArray.getResourceId(R.styleable.UserPhoneEditText_user_ic_left_unclick,-1);
         // 2. 根据资源ID获取图标资源（转化成Drawable对象）
         // 3. 设置图标大小（此处默认左侧图标点解 & 未点击状态的大小相同）
        if(ic_left_unclickResID==-1){
            ic_left_unclick= null;
        }else {
            ic_left_unclick =  getResources().getDrawable(ic_left_unclickResID);
            ic_left_unclick.setBounds(left_x, left_y,left_width, left_height);
        }



        /**
         * 初始化删除图标
         */

         // 1. 获取资源ID
         ic_deleteResID = typedArray.getResourceId(R.styleable.UserPhoneEditText_user_ic_delete,R.drawable.ic_login_et_delete);
         // 2. 根据资源ID获取图标资源（转化成Drawable对象）
         ic_delete =  getResources().getDrawable(ic_deleteResID);
         // 3. 设置图标大小
         // 起点(x，y)、宽= left_width、高 = left_height
         delete_x = (int) typedArray.getDimension(R.styleable.UserPhoneEditText_user_delete_x, ArmsUtils.dip2px(context,0));
         delete_y = (int) typedArray.getDimension(R.styleable.UserPhoneEditText_user_delete_y, ArmsUtils.dip2px(context,0));
         delete_width = (int) typedArray.getDimension(R.styleable.UserPhoneEditText_user_delete_width, ArmsUtils.dip2px(context,24));
         delete_height = (int) typedArray.getDimension(R.styleable.UserPhoneEditText_user_delete_height, ArmsUtils.dip2px(context,24));
         ic_delete.setBounds(delete_x, delete_y, delete_width, delete_height);

        /**
         * 设置EditText左侧 & 右侧的图片（初始状态仅有左侧图片））
         */
        setCompoundDrawables( ic_left_unclick, null,
                null, null);





        /**
         * 初始化分割线（颜色、粗细、位置）
         */
         // 1. 设置画笔
         mPaint = new Paint();
         lineStrokeWith = ArmsUtils.dip2px(context,1);

         mPaint.setStrokeWidth(lineStrokeWith); // 分割线粗细

         // 2. 设置分割线颜色（使用十六进制代码，如#333、#8e8e8e）
         int lineColorClick_default = context.getResources().getColor(R.color.color_4E79FF); // 默认 = 蓝色#1296db
         int lineColorunClick_default = context.getResources().getColor(R.color.color_B9BCCD); // 默认 = 灰色#9b9b9b
         lineColor_click = typedArray.getColor(R.styleable.UserPhoneEditText_user_lineColor_click, lineColorClick_default);
         lineColor_unclick = typedArray.getColor(R.styleable.UserPhoneEditText_user_lineColor_unclick, lineColorunClick_default);
         color = lineColor_unclick;

         mPaint.setColor(lineColor_unclick); // 分割线默认颜色 = 灰色
//         setTextColor(color); // 字体默认颜色 = 灰色

         // 3. 分割线位置
         linePosition = (int) typedArray.getDimension(R.styleable.UserPhoneEditText_user_linePosition, ArmsUtils.dip2px(context,1));

         // 消除自带下划线
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            setBackground(null);
        }

        typedArray.recycle();

         initType();
            //手机显示空格
            addTextChangedListener(watcher);



    }

    /**
     * 输出的格式
     */
    private void initType(){


                //手机号码11位
            maxLength = 13;
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

    private TextWatcher watcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            PhoneEditText.this.start = start;
            PhoneEditText.this.before = before;
            PhoneEditText.this.count = count;

        }

        @Override
        public void afterTextChanged(Editable s) {
            if (s == null) {
                return;
            }

            //判断是否是在中间输入，需要重新计算
            boolean isMiddle = (start + count) < (s.length());
            //在末尾输入时，是否需要加入空格
            boolean isNeedSpace = false;
            if (!isMiddle && isSpace(s.length())) {
                isNeedSpace = true;
            }
            if (isMiddle || isNeedSpace || count > 1) {
                String newStr = s.toString();
                newStr = newStr.replace(" ", "");
                StringBuilder sb = new StringBuilder();
                int spaceCount = 0;
                for (int i = 0; i < newStr.length(); i++) {
                    sb.append(newStr.substring(i, i+1));
                    //如果当前输入的字符下一位为空格(i+1+1+spaceCount)，因为i是从0开始计算的，所以一开始的时候需要先加1
                    if(isSpace(i + 2 + spaceCount)){
                        sb.append(" ");
                        spaceCount += 1;
                    }
                }
                removeTextChangedListener(watcher);
                s.replace(0, s.length(),sb);
                //如果是在末尾的话,或者加入的字符个数大于零的话（输入或者粘贴）
                if (!isMiddle || count > 1) {
                    if (count == 0) {
                        //如果删除时，光标停留在空格的前面，光标则要往前移一位
                        if (isSpace(start - before + 1)) {
                           setSelection((start - before) > 0 ? start - before : 0);
                        } else {
                             setSelection((start - before + 1) > s.length() ? s.length() : (start - before + 1));
                        }
                    }else {
                          setSelection(s.length() <= maxLength ? s.length() : maxLength);
                    }
                } else {
                    //如果是删除
                    if (count == 0) {
                        //如果删除时，光标停留在空格的前面，光标则要往前移一位
                        if (isSpace(start - before + 1)) {
                            setSelection((start - before) > 0 ? start - before : 0);
                        } else {
                            setSelection((start - before + 1) > s.length() ? s.length() : (start - before + 1));
                        }
                    }
                    //如果是增加
                    else {
                        if (isSpace(start - before + count)) {
                            setSelection((start + count - before + 1) < s.length() ? (start + count - before + 1) : s.length());
                        } else {
                            setSelection(start + count - before);
                        }
                    }
                }
                addTextChangedListener(watcher);
            }
        }
    };

    private boolean isSpace(int length) {
        return isSpacePhone(length);
    }

    private boolean isSpacePhone(int length) {
        return length >= 4 && (length == 4 || (length + 1) % 5 == 0);
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

                    setText("");

                }
                break;
        }
        return super.onTouchEvent(event);
    }

    /**
     * 关注1
     * 作用：判断是否显示删除图标 & 设置分割线颜色,分割线的粗线
     */
    private void setDeleteIconVisible(boolean deleteVisible,boolean leftVisible) {


            setCompoundDrawables(leftVisible ? ic_left_click : ic_left_unclick, null,
                    deleteVisible ? ic_delete : null, null);

        color = leftVisible ? lineColor_click : lineColor_unclick;
        lineStrokeWith = leftVisible ? ArmsUtils.dip2px(getContext(),1): ArmsUtils.dip2px(getContext(),1);

//        setTextColor(color);

        invalidate();
    }

    /**
     * 作用：绘制分割线
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        mPaint.setColor(color);
        mPaint.setStrokeWidth(lineStrokeWith);
//        setTextColor(color);
        // 绘制分割线
        // 需要考虑：当输入长度超过输入框时，所画的线需要跟随着延伸
        // 解决方案：线的长度 = 控件长度 + 延伸后的长度
        int x=this.getScrollX(); // 获取延伸后的长度
        int w=this.getMeasuredWidth(); // 获取控件长度

        // 传入参数时，线的长度 = 控件长度 + 延伸后的长度



                canvas.drawLine(0, this.getMeasuredHeight()- linePosition-lineStrokeWith, w+x,
                        this.getMeasuredHeight() - linePosition-lineStrokeWith, mPaint);

    }

}

