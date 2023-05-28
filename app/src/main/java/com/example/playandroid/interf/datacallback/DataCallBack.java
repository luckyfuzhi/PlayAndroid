package com.example.playandroid.interf.datacallback;


import java.util.List;

/**
 * 回调字符串数据
 */
public interface DataCallBack {
    void onSuccess(String data);

    void onFailure(Exception e);

    void getCookie(List<String> setCookieList);
}
