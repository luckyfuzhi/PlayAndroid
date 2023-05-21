package com.example.playandroid.model;

import android.util.Log;

import com.example.playandroid.base.BaseModel;
import com.example.playandroid.base.BaseModelForFragment;
import com.example.playandroid.interf.contract.SucceedLoginContract;
import com.example.playandroid.interf.datacallback.DataCallBack;
import com.example.playandroid.presenter.SucceedLoginPresenter;
import com.example.playandroid.util.WebUtil;

public class SucceedLoginModel extends BaseModelForFragment<SucceedLoginPresenter> implements SucceedLoginContract.M {

    private final static String EXIT_LOGIN_URL = "https://www.wanandroid.com/user/logout/json";

    public SucceedLoginModel(SucceedLoginPresenter mPresenter) {
        super(mPresenter);
    }

    @Override
    public void exitLogin() {
        WebUtil.getDataFromWeb(EXIT_LOGIN_URL, new DataCallBack() {
            @Override
            public void onSuccess(String data) {
                Log.d("test4545", data);
            }

            @Override
            public void onFailure(Exception e) {

            }
        });
    }
}
