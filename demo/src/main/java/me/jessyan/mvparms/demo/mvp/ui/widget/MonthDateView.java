package me.jessyan.mvparms.demo.mvp.ui.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.SparseArray;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.jess.arms.utils.ArmsUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import me.jessyan.mvparms.demo.utils.DateUtils;

public class MonthDateView extends View {
    private static final int NUM_COLUMNS = 7;
    private static int NUM_ROWS = 5;
    private Paint mPaint;
    private int mDayColor = Color.parseColor("#000000");
    private int mSelectDayColor = Color.parseColor("#ffffff");
    private int mCurrentColor = Color.parseColor("#ff0000");
    private int mSelectTextColor = Color.parseColor("#FFFFFF");
    private int mNomalTextColor = Color.parseColor("#7A7E95");
    private int mNomalBGColor = Color.parseColor("#EEF0F8");
    private int mCurrYear, mCurrMonth, mCurrDay;
    private int mSelYear, mSelMonth;
//    mSelDay;

    private int lastYear = -1, lastMonth = -1;
    private int mColumnSize;
    private DisplayMetrics mDisplayMetrics;
    private int mDaySize = 16;
    private TextView tv_state;
    private int weekRow;
    private int[][] daysString;
    private int mCircleRadius = 6;
    private DateClick dateClick;
    private int mCircleColor = Color.parseColor("#ff0000");
    //天 绘图时的数据
    private SparseArray<Day> days = new SparseArray<>();
    //已经签到的
    private List<Integer> daysHasThingList = new ArrayList<>();
    private int downX = 0, downY = 0;
    private int width;

    public MonthDateView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mDisplayMetrics = getResources().getDisplayMetrics();
        Calendar calendar = Calendar.getInstance();
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mCurrYear = calendar.get(Calendar.YEAR);
        mCurrMonth = calendar.get(Calendar.MONTH);
        mCurrDay = calendar.get(Calendar.DATE);

        setSelectYearMonth(mCurrYear, mCurrMonth);
        mCircleRadius = ArmsUtils.dip2px(context, 20);


    }

    /**
     * 设置年月
     *
     * @param year
     * @param month
     */
    private void setSelectYearMonth(int year, int month) {
        lastMonth = mSelYear;
        lastMonth = mSelMonth;

        mSelYear = year;
        mSelMonth = month;


    }

    private void drawCircle(int row, int column, int day, Canvas canvas) {
        if (daysHasThingList != null && daysHasThingList.size() > 0) {
            if (!daysHasThingList.contains(day)) return;
            mPaint.setColor(mCircleColor);
            float circleX = (float) (mColumnSize * column + mColumnSize * 0.8);
            float circley = (float) (mColumnSize * row + mColumnSize * 0.2);
            canvas.drawCircle(circleX, circley, mCircleRadius, mPaint);
        }
    }    @Override
    public boolean performClick() {
        return super.performClick();
    }

    /**
     * 左点击，日历向后翻页
     */
    public void onLeftClick() {
        int year = mSelYear;
        int month = mSelMonth;
        int day = 1;
        if (month == 0) {//若果是1月份，则变成12月份
            year = mSelYear - 1;
            month = 11;
        } else if (DateUtils.getMonthDays(year, month) == day) {
            //如果当前日期为该月最后一点，当向前推的时候，就需要改变选中的日期
            month = month - 1;
            day = DateUtils.getMonthDays(year, month);
        } else {
            month = month - 1;
        }
//        setSelectYearMonth(year, month);

    }    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int eventCode = event.getAction();
        switch (eventCode) {
            case MotionEvent.ACTION_DOWN:
                downX = (int) event.getX();
                downY = (int) event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                int upX = (int) event.getX();
                int upY = (int) event.getY();
                if (Math.abs(upX - downX) < 10 && Math.abs(upY - downY) < 10) {//点击事件
                    performClick();
                    doClickAction((upX + downX) / 2, (upY + downY) / 2);
                } else if ((Math.abs(upX - downX) / mColumnSize) >= 1 && Math.abs(upX - downX) >= 40) {
                    if (upX - downX > 0) {
//                        onLeftClick();
                    } else {
//                        onRightClick();
                    }
                }
                break;
        }
        return true;
    }

    /**
     * 右点击，日历向前翻页
     */
    public void onRightClick() {
        int year = mSelYear;
        int month = mSelMonth;
        int day = 1;
        if (month == 11) {//若果是12月份，则变成1月份
            year = mSelYear + 1;
            month = 0;
        } else if (DateUtils.getMonthDays(year, month) == day) {
            //如果当前日期为该月最后一点，当向前推的时候，就需要改变选中的日期
            month = month + 1;
            day = DateUtils.getMonthDays(year, month);
        } else {
            month = month + 1;
        }
//        setSelectYearMonth(year, month);

    }

    /**
     * 获取选择的年份
     *
     * @return
     */
    public int getmSelYear() {
        return mSelYear;
    }    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if(daysHasThingList.contains(mCurrDay)){
            tv_state.setText("今日已签到");
        }


        mPaint.setTextSize(mDaySize * mDisplayMetrics.scaledDensity);
        for (int i = 0; i < days.size(); i++) {
            Day day = days.valueAt(i);
            if (day.isSelected) {
                //被签到的
                mPaint.setShader(new LinearGradient(day.start, day.top, day.end, day.bottom, Color.parseColor("#FB9D75"),
                        Color.parseColor("#F66440"), Shader.TileMode.REPEAT));
                canvas.drawCircle(day.start + mCircleRadius, day.top + mCircleRadius, mCircleRadius, mPaint);
                mPaint.setShader(null);
                mPaint.setColor(mSelectTextColor);

                //该方法即为设置基线上那个点究竟是left,center,还是right  这里我设置为center
                mPaint.setTextAlign(Paint.Align.CENTER);
                Paint.FontMetrics fontMetrics = mPaint.getFontMetrics();
                float top = fontMetrics.top;//为基线到字体上边框的距离,即上图中的top
                float bottom = fontMetrics.bottom;//为基线到字体下边框的距离,即上图中的bottom
                int baseLineY = (int) (day.bottom - mCircleRadius - top / 2 - bottom / 2);//基线中间点的y轴计算公式
                canvas.drawText(day.content, day.start + mCircleRadius, baseLineY, mPaint);
            } else {

                mPaint.setShader(null);
                //正常的
                mPaint.setColor(mNomalBGColor);
                canvas.drawCircle(day.start + mCircleRadius, day.top + mCircleRadius, mCircleRadius, mPaint);
                mPaint.setColor(mNomalTextColor);

                //该方法即为设置基线上那个点究竟是left,center,还是right  这里我设置为center
                mPaint.setTextAlign(Paint.Align.CENTER);
                Paint.FontMetrics fontMetrics = mPaint.getFontMetrics();
                float top = fontMetrics.top;//为基线到字体上边框的距离,即上图中的top
                float bottom = fontMetrics.bottom;//为基线到字体下边框的距离,即上图中的bottom


                int baseLineY = (int) (day.bottom - mCircleRadius - top / 2 - bottom / 2);//基线中间点的y轴计算公式

                canvas.drawText(day.content, day.start + mCircleRadius, baseLineY, mPaint);
            }


        }


//        initSize();

        //       mPaint.setTextSize(mDaySize * mDisplayMetrics.scaledDensity);
//        String dayString;
//        int mMonthDays = DateUtils.getMonthDays(mSelYear, mSelMonth);
//        int weekNumber = DateUtils.getFirstDayWeek(mSelYear, mSelMonth);
//        Log.d("DateView", "DateView:" + mSelMonth + "月1号周" + weekNumber);
//        for (int day = 0; day < mMonthDays; day++) {
//            dayString = (day + 1) + "";
//            int column = (day + weekNumber - 1) % 7;
//            int row = (day + weekNumber - 1) / 7;
//            daysString[row][column] = day + 1;
//            int startX = (int) (mColumnSize * column + (mColumnSize - mPaint.measureText(dayString)) / 2);
//            int startY = (int) (mColumnSize * row + mColumnSize / 2 - (mPaint.ascent() + mPaint.descent()) / 2);


//			if(dayString.equals(mSelDay+"")){
        //绘制背景色矩形
//            int startRecX = mColumnSize * column + (mColumnSize - mCircleRadius) / 2;
//            int startRecY = mColumnSize * row + (mColumnSize - mCircleRadius) / 2;
//            int endRecX = startRecX + mCircleRadius;
//            int endRecY = startRecY + mCircleRadius;
//            mPaint.setColor(mNomalBGColor);
//	wujiaxing			canvas.drawRect(startRecX, startRecY, endRecX, endRecY, mPaint);
//            canvas.drawCircle((startRecX + endRecX) / 2, (startRecY + endRecY) / 2, (endRecX - startRecX) / 2 - 10, mPaint);
//				//记录第几行，即第几周
//            weekRow = row + 1;
//			}
//			//绘制事务圆形标志
//			drawCircle(row,column,day + 1,canvas);
//			if(dayString.equals(mSelDay+"")){
//				mPaint.setColor(mSelectDayColor);
//			}else if(dayString.equals(mCurrDay+"") && mCurrDay != mSelDay && mCurrMonth == mSelMonth){
//				//正常月，选中其他日期，则今日为红色
//				mPaint.setColor(mCurrentColor);
//			}else{
//				mPaint.setColor(mDayColor);
//			}
//			canvas.drawText(dayString, startX, startY, mPaint);


//        }
    }

    /**
     * 获取选择的月份
     *
     * @return
     */
    public int getmSelMonth() {
        return mSelMonth;
    }

    /**
     * 普通日期的字体颜色，默认黑色
     *
     * @param mDayColor
     */
    public void setmDayColor(int mDayColor) {
        this.mDayColor = mDayColor;
    }    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width = MeasureSpec.getSize(widthMeasureSpec);


        initSize();
   changeDays();
    }

    /**
     * 选择日期的颜色，默认为白色
     *
     * @param mSelectDayColor
     */
    public void setmSelectDayColor(int mSelectDayColor) {
        this.mSelectDayColor = mSelectDayColor;
    }    /**
     * 初始化列宽行高
     */
    private void initSize() {
        mColumnSize = getMeasuredWidth() / NUM_COLUMNS;

    }

    /**
     * 当前日期不是选中的颜色，默认红色
     *
     * @param mCurrentColor
     */
    public void setmCurrentColor(int mCurrentColor) {
        this.mCurrentColor = mCurrentColor;
    }    /**
     * 执行点击事件
     *
     * @param x
     * @param y
     */
    private void doClickAction(int x, int y) {
        int row = y / mColumnSize;
        int column = x / mColumnSize;


        //执行activity发送过来的点击处理事件
        if (dateClick != null) {
            dateClick.onClickOnDate(daysString[column][row]);
        }
    }

    /**
     * 日期的大小，默认18sp
     *
     * @param mDaySize
     */
    public void setmDaySize(int mDaySize) {
        this.mDaySize = mDaySize;
    }

    /**
     * 设置显示当前日期的控件
     *
     * @param tv_date  显示日期
     * @param tv_state 显示周
     */
    public void setTextView(TextView tv_date, TextView tv_state) {

        tv_date.setText((mSelMonth+1) + "月" + mCurrDay+"日");

        this.tv_state = tv_state;


    }

    /**
     * 设置事务天数
     *
     * @param daysHasThingList
     */
    public void setDaysHasThingList(List<Integer> daysHasThingList) {
        this.daysHasThingList = daysHasThingList;
        changeDays();
        invalidate();
    }

    /**
     * 刷新天数数据集坐标和状态
     */
    private void changeDays() {
        //这月的天数
        int mMonthDays = DateUtils.getMonthDays(mSelYear, mSelMonth);
        //这月从周几开始
        int weekNumber = DateUtils.getFirstDayWeek(mSelYear, mSelMonth);

        if (mMonthDays > 0) {

            int weeks = (int) (Math.ceil(((double) mMonthDays - 8 + weekNumber) / 7) + 1);
//            Log.d("tag",weeks+" : "+Math.ceil((mMonthDays- 8+DateUtils.getFirstDayWeek(mSelYear,mSelMonth))/7) +" :"+((double)mMonthDays- 8+DateUtils.getFirstDayWeek(mSelYear,mSelMonth))/7);
            //得到日历的行数
            NUM_ROWS = weeks;
        }
        setMeasuredDimension(width, width * NUM_ROWS / NUM_COLUMNS);


        //年和月改变 || 第一次进入
        if ((lastYear != -1 && lastYear != mSelYear)
                || (lastMonth != -1 && lastMonth != mSelMonth)
                || (lastYear == -1 && lastYear == -1)) {
            days.clear();

            daysString = new int[NUM_COLUMNS][NUM_ROWS];
            for (int i = 0; i < mMonthDays; i++) {

                Day day = new Day();
                day.content =String.format("%02d",i+1);
                day.date = i + 1;
                //day 的坐标
                int column = (i + weekNumber - 1) % 7;
                int row = (i + weekNumber - 1) / 7;
                //保存下，点击的时候会用到
                daysString[column][row] = i + 1;

                int padd = mColumnSize / 2 - mCircleRadius;
                day.start = mColumnSize * column + padd;
                day.top = mColumnSize * row + padd;
                day.end = day.start + mCircleRadius * 2;
                day.bottom = day.top + mCircleRadius * 2;


                days.put(i, day);
//                Log.e("tag,",mColumnSize+ " :"+column+"m :"+ day.toString() +": "+padd);
            }
        }

        for (int i : daysHasThingList) {

            if (days != null && days.get(i - 1) != null)
                days.get(i - 1).isSelected = true;

        }


    }

    /***
     * 设置圆圈的半径，默认为6
     * @param mCircleRadius
     */
    public void setmCircleRadius(int mCircleRadius) {
        this.mCircleRadius = mCircleRadius;
    }

    /**
     * 设置圆圈的半径
     *
     * @param mCircleColor
     */
    public void setmCircleColor(int mCircleColor) {
        this.mCircleColor = mCircleColor;
    }

    /**
     * 设置日期点击事件
     *
     * @param dateClick
     */
    public void setDateClick(DateClick dateClick) {
        this.dateClick = dateClick;
    }

    /**
     * 跳转至今天
     */
    public void setTodayToView() {
//        setSelectYearMonth(mCurrYear, mCurrMonth);

    }

    /**
     * 设置日期的点击回调事件
     *
     * @author shiwei.deng
     */
    public interface DateClick {
        public void onClickOnDate(int day);
    }

    class Day {
        String content;
        int date;
        //每个日期的边界
        int start, top, end, bottom;
        boolean isSelected;

        @Override
        public String toString() {
            return "Day{" +
                    "start=" + start +
                    ", top=" + top +
                    ", end=" + end +
                    ", bottom=" + bottom +
                    '}';
        }
    }












}
