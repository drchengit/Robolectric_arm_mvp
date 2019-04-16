package me.jessyan.mvparms.demo.utils;

import android.util.Log;

import java.util.Calendar;

public class DateUtils {
	private static String chinese;

	/**
     * 通过年份和月份 得到当月的日子
     * 
     * @param year
     * @param month
     * @return
     */
    public static int getMonthDays(int year, int month) {
		Calendar a = Calendar.getInstance();
		a.set(Calendar.YEAR, year);
		a.set(Calendar.MONTH, month);
		a.set(Calendar.DATE, 1);//把日期设置为当月第一天
		a.roll(Calendar.DATE, -1);//日期回滚一天，也就是最后一天
		int maxDate = a.get(Calendar.DATE);
		return maxDate;
	}
    /**
     * 返回当前月份1号位于周几
     * @param year
     * 		年份
     * @param month
     * 		月份，传入系统获取的，不需要正常的
     * @return
     * 	日：1		一：2		二：3		三：4		四：5		五：6		六：7
     */
    public static int getFirstDayWeek(int year, int month){
    	Calendar calendar = Calendar.getInstance();
    	calendar.set(year, month, 1);
    	Log.d("DateView", "DateView:First:" + calendar.getFirstDayOfWeek());
    	return calendar.get(Calendar.DAY_OF_WEEK);
    }

    public static String getWeek(){
    	Calendar c = Calendar.getInstance();
    	return WEEK[getFirstDayWeek(c.get(Calendar.YEAR),c.get(Calendar.DAY_OF_MONTH)+1)];
	}

   static final  String[] chineseMonth = new String[]{"一月","二月","三月","四月","五月","六月","七月","八月","九月","十月","十一月","十二月"};
	static final String[] WEEK = new String[]{"星期日","星期一","星期二","星期三","星期四","星期五","星期六"};

	public  static String getChineseMonth(int month) {


		return chineseMonth[month];
	}
}
