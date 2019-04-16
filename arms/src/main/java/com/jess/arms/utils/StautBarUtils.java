package com.jess.arms.utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.ColorRes;
import android.support.v4.view.ViewCompat;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.jess.arms.R;
import com.jess.arms.utils.ArmsUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;



/**
 * @author DrChen
 * @Date 2019/3/8 0008.
 * qq:1414355045
 *
 * View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN  是为了让视图能延伸到状态栏区域，使状态栏悬浮在视图布局之上。
*  View.SYSTEM_UI_FLAG_LAYOUT_STABLE 保持整个View稳定, 常和控制System UI悬浮, 隐藏的Flags共用, 使View不会因为System UI的变化而重新layout
 */
public class StautBarUtils {
    /*
     *  设置全屏
     *  没有用，请用下面的
     *
     */
    public static void fullScreen(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                //5.x开始需要把颜色设置透明，否则导航栏会呈现系统默认的浅灰色
                Window window = activity.getWindow();
                View decorView = window.getDecorView();
                //两个 flag 要结合使用，表示让应用的主体内容占用系统状态栏的空间
                int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
                decorView.setSystemUiVisibility(option);
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(Color.TRANSPARENT);
            } else {
                Window window = activity.getWindow();
                WindowManager.LayoutParams attributes = window.getAttributes();
                int flagTranslucentStatus = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
                int flagTranslucentNavigation = WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION;
                attributes.flags |= flagTranslucentStatus;
                window.setAttributes(attributes);
            }
        }
    }
    /*
     * @param view 需要设置的view，我这里用到的是布局文件中的include_title
     */
    public static void setBarPadding(View view) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            view.setPadding(view.getPaddingLeft(), ArmsUtils.dip2px(view.getContext(),8),view.getPaddingRight(), view.getPaddingBottom());
        } else {
            view.setPadding(view.getPaddingLeft(), getStatuBarHeight(), view.getPaddingRight(), view.getPaddingBottom());
        }
    }


    private static int getStatuBarHeight() {
        Resources res = Resources.getSystem();
        int resId = res.getIdentifier("status_bar_height", "dimen", "android");
        if (resId > 0) {
            return res.getDimensionPixelSize(resId);
        }
        return 0;
    }


    /**
     *
     * @param activity activity
     * @param isLightStatusBar 是否白色模式（字体是黑的）
     * @param isImmerseBar  是沉浸式状态栏
     */
    public static void setStatusMode(Activity activity, boolean isLightStatusBar ,boolean isImmerseBar) {
        if (activity == null) return;
        Window window = activity.getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                //5.x开始需要把颜色设置透明，否则导航栏会呈现系统默认的浅灰色

                View decorView = window.getDecorView();
                //两个 flag 要结合使用，表示让应用的主体内容占用系统状态栏的空间

                int option;

               if(isImmerseBar){//沉浸
                   option   = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                           | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
                   decorView.setSystemUiVisibility(option);
                   window.setStatusBarColor(Color.TRANSPARENT);
               }

//                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//
            } else {

                if(isImmerseBar){
                WindowManager.LayoutParams attributes = window.getAttributes();
                int flagTranslucentStatus = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
                int flagTranslucentNavigation = WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION;
                attributes.flags |= flagTranslucentStatus;
                window.setAttributes(attributes);
                }else {
                    isLightStatusBar = false;//4.4 的透明状态栏我暂时放弃了
                }
            }
        }

        if (isXiaomi()) {
            Log.e("导航栏适配","小米");

            setXiaomiStatusBar(window, isLightStatusBar ,isImmerseBar);


        }

//        else if (isMeizu()) {
//            Log.e("导航栏适配","魅族");
//            setMeizuStatusBar(window, isLightStatusBar);
//        }else if (Build.MANUFACTURER.equalsIgnoreCase("OPPO")) {
//            Log.e("导航栏适配","oppo");
//            //OPPO
//            setOPPOStatusTextColor(  window,isLightStatusBar);
//        }

        else {
            Log.e("导航栏适配","其他");
            setOtherStatusTextColor(window,isLightStatusBar);
        }
    }

    // 是否是小米手机
    private static boolean isXiaomi() {
        return "Xiaomi".equals(Build.MANUFACTURER);
    }

    // 设置小米状态栏
    private static void setXiaomiStatusBar(Window window, boolean isLightStatusBar , boolean lightStatusBar) {
        Class<? extends Window> clazz = window.getClass();
        try {
            Class<?> layoutParams = Class.forName("android.view.MiuiWindowManager$LayoutParams");
            Field field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE");
            int darkModeFlag = field.getInt(layoutParams);
            Method extraFlagField = clazz.getMethod("setExtraFlags", int.class, int.class);
            extraFlagField.invoke(window, isLightStatusBar ? darkModeFlag : 0, darkModeFlag);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 是否是魅族手机
    private static boolean isMeizu() {
        try {
            Method method = Build.class.getMethod("hasSmartBar");
            return method != null;
        } catch (NoSuchMethodException e) {
        }
        return false;
    }

    // 设置魅族状态栏
    private static void setMeizuStatusBar(Window window, boolean isLightStatusBar) {
        WindowManager.LayoutParams params = window.getAttributes();
        try {
            Field darkFlag = WindowManager.LayoutParams.class.getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON");
            Field meizuFlags = WindowManager.LayoutParams.class.getDeclaredField("meizuFlags");
            darkFlag.setAccessible(true);
            meizuFlags.setAccessible(true);
            int bit = darkFlag.getInt(null);
            int value = meizuFlags.getInt(params);
            if (isLightStatusBar) {
                value |= bit;
            } else {
                value &= ~bit;
            }
            meizuFlags.setInt(params, value);
            window.setAttributes(params);
            darkFlag.setAccessible(false);
            meizuFlags.setAccessible(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 设置OPPO手机状态栏字体为黑色(colorOS3.0,6.0以下部分手机)
     *
     * @param lightStatusBar
     * @param activity
     */
    private static final int SYSTEM_UI_FLAG_OP_STATUS_BAR_TINT = 0x00000010;

    private static void setOPPOStatusTextColor( Window window,boolean lightStatusBar) {

        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        int vis = window.getDecorView().getSystemUiVisibility();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (lightStatusBar) {
                vis |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            } else {
                vis &= ~View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            }
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (lightStatusBar) {
                vis |= SYSTEM_UI_FLAG_OP_STATUS_BAR_TINT;
            } else {
                vis &= ~SYSTEM_UI_FLAG_OP_STATUS_BAR_TINT;
            }
        }
        window.getDecorView().setSystemUiVisibility(vis);
    }

    /**
     * 获取主题颜色
     * @return
     */
    public static int  getColorPrimary(Window window){
        TypedValue typedValue = new  TypedValue();
        window.getContext() .getTheme().resolveAttribute(R.attr.colorPrimary, typedValue, true);
        return typedValue.data;
    }

    /**
     * 获取主题颜色
     * @return
     */
    public static int getDarkColorPrimary(Window window){
        TypedValue typedValue = new TypedValue();
        window.getContext().getTheme().resolveAttribute(R.attr.colorPrimaryDark, typedValue, true);
        return typedValue.data;
    }

    /**
     * 其他手机更改状态栏字体颜色
     * @param window
     * @param useDart
     */
    private static void setOtherStatusTextColor(Window window, boolean useDart) {
        if (useDart) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                window.getDecorView().setSystemUiVisibility
                        (  View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            }else if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP){//五点零不能改文字颜色，只能将状态栏还原
                window.setStatusBarColor(getDarkColorPrimary(window));
            }
        } else {
//            window.getDecorView().setSystemUiVisibility(
//                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        }

//            window.getDecorView().findViewById(android.R.id.content).setPadding
//                    (0, 0, 0, 0);


    }

    //判断是否存在NavigationBar
    public static boolean checkDeviceHasNavigationBar(Context context) {
        boolean hasNavigationBar = false;
        Resources rs = context.getResources();
        int id = rs.getIdentifier("config_showNavigationBar", "bool", "android");
        if (id > 0) {
            hasNavigationBar = rs.getBoolean(id);
        }
        try {
            Class systemPropertiesClass = Class.forName("android.os.SystemProperties");
            Method m = systemPropertiesClass.getMethod("get", String.class);
            String navBarOverride = (String) m.invoke(systemPropertiesClass, "qemu.hw.mainkeys");
            if ("1".equals(navBarOverride)) {
                hasNavigationBar = false;
            } else if ("0".equals(navBarOverride)) {
                hasNavigationBar = true;
            }
        } catch (Exception e) {

        }
        return hasNavigationBar;

    }
    /**
     * 获取底部导航栏高度
     * @return
     */
    public static int getNavigationBarHeight(Context context) {
        Resources resources = context.getResources();
        int resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android");
        //获取NavigationBar的高度
     int   navigationHeight = resources.getDimensionPixelSize(resourceId);
        return navigationHeight;
    }


    }
