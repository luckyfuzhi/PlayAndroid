package com.example.playandroid.interf.datacallback;


public interface DataCallBackForPost {
    void onSuccess(boolean result);

    void onFailure(Exception e);
}
