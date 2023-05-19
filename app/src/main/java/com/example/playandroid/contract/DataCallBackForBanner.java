package com.example.playandroid.contract;

import com.example.playandroid.entity.Banner;

import java.util.List;

public interface DataCallBackForBanner {
    void onSuccess(List<Banner> bannerList);

    void onFailure(Exception e);

}
