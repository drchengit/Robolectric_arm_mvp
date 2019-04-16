package com.jess.arms.utils;

import android.view.View;

import java.util.Calendar;

/**
 * @author DrChen
 * @Date 2018/10/21.
 * qq:1414355045
 */
public class DoubleClickUtils {
    private static DoubleClickUtils doubleClickUtils;

    private DoubleClickUtils(){

    }
    public static DoubleClickUtils getInstance(){
        if(doubleClickUtils==null){
            doubleClickUtils = new DoubleClickUtils();
        }
        return doubleClickUtils;
    }
    public static final int MIN_CLICK_DELAY_TIME = 1000;
    private long lastClickTime = 0;
    private int id = -1;


    public boolean isDouble(View v) {
        long currentTime = Calendar.getInstance().getTimeInMillis();

        if (v!=null&&id != v.getId()) {
            id = v.getId();
            lastClickTime = currentTime;
           return false;
        }
        if (currentTime - lastClickTime > MIN_CLICK_DELAY_TIME) {
            lastClickTime = currentTime;
          return false;
        }
        return true;
    }
}
