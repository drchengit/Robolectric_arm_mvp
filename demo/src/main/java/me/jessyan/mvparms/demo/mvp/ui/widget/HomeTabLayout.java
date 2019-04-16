package me.jessyan.mvparms.demo.mvp.ui.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.content.res.AppCompatResources;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;


import me.jessyan.mvparms.demo.R;
import me.jessyan.mvparms.demo.mvp.ui.widget.banner.ViewPagerHelperUtils;

/**
 * @author DrChen
 * @Date 2019/2/25 0025.
 * qq:1414355045
 */
public class HomeTabLayout extends LinearLayout{

    public HomeTabLayout(Context context) {
       this(context,null);
    }

    public HomeTabLayout(Context context, @Nullable AttributeSet attrs) {
        this(context,attrs,0);
    }

    public HomeTabLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        setOrientation(LinearLayout.HORIZONTAL);
        setGravity(Gravity.CENTER);
    }

    /**
     * 设置切换速度
     */
    public void setSiwtchSpeed(ViewPager viewPager,int time){
        ViewPagerHelperUtils.initSwitchTime(getContext(),viewPager,time);
    }

    /**
     * 关联viewpager,并设置函数
     */
    public void setTab(final ViewPager viewPager,final String[] texts,final int[] icons ,TabClickListener listener){
        if(texts.length>0&&icons.length>0 ){
            removeAllViews();
            for(int i= 0;i<texts.length;i++){
                //创建
                TextView tab = new TextView(getContext());
                //字
                tab.setText(texts[i]);
                tab.setTextSize(10);
                ColorStateList csl = ContextCompat.getColorStateList(getContext(),R.color.tab_home_text);
                tab.setTextColor(csl);
                if(viewPager.getCurrentItem()==i){
                    tab.setSelected(true);
                    }
                //图片
                 Drawable icon = AppCompatResources.getDrawable(getContext(),icons[i]);
                icon.setBounds(0,0,icon.getMinimumWidth(),icon.getMinimumHeight());
                tab.setCompoundDrawables(null,icon,null,null);

                tab.setGravity(Gravity.CENTER);
                //相对位置
                LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                params.weight = 1;


                addView(tab,params);

                int finalI = i;
                //点击事件，和联动
                tab.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if(listener!=null){
                            listener.onClick(finalI);
                        }
                        //联动
                        if(viewPager!=null){
                            viewPager.setCurrentItem(finalI);
                        }
                    }
                });
            }


        }

        //viewpager 联动
        if(viewPager!=null){
            viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    for (int i = 0; i < getChildCount(); i++) {

                        if (i == position) {
                            TextView currentView = (TextView) getChildAt(position);
                            if (currentView != null) {
                                currentView.setSelected(true);
                            }

                        } else {
                            TextView lastview = (TextView) getChildAt(i);
                            if(lastview.isSelected()) lastview.setSelected(false);
                        }
                    }
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });
        }

    }


    /**
     * tab点击事件的监听
     */
    public interface TabClickListener {
        void onClick(int position);
    }
}
