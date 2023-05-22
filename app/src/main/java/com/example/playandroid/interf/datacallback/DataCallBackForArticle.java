package com.example.playandroid.interf.datacallback;

import com.example.playandroid.entity.Article;

import java.util.List;

/**
 * 回调文章数据
 */
public interface DataCallBackForArticle {

    void onSuccess(List<Article> articleList);

    void onFailure(Exception e);

}
