package com.example.playandroid.contract;

import com.example.playandroid.entity.Article;

import java.util.List;

public interface DataCallBackForWords {

    void onSuccess(List<String> wordList);

    void onFailure(Exception e);

}
