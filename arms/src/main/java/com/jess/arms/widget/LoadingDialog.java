package com.jess.arms.widget;

import android.content.Context;
import android.support.v7.app.AppCompatDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.jess.arms.R;
import com.jess.arms.utils.DeviceUtils;


/**
 * Created by tjy on 2017/6/19.
 */
public class LoadingDialog extends AppCompatDialog {

        public static final int LOADING= 1;
        public static final int COMMITING = 2;

    public LoadingDialog(Context context) {
        super(context);
    }

    public LoadingDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    public static class Builder{
        private Context context;
        private int type = -1;
        private String message;
        private boolean isCancelable =false;
        private boolean isCancelOutside ;


        public Builder(Context context) {
            this.context = context;
            }


        public Builder setType(int type) {
            this.type = type;
            return this;
        }

        public LoadingDialog create(){
            if(type==LOADING){
                message = "加载中...";
            }else if(type==COMMITING){
                message = "提交中...";
            }else {
                throw new RuntimeException("加载dialog设置参数type");
            }
            LayoutInflater inflater = LayoutInflater.from(context);
            View view=inflater.inflate(R.layout.dialog_loading,null);
            view.bringToFront();

            LoadingDialog loadingDailog=new LoadingDialog(context, R.style.LoadingDialogStyle);
            TextView msgText= (TextView) view.findViewById( R.id.tipTextView);
            msgText.setText(message);
            loadingDailog.setContentView(view);
            loadingDailog.setCancelable(isCancelable);
            loadingDailog.setCanceledOnTouchOutside(isCancelOutside);
            WindowManager.LayoutParams lp=loadingDailog.getWindow().getAttributes();
            lp.height = (int) DeviceUtils.getScreenHeight(context);
                    lp.width = (int) DeviceUtils.getScreenWidth(context);
//
//// 模糊度getWindow().setFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND,WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
//            loadingDailog.getWindow().setAttributes(lp);
//
//            lp.alpha=0.5f; //透明度，黑暗度为lp.dimAmount=1.0f;
            return  loadingDailog;

        }


    }
}
