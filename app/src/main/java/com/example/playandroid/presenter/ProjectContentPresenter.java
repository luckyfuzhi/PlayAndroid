package com.example.playandroid.presenter;

import android.graphics.Bitmap;

import com.example.playandroid.base.BasePresenterForFragment;
import com.example.playandroid.interf.contract.ProjectArticleContract;
import com.example.playandroid.entity.Project;
import com.example.playandroid.model.ProjectContentModel;
import com.example.playandroid.view.fragment.ProjectContentFragment;

import java.util.List;

public class ProjectContentPresenter extends BasePresenterForFragment<ProjectContentFragment, ProjectContentModel> implements ProjectArticleContract.VP {
    @Override
    public ProjectContentModel getModelInstance() {
        return new ProjectContentModel(this);
    }

    @Override
    public void requestProjectData(int page, int typeId) {
        mModel.requestProjectData(page, typeId);
    }

    @Override
    public void requestProjectDataResult(List<Project> projectList, boolean over) {
        mView.requestProjectDataResult(projectList, over);
    }

    public void collectArticle(int articleId) {
        mModel.collectArticle(articleId);
    }

    public void collectResult(String msg) {
        mView.showCollectResult(msg);
    }
}
