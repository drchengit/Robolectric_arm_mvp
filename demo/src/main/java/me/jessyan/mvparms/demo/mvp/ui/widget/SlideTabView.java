package me.jessyan.mvparms.demo.mvp.ui.widget;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.jess.arms.utils.ArmsUtils;

import java.util.ArrayList;
import java.util.List;

import me.jessyan.mvparms.demo.R;

/**
 * 实现可变下划线长度的tabLayout
 */
public class SlideTabView extends HorizontalScrollView implements ViewPager.OnPageChangeListener {
    //标签布局容器
    private LinearLayout linearLayout;
    //指示器画笔
    private Paint paint;
    //tab容器
    private List<String> list;
    //text容器
    private List<TextView> textViews;
    //当前位置
    private int currIndex = 0;
    //上一次位置
    private int lastIndex = 0;
    //满屏显示数量
    private float maxCount = 5.5f;
    //偏移百分比
    private float offSet;
    //非选中字体颜色
    private int noCurrColor = 0xff000000;
    //选中字体颜色
    private int currColor = 0xffff0000;
    //指示器颜色
    private int offLineColor = 0xffff0000;
    //背景色
    private int background = R.color.white;
    //字体大小
    private int txtSize = 16;
    private int textTabWith= 0;
    private int paddingStart= 0;
    //line 的粗细
    private int line_stroke_with = 5;
    //viewPager
    private ViewPager viewPager;
    //上下文
    private Context context;

    private int tabWith = 20;

    public SlideTabView(Context context) {
        this(context, null);
    }

    public SlideTabView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }


    public SlideTabView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init(context,attrs);
    }

    //初始化View
    private void init(Context context, AttributeSet attrs) {
        TypedArray ta =context.obtainStyledAttributes(attrs,R.styleable.SlideTabView);
         tabWith = ta.getDimensionPixelOffset(R.styleable.SlideTabView_tabWith,ArmsUtils.dip2px(getContext(),12))   ;
         txtSize = ta.getInt(R.styleable.SlideTabView_txtSize,16);
        textTabWith = ta.getDimensionPixelOffset(R.styleable.SlideTabView_textTabWith,0);
        paddingStart = ta.getDimensionPixelOffset(R.styleable.SlideTabView_paddingStart,ArmsUtils.dip2px(context,15));
         ta.recycle();

        noCurrColor = ContextCompat.getColor(getContext(), R.color.color_ff_7A7E95);
        currColor = ContextCompat.getColor(getContext(), R.color.color_F66440);
        offLineColor = ContextCompat.getColor(getContext(), R.color.color_F66440);
        line_stroke_with = ArmsUtils.dip2px(getContext(), 2);



        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);

        textViews = new ArrayList<>();

        linearLayout = new LinearLayout(this.context);

        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        linearLayout.setGravity(Gravity.CENTER_VERTICAL);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);


        setBackgroundResource(background);
                linearLayout.setPadding(paddingStart,0,ArmsUtils.dip2px(getContext(),15),0);
                addView(linearLayout,params);

    }

    //初始化tab数据
    public void initTab(List<String> list, ViewPager viewPager) {
        this.list = list;
        this.viewPager = viewPager;

        setListener();
        addTab();
    }

    //设置监听
    private void setListener() {
        viewPager.setOnPageChangeListener(this);

        getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

            @SuppressWarnings("deprecation")
            @SuppressLint("NewApi")
            @Override
            public void onGlobalLayout() {

                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                    getViewTreeObserver().removeGlobalOnLayoutListener(this);
                } else {
                    getViewTreeObserver().removeOnGlobalLayoutListener(this);
                }

                currIndex = viewPager.getCurrentItem();
                scrollToChild(currIndex, 0);
            }
        });
    }

    //添加tab 默认选中第一个
    private void addTab() {
        for (int i = 0; i < list.size(); i++) {
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            final TextView textView = new TextView(context);
            if (i == 0) {
                textView.setTextColor(currColor);
                textView .setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
            } else {
                textView.setTextColor(noCurrColor);
                textView.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
            }



            layoutParams.gravity = Gravity.CENTER_VERTICAL;
//            layoutParams.setMargins(0, ArmsUtils.dip2px(getContext(),10), 0, 20);
            if(textTabWith!=0){
                layoutParams.width = textTabWith;
            }else {
            layoutParams.width = (int) (((float) getWth()) / maxCount);
            }

            textView.setLayoutParams(layoutParams);
            textView.setGravity(Gravity.CENTER);
            textView.setTextSize(txtSize);
            textView.setText(list.get(i));

            final int finalI = i;
            textView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (currIndex != finalI) { //点击的不是当前tab
                        refresh(finalI);
                        viewPager.setCurrentItem(finalI);
                    }

                }
            });

            textViews.add(textView);
            linearLayout.addView(textView);
        }
        invalidate();
    }

    private void scrollToChild(int position, int offset) {
        int newScrollX = 0;
        if (lastIndex > position) {// 左滑
            newScrollX = linearLayout.getChildAt(position).getLeft() - linearLayout.getChildAt(position).getWidth();
        } else { //右滑
            newScrollX = linearLayout.getChildAt(lastIndex).getLeft() - linearLayout.getChildAt(position).getWidth();
        }
        Log.e("TAG", "newScrollX:" + newScrollX);
        smoothScrollTo(newScrollX, 0);
    }

    private int getWth() {
        WindowManager manager = ((Activity) context).getWindowManager();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.widthPixels;
    }

    private void refresh(int index) {
        for (int i = 0; i < textViews.size(); i++) {
            if (i != index) {
                textViews.get(i).setTextColor(noCurrColor);
                textViews.get(i) .setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
            } else {
                textViews.get(i).setTextColor(currColor);
                textViews.get(i) .setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
            }
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float h = getHeight();

        paint.setColor(offLineColor);

        View view = linearLayout.getChildAt(currIndex);
        if(view==null)return;
        int mid = (view.getLeft() + view.getRight()) / 2;
        float lineLeft = mid - tabWith;
        float lineRight = mid + tabWith;

        if (offSet > 0f) {
            View nextTab = linearLayout.getChildAt(currIndex + 1);
            int nextMid = (nextTab.getLeft() + nextTab.getRight()) / 2;
            final float nextTabLeft = nextMid - tabWith;
            final float nextTabRight = nextMid + tabWith;


            lineLeft = (offSet * nextTabLeft + (1f - offSet) * lineLeft);
            lineRight = (offSet * nextTabRight + (1f - offSet) * lineRight);
        }
        canvas.drawRect(lineLeft, h - line_stroke_with, lineRight, h, paint);
    }

    /***
     * @param position             当向右滑动时，此参数是点击的页面位置，
     *                             滑动完成时，及当前页面位置
     *                             当向左滑动时，此参数是向左滑动页面位置，
     *                             及当前页面位置-1，滑动完成时，及当前页面位置
     * @param positionOffset       页面滑动偏移量百分比
     * @param positionOffsetPixels 页面滑动偏移量
     */
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        currIndex = position;
        offSet = positionOffset;

        invalidate();
    }

    @Override
    public void onPageSelected(int position) {
        lastIndex = position;
        refresh(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        if (state == 0) {
            Log.e("TAG", "currIndex:" + currIndex);
            if (currIndex != textViews.size() - 1 && currIndex != 0) {
                scrollToChild(currIndex, 0);
            }
        }
    }

    public void setBackground(int background) {
        this.background = background;
    }


}