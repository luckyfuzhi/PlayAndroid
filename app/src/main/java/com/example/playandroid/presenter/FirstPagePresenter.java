package com.example.playandroid.presenter;


import com.example.playandroid.base.BasePresenterForFragment;
import com.example.playandroid.contract.FirstPageContract;
import com.example.playandroid.entity.Banner;
import com.example.playandroid.model.FirstPageModel;
import com.example.playandroid.view.fragment.FirstPageFragment;

import java.util.List;

public class FirstPagePresenter extends BasePresenterForFragment<FirstPageFragment, FirstPageModel> implements FirstPageContract.VP {


    @Override
    public FirstPageModel getModelInstance() {
        return new FirstPageModel(this);
    }

    @Override
    public void requestBannerData() {
        mModel.requestBannerData();
    }

    @Override
    public void requestBannerDataResult(List<Banner> bannerList) {
        mView.requestBannerDataResult(bannerList);
    }
}
