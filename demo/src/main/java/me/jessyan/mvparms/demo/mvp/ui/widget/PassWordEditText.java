package me.jessyan.mvparms.demo.mvp.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.content.res.AppCompatResources;
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

public class PassWordEditText extends AppCompatEditText {

    /*
     * 定义属性变量
     * */
    private Paint mPaint; // 画笔

    private int ic_deleteResID; // 删除图标 资源ID
    private Drawable ic_delete; // 删除图标

    private int delete_x, delete_y, delete_width, delete_height; // 删除图标起点(x,y)、删除图标宽、高（px）

    private int ic_left_clickResID, ic_left_unclickResID;    // 左侧图标 资源ID（点击 & 无点击）
    private Drawable ic_left_click, ic_left_unclick; // 左侧图标（点击 & 未点击）
    private int left_x, left_y, left_width, left_height; // 左侧图标起点（x,y）、左侧图标宽、高（px）



    // 分割线变量
    private int lineColor_click, lineColor_unclick;// 点击时 & 未点击颜色
    private int color;
    private float linePosition;


    private int maxLength = 16;
    private String digits;
    private Bitmap ic_openEye, ic_closeEye;
    /**
     * 当控件输出超过长度时，延伸的x 长度
     *
     */
    private int srcoll_x;
    private int with,height;
    /**
     * 是否保密
     */
    private boolean isSecret = true;
    /**
     * 显示密码的图标
     */
    private Bitmap mShowIcon;


    private int lineStrokeWith;

    private InputLisenter inputLisenter;

    public PassWordEditText(Context context) {
        super(context);

    }

    public PassWordEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
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

        // a. 点击状态的左侧图标
        // 1. 获取资源ID
        ic_left_clickResID = typedArray.getResourceId(R.styleable.UserPhoneEditText_user_ic_left_click, -1);
        // 2. 根据资源ID获取图标资源（转化成Drawable对象）

        // 3. 设置图标大小
        // 起点(x，y)、宽= left_width、高 = left_height
        left_x = (int) typedArray.getDimension(R.styleable.UserPhoneEditText_user_left_x, ArmsUtils.dip2px(context, 0));
        left_y = (int) typedArray.getDimension(R.styleable.UserPhoneEditText_user_left_y, ArmsUtils.dip2px(context, 0));
        left_width = (int) typedArray.getDimension(R.styleable.UserPhoneEditText_user_left_width, ArmsUtils.dip2px(context, 24));
        left_height = (int) typedArray.getDimension(R.styleable.UserPhoneEditText_user_left_height, ArmsUtils.dip2px(context, 24));
       if(ic_left_clickResID==-1){
           ic_left_click = null;
       }else {
           ic_left_click = getResources().getDrawable(ic_left_clickResID);
           ic_left_click.setBounds(left_x, left_y, left_width, left_height);
       }


        // Drawable.setBounds(x,y,width,height) = 设置Drawable的初始位置、宽和高等信息
        // x = 组件在容器X轴上的起点、y = 组件在容器Y轴上的起点、width=组件的长度、height = 组件的高度

        // b. 未点击状态的左侧图标
        // 1. 获取资源ID
        ic_left_unclickResID = typedArray.getResourceId(R.styleable.UserPhoneEditText_user_ic_left_unclick, -1);
        // 2. 根据资源ID获取图标资源（转化成Drawable对象）
        // 3. 设置图标大小（此处默认左侧图标点解 & 未点击状态的大小相同）
        if(ic_left_unclickResID==-1){
            ic_left_unclick =null;
        }else {
            ic_left_unclick = getResources().getDrawable(ic_left_unclickResID);
            ic_left_unclick.setBounds(left_x, left_y, left_width, left_height);
        }

        /**
         * 初始化删除图标
         */

        // 1. 获取资源ID
        ic_deleteResID = typedArray.getResourceId(R.styleable.UserPhoneEditText_user_ic_delete, R.drawable.ic_login_et_delete);
        // 2. 根据资源ID获取图标资源（转化成Drawable对象）
        ic_delete = getResources().getDrawable(ic_deleteResID);
        // 3. 设置图标大小
        // 起点(x，y)、宽= left_width、高 = left_height
        delete_x = (int) typedArray.getDimension(R.styleable.UserPhoneEditText_user_delete_x, ArmsUtils.dip2px(context, -24));
        delete_y = (int) typedArray.getDimension(R.styleable.UserPhoneEditText_user_delete_y, ArmsUtils.dip2px(context, 0));
        delete_width = (int) typedArray.getDimension(R.styleable.UserPhoneEditText_user_delete_width, ArmsUtils.dip2px(context, 24));
        delete_height = (int) typedArray.getDimension(R.styleable.UserPhoneEditText_user_delete_height, ArmsUtils.dip2px(context, 24));
        ic_delete.setBounds(delete_x, delete_y, delete_x + delete_width, delete_y + delete_height);

        /**
         * 设置EditText左侧 & 右侧的图片（初始状态仅有左侧图片））
         */
        setCompoundDrawables(ic_left_unclick, null,
                null, null);

        // setCompoundDrawables(Drawable left, Drawable top, Drawable right, Drawable bottom)介绍
        // 作用：在EditText上、下、左、右设置图标（相当于android:drawableLeft=""  android:drawableRight=""）
        // 备注：传入的Drawable对象必须已经setBounds(x,y,width,height)，即必须设置过初始位置、宽和高等信息
        // x:组件在容器X轴上的起点 y:组件在容器Y轴上的起点 width:组件的长度 height:组件的高度
        // 若不想在某个地方显示，则设置为null

        // 另外一个相似的方法：setCompoundDrawablesWithIntrinsicBounds(Drawable left, Drawable top, Drawable right, Drawable bottom)
        // 作用：在EditText上、下、左、右设置图标
        // 与setCompoundDrawables的区别：setCompoundDrawablesWithIntrinsicBounds（）传入的Drawable的宽高=固有宽高（自动通过getIntrinsicWidth（）& getIntrinsicHeight（）获取）
        // 不需要设置setBounds(x,y,width,height)


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


        // 3. 分割线位置
        linePosition = typedArray.getDimension(R.styleable.UserPhoneEditText_user_linePosition, ArmsUtils.dip2px(context, 1));
        // 消除自带下划线
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            setBackground(null);
        }

        typedArray.recycle();

        initType();

        ic_openEye = getBitmapFromVectorDrawable(context, R.drawable.ic_openeye);
        ic_closeEye = getBitmapFromVectorDrawable(context, R.drawable.ic_closeeye);

        setSecret(true);


    }



    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    if(mShowIcon!=null&&!mShowIcon.isRecycled()){
        mShowIcon.recycle();
    }
    if(ic_closeEye!=null&&!ic_closeEye.isRecycled()){
        ic_closeEye.recycle();
    }
    if(ic_openEye!=null&&!ic_openEye.isRecycled()){
        ic_openEye.recycle();
    }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    with = w;
    height = h;

    }

    /**
     * 输出的格式
     */
    private void initType() {


        //手机号码11位
        maxLength = 16;
        digits = null;
        setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);

        //只显示一行
        setSingleLine();

        setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxLength)});
    }

    /**
     * svg图片转bitmap
     */
    public Bitmap getBitmapFromVectorDrawable(Context context, int drawableId) {
        Drawable drawable = AppCompatResources.getDrawable(context, drawableId);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            drawable = (DrawableCompat.wrap(drawable)).mutate();
        }

        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(),
                Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);

        return bitmap;
    }

// if (vm.isSecret()) {
//        vm.setSecret(false);
//        //明文
//        passWordEt.
//
//    } else {
//        vm.setSecret(true);
//
//        //密码 TYPE_CLASS_TEXT 和 TYPE_TEXT_VARIATION_PASSWORD 必须一起使用，不认无效。
//        passWordEt
//    }
//                    passWordEt.setSelection(passWordEt.getText().length());

    @Override
    public void setInputType(int type) {

        super.setInputType(type);
        /* 非常重要:setKeyListener要在setInputType后面调用，否则无效。*/
        if (!TextUtils.isEmpty(digits)) {
            setKeyListener(DigitsKeyListener.getInstance(digits));
        }
    }

    /**
     * 作用：绘制分割线
     */
    @Override
    protected void onDraw(Canvas canvas) {

        super.onDraw(canvas);
        mPaint.setColor(color);
        mPaint.setStrokeWidth(lineStrokeWith);

        // 绘制分割线
        // 需要考虑：当输入长度超过输入框时，所画的线需要跟随着延伸
        // 解决方案：线的长度 = 控件长度 + 延伸后的长度
        srcoll_x = getScrollX();


        // 传入参数时，线的长度 = 控件长度 + 延伸后的长度
        canvas.drawLine(0, height - linePosition, with + srcoll_x,
                this.getMeasuredHeight() - linePosition, mPaint);

        canvas.drawBitmap(mShowIcon, with+srcoll_x - mShowIcon.getWidth(), (height - mShowIcon.getHeight()) / 2, mPaint);

    }

    /**
     * 复写EditText本身的方法：onTextChanged（）
     * 调用时刻：当输入框内容变化时
     */
    @Override
    protected void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter);
        setDeleteIconVisible(hasFocus() && text.length() > 0, hasFocus());

        if(inputLisenter!=null){
            inputLisenter.inputChange(text.length() > 0);
        }


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
        setDeleteIconVisible(focused && length() > 0, focused);
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
                Drawable drawable = ic_delete;
                // 判断条件说明
                // event.getX() ：抬起时的位置坐标
                // getWidth()：控件的宽度
                // getPaddingRight():删除图标图标右边缘至EditText控件右边缘的距离
                // 即：getWidth() - getPaddingRight() = 删除图标的右边缘坐标 = X1
                // getWidth() - getPaddingRight() - drawable.getBounds().width() = 删除图标左边缘的坐标 = X2
                // 所以X1与X2之间的区域 = 删除图标的区域
                // 当手指抬起的位置在删除图标的区域（X2=<event.getX() <=X1），即视为点击了删除图标 = 清空搜索框内容

                if (mShowIcon != null) {//两个图标同时存在时
                    //显示图标的点击
                    if (decide(event.getX()
                            , (getWidth() - getPaddingRight() - mShowIcon.getWidth())+srcoll_x
                            , (getWidth() - getPaddingRight()+srcoll_x))) {
                        if (isSecret) {
                            setSecret(false);
                        } else {
                            setSecret(true);
                        }
                    }
                    //删除图标的点击
                    if (decide(event.getX()
                            , getWidth() - getPaddingRight() - mShowIcon.getWidth() - drawable.getBounds().width()+srcoll_x
                            , getWidth() - getPaddingRight() - mShowIcon.getWidth()+srcoll_x)) {
                        setText("");
                    }
                } else {
                    //只有删除图标的情况
                    if (drawable != null &&
                            decide(event.getX()
                                    , (getWidth() - getPaddingRight() - drawable.getBounds().width())
                                    , (getWidth() - getPaddingRight()))) {

                        setText("");
                    }
                }


                break;
        }
        return super.onTouchEvent(event);
    }



    /**
     * 判断是在点击区域区域内
     */
    private boolean decide(float x, int start, int end) {
        if (start < x && x < end) {
            return true;
        } else {
            return false;
        }
    }

    public void setSecret(boolean secret) {
        isSecret = secret;
        if (secret) {
            mShowIcon = ic_openEye;
            setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            setTypeface(Typeface.DEFAULT);

        } else {
            mShowIcon = ic_closeEye;
            setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);

        }
        setSelection( getText().length());

    }

    public void setInputLisenter(InputLisenter inputLisenter) {
        this.inputLisenter = inputLisenter;
    }

    /**
     * 关注1
     * 作用：判断是否显示删除图标 & 设置分割线颜色
     */
    private void setDeleteIconVisible(boolean deleteVisible, boolean leftVisible) {
        setCompoundDrawables(leftVisible ? ic_left_click : ic_left_unclick, null,
                deleteVisible ? ic_delete : null, null);
        color = leftVisible ? lineColor_click : lineColor_unclick;
        lineStrokeWith = leftVisible ? ArmsUtils.dip2px(getContext(),1): ArmsUtils.dip2px(getContext(),1);

        invalidate();
    }

    public PassWordEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

   public   interface InputLisenter {
        void inputChange(boolean isInput);
   }
}

