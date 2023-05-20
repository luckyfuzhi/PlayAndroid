package com.example.playandroid.presenter;

import com.example.playandroid.base.BasePresenter;
import com.example.playandroid.interf.contract.SearchContract;
import com.example.playandroid.model.SearchModel;
import com.example.playandroid.view.activity.SearchActivity;

import java.util.List;

public class SearchPresenter extends BasePresenter<SearchActivity, SearchModel> implements SearchContract.VP {
    @Override
    public SearchModel getModelInstance() {
        return new SearchModel(this);
    }

    @Override
    public void requestHotWord() {
        mModel.requestHotWord();
    }

    @Override
    public void requestHotWordResult(List<String> hotWordList) {
        mView.requestHotWordResult(hotWordList);
    }
}
