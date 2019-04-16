package me.jessyan.mvparms.demo.utils;

import java.text.SimpleDateFormat;

/**
 * 时间格式
 */

public class TimeFormatUtils {
    public static String DayOfMouthString(long time){
        if(time==0||time==0){
            return "";
        }
        SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
        String dateString = sdf.format(time);
        return dateString;
    }

    public static String standardFormat(long time) {
        if(time==0||time==0){
            return "";
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String dateString = sdf.format(time);
        return dateString;
    }
    public static String potStandFormat(long time){
        if(time==0||time==-1){
            return "";
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
        String dateString = sdf.format(time);
        return dateString;
    }

    public static String standardDayFormat(long time) {
        if(time==0||time==-1){
            return "";
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = sdf.format(time);
        return dateString;
    }

    public static String currentDayFormat( ) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = sdf.format(System.currentTimeMillis());
        return dateString;
    }


    public static long standardDayFormat(String time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return sdf.parse(time).getTime();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
    public static long potDayFormat(String time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
        try {
            return sdf.parse(time).getTime();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }






}
