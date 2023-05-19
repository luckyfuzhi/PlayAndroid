package com.example.playandroid.contract;

import android.graphics.Bitmap;


import java.util.List;

public interface DataCallBackForBitmap {
    void onSuccess(Bitmap bitmapList);
    void onFailure(Exception e);
}
