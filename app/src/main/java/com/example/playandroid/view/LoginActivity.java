package com.example.playandroid.view;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.playandroid.R;
import com.example.playandroid.base.BaseActivity;
import com.example.playandroid.contract.LoginContract;
import com.example.playandroid.presenter.LoginPresenter;

public class LoginActivity extends BaseActivity<LoginPresenter> implements LoginContract.VP {

    private EditText accountEdit;

    private EditText passwordEdit;

    private Button loginButton;



    @Override
    public void initView() {
        accountEdit = findViewById(R.id.account_edit);
        passwordEdit = findViewById(R.id.password_edit);
        loginButton = findViewById(R.id.login_button);
    }

    @Override
    public void initData() {
    }

    @Override
    public void initListener() {
        loginButton.setOnClickListener(this);
    }

    @Override
    public int getContentViewId() {
        return R.layout.login_layout;
    }

    @Override
    public LoginPresenter getPresenterInstance() {
        return new LoginPresenter();
    }

    @Override
    public void destroy() {
        LoginActivity.this.finish();
    }

    @Override
    public <ERROR> void responseError(ERROR error, Throwable throwable) {
        Toast.makeText(this, "登录异常", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View view) {
        String account = accountEdit.getText().toString();
        String password = passwordEdit.getText().toString();

        requestLogin(account, password);
    }


    @Override
    public void requestLogin(String account, String password) {
        mPresenter.requestLogin(account, password);
    }

    @Override
    public void responseLoginResult(boolean loginResult) {
        Toast.makeText(this, loginResult ? "登录成功" : "登录失败", Toast.LENGTH_SHORT).show();
    }
}
