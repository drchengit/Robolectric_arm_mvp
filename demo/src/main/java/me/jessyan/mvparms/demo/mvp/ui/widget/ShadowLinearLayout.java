package me.jessyan.mvparms.demo.mvp.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import me.jessyan.mvparms.demo.R;


/**
 * @author Drchen
 * <p>
 * 自写带阴影的ConstraintLayout
 * <p>
 * 必须设置backGround 不然不进onDraw(),
 */

public class ShadowLinearLayout extends LinearLayout {
    /**
     * 画笔，这里设置阴影
     */
    private Paint paint2;
    /**
     * 背景颜色
     */
    private int bg_color;
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
    private boolean noleft, notop, noright, nobottom;


    private String TAG = "ShawderConstrainLayout";


    public ShadowLinearLayout(Context context) {
        this(context, null);


    }

    public ShadowLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    private void init(AttributeSet attrs) {


        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.PublicShadowLayout);
        /**
         * 本项目的radio 不做百分比， 都用dp
         */
        radius = typedArray.getDimensionPixelSize(R.styleable.PublicShadowLayout_public_radius, 50);

        bg_color = typedArray.getColor(R.styleable.PublicShadowLayout_public_bg_color, Color.BLUE);
        shawder_color = typedArray.getColor(R.styleable.PublicShadowLayout_public_shaw_color, Color.BLACK);

        effect = typedArray.getDimensionPixelSize(R.styleable.PublicShadowLayout_public_effect, 20);

        offset_X = typedArray.getDimensionPixelOffset(R.styleable.PublicShadowLayout_public_offset_x, 20);
        offset_Y = typedArray.getDimensionPixelOffset(R.styleable.PublicShadowLayout_public_offset_y, 20);
        notop = typedArray.getBoolean(R.styleable.PublicShadowLayout_public_notop, false);
        noleft = typedArray.getBoolean(R.styleable.PublicShadowLayout_public_noleft, false);
        noright = typedArray.getBoolean(R.styleable.PublicShadowLayout_public_noright, false);
        nobottom = typedArray.getBoolean(R.styleable.PublicShadowLayout_public_nobottom, false);
        typedArray.recycle();

        paint2 = new Paint();
        paint2.setAntiAlias(true);
        // 设定颜色
        paint2.setColor(bg_color);
        // 设定阴影(柔边, X 轴位移, Y 轴位移, 阴影颜色)
//            paint2.setShadowLayer(5, 3, 3, );
        paint2.setShadowLayer(effect, offset_X, offset_Y, shawder_color);


        //这个要开，不然没有阴影
        setLayerType(LAYER_TYPE_SOFTWARE, null);
        int padd_top;
        int padd_left;
        int padd_right;
        int padd_bottom;
        if (noleft) {
            padd_left = 0;
        } else {
            padd_left = Math.abs(offset_X) + effect * 2;
        }
        if (notop) {
            padd_top = 0;
        } else {
            padd_top =  Math.abs(offset_Y) + effect * 2;
        }
        if (noright) {
            padd_right = 0;
        } else {
            padd_right =  Math.abs(offset_Y) +effect * 2;
        }

        if (nobottom) {
            padd_bottom = 0;
        } else {
            padd_bottom =  Math.abs(offset_Y) + effect * 2;
        }

        setPadding(padd_left, padd_top, padd_right, padd_bottom);



    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        this.with = w;
        this.higth = h;
        if (noleft) {
            left = 0;
        } else {
            left = Math.abs(offset_X) + effect * 2;
        }
        if (notop) {
            top = 0;
        } else {
            top = Math.abs(offset_Y) + effect * 2;
        }
        if (noright) {
            right = with;
        } else {
            right = with - Math.abs(offset_Y) - effect * 2;
        }
        if (nobottom) {
            bottom = higth;
        } else {
            bottom = higth - Math.abs(offset_Y) - effect * 2;
        }

    }

    @Override
    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas); //去掉减少不必要的绘制

        RectF rectF = new RectF(left, top, right, bottom);
        canvas.drawRoundRect(rectF, radius, radius, paint2);

    }


}