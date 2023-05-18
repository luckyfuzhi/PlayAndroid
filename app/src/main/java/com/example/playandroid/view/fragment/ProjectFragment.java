package com.example.playandroid.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.playandroid.R;
import com.example.playandroid.base.BaseFragment;
import com.example.playandroid.contract.ProjectContract;
import com.example.playandroid.entity.Project;
import com.example.playandroid.entity.ProjectType;
import com.example.playandroid.presenter.ProjectPresenter;

import java.util.List;

public class ProjectFragment extends BaseFragment<ProjectPresenter> implements ProjectContract.VP {


    @Override
    public int getFragmentId() {
        return R.layout.project_fragment;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {

    }

    @Override
    public ProjectPresenter getPresenterInstance() {
        return new ProjectPresenter();
    }

    @Override
    public void requestProjectTypeData() {

    }

    @Override
    public void requestProjectTypeDataResult(List<ProjectType> projectTypeList) {

    }

    @Override
    public void requestProjectData() {

    }

    @Override
    public void requestProjectDataResult(List<Project> projectList) {

    }
}
