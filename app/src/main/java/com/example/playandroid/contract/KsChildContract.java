package com.example.playandroid.contract;

import com.example.playandroid.entity.Article;

import java.util.List;

public interface KsChildContract {

    interface M {
        void requestArticleData(int page, int typeId);//返回文章数据
    }

    interface VP {

        void requestArticleData(int page, int typeId);//请求文章数据

        void requestArticleDataResult(List<Article> articleList);//返回文章数据

    }
}