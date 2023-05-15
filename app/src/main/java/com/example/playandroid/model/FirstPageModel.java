package com.example.playandroid.model;

import android.util.Log;

import com.example.playandroid.base.BaseModelForFragment;
import com.example.playandroid.contract.DataCallBack;
import com.example.playandroid.contract.DataCallBackForBanner;
import com.example.playandroid.contract.FirstPageContract;
import com.example.playandroid.entity.Banner;
import com.example.playandroid.presenter.FirstPagePresenter;
import com.example.playandroid.util.WebUtil;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class FirstPageModel extends BaseModelForFragment<FirstPagePresenter> implements FirstPageContract.M {

    private final static String BANNER_URL = "https://www.wanandroid.com/banner/json";


    private static final String TAG = "BannerModel";

    public FirstPageModel(FirstPagePresenter mPresenter) {
        super(mPresenter);
    }

    /**
     * 获取banner数据
     * @return banner数据
     */
    public void getBannerData(){

        WebUtil.getDataFromWeb(BANNER_URL, new DataCallBack() {
            @Override
            public void onSuccess(String data) {
                int startIndex = data.indexOf("[");
                int endIndex = data.lastIndexOf("]");
                parseBannerData(data.substring(startIndex, endIndex + 1), new DataCallBackForBanner() {
                    @Override
                    public void onSuccess(List<Banner> bannerList) {
                        mPresenter.requestBannerDataResult(bannerList);
                    }

                    @Override
                    public void onFailure(Exception e) {
                        e.printStackTrace();
                        Log.e(TAG, "onFailure: 获取Banner数据失败");
                    }
                });
            }

            @Override
            public void onFailure(Exception e) {
            }
        });


    }

    /**
     *  解析Banner的Json数据
     * @param data Json数据
     * @return banner对象的集合
     */
    public void parseBannerData(String data, DataCallBackForBanner dataCallBack){

        List<Banner> bannerList = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(data);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Banner banner = new Banner();
                banner.setDesc(jsonObject.getString("desc"));
                banner.setId(jsonObject.getInt("id"));
                banner.setImagePath(jsonObject.getString("imagePath"));
                banner.setIsVisible(jsonObject.getInt("isVisible"));
                banner.setOrder(jsonObject.getInt("order"));
                banner.setTitle(jsonObject.getString("title"));
                banner.setType(jsonObject.getInt("type"));
                banner.setUrl(jsonObject.getString("url"));
                bannerList.add(banner);
            }
            dataCallBack.onSuccess(bannerList);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "parseBannerData: 解析数据出现异常");
        }
    }


    @Override
    public void requestBannerData() {
        getBannerData();
    }
}
