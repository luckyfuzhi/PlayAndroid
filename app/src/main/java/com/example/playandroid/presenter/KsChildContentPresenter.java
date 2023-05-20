package com.example.playandroid.presenter;

import com.example.playandroid.base.BasePresenterForFragment;
import com.example.playandroid.contract.KsChildContract;
import com.example.playandroid.entity.Article;
import com.example.playandroid.model.KsChildContentModel;
import com.example.playandroid.view.fragment.KsChildContentFragment;

import java.util.List;

public class KsChildContentPresenter extends BasePresenterForFragment<KsChildContentFragment, KsChildContentModel> implements KsChildContract.VP {

    @Override
    public void requestArticleData(int page, int typeId) {
        mModel.requestArticleData(page, typeId);
    }

    @Override
    public void requestArticleDataResult(List<Article> articleList) {
        mView.requestArticleDataResult(articleList);
    }


    @Override
    public KsChildContentModel getModelInstance() {
        return new KsChildContentModel(this);
    }
}
