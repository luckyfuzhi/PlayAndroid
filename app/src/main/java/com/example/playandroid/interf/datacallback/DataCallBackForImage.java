package com.example.playandroid.interf.datacallback;

import java.io.InputStream;

/**
 * 回调图片输入流数据
 */
public interface DataCallBackForImage {
    void onSuccess(InputStream data);

    void onFailure(Exception e);
}
