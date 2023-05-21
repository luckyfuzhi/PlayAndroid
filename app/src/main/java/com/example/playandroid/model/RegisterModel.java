package com.example.playandroid.model;

import android.util.Log;

import com.example.playandroid.base.BaseModel;
import com.example.playandroid.interf.contract.RegisterContract;
import com.example.playandroid.interf.datacallback.DataCallBack;
import com.example.playandroid.presenter.RegisterPresenter;
import com.example.playandroid.util.WebUtil;

import java.util.Map;

public class RegisterModel extends BaseModel<RegisterPresenter> implements RegisterContract.M {

    private final static String REGISTER_URL = "https://www.wanandroid.com/user/register";

    private final static int PSW_LENGTH_LACK = -1;
    private final static int REGISTER_SUCCESS = 1;

    private final static int USERNAME_REGISTERED = -2;

    public RegisterModel(RegisterPresenter mPresenter) {
        super(mPresenter);
    }

    @Override
    public void sendRegisterData(Map<String, String> paramMap) {
        WebUtil.postDataToWeb(REGISTER_URL, paramMap, new DataCallBack() {
            @Override
            public void onSuccess(String data) {
                if(data.contains("密码长度必须大于6位！")){
                    mPresenter.responseRegisterResult(PSW_LENGTH_LACK);
                } else if (data.contains("用户名已经被注册!")) {
                    mPresenter.responseRegisterResult(USERNAME_REGISTERED);
                } else {
                    mPresenter.responseRegisterResult(REGISTER_SUCCESS);
                }
            }

            @Override
            public void onFailure(Exception e) {
                e.printStackTrace();
                Log.e("sendRegisterData", e.toString());
            }
        });
    }
}
