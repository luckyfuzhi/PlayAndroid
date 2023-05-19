package com.example.playandroid.contract;

import com.example.playandroid.entity.Project;
import com.example.playandroid.entity.ProjectType;

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
