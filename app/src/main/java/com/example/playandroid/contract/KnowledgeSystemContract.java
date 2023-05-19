package com.example.playandroid.contract;

import com.example.playandroid.entity.Article;

import java.util.List;

public interface KnowledgeSystemContract {
    interface M {
        void requestKsData();//返回知识体系数据
    }

    interface VP{

        void requestKsData();//请求知识体系数据
        void requestKsDataResult(List<Article> articleList);//返回知识体系数据

    }
}
