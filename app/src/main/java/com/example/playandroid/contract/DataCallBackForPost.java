package com.example.playandroid.contract;


public interface DataCallBackForPost {
    void onSuccess(boolean result);

    void onFailure(Exception e);
}
