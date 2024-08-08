package com.example.playandroid.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
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

    private FrameLayout loadingLayout;

    private List<Article> mArticleList = new ArrayList<>();

    private RecyclerView articleRecyclerView;

    private ArticleRecyclerAdapter articleRecyclerAdapter;

    public void showMsgResult(String msg) {
        Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show();
    }
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
        loadingLayout = view.findViewById(R.id.load_layout);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        articleRecyclerView.setLayoutManager(mLayoutManager);
        articleRecyclerView.addItemDecoration(new DividerItemDecoration(mActivity,
                DividerItemDecoration.VERTICAL));//设置分界线

        articleRecyclerAdapter = new ArticleRecyclerAdapter(mArticleList, new DataCallBackForArticleAdapter() {
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
    }

    @Override
    public void initData() {
        Log.d("test111", "ksInit");
        requestArticleData(0, typeId);
    }

    //加载更多文章数据
    public void loadMoreArticle(int typeId) {
        page++;
        requestArticleData(page, typeId);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        loadingLayout = null;
        articleRecyclerView.setAdapter(null);
        articleRecyclerView = null;
    }

    @Override
    public KsChildContentPresenter getPresenterInstance() {
        return new KsChildContentPresenter();
    }

//    private Handler handler = new Handler(Looper.getMainLooper()) {
//        @Override
//        public void handleMessage(@NonNull Message msg) {
//            if (msg.what == UPDATE_ARTICLE) {
//                loadingLayout.setVisibility(View.GONE);
//                articleRecyclerAdapter.notifyItemInserted(dataSize-1);//刷新界面
//            }
//        }
//    };

    @Override
    public void requestArticleData(int page, int typeId) {
        mPresenter.requestArticleData(page, typeId);
    }

    @Override
    public void requestArticleDataResult(List<Article> articleList) {
        if (articleRecyclerAdapter != null) {
            articleRecyclerAdapter.addArticle(articleList);//刷新界面
        }
        if (loadingLayout != null) loadingLayout.setVisibility(View.GONE);
//        if (handler != null) {
//            Message message = new Message();
//            message.what = UPDATE_ARTICLE;
//            handler.sendMessage(message);
//        }
    }
}
