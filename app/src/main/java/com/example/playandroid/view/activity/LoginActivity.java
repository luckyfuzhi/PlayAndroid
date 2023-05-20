package com.example.playandroid.view.activity;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.playandroid.R;
import com.example.playandroid.base.BaseActivity;
import com.example.playandroid.interf.contract.LoginContract;
import com.example.playandroid.presenter.LoginPresenter;

public class LoginActivity extends BaseActivity<LoginPresenter> implements LoginContract.VP {

    private EditText accountEdit;

    private EditText passwordEdit;

    private Button loginButton;
    
    private Button registerButton;
    
    private Button backButton;



    @Override
    public void initView() {
        accountEdit = findViewById(R.id.account_edit);
        passwordEdit = findViewById(R.id.password_edit);
        loginButton = findViewById(R.id.login_button);
        registerButton = findViewById(R.id.register_button);
        backButton = findViewById(R.id.login_back);
    }

    @Override
    public void initData() {
    }

    @Override
    public void initListener() {
        loginButton.setOnClickListener(this);
        backButton.setOnClickListener(this);
        registerButton.setOnClickListener(this);
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
        Log.e("LoginActivity", "responseError: 登录异常/" + error, null);
        throwable.printStackTrace();
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.login_button) {
            String account = accountEdit.getText().toString();
            String password = passwordEdit.getText().toString();
            requestLogin(account, password);
        } else if (view.getId() == R.id.login_back) {
            finish();
        } else if (view.getId() == R.id.register_button) {
            
        }
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
