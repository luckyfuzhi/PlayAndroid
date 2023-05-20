package com.example.playandroid.model;

import android.util.Log;

import com.example.playandroid.base.BaseModelForFragment;
import com.example.playandroid.interf.datacallback.DataCallBack;
import com.example.playandroid.interf.datacallback.DataCallBackForKnowledgeType;
import com.example.playandroid.interf.contract.KnowledgeSystemContract;
import com.example.playandroid.entity.KnowledgeType;
import com.example.playandroid.presenter.KnowledgeSystemPresenter;
import com.example.playandroid.util.WebUtil;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class KnowledgeSystemModel extends BaseModelForFragment<KnowledgeSystemPresenter> implements KnowledgeSystemContract.M {

    private final String KS_DATA = "https://www.wanandroid.com/tree/json";


    public void getKsTypeData(){
        WebUtil.getDataFromWeb(KS_DATA, new DataCallBack() {
            @Override
            public void onSuccess(String data) {
                int startIndex = data.indexOf("[");
                int endIndex = data.lastIndexOf("]");
                parseKsTypeData(data.substring(startIndex, endIndex + 1), new DataCallBackForKnowledgeType() {
                    @Override
                    public void onSuccess(List<KnowledgeType> knowledgeTypeList) {
                        mPresenter.requestKsDataResult(knowledgeTypeList);
                    }

                    @Override
                    public void onFailure(Exception e) {
                        e.printStackTrace();
                        Log.e("getKsTypeData", "onFailure: " + e);
                    }
                });

            }

            @Override
            public void onFailure(Exception e) {

            }
        });
    }

    public void parseKsTypeData(String data, DataCallBackForKnowledgeType callBackForKnowledgeType){
        List<KnowledgeType> knowledgeTypeList = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(data);

            for (int i = 0; i < jsonArray.length(); i++) {
                List<KnowledgeType> childList = new ArrayList<>();
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                KnowledgeType knowledgeType = new KnowledgeType();
                knowledgeType.setId(jsonObject.getInt("id"));
                knowledgeType.setName(jsonObject.optString("name"));

                JSONArray jsonArray1 = jsonObject.getJSONArray("children");
                for (int j = 0; j < jsonArray1.length(); j++) {
                    KnowledgeType childType = new KnowledgeType();
                    childType.setName(jsonArray1.getJSONObject(j).optString("name"));
                    childType.setId(jsonArray1.getJSONObject(j).optInt("id"));
                    childList.add(childType);
                }
                knowledgeType.setChildList(childList);

                knowledgeTypeList.add(knowledgeType);
            }
            callBackForKnowledgeType.onSuccess(knowledgeTypeList);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("parseKsTypeData", "parseKsTypeData: 解析数据出现异常/" + e);
            callBackForKnowledgeType.onFailure(e);
        }
    }

    public KnowledgeSystemModel(KnowledgeSystemPresenter mPresenter) {
        super(mPresenter);
    }

    @Override
    public void requestKsData() {
        getKsTypeData();
    }
}
