package me.jessyan.mvparms.demo.mvp.model.login;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import com.jess.arms.di.scope.ActivityScope;

import javax.inject.Inject;

import io.reactivex.Observable;
import me.jessyan.mvparms.demo.mvp.contract.login.LoginContract;
import me.jessyan.mvparms.demo.mvp.model.api.service.CommonService;
import me.jessyan.mvparms.demo.mvp.model.entity.User;


@ActivityScope
public class LoginModel extends BaseModel implements LoginContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public LoginModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<User> login(String mobileStr, String passWordStr) {
        //调用登录接口，正确的密码：abc  手机号只要等于11位判断账号为正确
        String name;
        if(passWordStr.equals("abc")){//正确密码，
            name = "drchengit";
        }else {
            name = "drchengi";
        }

        //由于不知道上哪里去找一个稳定且长期可用的登录接口，
        // 所以用的接口是github 上的查询接口：https://api.github.com/users/drchengit
        // 这里的处理是正确的密码，请求存在的用户名：drchengit  错误的密码请求不存在的用户名： drchengi
        // 将就一下
        return mRepositoryManager.obtainRetrofitService(CommonService.class).getUser(name);
    }
}