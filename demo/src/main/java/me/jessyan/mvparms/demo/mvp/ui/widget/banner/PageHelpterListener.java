package me.jessyan.mvparms.demo.mvp.ui.widget.banner;

import android.view.View;

/**
 * @author DrChen
 * @Date 2018/9/26 0026.
 * qq:1414355045
 */
public interface PageHelpterListener<T> {

    void getItemView(View view, T data);
}
