package com.example.playandroid.presenter;

import com.example.playandroid.base.BasePresenterForFragment;
import com.example.playandroid.contract.ProjectContract;
import com.example.playandroid.entity.Project;
import com.example.playandroid.entity.ProjectType;
import com.example.playandroid.model.ProjectModel;
import com.example.playandroid.view.fragment.ProjectFragment;

import java.util.List;

public class ProjectPresenter extends BasePresenterForFragment<ProjectFragment, ProjectModel> implements ProjectContract.VP {
    @Override
    public ProjectModel getModelInstance() {
        return new ProjectModel(this);
    }

    @Override
    public void requestProjectTypeData() {
        mModel.requestProjectTypeData();
    }

    @Override
    public void requestProjectTypeDataResult(List<ProjectType> projectTypeList) {
        mView.requestProjectTypeDataResult(projectTypeList);
    }

}
