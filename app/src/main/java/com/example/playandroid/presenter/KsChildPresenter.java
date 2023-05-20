package com.example.playandroid.presenter;

import com.example.playandroid.base.BasePresenter;
import com.example.playandroid.model.KsChildModel;
import com.example.playandroid.view.activity.KsChildActivity;

public class KsChildPresenter extends BasePresenter<KsChildActivity, KsChildModel> {
    @Override
    public KsChildModel getModelInstance() {
        return new KsChildModel(this);
    }
}
