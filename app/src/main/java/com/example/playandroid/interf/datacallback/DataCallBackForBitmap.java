package com.example.playandroid.interf.datacallback;

import android.graphics.Bitmap;

/**
 * 回调图片bitmap数据
 */
public interface DataCallBackForBitmap {
    void onSuccess(Bitmap bitmap);
    void onFailure(Exception e);
}
