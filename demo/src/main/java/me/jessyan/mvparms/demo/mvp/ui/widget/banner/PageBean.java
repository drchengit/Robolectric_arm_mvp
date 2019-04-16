package me.jessyan.mvparms.demo.mvp.ui.widget.banner;

import android.view.View;

import java.util.List;

/**
 * @author DrChen
 * @Date 2018/9/26 0026.
 * qq:1414355045
 */
public class PageBean {
    public View bottomLayout;
    private View openview;
    public List<Object> datas;

    public PageBean(Builder builder) {
         this.bottomLayout = builder.bottomLayout;
         this.openview = builder.openview;
         this.datas = builder.datas;
    }

    public static class  Builder<T>{
        View bottomLayout;
        View openview;
        List<T> datas;
        public Builder setIndicator (View bottomLayout){
            this.bottomLayout = bottomLayout;
            return this;
        }
        public Builder setOpenView (View openView){
            this.openview = openView;
            return this;
        }
        public Builder setDataObjects(List<T> datas){
            this.datas = datas;
            return this;
        }
        public PageBean builder(){
            return new PageBean(this);
        }

    }
}
