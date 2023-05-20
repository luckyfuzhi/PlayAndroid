package com.example.playandroid.presenter;

import com.example.playandroid.base.BasePresenter;
import com.example.playandroid.contract.KsChildContract;
import com.example.playandroid.entity.Article;
import com.example.playandroid.model.KsChildModel;
import com.example.playandroid.view.activity.KsChildActivity;

import java.util.List;

public class KsChildPresenter extends BasePresenter<KsChildActivity, KsChildModel>{
    @Override
    public KsChildModel getModelInstance() {
        return new KsChildModel(this);
    }


}
