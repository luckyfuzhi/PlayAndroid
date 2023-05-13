package com.example.playandroid.base;

import com.example.playandroid.contract.LoginContract;

public abstract class BaseModel<P extends BasePresenter>{

    public P mPresenter;

    public BaseModel(P mPresenter) {
        this.mPresenter = mPresenter;
    }
}
