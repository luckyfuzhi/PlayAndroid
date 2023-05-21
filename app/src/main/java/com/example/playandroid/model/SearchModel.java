package com.example.playandroid.model;

import android.util.Log;

import com.example.playandroid.base.BaseModel;
import com.example.playandroid.interf.datacallback.DataCallBack;
import com.example.playandroid.interf.datacallback.DataCallBackForWords;
import com.example.playandroid.interf.contract.SearchContract;
import com.example.playandroid.presenter.SearchPresenter;
import com.example.playandroid.util.WebUtil;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SearchModel extends BaseModel<SearchPresenter> implements SearchContract.M {

    private final static String HOT_WORD_URL = "https://www.wanandroid.com//hotkey/json";

    private List<String> hotWordList = new ArrayList<>();

    public SearchModel(SearchPresenter mPresenter) {
        super(mPresenter);
    }


    /**
     * 解析热词数据
     * @param data 热词json数据
     * @param callBackForWords 接口回调
     */
    public void parseHotWord(String data, DataCallBackForWords callBackForWords){
        List<String> hotWordList = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(data);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                hotWordList.add(jsonObject.optString("name"));
            }
            callBackForWords.onSuccess(hotWordList);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("parseHotWord", "parseHotWord: 解析数据出现异常");
            callBackForWords.onFailure(e);
        }
    }


    @Override
    public void requestHotWord() {
        WebUtil.getDataFromWeb(HOT_WORD_URL, new DataCallBack() {
            @Override
            public void onSuccess(String data) {
                int startIndex = data.indexOf("[");
                int endIndex = data.lastIndexOf("]");
                parseHotWord(data.substring(startIndex, endIndex + 1), new DataCallBackForWords() {
                    @Override
                    public void onSuccess(List<String> wordList) {
                        mPresenter.requestHotWordResult(wordList);
                    }

                    @Override
                    public void onFailure(Exception e) {
                        e.printStackTrace();
                        Log.e("requestHotWord", "获取热词数据错误/" + e);
                    }
                });

            }

            @Override
            public void onFailure(Exception e) {
                e.printStackTrace();
                Log.e("requestHotWord", e.toString());
            }
        });
    }



}
