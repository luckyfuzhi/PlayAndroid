package com.example.playandroid.model;

import com.example.playandroid.base.BaseModel;
import com.example.playandroid.interf.contract.LoginContract;
import com.example.playandroid.presenter.LoginPresenter;

public class LoginModel extends BaseModel<LoginPresenter> implements LoginContract.M {
    public LoginModel(LoginPresenter mPresenter) {
        super(mPresenter);
    }

    @Override
    public void requestLoginData(String account, String password) throws Exception {
        if (account.equals("admin") && password.equals("123")) {
            mPresenter.responseLoginResult(true);
        } else {
            mPresenter.responseLoginResult(false);
        }
    }
}
