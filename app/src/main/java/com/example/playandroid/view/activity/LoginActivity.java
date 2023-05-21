package com.example.playandroid.view.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.playandroid.R;
import com.example.playandroid.base.BaseActivity;
import com.example.playandroid.interf.contract.LoginContract;
import com.example.playandroid.presenter.LoginPresenter;

import java.util.Collection;

public class LoginActivity extends BaseActivity<LoginPresenter> implements LoginContract.VP {

    private EditText accountEdit;

    private EditText passwordEdit;

    private Button loginButton;
    
    private Button registerButton;
    
    private Button backButton;

    private Activity mActivity = this;


    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        String username = accountEdit.getText().toString();
        String password = passwordEdit.getText().toString();
        outState.putString("username", username);
        outState.putString("password", password);
    }

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
        if (mSavedInstanceState != null){
            String username = mSavedInstanceState.getString("username");
            String password = mSavedInstanceState.getString("password");
            accountEdit.setText(username);
            passwordEdit.setText(password);
        }

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
            if(accountEdit.length() != 0 && passwordEdit.length() != 0) {
                requestLogin(account, password);
            } else {
                Toast.makeText(this, "账号或者密码输入不能为空！", Toast.LENGTH_SHORT).show();
            }
        } else if (view.getId() == R.id.login_back) {
            finish();
        } else if (view.getId() == R.id.register_button) {
            Intent intent = new Intent(this, RegisterActivity.class);
            startActivity(intent);
        }
    }

    private Handler handler = new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(@NonNull Message msg) {
            if(msg.what == 1){
                Toast.makeText(mActivity, "登录成功", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(mActivity, BottomActivity.class);
                intent.putExtra("isSuccessLogin", true);
                intent.putExtra("username", accountEdit.getText().toString());
                startActivity(intent);
                ActivityCollector.removeActivity(mActivity);

            } else if (msg.what == 0) {
                Toast.makeText(mActivity, "账号或者密码不正确", Toast.LENGTH_SHORT).show();
            }
        }
    };


    @Override
    public void requestLogin(String account, String password) {
        mPresenter.requestLogin(account, password);
    }

    @Override
    public void responseLoginResult(boolean loginResult) {
        Message message = new Message();
        if(loginResult){
            message.what = 1;
        } else {
            message.what = 0;
        }
       handler.sendMessage(message);
    }
}
