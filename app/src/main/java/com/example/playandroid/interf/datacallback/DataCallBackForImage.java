package com.example.playandroid.interf.datacallback;

import java.io.InputStream;

public interface DataCallBackForImage {
    void onSuccess(InputStream data);

    void onFailure(Exception e);
}
