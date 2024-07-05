package com.example.playandroid.presenter;

import android.util.Log;

import com.example.playandroid.base.BasePresenter;
import com.example.playandroid.entity.Article;
import com.example.playandroid.entity.ArticleResponse;
import com.example.playandroid.entity.CollectArticle;
import com.example.playandroid.entity.SingleDataResponse;
import com.example.playandroid.interf.contract.CollectionContract;
import com.example.playandroid.model.CollectionModel;
import com.example.playandroid.view.activity.CollectionActivity;

import java.util.List;
import java.util.Objects;

public class CollectionPresenter extends BasePresenter<CollectionActivity, CollectionModel> {
    private boolean isLoading = false;

    private int pages = 0;
    private int pageCount;
    @Override
    public CollectionModel getModelInstance() {
        return new CollectionModel(this);
    }

    public void loadArticles() {
        mModel.loadArticles(pages);
    }

    public void loadMoreArticles() {
        if (isLoading || pages >= pageCount){
            if (pages == pageCount) mView.hideLoading();//到底了，去掉进度条
            return;
        }
        isLoading = true;
        mModel.loadArticles(pages);
    }

    public void getLoadArticles(List<CollectArticle> articles, int pageCount) {
        List<CollectArticle> articleList = articles;
        this.pageCount = pageCount;
        if (pages == 0) {
            mView.showArticles(articleList);
        } else {
            mView.hideLoading(); //去掉上一次请求的数据后面的进度条
            mView.showMoreArticles(articleList);
            isLoading = false;
        }
        pages++;
    }

    public void responseError(String error, Throwable throwable) {
        mView.responseError(error, throwable);
    }
}
