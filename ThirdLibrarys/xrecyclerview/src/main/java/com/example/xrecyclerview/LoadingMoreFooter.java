package com.example.xrecyclerview;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

public class LoadingMoreFooter extends FrameLayout {

    public final static int STATE_LOADING = 0;
    public final static int STATE_COMPLETE = 1;
    public final static int STATE_NOMORE = 2;
    private TextView mText;

    private ProgressBar progressBar;

    public LoadingMoreFooter(Context context) {
        super(context);
        initView(context);
    }

    /**
     * @param context
     * @param attrs
     */
    public LoadingMoreFooter(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public void initView(Context context) {

View parent =        LayoutInflater.from(context).inflate(R.layout.yun_refresh_footer, this,false);
        mText = (TextView) parent.findViewById(R.id.msg);
        progressBar =  parent. findViewById(R.id.progressbar);

//        setLayoutParams();
        addView(parent,new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    }

    public void setState(int state) {
        switch (state) {
            case STATE_LOADING:

                progressBar.setVisibility(View.VISIBLE);
                mText.setText(getContext().getText(R.string.xrecycler_listview_loading));
                this.setVisibility(View.VISIBLE);
                break;
            case STATE_COMPLETE:

                mText.setText(getContext().getText(R.string.xrecycler_listview_loading));
                this.setVisibility(View.GONE);
                break;
            case STATE_NOMORE:

                mText.setText(getContext().getText(R.string.xrecycler_nomore_loading));
                progressBar.setVisibility(View.GONE);
                this.setVisibility(View.VISIBLE);
                break;
        }
    }

    public void reSet() {
        this.setVisibility(GONE);
    }
}
