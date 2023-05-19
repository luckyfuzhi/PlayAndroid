package com.example.playandroid.presenter;

import com.example.playandroid.base.BasePresenterForFragment;
import com.example.playandroid.model.KnowledgeSystemModel;
import com.example.playandroid.view.fragment.KnowledgeSystemFragment;

public class KnowledgeSystemPresenter extends BasePresenterForFragment<KnowledgeSystemFragment, KnowledgeSystemModel> {
    @Override
    public KnowledgeSystemModel getModelInstance() {
        return new KnowledgeSystemModel(this);
    }
}
