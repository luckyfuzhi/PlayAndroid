package com.example.playandroid.interf.contract;

import com.example.playandroid.entity.ProjectType;

import java.util.List;

/**
 * 项目模块契约接口
 */
public interface ProjectContract {

    interface M {
        void requestProjectTypeData();//返回项目文章类型数据

    }

    interface VP {

        void requestProjectTypeData();//请求项目文章类型数据

        void requestProjectTypeDataResult(List<ProjectType> projectTypeList);//返回项目文章类型数据


    }

}
