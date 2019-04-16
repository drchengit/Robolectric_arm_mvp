package me.jessyan.mvparms.demo.mvp.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;

import com.jess.arms.utils.ArmsUtils;

import me.jessyan.mvparms.demo.R;

/**
 *  气泡view
 */
public class BubbleView extends View{
    private int shadowColor ;
    //三角形的color,和背景的color;
    private int shapeColor;
    private int effect;

    private Paint mPaint;

    /**
     * 三角形距离右边多少
     */
    private int triangleMaginEnd;
    /**
     * 三角形高
     */
    private int triangleH;
    /**
     * 宽
     */
    private int triangleW;

    /**
     * 气泡长方形的绘制区域
     */
    private RectF f ;

    /**
     * 是否有三角形
     */
    private boolean hasTriangle=false;

    private boolean hasBottom = false;
    /**
     * 三角形 和矩形区域
     */
    private Path mPath;

    private String text = "删除";
    /**
     * 字体颜色这些东西
     */
    private int textColor ;
    private int textSize;


    public BubbleView(Context context) {
        this(context,null);
    }

    public BubbleView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public BubbleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray ta = context.obtainStyledAttributes(attrs,R.styleable.BubbleView);
        hasTriangle = ta.getBoolean(R.styleable.BubbleView_hasTriangle,true);
        hasBottom = ta.getBoolean(R.styleable.BubbleView_hasBottom,true);
        text = ta.getString(R.styleable.BubbleView_text);
        ta.recycle();

        textColor = ContextCompat.getColor(context,R.color.color_2F3344);
        shadowColor = ContextCompat.getColor(context, R.color.color_n10_000000);
        shapeColor = ContextCompat.getColor(context,R.color.white);
        textSize = ArmsUtils.dip2px(context,14);

        effect = ArmsUtils.dip2px(getContext(),4);
        triangleMaginEnd = ArmsUtils.dip2px(context,8+12);
        triangleH = ArmsUtils.dip2px(context,8);
        triangleW = ArmsUtils.dip2px(context,8);

        mPaint = new Paint();
        mPaint.setAntiAlias(true);


        // 设定阴影(柔边, X 轴位移, Y 轴位移, 阴影颜色)
//            paint2.setShadowLayer(5, 3, 3, );
        mPaint.setShadowLayer(effect, 0, 0, shadowColor);
        //这个要开，不然没有阴影
        setLayerType(LAYER_TYPE_SOFTWARE, null);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        int top = 0;
        int bottom=0;
        if(hasBottom){
            bottom = h-effect*2;

        }else {
            bottom = h;
        }

        if(hasTriangle){
            top = triangleH;
        }else {
            top = 0;
        }
        /**
         * 矩形区域
         */
 f = new RectF(effect*2,top,w-effect*2,bottom);
        mPath = new Path();
    if(hasTriangle){
        initTriangleAndRect(w,h);
    }


    }

    /**
     * 绘制三角形和矩形区域
     */
    private void initTriangleAndRect(int w,int h) {
            //矩形top left点
            mPath.moveTo(f.left,f.top);
            //三角形left 底点
            mPath.lineTo(w-triangleMaginEnd-triangleW,triangleH);
            //三角形顶点
            mPath.lineTo(w-triangleMaginEnd-triangleW/2,0);
            //三角形底点
            mPath.lineTo(w-triangleMaginEnd,triangleH);
            //矩形right top 点
            mPath.lineTo(f.right,f.top);
            //矩形right bottom 点
           mPath.lineTo(f.right,f.bottom);
           //矩形 left bottom 点
            mPath.lineTo(f.left,f.bottom);
            //关闭
            mPath.close();

//        mTrianglePath.moveTo(w-triangleMaginEnd,triangleH);
//        mTrianglePath.lineTo(w-triangleMaginEnd-triangleW/2,0);
//        mTrianglePath.lineTo(w-triangleMaginEnd-triangleW,triangleH);
//
//        mTrianglePath.close();
    }

    public void setText(String text) {
        this.text = text;
        postInvalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);
        mPaint.setColor(shapeColor);
//        //绘制矩形区域
     if(!hasTriangle) canvas.drawRect(f,mPaint);

        //绘制三角形和矩形区域
       if(hasTriangle) canvas.drawPath(mPath,mPaint);

       //绘制字体
        mPaint.setColor(textColor);
        //用文字的中间底线部分作为位置的标准
        mPaint.setTextAlign(Paint.Align.CENTER);
        mPaint.setTextSize(textSize);

        float centerY = ((f.top+f.bottom)/2);



        Paint.FontMetrics fontMetrics = mPaint.getFontMetrics();
        float top = fontMetrics.top;//为基线到字体上边框的距离 top 是负数
        float bottom = fontMetrics.bottom;//为基线到字体下边框的距离

        int baseLineY = (int) (centerY-top-(bottom-top)/2);//最终基线的位置

        canvas.drawText(text,(f.right+f.left)/2,baseLineY,mPaint);
    }


}
