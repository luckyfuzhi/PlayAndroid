package com.example.playandroid.model;

import android.util.Log;

import com.example.playandroid.base.BaseModel;
import com.example.playandroid.interf.contract.LoginContract;
import com.example.playandroid.interf.datacallback.DataCallBack;
import com.example.playandroid.presenter.LoginPresenter;
import com.example.playandroid.util.WebUtil;

import java.util.HashMap;
import java.util.Map;

public class LoginModel extends BaseModel<LoginPresenter> implements LoginContract.M {

    //登录网址
    private final static String LOGIN_URL = "https://www.wanandroid.com/user/login";

    public LoginModel(LoginPresenter mPresenter) {
        super(mPresenter);
    }

    @Override
    public void requestLoginData(String account, String password) throws Exception {
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("username", account);
        paramMap.put("password", password);

        WebUtil.postDataToWeb(LOGIN_URL, paramMap, new DataCallBack() {
            @Override
            public void onSuccess(String data) {
                if(data.contains("账号密码不匹配")){
                    mPresenter.responseLoginResult(false);
                } else {
                    mPresenter.responseLoginResult(true);
                }
            }

            @Override
            public void onFailure(Exception e) {
                e.printStackTrace();
                Log.e("requestLoginData", "登录异常/" + e);
            }
        });
    }
}
