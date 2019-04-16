package com.jess.arms.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Picture;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.jess.arms.R;


/**
 * 罗浮广场画布旋转加载动效
 */
public class LFRotateView extends View {
    private int mWidth;
    private int mHeight;

    private Paint mPaint;

    private Bitmap onePateal;

    private int petalWitdth;
    private int petalHeight;
    private int fromCenter;


    private int petalSize = 8;
    private int currenPetalSize = 0;


    private int colors[]  = new int[]{Color.parseColor("#AACC03"), Color.parseColor("#00A63C"),
            Color.parseColor("#00AEEB"), Color.parseColor("#005BAB"),
            Color.parseColor("#AE4283"), Color.parseColor("#C7161D"),
            Color.parseColor("#EA6100"), Color.parseColor("#F9BD00"),
          };


    private Handler handler = new Handler();


    public LFRotateView(Context context) {
        this(context, null);
    }

    public LFRotateView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LFRotateView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);


        onePateal = BitmapFactory.decodeResource(getResources(), R.mipmap.one_petal);

        petalWitdth = onePateal.getWidth();
        petalHeight = onePateal.getHeight();
        fromCenter = petalHeight / 7;

        //关闭硬件加速，
//        setLayerType(LAYER_TYPE_SOFTWARE, null);
        this.mWidth = (petalHeight + fromCenter) * 2;
        this.mHeight = mWidth;




    }

//    /**
//     * 录制花瓣绘制帧，
//     */
//    private void recoding() {
//        for (int i = 0; i < petalSize; i++) {
//            pictures[i] = new Picture();
//            //开始录制,得到对应帧的画布
//            Canvas canvas = pictures[i].beginRecording(mWidth, mHeight);
//            canvas.translate(mWidth / 2, mHeight / 2);
//            //每一帧都会增加一个花瓣数
//            for (int j = 0; j <= i; j++) {
//
//                // 改变bitmap 颜色
//                mPaint.setColorFilter(new PorterDuffColorFilter(colors[j], PorterDuff.Mode.SRC_IN));
//
//                //绘制花瓣
//                canvas.drawBitmap(onePateal, -petalWitdth / 2, -petalHeight - fromCenter, mPaint);
//
//                //旋转画布
//                canvas.rotate(360 / petalSize);
//
//
//            }
//            //结束录制
//            pictures[i].endRecording();
//        }
//
//
//    }

    @Override
    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);


            if(currenPetalSize==-1){//空白
                currenPetalSize++;
            }else if (currenPetalSize < petalSize - 1) {//1到7
               drawPetal(canvas);
                currenPetalSize++;
            } else {//8
                drawPetal(canvas);
                currenPetalSize = -1;
            }
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                postInvalidate();

            }
        }, 150); // 延时0.15秒
    }

    /**
     * 绘制每一帧的花瓣
     * @param canvas
     */
    private void drawPetal(Canvas canvas) {
        if(onePateal.isRecycled()){
            return;
        }
        canvas.save();



            canvas.translate(mWidth / 2, mHeight / 2);
            //每一帧都会增加一个花瓣数
            for (int j = 0; j <= currenPetalSize; j++) {

                // 改变bitmap 颜色
                mPaint.setColorFilter(new PorterDuffColorFilter(colors[j], PorterDuff.Mode.SRC_IN));

                //绘制花瓣
                canvas.drawBitmap(onePateal, -petalWitdth / 2, -petalHeight - fromCenter, mPaint);

                //旋转画布
                canvas.rotate(360 / petalSize);


            }


        canvas.restore();
    }

    @Override
    protected void onDetachedFromWindow() {
        handler.removeCallbacksAndMessages(null);
        onePateal.recycle();
        super.onDetachedFromWindow();

    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(mWidth, mHeight);
    }

}
