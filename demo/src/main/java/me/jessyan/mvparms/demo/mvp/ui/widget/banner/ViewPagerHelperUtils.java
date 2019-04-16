package me.jessyan.mvparms.demo.mvp.ui.widget.banner;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.widget.Scroller;

import java.lang.reflect.Field;

/**
 * @author DrChen
 * @Date 2018/9/26 0026.
 * qq:1414355045
 */
public  class ViewPagerHelperUtils {
    public  static  final  int LOOP_COUNT = 5000;

    /**
     * 设置viewpager之间的切换速度
     */
    public static void  initSwitchTime(Context context, ViewPager viewPager, int time){
        try {
            Field field = ViewPager.class.getDeclaredField("mScroller");
            field.setAccessible(true);
            field.set(viewPager,new  ViewPagerScroller(context,time));
        }catch (Exception e){
            e.printStackTrace();
        }
    }
 public  static class ViewPagerScroller extends Scroller{
        int time;
        public ViewPagerScroller(Context context,int time) {
            super(context);

            this.time = time;
        }

     @Override
     public void startScroll(int startX, int startY, int dx, int dy, int duration) {
         super.startScroll(startX, startY, dx, dy, time);
     }

     @Override
     public void startScroll(int startX, int startY, int dx, int dy) {
         super.startScroll(startX, startY, dx, dy, time);
     }

 }

}
