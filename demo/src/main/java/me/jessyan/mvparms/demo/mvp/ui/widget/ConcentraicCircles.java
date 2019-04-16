package me.jessyan.mvparms.demo.mvp.ui.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;


import com.jess.arms.utils.ArmsUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


/**
 * 每日签到的同心圆
 */
public class ConcentraicCircles extends View {

    private int mMaxRadius; // 最大波纹半径

    private int mInitialRadius;

    private long mDuration = 2000; // 一个波纹从创建到消失的持续时间
    private int mSpeed = 1000;   // 波纹的创建速度，每500ms创建一个
    private List<Circle> mCircleList = new ArrayList<Circle>();
    private Interpolator mInterpolator = new LinearInterpolator();
    /**
     * 最外面圆的color
     */
    private int waveColor = Color.parseColor("#ffffff");

    private int threeColor = Color.parseColor("#ffffff");

    private int strColor = Color.parseColor("#F66440");
    private String oneStr = "点击签到";
    private int oneStrSize;
    private String twoStr = "+20积分";
    private int twoStrSize;

    private int cx, cy;

    private Paint mBgPaint;
    //波纹画笔
    private Paint mWavePaint ;

    private boolean mIsRunning;

    private Runnable mCreateCircle = new Runnable() {
        @Override
        public void run() {
            if (mIsRunning) {
                newCircle();
                postDelayed(mCreateCircle, mSpeed);
            }
        }
    };

    public ConcentraicCircles(Context context) {
        this(context, null);
    }

    public ConcentraicCircles(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ConcentraicCircles(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mBgPaint = new Paint();
        mBgPaint.setAntiAlias(true);

        mWavePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mWavePaint.setAntiAlias(true);
        mWavePaint.setStyle(Paint.Style.FILL);
        mWavePaint.setColor(waveColor);
        oneStrSize = ArmsUtils.dip2px(getContext(), 14);
        twoStrSize = ArmsUtils.dip2px(getContext(), 10);
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mMaxRadius = w / 2;

        mInitialRadius = mMaxRadius - ArmsUtils.dip2px(getContext(), 12) ;

        cx = w / 2;
        cy = cx;

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //绘制波纹
        drawWave(canvas);


        //绘制第背景
        mBgPaint.setColor(threeColor);
        canvas.drawCircle(cx, cy, mInitialRadius, mBgPaint);

        drawText(canvas);


        //刷新
        if (mCircleList.size() > 0) {
            postInvalidateDelayed(10);
        }

    }

    @Override
    protected void onDetachedFromWindow() {
        stopImmediately();
        super.onDetachedFromWindow();

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int w = getDefaultSize(getMinimumWidth(), widthMeasureSpec);
        setMeasuredDimension(w, w);
    }


    /**
     * 缓慢停止
     */
    public void stop() {
        mIsRunning = false;
    }

    /**
     * 立即停止
     */
    public void stopImmediately() {
        mIsRunning = false;
        mCircleList.clear();
        invalidate();
    }
    /**
     * 绘制波纹
     *
     * @param canvas
     */
    private void drawWave(Canvas canvas) {
        Iterator<Circle> iterator = mCircleList.iterator();
        while (iterator.hasNext()) {
            Circle circle = iterator.next();
            float radius = circle.getCurrentRadius();
            if (System.currentTimeMillis() - circle.mCreateTime < mDuration) {

                mWavePaint.setAlpha(circle.getAlpha());
                canvas.drawCircle(getWidth() / 2, getHeight() / 2, radius, mWavePaint);
            } else {
                iterator.remove();
            }
        }

        canvas.drawCircle(getWidth() / 2, getHeight() / 2, mInitialRadius, mBgPaint);
    }

    /**
     * 绘制文字
     *
     * @param canvas
     */
    private void drawText(Canvas canvas) {
        //用文字的中间底线部分作为位置的标准
        mBgPaint.setTextAlign(Paint.Align.CENTER);


        mBgPaint.setColor(strColor);

        int liney = cy + ArmsUtils.dip2px(getContext(), 6);

        //绘制上面的Text
        mBgPaint.setTextSize(oneStrSize);
        Paint.FontMetrics fontMetrics = mBgPaint.getFontMetrics();
//        float top = fontMetrics.top;//为基线到字体上边框的距离
        float bottom = fontMetrics.bottom;//为基线到字体下边框的距离
        int baseLineY = (int) (liney - bottom - ArmsUtils.dip2px(getContext(), 2));//最终基线的位置
        canvas.drawText(oneStr, cx, baseLineY, mBgPaint);

        Rect band = new Rect();
        mBgPaint.getTextBounds(oneStr, 0, oneStr.length(), band);

        //绘制line
        canvas.drawLine(cx - band.width() / 2, liney, cx + band.width() / 2, liney + ArmsUtils.dip2px(getContext(), 1), mBgPaint);

        //绘制下面的
        mBgPaint.setTextSize(twoStrSize);
        Paint.FontMetrics metrics = mBgPaint.getFontMetrics();
        float twoTop = metrics.top;
        //最终的文字基线
        int textY = (int) (liney + ArmsUtils.dip2px(getContext(), 3) + Math.abs(twoTop));
        canvas.drawText(twoStr, cx, textY, mBgPaint);
    }



    /**
     * 开始
     */
    public void start() {
        if (!mIsRunning) {
            mIsRunning = true;
            mCreateCircle.run();
        }
    }

    private void newCircle() {

        Circle circle = new Circle();
        mCircleList.add(circle);
        invalidate();

    }

    /**
     * 改变状态
     *
     * @param oneStr
     * @param twoStr
     */
    public void setStrs(String oneStr, String twoStr) {
        this.oneStr = oneStr;
        this.twoStr = twoStr;
        invalidate();
    }

    private class Circle {
        private long mCreateTime;


        Circle(int lateTime) {
            mCreateTime = System.currentTimeMillis() + lateTime;
        }

        Circle() {
            mCreateTime = System.currentTimeMillis();
        }

        int getAlpha() {

            float percent = (getCurrentRadius() - mInitialRadius) / (mMaxRadius - mInitialRadius);

            if (percent < 0) {
                Log.e("透明度", 0 + "  ");
                return 0;
            } else {
                Log.e("透明度", (255 * 0.5 - mInterpolator.getInterpolation(percent) * 255 * 0.5) + "  ");
                return (int) (255 * 0.5 - mInterpolator.getInterpolation(percent) * 255 * 0.5);
            }
//            return 255;
        }

        float getCurrentRadius() {
            float percent = (System.currentTimeMillis() - mCreateTime) * 1.0f / mDuration;
            if (percent < 0) {
                percent = 0;
                return 0;
            }
            Log.e("透明度", percent + " ss ");
            return mInitialRadius + mInterpolator.getInterpolation(percent) * (mMaxRadius - mInitialRadius);
        }
    }

}
