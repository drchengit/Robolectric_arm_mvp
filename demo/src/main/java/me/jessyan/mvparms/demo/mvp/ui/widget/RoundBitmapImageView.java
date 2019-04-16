package me.jessyan.mvparms.demo.mvp.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.util.Log;

import me.jessyan.mvparms.demo.R;


/**
 * @author DrChen
 * @Date 2019/2/27 0027.
 * qq:1414355045
 */
public class RoundBitmapImageView extends AppCompatImageView {


    /**
     * 减去阴影后，布局实际背景绘制区域，left
     */
    private int left;
    /**
     * 减去阴影后，布局实际背景绘制区域，top
     */
    private int top;
    /**
     * 减去阴影后，布局实际背景绘制区域，right
     */
    private int right;
    /**
     * 减去阴影后，布局实际背景绘制区域，bottom
     */
    private int bottom;
    private float mRadius;
    private float effect;
    /**
     * 阴影斜度x
     */
    private int offset_X;
    /**
     * 阴影斜度y
     */
    private int offset_Y;
    private int mShadowColor;


    private int width;
    private int height;
    //图片的实际绘制区域
    private int imageWidth;
    private int imageHeight;
    private Paint mPaint;


    public RoundBitmapImageView(final Context context) {
        this(context, null);
    }

    public RoundBitmapImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RoundBitmapImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.setScaleType(ScaleType.FIT_XY);
        TypedArray ta = context.obtainStyledAttributes(attrs,
                R.styleable.RoundImageView, defStyle, 0);
        if (ta != null) {
            mRadius = ta.getDimension(R.styleable.RoundImageView_image_radius, 0);
            effect = ta.getDimension(R.styleable.RoundImageView_image_effect, 0);
            offset_X = ta.getDimensionPixelOffset(R.styleable.RoundImageView_public_offset_x, 0);
            offset_Y = ta.getDimensionPixelOffset(R.styleable.RoundImageView_public_offset_y, 0);

            mShadowColor = ta.getColor(R.styleable.RoundImageView_shadow_color, Color.WHITE);
            ta.recycle();
        } else {
            mRadius = 0;
            effect = 0;
            mShadowColor = 0xffe4e4e4;
        }


        initPaint();
    }

    private void initPaint() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        this.width = w;
        this.height = h;
        imageWidth = (int) (w - effect * 4 - Math.abs(offset_X) * 2);
        imageHeight = (int) (h - effect * 4 - Math.abs(offset_Y) * 2);

        left = (int) (Math.abs(offset_X) + effect * 2);
        top = (int) (Math.abs(offset_Y) + effect * 2);
        right = (int) (width - Math.abs(offset_Y) - effect * 2);
        bottom = (int) (height - Math.abs(offset_Y) - effect * 2);

    }

    public int getImageWidth() {
        return imageWidth;
    }

    public int getImageHeight() {
        return imageHeight;
    }

    /**
     * drawable转bitmap
     *
     * @param drawable
     * @return
     */
    private Bitmap drawableToBitmap(Drawable drawable) {
        if (drawable == null) {
            return null;
        } else if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        }
        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicHeight(),
                drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }

    /**
     * 重设Bitmap的宽高
     *
     * @param bitmap
     * @param newWidth
     * @param newHeight
     * @return
     */
    private Bitmap reSizeImage(Bitmap bitmap, int newWidth, int newHeight) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        // 计算出缩放比
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // 矩阵缩放bitmap
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        return Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension(width, height);
    }

    @Override
    public void onDraw(Canvas canvas) {
//        Bitmap image = drawableToBitmap(getDrawable());

        //减去阴影重设高宽
        Bitmap  reSizeImage = reSizeImage(drawableToBitmap(getDrawable()), imageWidth, imageHeight);


        canvas.drawBitmap(createRoundImage(reSizeImage),
                0, 0, null);


    }

    private Bitmap createRoundImage(Bitmap bitmap) {
        if (bitmap == null) {
            throw new NullPointerException("Bitmap can't be null");
        }
        //将图片切成圆角
        BitmapShader bitmapShader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        //新建对象
        Bitmap targetBitmap = Bitmap.createBitmap(imageWidth, imageHeight, Bitmap.Config.ARGB_8888);
        //得到这个对象的画布
        Canvas targetCanvas = new Canvas(targetBitmap);
        //将图片着色到画笔上
        mPaint.setShader(bitmapShader);
        RectF rect = new RectF(0, 0, imageWidth, imageHeight);
        //将图片画到新图上，带圆角的；
        targetCanvas.drawRoundRect(rect, mRadius, mRadius, mPaint);

        //新建最终的bitmap
        Bitmap target = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        //得到画布
        Canvas canvas = new Canvas(target);

        //准备阴影画笔
        mPaint.setShader(null);
        mPaint.setColor(mShadowColor);
        mPaint.setShadowLayer(effect, offset_X, offset_Y, mShadowColor);

        //绘制阴影
        RectF rectF = new RectF(left, top, right, bottom);

        canvas.drawRoundRect(rectF, mRadius, mRadius, mPaint);
        //设置提取模式
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OVER));
        //清理画笔
//        mPaint.setShadowLayer(0, 0, 0, 0xffffff);
        mPaint.clearShadowLayer();
        mPaint.setColor(Color.WHITE);

        //画上圆角图片

        canvas.drawBitmap(targetBitmap,left,top, mPaint);


        return target;

//
//        if(mIsShadow){
//            mPaint.setShader(null);
//            mPaint.setColor(mShadowColor);
//            mPaint.setShadowLayer(effect, offset_X, offset_Y, mShadowColor);
//        }
//        if (mIsShadow){
//
//            Bitmap target = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
//            Canvas canvas = new Canvas(target);
//
//            //阴影区域
//            RectF rectF = new RectF(left, top, right, bottom);
//            canvas.drawRoundRect(rectF, mRadius, mRadius, mPaint);
//
//            canvas.drawBitmap(bitmap, mRadius, mRadius, mPaint);
//            return target;
//        }else {
//            return targetBitmap;
//        }

    }



}
