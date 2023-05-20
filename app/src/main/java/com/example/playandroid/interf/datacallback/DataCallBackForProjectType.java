package com.example.playandroid.interf.datacallback;

import com.example.playandroid.entity.ProjectType;

import java.util.List;

public interface DataCallBackForProjectType {

    void onSuccess(List<ProjectType> projectTypeList);

    void onFailure(Exception e);
}
