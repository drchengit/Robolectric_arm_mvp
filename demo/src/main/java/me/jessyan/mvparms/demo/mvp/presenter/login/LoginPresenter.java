package me.jessyan.mvparms.demo.mvp.presenter.login;

import android.app.Application;
import android.support.annotation.NonNull;

import com.jess.arms.integration.AppManager;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.http.imageloader.ImageLoader;

import me.jessyan.mvparms.demo.app.MyErrorHandleSubscriber;
import me.jessyan.mvparms.demo.app.utils.RxUtils;
import me.jessyan.mvparms.demo.mvp.model.entity.User;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;

import javax.inject.Inject;

import me.jessyan.mvparms.demo.mvp.contract.login.LoginContract;


@ActivityScope
public class LoginPresenter extends BasePresenter<LoginContract.Model, LoginContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    public void login() {
        if(mRootView.getMobileStr().length() != 11){
            mRootView.showMessage("手机号码不正确");
            return;
        }
        if(mRootView.getPassWordStr().length() < 1){
            mRootView.showMessage("密码太短");
            return;
        }
        //调用登录接口，正确的密码：abc  手机号只要等于11位判断账号为正确
        mModel.login(mRootView.getMobileStr(),mRootView.getPassWordStr())
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new MyErrorHandleSubscriber<User>(mErrorHandler) {
                    //这个类是我自定义的一个类，统一拦截所有error 并回调给： ResponseErrorListenerImpl
                    @Override
                    public void onNext(User user) {
                            mRootView.loginSuccess();
                    }
                });

    }
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public LoginPresenter(LoginContract.Model model, LoginContract.View rootView) {
        super(model, rootView);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
        this.mAppManager = null;
        this.mImageLoader = null;
        this.mApplication = null;
    }

//    // 可以不统一处理，直接重写覆盖：
//    @Override
//    public void onError(@NonNull Throwable t) {
//        super.onError(t);
//    }
//
//    @Override
//    public void onComplete() {
//        super.onComplete();
//    }
}
