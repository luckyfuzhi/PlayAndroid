package com.example.playandroid.presenter;

import com.example.playandroid.base.BasePresenter;
import com.example.playandroid.model.CollectionModel;
import com.example.playandroid.view.activity.CollectionActivity;

public class CollectionPresenter extends BasePresenter<CollectionActivity, CollectionModel> {
    @Override
    public CollectionModel getModelInstance() {
        return new CollectionModel(this);
    }
}
