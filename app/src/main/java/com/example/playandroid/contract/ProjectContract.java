package com.example.playandroid.contract;

import com.example.playandroid.entity.Banner;
import com.example.playandroid.entity.FPArticle;
import com.example.playandroid.entity.Project;
import com.example.playandroid.entity.ProjectType;

import java.util.List;

public interface ProjectContract {

    interface M {
        void requestProjectTypeData();

        void requestProjectData();
    }

    interface VP {

        void requestProjectTypeData();

        void requestProjectTypeDataResult(List<ProjectType> projectTypeList);

        void requestProjectData();

        void requestProjectDataResult(List<Project> projectList);

    }

}
