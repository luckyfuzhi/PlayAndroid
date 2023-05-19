package com.example.playandroid.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.playandroid.R;
import com.example.playandroid.base.BaseFragment;
import com.example.playandroid.presenter.KnowledgeSystemPresenter;

public class KnowledgeSystemFragment extends BaseFragment<KnowledgeSystemPresenter> {


    @Override
    public int getFragmentId() {
        return R.layout.knowledge_system_fragement;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {

    }

    @Override
    public KnowledgeSystemPresenter getPresenterInstance() {
        return new KnowledgeSystemPresenter();
    }
}
