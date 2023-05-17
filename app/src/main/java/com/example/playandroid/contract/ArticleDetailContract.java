package com.example.playandroid.contract;

import com.example.playandroid.entity.Banner;
import com.example.playandroid.entity.FPArticle;

import java.util.List;

public interface ArticleDetailContract {

    interface M {
        void requestDetailArticleData(int page);//返回文章数据
    }

    interface VP{

        void requestDetailArticleData(int page);//请求文章数据
        void requestArticleDataResult(List<FPArticle> articleList);//返回文章数据

    }
}
