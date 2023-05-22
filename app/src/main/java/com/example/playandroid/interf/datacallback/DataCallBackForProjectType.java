package com.example.playandroid.interf.datacallback;

import com.example.playandroid.entity.ProjectType;

import java.util.List;

/**
 * 回调项目种类数据
 */
public interface DataCallBackForProjectType {

    void onSuccess(List<ProjectType> projectTypeList);

    void onFailure(Exception e);
}
