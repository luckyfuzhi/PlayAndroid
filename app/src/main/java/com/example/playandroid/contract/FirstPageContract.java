package com.example.playandroid.contract;

import com.example.playandroid.entity.Banner;

import java.util.List;

public interface FirstPageContract {
    interface M {
        void requestBannerData();

    }

    interface VP{
        void requestBannerData();
        void requestBannerDataResult(List<Banner> bannerList);
    }
}
