package com.example.playandroid.presenter;

import com.example.playandroid.base.BasePresenter;
import com.example.playandroid.model.SearchModel;
import com.example.playandroid.view.activity.SearchActivity;

public class SearchPresenter extends BasePresenter<SearchActivity, SearchModel> {
    @Override
    public SearchModel getModelInstance() {
        return new SearchModel(this);
    }
}
