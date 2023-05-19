package com.example.playandroid.contract;

import com.example.playandroid.entity.Article;

import java.util.List;

public interface DataCallBackForArticle {

    void onSuccess(List<Article> articleList);

    void onFailure(Exception e);

}
