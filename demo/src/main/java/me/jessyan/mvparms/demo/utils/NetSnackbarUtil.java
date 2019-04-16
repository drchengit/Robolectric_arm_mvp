package me.jessyan.mvparms.demo.utils;

import android.app.Activity;
import android.content.Intent;
import android.provider.Settings;
import android.support.design.widget.CoordinatorLayout;
import android.view.View;
import android.widget.FrameLayout;

import com.github.megatronking.svg.support.SVGDrawable;
import com.jess.arms.utils.DeviceUtils;
import com.jess.arms.utils.PerfectClickListener;

import java.lang.ref.SoftReference;

import me.jessyan.mvparms.demo.drawables.ic_arrow_right;
import me.jessyan.mvparms.demo.drawables.ic_yellow_exclamation_point;
import me.jessyan.mvparms.demo.mvp.ui.widget.tsnackbar.TSnackbar;

/**
 * @author DrChen
 * @Date 2019/3/18 0018.
 * qq:1414355045
 */
public class NetSnackbarUtil {
    static NetSnackbarUtil instance= new NetSnackbarUtil();
    SoftReference<TSnackbar> reference;
    private NetSnackbarUtil(){

    }
    public static NetSnackbarUtil getInstance(){
        return instance;
    }
    public void checkAndShow(View view, Activity activity){
        if(view==null||activity==null){
            return;
        }
        if(!(view instanceof FrameLayout) && !(view instanceof CoordinatorLayout)){
            throw new IllegalArgumentException("snackbar传入布局有问题");
        }
        if(!DeviceUtils.hasInternet(view.getContext())){
            TSnackbar snackbar= TSnackbar.make(view,"当前无网络可用，请检查网络设置",TSnackbar.LENGTH_INDEFINITE)
                    .setIconLeft(new SVGDrawable(new ic_yellow_exclamation_point(view.getContext())),16)
                    .setIconRight(new SVGDrawable(new ic_arrow_right(view.getContext())),16)
                    .setAction(new PerfectClickListener() {
                        @Override
                        protected void onNoDoubleClick(View v) {
                            Intent intent = new Intent(Settings.ACTION_SETTINGS);
                            activity.startActivity(intent);
                        }
                    });
            snackbar.show();
            reference = new SoftReference<TSnackbar>(snackbar);
        }else {
          if(reference!=null&&reference.get()!=null){
              reference.get().dismiss();
          }
        }
        }
}
