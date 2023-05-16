package com.example.playandroid.contract;

import com.example.playandroid.entity.FPArticle;

import java.util.List;

public interface DataCallBackForArticle {

    void onSuccess(List<FPArticle> articleList);

    void onFailure(Exception e);

}
