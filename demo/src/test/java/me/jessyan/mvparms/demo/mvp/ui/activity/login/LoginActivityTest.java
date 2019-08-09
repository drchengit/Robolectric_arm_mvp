package me.jessyan.mvparms.demo.mvp.ui.activity.login;

import android.content.Intent;
import android.widget.EditText;
import android.widget.TextView;

import com.jess.arms.utils.ArmsUtils;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.Shadows;
import org.robolectric.annotation.Config;
import org.robolectric.shadow.api.Shadow;
import org.robolectric.shadows.ShadowActivity;
import org.robolectric.shadows.ShadowLog;
import org.robolectric.shadows.ShadowToast;

import io.reactivex.Scheduler;
import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.functions.Function;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.mvparms.demo.BuildConfig;
import me.jessyan.mvparms.demo.R;
import me.jessyan.mvparms.demo.mvp.model.login.LoginModel;
import me.jessyan.mvparms.demo.mvp.ui.activity.MainActivity;
import me.jessyan.mvparms.demo.mvp.ui.widget.PassWordEditText;
import me.jessyan.mvparms.demo.mvp.ui.widget.PhoneEditText;

import static org.junit.Assert.*;

/**
 * @author DrChen
 * @Date 2019/8/8 0008.
 * qq:1414355045
 */
@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 27)
public class LoginActivityTest {
    TextView loginTv;

    EditText phoneEt;
    EditText passWrodEt;
    private LoginActivity loginActivity;



    @Before
    public void setUp() {
        ShadowLog.stream = System.out;
        loginActivity = Robolectric.buildActivity(LoginActivity.class)
                .create()
                .resume()
                .get();
        loginTv = loginActivity.findViewById(R.id.tv_login);
        phoneEt = loginActivity.findViewById(R.id.et_mobile);
        passWrodEt = loginActivity.findViewById(R.id.et_pass);

    }


    @Test
    public  void login(){
        initRxJava();
        //直接点击登录
        loginTv.performClick();
        Assert.assertEquals("手机号码不正确", ShadowToast.getTextOfLatestToast());
        phoneEt.setText("13547250999");
        //没有输入密码
        loginTv.performClick();
        Assert.assertEquals("密码太短", ShadowToast.getTextOfLatestToast());
        //错误密码
        passWrodEt.setText("aaaa");
        loginTv.performClick();
        //这里是验证网络框架提示
        Assert.assertEquals("密码错误", ShadowToast.getTextOfLatestToast());
        //正确密码登录
        passWrodEt.setText("abc");
        loginTv.performClick();
        Assert.assertEquals("登录成功",ShadowToast.getTextOfLatestToast());

        //验证跳转
        ShadowActivity shadowActivity = Shadows.shadowOf(loginActivity);
        Intent intent = shadowActivity.getNextStartedActivity();
        Assert.assertEquals(intent.getComponent().getClassName(), MainActivity.class.getName());


    }

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

}