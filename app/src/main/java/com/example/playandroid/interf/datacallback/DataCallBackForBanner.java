package com.example.playandroid.interf.datacallback;

import com.example.playandroid.entity.Banner;

import java.util.List;

/**
 * 回调banner数据
 */
public interface DataCallBackForBanner {
    void onSuccess(List<Banner> bannerList);

    void onFailure(Exception e);

}
