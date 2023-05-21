package com.example.playandroid.presenter;

import com.example.playandroid.base.BasePresenterForFragment;
import com.example.playandroid.interf.contract.SucceedLoginContract;
import com.example.playandroid.model.SucceedLoginModel;
import com.example.playandroid.view.fragment.SucceedLoginFragment;

public class SucceedLoginPresenter extends BasePresenterForFragment<SucceedLoginFragment, SucceedLoginModel> implements SucceedLoginContract.VP {
    @Override
    public SucceedLoginModel getModelInstance() {
        return new SucceedLoginModel(this);
    }

    @Override
    public void exitLogin() {
        mModel.exitLogin();
    }

    @Override
    public void exitLoginResult() {

    }
}
