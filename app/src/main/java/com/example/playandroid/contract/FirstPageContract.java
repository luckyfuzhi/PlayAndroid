package com.example.playandroid.contract;

import com.example.playandroid.entity.Banner;
import com.example.playandroid.entity.FPArticle;

import java.util.List;

public interface FirstPageContract {
    interface M {
        void requestBannerData();//返回文章数据

        void requestArticleData(int page);//返回文章数据

        void requestTopArticleData();//返回置顶文章数据
    }

    interface VP{

        void requestBannerData();//请求banner数据
        void requestBannerDataResult(List<Banner> bannerList);//返回banner数据

        void requestArticleData(int page);//请求文章数据
        void requestArticleDataResult(List<FPArticle> articleList);//返回文章数据

        void requestTopArticleData();//请求置顶文章数据
        void requestTopArticleDataResult(List<FPArticle> topArticleList);//返回置顶文章数据
    }
}
