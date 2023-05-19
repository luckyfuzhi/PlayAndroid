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
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.playandroid.R;
import com.example.playandroid.base.BaseFragment;
import com.example.playandroid.contract.KnowledgeSystemContract;
import com.example.playandroid.entity.Article;
import com.example.playandroid.entity.KnowledgeType;
import com.example.playandroid.presenter.KnowledgeSystemPresenter;

import java.util.ArrayList;
import java.util.List;

public class KnowledgeSystemFragment extends BaseFragment<KnowledgeSystemPresenter> implements KnowledgeSystemContract.VP {

    private RecyclerView ksTypeRecyclerView;

    private List<KnowledgeType> knowledgeTypeList = new ArrayList<>();


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.knowledge_system_fragement, container, false);

        ksTypeRecyclerView = view.findViewById(R.id.knowledge_system_type_rv);

        return view;
    }

    @Override
    public int getFragmentId() {
        return R.layout.knowledge_system_fragement;
    }

    @Override
    public void initData() {

    }

    @Override
    public void initView() {

    }



    @Override
    public KnowledgeSystemPresenter getPresenterInstance() {
        return new KnowledgeSystemPresenter();
    }

    @Override
    public void requestKsData() {
        mPresenter.requestKsData();
    }

    @Override
    public void requestKsDataResult(List<KnowledgeType> knowledgeTypeList) {

    }


}
