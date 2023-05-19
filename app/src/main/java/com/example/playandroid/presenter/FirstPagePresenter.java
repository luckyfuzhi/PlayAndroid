package com.example.playandroid.presenter;


import com.example.playandroid.base.BasePresenterForFragment;
import com.example.playandroid.contract.FirstPageContract;
import com.example.playandroid.entity.Banner;
import com.example.playandroid.entity.Article;
import com.example.playandroid.model.FirstPageModel;
import com.example.playandroid.view.fragment.FirstPageFragment;

import java.util.List;

public class FirstPagePresenter extends BasePresenterForFragment<FirstPageFragment, FirstPageModel> implements FirstPageContract.VP {


    @Override
    public FirstPageModel getModelInstance() {
        return new FirstPageModel(this);
    }

    @Override
    public void requestBannerData() {
        mModel.requestBannerData();
    }

    @Override
    public void requestBannerDataResult(List<Banner> bannerList) {
        mView.requestBannerDataResult(bannerList);
    }

    @Override
    public void requestArticleData(int page) {
        mModel.requestArticleData(page);
    }

    @Override
    public void requestArticleDataResult(List<Article> articleList) {
        mView.requestArticleDataResult(articleList);
    }

    @Override
    public void requestTopArticleData() {
        mModel.requestTopArticleData();
    }

    @Override
    public void requestTopArticleDataResult(List<Article> topArticleList) {
        mView.requestTopArticleDataResult(topArticleList);
    }
}
