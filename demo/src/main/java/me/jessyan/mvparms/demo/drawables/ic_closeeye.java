package me.jessyan.mvparms.demo.drawables;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;

import com.github.megatronking.svg.support.SVGRenderer;

/**
 * AUTO-GENERATED FILE.  DO NOT MODIFY.
 * 
 * This class was automatically generated by the
 * SVG-Generator. It should not be modified by hand.
 */
public class ic_closeeye extends SVGRenderer {

    public ic_closeeye(Context context) {
        super(context);
        mAlpha = 1.0f;
        mWidth = dip2px(16.0f);
        mHeight = dip2px(16.0f);
    }

    @Override
    public void render(Canvas canvas, int w, int h, ColorFilter filter) {
        final float scaleX = w / 16.0f;
        final float scaleY = h / 16.0f;
        
        mPath.reset();
        mRenderPath.reset();
        
        mFinalPathMatrix.setValues(new float[]{1.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 1.0f});
        mFinalPathMatrix.postScale(scaleX, scaleY);
        
        mPath.moveTo(15.8625f, 5.5555f);
        mPath.cubicTo(16.0338f, 5.2994f, 15.9774f, 4.9567f, 15.7475f, 4.7571f);
        mPath.cubicTo(15.4915f, 4.5575f, 15.1487f, 4.6139f, 14.9491f, 4.872f);
        mPath.cubicTo(14.9209f, 4.9002f, 11.7513f, 8.6404f, 7.9829f, 8.6404f);
        mPath.cubicTo(4.3294f, 8.6404f, 1.0167f, 4.872f, 0.9885f, 4.8438f);
        mPath.cubicTo(0.7889f, 4.6159f, 0.4179f, 4.5877f, 0.19f, 4.7873f);
        mPath.cubicTo(-0.0378f, 4.9869f, -0.066f, 5.3579f, 0.1336f, 5.5857f);
        mPath.cubicTo(0.19f, 5.6704f, 0.8756f, 6.4427f, 1.9603f, 7.2996f);
        mPath.lineTo(0.5046f, 8.8118f);
        mPath.cubicTo(0.2767f, 9.0396f, 0.305f, 9.4106f, 0.5328f, 9.6102f);
        mPath.cubicTo(0.5893f, 9.7251f, 0.7324f, 9.7816f, 0.8756f, 9.7816f);
        mPath.cubicTo(1.0187f, 9.7816f, 1.1619f, 9.7251f, 1.2748f, 9.6102f);
        mPath.lineTo(2.8455f, 7.9831f);
        mPath.cubicTo(3.5874f, 8.4972f, 4.4726f, 8.9832f, 5.4424f, 9.3239f);
        mPath.lineTo(4.8436f, 11.3502f);
        mPath.cubicTo(4.7589f, 11.6648f, 4.9282f, 11.9793f, 5.2428f, 12.064f);
        mPath.cubicTo(5.2992f, 12.064f, 5.3577f, 12.064f, 5.4142f, 12.064f);
        mPath.cubicTo(5.6702f, 12.064f, 5.9001f, 11.8926f, 5.9565f, 11.6366f);
        mPath.lineTo(6.5554f, 9.6102f);
        mPath.cubicTo(7.0131f, 9.6949f, 7.497f, 9.7534f, 7.9829f, 9.7534f);
        mPath.cubicTo(8.4688f, 9.7534f, 8.9527f, 9.6969f, 9.4104f, 9.6102f);
        mPath.lineTo(10.0092f, 11.6083f);
        mPath.cubicTo(10.0657f, 11.8644f, 10.3238f, 12.0358f, 10.5516f, 12.0358f);
        mPath.cubicTo(10.6081f, 12.0358f, 10.6665f, 12.0358f, 10.6948f, 12.0075f);
        mPath.cubicTo(11.0093f, 11.9229f, 11.1807f, 11.6083f, 11.094f, 11.2938f);
        mPath.lineTo(10.4952f, 9.2957f);
        mPath.cubicTo(11.465f, 8.9529f, 12.3501f, 8.467f, 13.0921f, 7.9549f);
        mPath.lineTo(14.6345f, 9.5538f);
        mPath.cubicTo(14.7495f, 9.6687f, 14.8906f, 9.7251f, 15.0338f, 9.7251f);
        mPath.cubicTo(15.1769f, 9.7251f, 15.3201f, 9.6687f, 15.433f, 9.5538f);
        mPath.cubicTo(15.6608f, 9.3259f, 15.6608f, 8.9832f, 15.4612f, 8.7553f);
        mPath.lineTo(14.0055f, 7.2431f);
        mPath.cubicTo(15.1769f, 6.3822f, 15.8624f, 5.5555f, 15.8624f, 5.5555f);
        mPath.lineTo(15.8625f, 5.5555f);
        mPath.close();
        mPath.moveTo(15.8625f, 5.5555f);
        
        mRenderPath.addPath(mPath, mFinalPathMatrix);
        mRenderPath.setFillType(android.graphics.Path.FillType.EVEN_ODD);
        if (mFillPaint == null) {
            mFillPaint = new Paint();
            mFillPaint.setStyle(Paint.Style.FILL);
            mFillPaint.setAntiAlias(true);
        }
        mFillPaint.setColor(applyAlpha(-8749419, 1.0f));
        mFillPaint.setColorFilter(filter);
        canvas.drawPath(mRenderPath, mFillPaint);

    }

}