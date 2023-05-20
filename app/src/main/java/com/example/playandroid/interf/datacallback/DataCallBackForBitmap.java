package com.example.playandroid.interf.datacallback;

import android.graphics.Bitmap;

public interface DataCallBackForBitmap {
    void onSuccess(Bitmap bitmapList);
    void onFailure(Exception e);
}
