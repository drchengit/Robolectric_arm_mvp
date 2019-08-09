package me.jessyan.mvparms.demo.mvp.ui.activity.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.utils.DoubleClickUtils;
import com.jess.arms.widget.LoadingDialog;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.jessyan.mvparms.demo.R;
import me.jessyan.mvparms.demo.di.component.login.DaggerLoginComponent;
import me.jessyan.mvparms.demo.di.module.login.LoginModule;
import me.jessyan.mvparms.demo.mvp.contract.login.LoginContract;
import me.jessyan.mvparms.demo.mvp.presenter.login.LoginPresenter;
import me.jessyan.mvparms.demo.mvp.ui.activity.MainActivity;

import static com.jess.arms.utils.Preconditions.checkNotNull;


public class LoginActivity extends BaseActivity<LoginPresenter> implements LoginContract.View {
    /**
     * 网络加载diaLog
     */
    protected LoadingDialog mLoadingDialog;
    @BindView(R.id.et_mobile)
    EditText etMobile;
    @BindView(R.id.et_pass)
    EditText etPass;
    @BindView(R.id.tv_login)
    TextView tvLogin;
    @BindView(R.id.linearLayout2)
    LinearLayout linearLayout2;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerLoginComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .loginModule(new LoginModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_login; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

    }

    @Override
    public void showLoading() {
        if (mLoadingDialog == null) {
            mLoadingDialog = new LoadingDialog.Builder(this)
                    .setType(LoadingDialog.LOADING)
                    .create();

        }
        if (!mLoadingDialog.isShowing()) {
            mLoadingDialog.show();

        }
    }

    @Override
    public void hideLoading() {
        if (mLoadingDialog != null && mLoadingDialog.isShowing()) {
            mLoadingDialog.dismiss();

        }
    }

    @Override
    public void showMessage(@NonNull String message) {
        checkNotNull(message);
        Toast.makeText(getApplicationContext(),message,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void launchActivity(@NonNull Intent intent) {
        checkNotNull(intent);
        ArmsUtils.startActivity(intent);
    }

    @Override
    public void killMyself() {
        finish();
    }

    @Override
    protected void onDestroy() {
        if (mLoadingDialog != null && mLoadingDialog.isShowing()) {
            mLoadingDialog.dismiss();
            mLoadingDialog = null;
        }
        super.onDestroy();
    }

    @OnClick(R.id.tv_login)
    public void onViewClicked() {
        mPresenter.login();
    }

    @Override
    public String getMobileStr() {
        return ArmsUtils.isEmpty(etMobile.getText())? "":etMobile.getText().toString();
    }

    @Override
    public String getPassWordStr() {
        return ArmsUtils.isEmpty(etPass.getText())? "":etPass.getText().toString();
    }

    @Override
    public void loginSuccess() {
        showMessage("登录成功");
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("update",true);
        startActivity(intent);
    }
}
