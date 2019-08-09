package me.jessyan.mvparms.demo.app;


import android.util.Log;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandlerFactory;

/**
 * @author DrChen
 * @Date 2018/11/16 0016.
 * qq:1414355045
 */
public abstract class MyErrorHandleSubscriber<T> implements Observer<T> {
    private ErrorHandlerFactory mHandlerFactory;

    public MyErrorHandleSubscriber(RxErrorHandler rxErrorHandler){
        this.mHandlerFactory = rxErrorHandler.getHandlerFactory();
    }


    @Override
    public void onSubscribe(@NonNull Disposable d) {
        Log.e("tag","onSubscribe");
//        EspressoIdlingResource.increment();
    }


    @Override
    public void onComplete() {
//        if (!EspressoIdlingResource.getIdlingResource().isIdleNow()) {
//            EspressoIdlingResource.decrement();
//        }
        Log.e("tag","onComplete");
    }

    public void onNoticeError(@NonNull Throwable t){
        Log.e("tag","onError"+t.getMessage());
//        if (!EspressoIdlingResource.getIdlingResource().isIdleNow()) {
//            EspressoIdlingResource.decrement();
//        }
    }


    @Override
    public void onError(@NonNull Throwable t) {
//        if (!EspressoIdlingResource.getIdlingResource().isIdleNow()) {
//            EspressoIdlingResource.decrement();
//        }

        Log.e("tag","onError"+t.getMessage());
//        t.printStackTrace();
//        //如果你某个地方不想使用全局错误处理,则重写 onError(Throwable) 并将 super.onError(e); 删掉
//        //如果你不仅想使用全局错误处理,还想加入自己的逻辑,则重写 onError(Throwable) 并在 super.onError(e); 后面加入自己的逻辑
        mHandlerFactory.handleError(t);
//        if(t instanceof TokenInvalidException){
//            //跳转到登录页面
//            ARouter.getInstance().build(RouterHub.USER_USERLOGINACTIVITY)
//                    .withTransition(com.jess.arms.R.anim.activity_in, com.jess.arms.R.anim.activity_end)
//                    .navigation();
//        }
    }



}
