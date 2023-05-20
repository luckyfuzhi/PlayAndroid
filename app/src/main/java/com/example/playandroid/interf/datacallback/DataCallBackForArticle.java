package com.example.playandroid.interf.datacallback;

import com.example.playandroid.entity.Article;

import java.util.List;

public interface DataCallBackForArticle {

    void onSuccess(List<Article> articleList);

    void onFailure(Exception e);

}
