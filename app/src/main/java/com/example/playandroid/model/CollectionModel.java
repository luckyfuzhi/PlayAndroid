package com.example.playandroid.model;

import android.util.Log;

import com.example.playandroid.base.BaseModel;
import com.example.playandroid.entity.Article;
import com.example.playandroid.entity.ArticleResponse;
import com.example.playandroid.entity.CollectArticle;
import com.example.playandroid.entity.CollectArticleResponse;
import com.example.playandroid.entity.SingleDataResponse;
import com.example.playandroid.interf.contract.CollectionContract;
import com.example.playandroid.interf.service.CollectionService;
import com.example.playandroid.presenter.CollectionPresenter;
import com.example.playandroid.util.RetrofitUtil;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CollectionModel extends BaseModel<CollectionPresenter> implements CollectionContract.M {

    private CollectionService apiService = RetrofitUtil.getRetrofitInstance().create(CollectionService.class);

    public CollectionModel(CollectionPresenter mPresenter) {
        super(mPresenter);
    }

    @Override
    public void loadArticles(int page) {
        Call<SingleDataResponse<CollectArticleResponse>> call = apiService.getCollectionArticle(page);
        call.enqueue(new Callback<SingleDataResponse<CollectArticleResponse>>() {
            @Override
            public void onResponse(Call<SingleDataResponse<CollectArticleResponse>> call, Response<SingleDataResponse<CollectArticleResponse>> response) {
                if (response.isSuccessful()) {
                    SingleDataResponse<CollectArticleResponse> dataResponse = response.body();
                    if (dataResponse != null) {
                        Log.d("test111", dataResponse.getData().getDatas().toString());
                        List<CollectArticle> articles = dataResponse.getData().getDatas();
                        mPresenter.getLoadArticles(articles, dataResponse.getData().getPageCount());
                    } else {
                        mPresenter.responseError("响应为空", new Throwable("响应为空"));
                    }
                } else {
                    mPresenter.responseError(response.message(), new Throwable(response.message()));
                }
            }

            @Override
            public void onFailure(Call<SingleDataResponse<CollectArticleResponse>> call, Throwable t) {
                mPresenter.responseError(t.getMessage(), t);
                Log.e("getBannerData", "fail/" + t);
            }
        });
    }
}
