package com.example.playandroid.view.activity;

import android.view.View;

import com.example.playandroid.R;
import com.example.playandroid.base.BaseActivity;
import com.example.playandroid.presenter.KsChildPresenter;

public class KsChildActivity extends BaseActivity<KsChildPresenter> {
    @Override
    public void initView() {

    }

    @Override
    public void initData() {

    }

    @Override
    public void initListener() {
    }

    @Override
    public int getContentViewId() {
        return R.layout.knowledge_system_detail_activity;
    }

    @Override
    public KsChildPresenter getPresenterInstance() {
        return null;
    }

    @Override
    public void destroy() {

    }

    @Override
    public <ERROR> void responseError(ERROR error, Throwable throwable) {

    }

    @Override
    public void onClick(View view) {

    }
}
