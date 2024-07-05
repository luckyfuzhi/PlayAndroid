package com.example.playandroid.view.activity;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.playandroid.R;
import com.example.playandroid.adapter.CollectionAdapter;
import com.example.playandroid.base.BaseActivity;
import com.example.playandroid.entity.Article;
import com.example.playandroid.entity.ArticleResponse;
import com.example.playandroid.entity.CollectArticle;
import com.example.playandroid.entity.SingleDataResponse;
import com.example.playandroid.interf.contract.CollectionContract;
import com.example.playandroid.interf.datacallback.DataCallBackForArticleAdapter;
import com.example.playandroid.presenter.CollectionPresenter;

import java.util.ArrayList;
import java.util.List;

public class CollectionActivity extends BaseActivity<CollectionPresenter> implements CollectionContract.V {

    private RecyclerView recyclerView;

    private CollectionAdapter collectionAdapter;
    private List<CollectArticle> articleList;

    @Override
    public void initView() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(view1 -> {
            finish();
        });
        recyclerView = findViewById(R.id.rv_collection);
        articleList = new ArrayList<>();
        collectionAdapter = new CollectionAdapter(articleList, new DataCallBackForArticleAdapter() {
            @Override
            public void getLoveImg(ImageView loveImg, int articleId) {
                loveImg.setSelected(false);
                //todo 取消收藏
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(collectionAdapter);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                int lastPosition = -1;

                if (newState == RecyclerView.SCROLL_STATE_IDLE) {

                    RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();

                    assert layoutManager != null;
                    lastPosition = ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();

                    if (lastPosition == layoutManager.getItemCount() - 1) {
                        mPresenter.loadMoreArticles();
                    }

                }
            }
        });
    }

    @Override
    public void initData() {
        mPresenter.loadArticles();
    }

    @Override
    public void initListener() {

    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_collection;
    }

    @Override
    public CollectionPresenter getPresenterInstance() {
        return new CollectionPresenter();
    }

    @Override
    public void destroy() {

    }

    @Override
    public <ERROR> void responseError(ERROR error, Throwable throwable) {
        Toast.makeText(this, error.toString(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void showArticles(List<CollectArticle> articles) {
        articleList.clear();
        articles.add(null); //添加进度条，列表数据中为null的为进度条的位置，在请求的数据尾部添加
        articleList.addAll(articles);
        collectionAdapter.notifyDataSetChanged();
    }

    @Override
    public void showMoreArticles(List<CollectArticle> articles) {
        int startPosition = articleList.size();
        articles.add(null); //添加进度条，列表数据中为null的为进度条的位置，在请求的数据尾部添加
        articleList.addAll(articles);
        collectionAdapter.notifyItemRangeInserted(startPosition, articles.size());
    }


    @Override
    public void hideLoading() {
        if (articleList.get(articleList.size()-1) == null) {
            articleList.remove(articleList.size()-1);
            collectionAdapter.notifyItemRemoved(articleList.size());
        }
    }
}
