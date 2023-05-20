package com.example.playandroid.presenter;

import com.example.playandroid.base.BasePresenterForFragment;
import com.example.playandroid.interf.contract.KnowledgeSystemContract;
import com.example.playandroid.entity.KnowledgeType;
import com.example.playandroid.model.KnowledgeSystemModel;
import com.example.playandroid.view.fragment.KnowledgeSystemFragment;

import java.util.List;

public class KnowledgeSystemPresenter extends BasePresenterForFragment<KnowledgeSystemFragment, KnowledgeSystemModel>
        implements KnowledgeSystemContract.VP {
    @Override
    public KnowledgeSystemModel getModelInstance() {
        return new KnowledgeSystemModel(this);
    }

    @Override
    public void requestKsData() {
        mModel.requestKsData();
    }

    @Override
    public void requestKsDataResult(List<KnowledgeType> knowledgeTypeList) {
        mView.requestKsDataResult(knowledgeTypeList);
    }


}
