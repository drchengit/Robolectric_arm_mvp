package me.jessyan.mvparms.demo.net;

import com.jess.arms.http.log.DefaultFormatPrinter;
import com.jess.arms.http.log.RequestInterceptor;

import org.robolectric.RuntimeEnvironment;

import java.io.IOException;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import me.jessyan.mvparms.demo.app.GlobalHttpHandlerImpl;
import me.jessyan.mvparms.demo.mvp.model.api.Api;
import okhttp3.Dispatcher;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import okhttp3.internal.Util;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author DrChen
 * @Date 2019/8/7 0007.
 * qq:1414355045
 */
public class TestRetrofit<T> {
    /**
     *
    *   本地数据模拟
     */
    public T createMockService(Class<T> service,String json) {
        OkHttpClient okHttpClient =  new OkHttpClient.Builder()
                .connectTimeout(1, TimeUnit.SECONDS)//一秒超时
                .readTimeout(1, TimeUnit.SECONDS)//一秒超时，快速结束请求
                 .addInterceptor(new MockInterceptor(json))//最后得到假数据
                               .build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.APP_DOMAIN)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        return retrofit.create(service);
    }



}
