package com.example.playandroid.presenter;

import com.example.playandroid.base.BasePresenterForFragment;
import com.example.playandroid.entity.Article;
import com.example.playandroid.interf.contract.SearchResultContract;
import com.example.playandroid.model.SearchResultModel;
import com.example.playandroid.view.fragment.SearchResultFragment;

import java.util.List;
import java.util.Map;

public class SearchResultPresenter extends BasePresenterForFragment<SearchResultFragment, SearchResultModel>
        implements SearchResultContract.VP {
    @Override
    public SearchResultModel getModelInstance() {
        return new SearchResultModel(this);
    }

    @Override
    public void requestArticleData(int page, Map<String, String> paramMap) {
        mModel.requestArticleData(page, paramMap);
    }

    @Override
    public void requestArticleDataResult(List<Article> articleList) {
        mView.requestArticleDataResult(articleList);
    }
}
