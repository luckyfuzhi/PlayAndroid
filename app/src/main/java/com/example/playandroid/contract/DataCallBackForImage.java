package com.example.playandroid.contract;

import java.io.InputStream;

public interface DataCallBackForImage {
    void onSuccess(InputStream data);

    void onFailure(Exception e);
}
