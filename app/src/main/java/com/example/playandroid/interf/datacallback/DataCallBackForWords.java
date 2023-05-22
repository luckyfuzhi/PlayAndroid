package com.example.playandroid.interf.datacallback;

import java.util.List;

/**
 * 回调热词数据
 */
public interface DataCallBackForWords {

    void onSuccess(List<String> wordList);

    void onFailure(Exception e);

}
