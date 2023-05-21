package com.example.playandroid.interf.contract;

import com.example.playandroid.entity.Article;

import java.util.List;
import java.util.Map;

public interface SearchResultContract {

    interface M {

        void requestArticleData(int page, Map<String, String> paramMap);//返回搜索结果文章数据

    }

    interface VP {

        void requestArticleData(int page, Map<String, String> paramMap);//请求搜索结果文章数据

        void requestArticleDataResult(List<Article> articleList);//返回搜索结果文章数据


    }

}
