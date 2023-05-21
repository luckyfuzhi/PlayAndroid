package com.example.playandroid.interf.contract;

import com.example.playandroid.entity.Project;

import java.util.List;

public interface ProjectArticleContract {


    interface M {

        void requestProjectData(int page, int typeId);//返回项目文章数据

    }

    interface VP {


        void requestProjectData(int page, int typeId);//请求项目文章数据

        void requestProjectDataResult(List<Project> projectList);//返回项目文章数据

    }

}
