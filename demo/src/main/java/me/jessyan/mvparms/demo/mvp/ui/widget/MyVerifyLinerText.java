package me.jessyan.mvparms.demo.mvp.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jess.arms.utils.ArmsUtils;

import java.lang.ref.SoftReference;

import me.jessyan.mvparms.demo.R;
import me.jessyan.mvparms.demo.utils.PerfectClickListener;


/**
 * @author DrChen
 * @Date 2018/10/19 0019.
 * qq:1414355045
 */
public class MyVerifyLinerText extends FrameLayout {
    /**
     * 验证码号码
     */
    private VerifyEditText editText;
    /**
     * 提示倒数时
     */
    private TextView noticeText;

    /**
     * 验证码提示文字
     */
    private String hint = "请输入验证码";
    /**
     * 提示初始文字
     */
    private String noticeInitStr = "获取验证码";
    /**
     * 第二次时
     */
    private String noticeMoreStr = "重新发送";


    /**
     * 是否在倒计时中
     */
    private boolean isTiming;
    /**
     * 倒计时颜色
     */
    private int noticeColorClick, noticeColorUnClick;

    /**
     * 下划线颜色
     */
    private int lineColorCilck, lineColorUnCilck;
    /**
     * 画笔呀
     */
    private Paint mPaint;

    private boolean noHasleftDrawble;

    private float linePosition;

    private float lineStrokeWith;

    private SoftReference<VerifyListener> verifyListener;
    /**
     *
     */
    private OnFocusChangeListener focusChangeListener = new OnFocusChangeListener() {
        @Override
        public void onFocusChange(View v, boolean hasFocus) {

            if (hasFocus) {
                lineStrokeWith = ArmsUtils.dip2px(getContext(), 1);
                mPaint.setStrokeWidth(lineStrokeWith);
                mPaint.setColor(lineColorCilck);
            } else {
                lineStrokeWith = ArmsUtils.dip2px(getContext(), 1);
                mPaint.setColor(lineColorUnCilck);
                mPaint.setStrokeWidth(lineStrokeWith);
            }
        }
    };
    /**
     * 倒计时
     */
    private CountDownTimer timer = new CountDownTimer(60 * 1000, 1000) {
        @Override
        public void onTick(long millisUntilFinished) {
            isTiming = true;
            noticeText.setText((millisUntilFinished / 1000) + "秒后获取");
            setTextViewClickable(false);

        }

        @Override
        public void onFinish() {
            isTiming = false;
            if (noticeInitStr != null) {
                noticeText.setText(noticeMoreStr);
            }

            setTextViewClickable(true);
        }
    };
    public MyVerifyLinerText(Context context) {
        this(context, null);
    }


    public MyVerifyLinerText(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);


        init(context, attrs);
    }

    /**
     * 初始化
     */
    private void init(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs,
                R.styleable.UserMyVerifyLinerText);
        hint = typedArray.getString(R.styleable.UserMyVerifyLinerText_user_hint);
        noticeInitStr = typedArray.getString(R.styleable.UserMyVerifyLinerText_user_text);

        // 2. 设置分割线颜色（使用十六进制代码，如#333、#8e8e8e）
        int lineColorClick_default = context.getResources().getColor(R.color.color_4E79FF); // 默认 = 蓝色#1296db
        int lineColorunClick_default = context.getResources().getColor(R.color.color_B9BCCD); // 默认 = 灰色#9b9b9b
        noticeColorClick = typedArray.getColor(R.styleable.UserMyVerifyLinerText_user_lineColor_click, lineColorClick_default);
        noticeColorUnClick = typedArray.getColor(R.styleable.UserMyVerifyLinerText_user_lineColor_unclick, lineColorunClick_default);

        lineColorCilck = typedArray.getColor(R.styleable.UserMyVerifyLinerText_user_lineColor_click, lineColorClick_default);
        lineColorUnCilck = typedArray.getColor(R.styleable.UserMyVerifyLinerText_user_lineColor_unclick, lineColorunClick_default);
        linePosition = typedArray.getDimension(R.styleable.UserMyVerifyLinerText_user_linePosition, ArmsUtils.dip2px(context, 1));
        noHasleftDrawble = typedArray.getBoolean(R.styleable.UserMyVerifyLinerText_user_noHasleftDrawble, false);
        LayoutInflater inflater = LayoutInflater.from(context);

        View contentView = inflater.inflate(R.layout.user_custom_my_verify_linertext, this, false);
        noticeText = contentView.findViewById(R.id.custom_notice_tv);
        noticeText.setText(noticeInitStr);
        noticeText.setTextColor(noticeColorClick);
        editText = contentView.findViewById(R.id.custom_verify_et);
        //倒计时避免双次点击的点击监听
        noticeText.setOnClickListener(new PerfectClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                if (v.getId() == R.id.custom_notice_tv) {
                    if (verifyListener.get() != null) {
                        verifyListener.get().onClick(v);
                    }
                }
            }
        });
        LinearLayout.LayoutParams lParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);

        mPaint = new Paint();
        lineStrokeWith = ArmsUtils.dip2px(context, 1);
        mPaint.setStrokeWidth(lineStrokeWith);
        mPaint.setColor(lineColorUnCilck);

        editText.setNoHasleftDrawble(noHasleftDrawble);
        editText.setOnFocusChangeListener(focusChangeListener);

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (verifyListener.get() != null) {

                    verifyListener.get().onTextChanged(s, start, before, count);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        //加载布局
        this.addView(contentView, lParams);
                //置灰等待刷新
        setTextViewClickable(true);
    }

    /**
     * 按钮不可点和置灰
     */
    public void setTextViewClickable(boolean b) {
        if (b) {
            noticeText.setTextColor(noticeColorClick);
            noticeText.setClickable(true);
        } else {
            noticeText.setTextColor(noticeColorUnClick);
            noticeText.setClickable(false);
        }
    }

    public void setVerifyListener(VerifyListener verifyListener) {
        this.verifyListener =new SoftReference<VerifyListener>(verifyListener);
    }

    public String getText() {
        return ArmsUtils.isEmpty(editText.getText()) ? "" : editText.getText().toString();
    }

    /**
     * 开始计时
     */
    public void startTimer() {
        //没有正在倒计时
        if (!isTiming) {
            timer.start();
        }
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);

        //绘制下划线
        canvas.drawLine(0, this.getMeasuredHeight() - linePosition - lineStrokeWith, this.getMeasuredWidth(),
                this.getMeasuredHeight() - linePosition - lineStrokeWith, mPaint);
    }



    @Override
    protected void onDetachedFromWindow() {

        if (timer != null) {
            timer.cancel();
            timer = null;
        }
        if(verifyListener.get()!=null){
            verifyListener = null;
        }
        super.onDetachedFromWindow();
    }

    public interface VerifyListener {
        void onTextChanged(CharSequence s, int start, int before, int count);

        void onClick(View view);
    }

}



