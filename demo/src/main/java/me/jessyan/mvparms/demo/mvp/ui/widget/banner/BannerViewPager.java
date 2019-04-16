package me.jessyan.mvparms.demo.mvp.ui.widget.banner;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;



import java.util.List;

import me.jessyan.mvparms.demo.R;

/**
 * @author DrChen
 * @Date 2018/9/26 0026.
 * qq:1414355045
 */
public class BannerViewPager extends ViewPager implements View.OnTouchListener {
    /**
     * const
     */
    private static final String TAG = "BannerViewPager";
    private static final int LOOP_MSG = 0x1001;
    private static final int LOOP_COUNT = 5000;

    /**
     * attrs
     */
    private int loopTime;
    private boolean isLoop;//是否自动轮播
    private int switchTime;
    private int loopMaxCount = 1;
    private boolean isSlide;//是否可以轮播滑动

    /**
     * others
     */
    private int currentIndex;
    private LayoutInflater inflater;
    private Rect screentRect;

    /**
     * item 视差
     */
    private CardTransformer mTransformer;

    /**
     * handler
     */
    private Handler handler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == LOOP_MSG) {
                if (isSlide) {
                    currentIndex = getCurrentItem();
                    if (currentIndex >= LOOP_COUNT / 2) {
                        currentIndex++;
                    }
                    if (currentIndex > LOOP_COUNT) {
                        currentIndex = LOOP_COUNT / 2;
                    }
                    setCurrentItem(currentIndex);

                    handler.sendEmptyMessageDelayed(LOOP_MSG, loopTime);


                }
            }
        }
    };


    public BannerViewPager(@NonNull Context context) {
        this(context, null);
    }

    public BannerViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.PublicBannerViewPager);
        isLoop = ta.getBoolean(R.styleable.PublicBannerViewPager_public_banner_isloop, false);
        loopTime = ta.getInteger(R.styleable.PublicBannerViewPager_public_banner_looptime, 4000);
        switchTime = ta.getInteger(R.styleable.PublicBannerViewPager_public_banner_switchtime, 200);
        loopMaxCount = ta.getInteger(R.styleable.PublicBannerViewPager_public_banner_loop_max_count, loopMaxCount);
        ta.recycle();
        inflater = LayoutInflater.from(context);
        setOnTouchListener(this);

        ViewPagerHelperUtils.initSwitchTime(context, this, switchTime);

        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(dm);
        screentRect = new Rect(0, 0, dm.widthPixels, dm.heightPixels);
    }

    /**
     * 请在设置adapter之前调用，即在bind方法之前调用
     *
     * @param maxOffset ？
     * @param scaleRate 缩放比例
     */
    public void setmTransformer(float maxOffset, float scaleRate) {
        int cardMaxOffset = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, maxOffset, getResources().getDisplayMetrics());

        if (mTransformer == null) {
            mTransformer = new CardTransformer(cardMaxOffset, scaleRate);
            setPageTransformer(false, mTransformer);
        }
    }

    /**
     * 设置监听
     */
    public void setPageListener(PageBean bean, int layoutId, PageHelpterListener listener) {
        if (bean.datas.size() >= loopMaxCount) {
            isSlide = true;
        } else {
            isSlide = false;
        }
        CusViewPagerAdapter adapter = new CusViewPagerAdapter(bean.datas, layoutId, listener);
        adapter.notifyDataSetChanged();
        setAdapter(adapter);
        setOffscreenPageLimit(3);

        if (isSlide) {
            int index = ViewPagerHelperUtils.LOOP_COUNT / 2 % bean.datas.size();
            setCurrentItem(ViewPagerHelperUtils.LOOP_COUNT / 2 - index + bean.datas.size());
        } else {
            setCurrentItem(0);
        }


    }

    /**
     * 当有触摸时停止
     *
     * @param v
     * @param event
     * @return
     */
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        handler.removeMessages(LOOP_MSG);
        switch (event.getAction()) {

            case MotionEvent.ACTION_UP:
                if (isLoop) {
                    handler.sendEmptyMessageDelayed(LOOP_MSG, loopTime);
                }
                break;
            default:
        }
        return false;
    }

    /**
     * 如果退出了，自动停止，进来则自动开始
     */
    @Override
    protected void onWindowVisibilityChanged(int visibility) {
        super.onWindowVisibilityChanged(visibility);
        if (isLoop) {
            if (visibility == View.VISIBLE) {
                startAnim();
            } else {
                stopAnim();
            }
        } else {

        }
    }

    /**
     * 手动开始
     */
    public void startAnim() {
        if (isLoop) {
            handler.removeMessages(LOOP_MSG);
            handler.sendEmptyMessageDelayed(LOOP_MSG, loopTime);
        }
    }

    /**
     * 手动停止
     */
    public void stopAnim() {
        if (isLoop) {
            handler.removeMessages(LOOP_MSG);
        }
    }

    @Override
    protected void detachAllViewsFromParent() {
        super.detachAllViewsFromParent();
        handler.removeCallbacksAndMessages(null);
    }

    /**
     * 配置adapter
     */
    class CusViewPagerAdapter<T> extends PagerAdapter {
        private PageHelpterListener listener;
        private List<T> list;
        private int layoutId;

        public CusViewPagerAdapter(List<T> list, @Nullable int layoutId, PageHelpterListener listener) {
            this.listener = listener;
            this.list = list;
            this.layoutId = layoutId;
        }

        @Override
        public int getCount() {
            if (isSlide) {
                return this.list.size() + ViewPagerHelperUtils.LOOP_COUNT;
            } else {
                return this.list.size();
            }
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            View view = inflater.inflate(layoutId, null);
            if (isSlide) {
                this.listener.getItemView(view, this.list.get(position % this.list.size()));
            } else {
                this.listener.getItemView(view, this.list.get(position));
            }
            container.addView(view, 0);
            return view;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((View) object);
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }
    }


}
