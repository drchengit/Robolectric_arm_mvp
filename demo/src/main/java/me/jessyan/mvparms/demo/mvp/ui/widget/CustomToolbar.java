package me.jessyan.mvparms.demo.mvp.ui.widget;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.content.res.AppCompatResources;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.jess.arms.utils.ArmsUtils;

import me.jessyan.mvparms.demo.R;
import me.jessyan.mvparms.demo.utils.PerfectClickListener;

import static me.jessyan.autosize.utils.ScreenUtils.getStatusBarHeight;

/**
 *https://blog.csdn.net/qq_34879948/article/details/80778137
 *  适配了状态栏的toolbar
 */
public class CustomToolbar extends FrameLayout {

    private boolean mLayoutReady;
    private View contentView;
    private ImageView mLeftIv;
    private TextView mTitleTv;
    private ImageView mRightIv;
    private TextView mRightTv;
    private ToolbarListener listener;
    private String title = "谢谢回顾谷歌";

    private boolean isNormal=true;

    public CustomToolbar(Context context) {
        this(context, null);

    }

    public CustomToolbar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomToolbar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

       TypedArray ta= context.obtainStyledAttributes(attrs,R.styleable.CustomToolbar);
       title = ta.getString(R.styleable.CustomToolbar_title);
       isNormal = ta.getBoolean(R.styleable.CustomToolbar_isNormal,true);
       ta.recycle();
        initView();

    }

    private void initView() {
        if(contentView==null){
            contentView = LayoutInflater.from(getContext()).inflate(R.layout.toolbar,null);
            mLeftIv = contentView.findViewById(R.id.back_iv);
            mTitleTv = contentView.findViewById(R.id.title_tv);
            mRightIv = contentView.findViewById(R.id.right_iv);
            mRightTv = contentView.findViewById(R.id.right_tv);
            mTitleTv.setText(title);

            LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.CENTER_HORIZONTAL);
            layoutParams.height = ArmsUtils.dip2px(getContext(),44);
            //如果没有这行代码，title不会居中显示
            addView(contentView,layoutParams);

            mLeftIv.setOnClickListener(new PerfectClickListener() {
                @Override
                protected void onNoDoubleClick(View v) {
                    if(listener!=null){
                        listener.onLeftBack();
                    }
                }
            });
            mRightIv.setOnClickListener(new PerfectClickListener() {
                @Override
                protected void onNoDoubleClick(View v) {
                    if(listener!=null){
                        listener.onRightBack();
                    }
                }
            });



            mRightTv.setOnClickListener(new PerfectClickListener() {
                @Override
                protected void onNoDoubleClick(View v) {
                    if(listener!=null){
                        listener.onRightBack();
                    }
                }
            });

            if(!isNormal){
                mTitleTv.setTextColor(ContextCompat.getColor(getContext(),R.color.white));
                Drawable drawable = AppCompatResources.getDrawable(getContext(),R.drawable.ic_pin_left_withe);
                drawable.setBounds(0,0,getMinimumWidth(),getMinimumHeight());
                mLeftIv.setImageDrawable(drawable);
            }else {
                setBackgroundColor(ContextCompat.getColor(getContext(),R.color.white));
            }

        }
    }

    public void setListener(ToolbarListener listener) {
        this.listener = listener;
    }




    public void setTitle(String title) {
        initView();
        if (mTitleTv!=null){
            mTitleTv.setText(title);
            showTitleView();
        }
    }

    public void setRightIv(int pic){
        if(mRightIv!=null){
            mRightIv.setVisibility(VISIBLE);
            mRightIv.setBackgroundResource(pic);
        }
    }

    public void setRightTv(String txt){
            if(mRightTv!=null){
                mRightTv.setVisibility(VISIBLE);
                mRightTv.setText(txt);
            }
    }

    private void showTitleView() {
        if(mTitleTv!=null){
            mTitleTv.setVisibility(VISIBLE);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//        if (!mLayoutReady) {
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//                if ((getWindowSystemUiVisibility() &
//                        (SYSTEM_UI_FLAG_LAYOUT_STABLE|SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)) ==
//                        (SYSTEM_UI_FLAG_LAYOUT_STABLE|SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)) {
//                    int statusBarHeight = getStatusBarHeight();
//
////                    setPadding(0, statusBarHeight, 0, 0);
//                }
//            }
//
//            mLayoutReady = true;
//        }
    }



    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);


    }

    private int getStatusBarHeight() {
        Resources res = Resources.getSystem();
        int resId = res.getIdentifier("status_bar_height", "dimen", "android");
        if (resId > 0) {
            return res.getDimensionPixelSize(resId);
        }
        return 0;
    }

    public View getRightIv() {
        return mRightIv;
    }

    public  interface  ToolbarListener{
        void onLeftBack();
        void onRightBack();
    }
}