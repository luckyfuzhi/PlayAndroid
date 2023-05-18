package com.example.playandroid.model;

import com.example.playandroid.base.BaseModelForFragment;
import com.example.playandroid.contract.ProjectContract;
import com.example.playandroid.presenter.ProjectPresenter;

public class ProjectModel extends BaseModelForFragment<ProjectPresenter> implements ProjectContract.M {
    public ProjectModel(ProjectPresenter mPresenter) {
        super(mPresenter);
    }

    @Override
    public void requestProjectTypeData() {

    }

    @Override
    public void requestProjectData() {

    }
}
