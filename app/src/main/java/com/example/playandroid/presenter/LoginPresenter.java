package com.example.playandroid.presenter;

import android.util.Log;

import com.example.playandroid.base.BasePresenter;
import com.example.playandroid.interf.contract.LoginContract;
import com.example.playandroid.model.LoginModel;
import com.example.playandroid.view.activity.LoginActivity;

import java.util.List;

public class LoginPresenter extends BasePresenter<LoginActivity, LoginModel> implements LoginContract.VP {

    private static final String TAG = "LoginPresenter";

    @Override
    public LoginModel getModelInstance() {
        return new LoginModel(this);
    }

    @Override
    public void requestLogin(String account, String password) {
//        getModelInstance().requestLoginData();
        try {
            mModel.requestLoginData(account, password);
        } catch (Exception e) {
            Log.e(TAG, "requestLogin: 登录数据请求失败");
            e.printStackTrace();
        }
    }

    @Override
    public void responseLoginResult(String loginResult) {
        mView.responseLoginResult(loginResult);
    }

    @Override
    public void responseCookie(List<String> setCookies) {
        mView.responseCookie(setCookies);
    }


}
