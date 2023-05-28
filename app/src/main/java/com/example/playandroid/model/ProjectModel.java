package com.example.playandroid.model;

import android.util.Log;

import com.example.playandroid.base.BaseModelForFragment;
import com.example.playandroid.interf.datacallback.DataCallBack;
import com.example.playandroid.interf.datacallback.DataCallBackForProjectType;
import com.example.playandroid.interf.contract.ProjectContract;
import com.example.playandroid.entity.ProjectType;
import com.example.playandroid.presenter.ProjectPresenter;
import com.example.playandroid.util.WebUtil;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ProjectModel extends BaseModelForFragment<ProjectPresenter> implements ProjectContract.M {

    //项目种类网址
    private final static String PROJECT_TYPE_URL = "https://www.wanandroid.com/project/tree/json";

    public ProjectModel(ProjectPresenter mPresenter) {
        super(mPresenter);
    }


    public void getProjectTypeData() {
        WebUtil.getDataFromWeb(PROJECT_TYPE_URL, new DataCallBack() {
            @Override
            public void onSuccess(String data) {
                int startIndex = data.indexOf("[");
                int endIndex = data.lastIndexOf("]");
                parseProjectTypeData(data.substring(startIndex, endIndex + 1), new DataCallBackForProjectType() {
                    @Override
                    public void onSuccess(List<ProjectType> projectTypeList) {
                        mPresenter.requestProjectTypeDataResult(projectTypeList);
                    }

                    @Override
                    public void onFailure(Exception e) {
                        e.printStackTrace();
                        Log.e("getProjectTypeData", "onFailure: 获取项目类型数据失败");
                    }
                });
            }

            @Override
            public void onFailure(Exception e) {
                e.printStackTrace();
                Log.e("getProjectTypeData", "onFailure: 获取网络数据失败");
            }

            @Override
            public void getCookie(List<String> setCookieList) {

            }
        });
    }


    /**
     * 解析ProjectType的Json数据
     *
     * @param data Json数据
     * @return ProjectType对象的集合
     */
    public void parseProjectTypeData(String data, DataCallBackForProjectType dataCallBack) {

        List<ProjectType> projectTypeList = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(data);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                ProjectType projectType = new ProjectType();
                projectType.setId(jsonObject.getInt("id"));
                projectType.setName(jsonObject.getString("name"));
                projectTypeList.add(projectType);
            }
            dataCallBack.onSuccess(projectTypeList);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("parseProjectTypeData", "parseProjectTypeData: 解析数据出现异常");
            dataCallBack.onFailure(e);
        }
    }


    @Override
    public void requestProjectTypeData() {
        getProjectTypeData();
    }

}
