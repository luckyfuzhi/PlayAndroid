package com.example.playandroid.interf.datacallback;


/**
 * 回调字符串数据
 */
public interface DataCallBack {
    void onSuccess(String data);

    void onFailure(Exception e);
}
