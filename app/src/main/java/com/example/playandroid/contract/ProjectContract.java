package com.example.playandroid.contract;

import com.example.playandroid.entity.ProjectType;

import java.util.List;

public interface ProjectContract {

    interface M {
        void requestProjectTypeData();

    }

    interface VP {

        void requestProjectTypeData();

        void requestProjectTypeDataResult(List<ProjectType> projectTypeList);


    }

}
