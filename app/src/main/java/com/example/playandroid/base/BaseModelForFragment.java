package com.example.playandroid.base;

public class BaseModelForFragment<P extends BasePresenterForFragment> {

    public P mPresenter;

    public BaseModelForFragment(P mPresenter) {
        this.mPresenter = mPresenter;
    }
}
