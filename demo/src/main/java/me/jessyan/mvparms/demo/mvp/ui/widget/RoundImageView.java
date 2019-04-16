package me.jessyan.mvparms.demo.mvp.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.Region;
import android.os.Build;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;

import me.jessyan.mvparms.demo.R;


/**
 * @author Drchen
 * <p>
 * 自写带阴影的ConstraintLayout
 * <p>
 * 必须设置backGround 不然不进onDraw(),
 */

public class RoundImageView extends android.support.v7.widget.AppCompatImageView {
    /**
     * 画笔，这里设置阴影
     */
    private Paint paint2;



    /**
     * 阴影颜色
     */
    private int shawder_color;
    /**
     * 圆角
     */
    private int radius;
    /**
     * 阴影斜度x
     */
    private int offset_X;
    /**
     * 阴影斜度y
     */
    private int offset_Y;
    /**
     * 阴影宽度
     */
    private int effect;
    /**
     * 布局宽
     */
    private int with;
    /**
     * 布局高
     */
    private int higth;

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
    /**
     * 四边是否需要绘制
     */
    private boolean notop;


    private String TAG = "ShawderConstrainLayout";
    private RectF rectF;
    private Path path;
    private RectF layer;  //内容区域

    public RoundImageView(Context context) {
        this(context, null);


    }

    public RoundImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    private void init(AttributeSet attrs) {


        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.PublicShadowLayout);
        /**
         * 本项目的radio 不做百分比， 都用dp
         */
        radius = typedArray.getDimensionPixelSize(R.styleable.PublicShadowLayout_public_radius, 50);


        shawder_color = typedArray.getColor(R.styleable.PublicShadowLayout_public_shaw_color, Color.BLACK);

        effect = typedArray.getDimensionPixelSize(R.styleable.PublicShadowLayout_public_effect, 20);

        offset_X = typedArray.getDimensionPixelOffset(R.styleable.PublicShadowLayout_public_offset_x, 20);
        offset_Y = typedArray.getDimensionPixelOffset(R.styleable.PublicShadowLayout_public_offset_y, 20);
        notop = typedArray.getBoolean(R.styleable.PublicShadowLayout_public_notop, false);
        typedArray.recycle();
initPaint();

        int padd_top;
        int padd_left;
        int padd_right;
        int padd_bottom;

        padd_left = Math.abs(offset_X) + effect * 2;

        if (notop) {
            padd_top = 0;
        } else {
            padd_top = Math.abs(offset_Y) + effect * 2;
        }

        padd_right = Math.abs(offset_X) + effect * 2;


        padd_bottom = Math.abs(offset_Y) + effect * 2;


        setPadding(padd_left, padd_top, padd_right, padd_bottom);


    }

    private void initPaint() {
        //阴影画笔
        paint2 = new Paint();
        paint2.setAntiAlias(true);
        // 设定颜色
        paint2.setColor(Color.TRANSPARENT);
        // 设定阴影(柔边, X 轴位移, Y 轴位移, 阴影颜色)
//            paint2.setShadowLayer(5, 3, 3, );
        paint2.setShadowLayer(effect, offset_X, offset_Y, shawder_color);

//        //遮挡圆角画笔
//        paint =new Paint();
//        paint.setColor(Color.WHITE);
//        paint.setAntiAlias(true);//消除锯齿

        path = new Path();
        //这个要开，不然没有阴影
        setLayerType(LAYER_TYPE_SOFTWARE, null);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        layer = new RectF(0, 0, w, h);
        this.with = w;
        this.higth = h;

        left = Math.abs(offset_X) + effect * 2;

        if (notop) {
            top = 0;
        } else {
            top = Math.abs(offset_Y) + effect * 2;
        }

        right = with - Math.abs(offset_X) - effect * 2;

        bottom = higth - Math.abs(offset_Y) - effect * 2;

        rectF = new RectF(left, top, right, bottom);
        //裁剪区域
        path.reset();
        path.addRoundRect(rectF, radius, radius, Path.Direction.CCW);
    }
    @Override
    protected void onDraw(Canvas canvas) {

        //阴影绘制
        canvas.drawRoundRect(rectF, radius, radius, paint2);
        if(Build.VERSION.SDK_INT >= 26){
            canvas.clipPath(path);
        }else {
            canvas.clipPath(path, Region.Op.REPLACE);
        }

        super.onDraw(canvas);
    }

//    @Override
//    public void draw(Canvas canvas) {
//        super.draw(canvas);
//        drawLeftTop(canvas);
//        drawRightTop(canvas);
//        drawLeftBottom(canvas);
//        drawRightBottom(canvas);
//    }

//    private void drawLeftTop(Canvas canvas) {
//        Path path = new Path();
//        path.moveTo(0, cornerSize);
//        path.lineTo(0, 0);
//        path.lineTo(cornerSize, 0);
//        path.arcTo(new RectF(0, 0, cornerSize * 2, cornerSize * 2), -90, -90);
//        path.close();
//        canvas.drawPath(path, paint);
//    }
//
//    private void drawRightTop(Canvas canvas) {
//        Path path = new Path();
//        path.moveTo(getWidth(), cornerSize);
//        path.lineTo(getWidth(), 0);
//        path.lineTo(getWidth() - cornerSize, 0);
//        path.arcTo(new RectF(getWidth() - cornerSize * 2, 0, getWidth(), 0 + cornerSize * 2), -90, 90);
//        path.close();
//        canvas.drawPath(path, paint);
//    }
//
//    private void drawLeftBottom(Canvas canvas) {
//        Path path = new Path();
//        path.moveTo(0, getHeight() - cornerSize);
//        path.lineTo(0, getHeight());
//        path.lineTo(cornerSize, getHeight());
//        path.arcTo(new RectF(0, getHeight() - cornerSize * 2, cornerSize * 2, getHeight()), 90, 90);
//        path.close();
//        canvas.drawPath(path, paint);
//    }
//
//    private void drawRightBottom(Canvas canvas) {
//        Path path = new Path();
//        path.moveTo(getWidth() - cornerSize, getHeight());
//        path.lineTo(getWidth(), getHeight());
//        path.lineTo(getWidth(), getHeight() - cornerSize);
//        RectF oval = new RectF(getWidth() - cornerSize * 2, getHeight() - cornerSize * 2, getWidth(), getHeight());
//        path.arcTo(oval, 0, 90);
//        path.close();
//        canvas.drawPath(path, paint);
//    }


}

          
