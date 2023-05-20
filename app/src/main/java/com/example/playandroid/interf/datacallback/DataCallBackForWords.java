package com.example.playandroid.interf.datacallback;

import java.util.List;

public interface DataCallBackForWords {

    void onSuccess(List<String> wordList);

    void onFailure(Exception e);

}
