package com.example.playandroid.interf.contract;

import com.example.playandroid.entity.Project;

import java.util.List;

public interface ProjectArticleContract {


    interface M {

        void requestProjectData(int page, int typeId);

    }

    interface VP {


        void requestProjectData(int page, int typeId);

        void requestProjectDataResult(List<Project> projectList);

    }

}
