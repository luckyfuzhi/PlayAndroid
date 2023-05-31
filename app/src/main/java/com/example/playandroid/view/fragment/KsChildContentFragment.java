package com.example.playandroid.view.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.playandroid.R;
import com.example.playandroid.adapter.ArticleRecyclerAdapter;
import com.example.playandroid.base.BaseFragment;
import com.example.playandroid.interf.contract.KsChildContract;
import com.example.playandroid.entity.Article;
import com.example.playandroid.interf.datacallback.DataCallBackForArticleAdapter;
import com.example.playandroid.presenter.KsChildContentPresenter;

import java.util.ArrayList;
import java.util.List;

public class KsChildContentFragment extends BaseFragment<KsChildContentPresenter> implements KsChildContract.VP {

    private final static int UPDATE_ARTICLE = 1;

    private Context mContext;
    private FragmentActivity mActivity;

    private List<Article> mArticleList = new ArrayList<>();

    private ProgressDialog progressDialog;
    private RecyclerView articleRecyclerView;
    private ProgressBar progressBar;

    private ArticleRecyclerAdapter articleRecyclerAdapter = new ArticleRecyclerAdapter(mArticleList, new DataCallBackForArticleAdapter() {
        @Override
        public void getLoveImg(ImageView loveImg) {
            loveImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (loveImg.isSelected()){//爱心亮了

                        loveImg.setSelected(false);
                    } else {//爱心没亮

                        loveImg.setSelected(true);
                    }
                }
            });
        }
    });

    private int page = 0;


    private int typeId;

    public KsChildContentFragment(int typeId) {
        this.typeId = typeId;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
        mActivity = requireActivity();
    }

    @Override
    public int getFragmentId() {
        return R.layout.knowledge_system_content_fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.knowledge_system_content_fragment, container, false);
        articleRecyclerView = view.findViewById(R.id.ks_article_rv);
        progressBar = view.findViewById(R.id.ks_progressBar);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        articleRecyclerView.setLayoutManager(mLayoutManager);
        articleRecyclerView.addItemDecoration(new DividerItemDecoration(mActivity,
                DividerItemDecoration.VERTICAL));//设置分界线
        articleRecyclerView.setAdapter(articleRecyclerAdapter);

        articleRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {//滑到底则加载更多数据
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                int lastPosition = -1;

                if (newState == RecyclerView.SCROLL_STATE_IDLE) {

                    RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();

                    lastPosition = ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();

                    if (lastPosition == recyclerView.getLayoutManager().getItemCount() - 1) {
                        loadMoreArticle(typeId);
                    }
                }
            }
        });

        return view;
    }

    @Override
    public void initView() {
        progressDialog = new ProgressDialog(requireContext());
        progressDialog.setMessage("正在努力加载中");
        progressDialog.setCancelable(false);
    }

    @Override
    public void initData() {
        requestArticleData(0, typeId);
    }

    //加载更多文章数据
    public void loadMoreArticle(int typeId) {
        progressDialog.show();
        page++;
        requestArticleData(page, typeId);
    }

    @Override
    public KsChildContentPresenter getPresenterInstance() {
        return new KsChildContentPresenter();
    }

    private Handler handler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what) {
                case UPDATE_ARTICLE:
                    articleRecyclerAdapter.notifyDataSetChanged();//刷新界面
                    progressDialog.dismiss();
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    public void requestArticleData(int page, int typeId) {
        mPresenter.requestArticleData(page, typeId);
    }

    @Override
    public void requestArticleDataResult(List<Article> articleList) {
        mArticleList.addAll(articleList);
        Message message = new Message();
        message.what = UPDATE_ARTICLE;
        handler.sendMessage(message);
    }
}
