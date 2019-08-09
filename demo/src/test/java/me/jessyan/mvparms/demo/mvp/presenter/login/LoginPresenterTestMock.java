package me.jessyan.mvparms.demo.mvp.presenter.login;


import com.jess.arms.utils.ArmsUtils;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowLog;
import org.robolectric.shadows.ShadowToast;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.functions.Function;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.mvparms.demo.BuildConfig;
import me.jessyan.mvparms.demo.app.ResponseErrorListenerImpl;
import me.jessyan.mvparms.demo.base.MyPresenterRunner;
import me.jessyan.mvparms.demo.mvp.contract.login.LoginContract;
import me.jessyan.mvparms.demo.mvp.model.api.service.CommonService;
import me.jessyan.mvparms.demo.mvp.model.entity.User;
import me.jessyan.mvparms.demo.mvp.model.login.LoginModel;
import me.jessyan.mvparms.demo.mvp.ui.activity.login.LoginActivity;
import me.jessyan.mvparms.demo.net.TestRetrofit;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;

/**
 * @author DrChen
 * @Date 2019/8/9 0009.
 * qq:1414355045
 * 这个类是本地json请求
 */
@RunWith(MyPresenterRunner.class)
@Config(constants = BuildConfig.class, sdk = 27)
public class LoginPresenterTestMock {
    /**
     * 引入mockito 模拟假数据
     */
    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    private LoginPresenter mPresenter;
    private LoginModel model;
    private LoginContract.View view;
    String dirPath;

    @Before
    public void setUp() throws Exception {
        //打印log
        ShadowLog.stream = System.out;
        //线程同步走起
        initRxJava();
        //模拟假对象
        model = Mockito.mock(LoginModel.class);
        view = Mockito.mock(LoginActivity.class);//这里生命周期不会调用，只是一个简单java 对象

        mPresenter = new LoginPresenter(model, view);
        mPresenter.mErrorHandler = RxErrorHandler
                .builder()
                .with(RuntimeEnvironment.application)
                .responseErrorListener(new ResponseErrorListenerImpl())
                .build();
        dirPath = getClass().getResource("/json/").toURI().getPath();//得到json文件夹位置
    }

    /**
     * 这是线程同步的方法
     */
    private void initRxJava() {
        RxJavaPlugins.reset();
        RxJavaPlugins.setIoSchedulerHandler(new Function<Scheduler, Scheduler>() {
            @Override
            public Scheduler apply(Scheduler scheduler) throws Exception {
                return Schedulers.trampoline();
            }
        });

        //这个哟
        RxJavaPlugins.setSingleSchedulerHandler(new Function<Scheduler, Scheduler>() {
            @Override
            public Scheduler apply(Scheduler scheduler) throws Exception {
                return Schedulers.trampoline();
            }
        });

        RxAndroidPlugins.reset();
        RxAndroidPlugins.setMainThreadSchedulerHandler(new Function<Scheduler, Scheduler>() {
            @Override
            public Scheduler apply(Scheduler scheduler) throws Exception {
                return Schedulers.trampoline();
            }
        });


    }

    @Test
    public void loginFailed() {
        //模拟数据
        Mockito.when(view.getMobileStr()).thenReturn("13547250999");
        Mockito.when(view.getPassWordStr()).thenReturn("abc");


        //实现loginModel login 方法
        //由于不知道上哪里去找一个稳定且长期可用的登录接口，
        // 所以用的接口是github 上的查询接口：https://api.github.com/users/drchengit
        // 这里的处理是正确的密码，请求存在的用户名：drchengit  错误的密码请求不存在的用户名： drchengi
        Observable<User> observable =  new TestRetrofit<CommonService>().createMockService(CommonService.class,dirPath)
                .getUser("drchengit");

        //模拟无论怎么调用，login都是返回上面的Observable对象
        Mockito.when(model.login(Mockito.anyString(), Mockito.anyString()))
                .thenReturn(observable);
        //登录
        mPresenter.login();
        //验证
        Assert.assertEquals("密码错误", ShadowToast.getTextOfLatestToast());

    }
}