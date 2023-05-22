package com.example.playandroid.interf.datacallback;

import com.example.playandroid.entity.Project;

import java.util.List;

/**
 * 回调项目文章数据
 */
public interface DataCallBackForProjectArticle {
    void onSuccess(List<Project> projectArticleList);

    void onFailure(Exception e);
}
