package com.example.playandroid.interf.datacallback;

import com.example.playandroid.entity.Project;

import java.util.List;

public interface DataCallBackForProjectArticle {
    void onSuccess(List<Project> projectArticleList);

    void onFailure(Exception e);
}
