package com.example.playandroid.view.fragment;

import android.annotation.SuppressLint;
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
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.playandroid.R;
import com.example.playandroid.adapter.ArticleRecyclerAdapter;
import com.example.playandroid.base.BaseFragment;
import com.example.playandroid.entity.Article;
import com.example.playandroid.interf.contract.SearchResultContract;
import com.example.playandroid.interf.datacallback.DataCallBackForArticleAdapter;
import com.example.playandroid.presenter.SearchResultPresenter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SearchResultFragment extends BaseFragment<SearchResultPresenter> implements SearchResultContract.VP {

    private final static int UPDATE_RESULT_ARTICLE = 1;

    private RecyclerView resultRecyclerView;
    private ProgressBar progressBar;
    private TextView noResultTV;

    private ImageView noResultImg;

    private RelativeLayout noResult;

    private List<Article> articleList = new ArrayList<>();

    private Map<String, String> paramMap = new HashMap<>();

    private ProgressDialog progressDialog;

    private FragmentActivity mActivity;
    private Context mContext;

    private int page = 0;

    private ArticleRecyclerAdapter resultRecyclerAdapter = new ArticleRecyclerAdapter(articleList, new DataCallBackForArticleAdapter() {
        @Override
        public void getLoveImg(ImageView loveImg, int articleId) {
            loveImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (loveImg.isSelected()){//爱心亮了

                        loveImg.setSelected(false);
                    } else {//爱心没亮
                        mPresenter.collectArticle(articleId);
                        loveImg.setSelected(true);
                    }
                }
            });
        }
    });
    public void showCollectResult(String msg) {
        Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show();
    }

    public SearchResultFragment(String key) {
        paramMap.put("k", key);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mActivity = requireActivity();
        mContext = context;
    }

    @Override
    public int getFragmentId() {
        return R.layout.search_result_fragment;
    }

    @Override
    public void initData() {
        requestArticleData(0, paramMap);
    }

    @Override
    public void initView() {
    }

    @SuppressLint("MissingInflatedId")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.search_result_fragment, container, false);
        progressDialog = new ProgressDialog(mContext);
        progressDialog.setMessage("正在努力加载文章数据");
        progressDialog.setCancelable(false);
        progressDialog.show();
        resultRecyclerView = view.findViewById(R.id.search_result_rv);
        progressBar = view.findViewById(R.id.search_result_progressBar);
        noResultTV = view.findViewById(R.id.no_result_text);
        noResultImg = view.findViewById(R.id.no_result_img);
        noResult = view.findViewById(R.id.no_result_relative);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        resultRecyclerView.setLayoutManager(mLayoutManager);
        resultRecyclerView.addItemDecoration(new DividerItemDecoration(mActivity,
                DividerItemDecoration.VERTICAL));//设置分界线
        resultRecyclerView.setAdapter(resultRecyclerAdapter);

        resultRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {//滑到底则加载更多数据
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                int lastPosition = -1;

                if (newState == RecyclerView.SCROLL_STATE_IDLE) {

                    RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();

                    lastPosition = ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();

                    if (lastPosition == recyclerView.getLayoutManager().getItemCount() - 1) {
                        loadMoreArticle(paramMap);
                    }


                }
            }
        });

        return view;
    }

    //加载更多文章数据
    public void loadMoreArticle(Map<String, String> paramMap) {
        progressDialog.show();
        page++;
        requestArticleData(page, paramMap);
    }

    @Override
    public SearchResultPresenter getPresenterInstance() {
        return new SearchResultPresenter();
    }

    @Override
    public void requestArticleData(int page, Map<String, String> paramMap) {
        mPresenter.requestArticleData(page, paramMap);
    }

    private final Handler handler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what) {
                case UPDATE_RESULT_ARTICLE:
                    resultRecyclerAdapter.notifyDataSetChanged();//刷新界面
                    progressDialog.dismiss();
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    public void requestArticleDataResult(List<Article> articleList) {
        this.articleList.addAll(articleList);
        if (articleList.size() != 0) {
            Message message = new Message();
            message.what = UPDATE_RESULT_ARTICLE;
            handler.sendMessage(message);
        } else {
            progressDialog.dismiss();
            noResult.setVisibility(View.VISIBLE);

        }
    }
}
