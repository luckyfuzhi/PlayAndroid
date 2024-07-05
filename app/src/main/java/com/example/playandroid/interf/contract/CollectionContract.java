package com.example.playandroid.interf.contract;

import com.example.playandroid.entity.Article;
import com.example.playandroid.entity.CollectArticle;

import java.util.List;

public interface CollectionContract {
    interface M {
        void loadArticles(int page);
    }

    interface V {
        void showArticles(List<CollectArticle> articles);
        void showMoreArticles(List<CollectArticle> articles);
        void hideLoading();
    }
}
