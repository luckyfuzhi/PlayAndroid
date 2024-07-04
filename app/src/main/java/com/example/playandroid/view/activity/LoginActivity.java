package com.example.playandroid.view.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
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

import com.example.playandroid.MyApplication;
import com.example.playandroid.R;
import com.example.playandroid.base.BaseActivity;
import com.example.playandroid.interf.contract.LoginContract;
import com.example.playandroid.presenter.LoginPresenter;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class LoginActivity extends BaseActivity<LoginPresenter> implements LoginContract.VP {

    private EditText accountEdit;

    private EditText passwordEdit;

    private Button loginButton;

    private Button registerButton;

    private Button backButton;

    private final Activity mActivity = this;

    private SharedPreferences sharedPreferences;

    private SharedPreferences.Editor editor;

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
        sharedPreferences = MyApplication.getContext().getSharedPreferences("cookies_prefs", MODE_PRIVATE);
        editor = sharedPreferences.edit();
        if (sharedPreferences.contains("cookies") && !sharedPreferences.getStringSet("cookies", new HashSet<>()).isEmpty()) {//检验是否已经存在cookie，若存在则自动登录
            Intent intent = new Intent(this, BottomActivity.class);
            intent.putExtra("isAutoLogin", true);
            intent.putExtra("userName", getUserNameFromCookies(sharedPreferences.getStringSet("cookies", new HashSet<>())));
            startActivity(intent);
        } else if (!getIntent().getBooleanExtra("isFromBottomActivity", false)) {
            //检验是否从主界面跳转过来的，不是则执行下面逻辑（在刚打开程序，如果之前未登录的话要登陆则执行这种情况）
            Intent intent = new Intent(this, BottomActivity.class);
            intent.putExtra("isAutoLogin", false);
            startActivity(intent);
        }
    }

    private String getUserNameFromCookies(Set<String> cookies) {
        if (!cookies.isEmpty()) {
            for (String cookie : cookies) {
                String[] cookieAttributes = cookie.split(";");
                for (String attribute : cookieAttributes) {
                    if (attribute.trim().startsWith("loginUserName=")) {
                        return attribute.split("=")[1].trim();
                    }
                }
            }
        }
        return null; // 如果没有找到 userName 字段，返回 null

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
        if (view.getId() == R.id.login_button) {
            String account = accountEdit.getText().toString();
            String password = passwordEdit.getText().toString();
            if (accountEdit.length() != 0 && passwordEdit.length() != 0) {
                requestLogin(account, password);
            } else {
                Toast.makeText(this, "账号或者密码输入不能为空！", Toast.LENGTH_SHORT).show();
            }
        } else if (view.getId() == R.id.login_back) {
            if (getIntent().getBooleanExtra("isLoginAgain", false)) {
                //重新登录的时候未登录成功又直接返回退出，则执行下面的代码
                Intent intent = new Intent(this, BottomActivity.class);
                intent.putExtra("exitLogin", true);
                startActivity(intent);
            }
            finish();
        } else if (view.getId() == R.id.register_button) {
            Intent intent = new Intent(this, RegisterActivity.class);
            startActivity(intent);
        }
    }


//    private final Handler handler = new Handler(Looper.getMainLooper()) {
//        @Override
//        public void handleMessage(@NonNull Message msg) {
//            if (msg.what == 1) {
//                Toast.makeText(mActivity, "登录成功", Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent(mActivity, BottomActivity.class);
//                intent.putExtra("isSuccessLogin", true);
//                intent.putExtra("username", accountEdit.getText().toString());
//                startActivity(intent);
//                ActivityCollector.removeActivity(mActivity);
//                finish();
//            } else {
//                Toast.makeText(getBaseContext(), "账号密码不匹配", Toast.LENGTH_LONG).show();
//            }
//        }
//    };


    @Override
    public void requestLogin(String account, String password) {
        mPresenter.requestLogin(account, password);
    }

    @Override
    public void responseLoginResult(String loginResult) {
        if (loginResult.isEmpty()) {
            Toast.makeText(mActivity, "登录成功", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(mActivity, BottomActivity.class);
            intent.putExtra("isSuccessLogin", true);
            intent.putExtra("username", accountEdit.getText().toString());
            startActivity(intent);
            ActivityCollector.removeActivity(mActivity);
            finish();
        } else {
            Toast.makeText(getBaseContext(), "账号密码不匹配", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void responseCookie(List<String> setCookies) {
        saveCookie(setCookies);
    }

    @Override
    public void responseLoginState(int errorCode, String errorMessage) {
        if (!(errorCode == 200 && errorMessage.equals("OK"))) {
            editor.remove("cookie");//清除cookie
            editor.commit();
            Toast.makeText(MyApplication.getContext(), "登录失效，请重新登录",
                    Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this, LoginActivity.class);
            intent.putExtra("isLoginAgain", true);
            startActivity(intent);
        }
    }


    public void saveCookie(List<String> setCookies) {//保存cookie
        String cookie = encodeCookie(setCookies);
        Log.d("cookie", cookie);
        editor.putString("cookie", cookie);
        editor.apply();
    }

    /**
     * 整合cookie为唯一字符串
     */
    private String encodeCookie(List<String> cookies) {
        StringBuilder sb = new StringBuilder();
        Set<String> cookieSet = new HashSet<>();
        if (cookies != null) {
            for (String cookie : cookies) {
                String[] arr = cookie.split(";");
                for (String s : arr) {
                    if (cookieSet.contains(s)) {
                        continue;
                    }
                    cookieSet.add(s);

                }
            }

            for (String cookie : cookieSet) {
                sb.append(cookie).append(";");
            }

            int last = sb.lastIndexOf(";");
            if (sb.length() - 1 == last) {
                sb.deleteCharAt(last);
            }

        }

        return sb.toString();
    }

}
