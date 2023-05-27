package com.example.playandroid.presenter;

import com.example.playandroid.base.BasePresenter;
import com.example.playandroid.interf.contract.RegisterContract;
import com.example.playandroid.model.RegisterModel;
import com.example.playandroid.view.activity.RegisterActivity;

import java.util.Map;

public class RegisterPresenter extends BasePresenter<RegisterActivity, RegisterModel> implements RegisterContract.VP {
    @Override
    public RegisterModel getModelInstance() {
        return new RegisterModel(this);
    }

    @Override
    public void sendRegisterData(Map<String, String> paramMap) {
        mModel.sendRegisterData(paramMap);
    }

    @Override
    public void responseRegisterResult(String registerResult) {
        mView.responseRegisterResult(registerResult);
    }
}
