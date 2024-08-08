package com.example.playandroid.view.fragment;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
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

import com.bumptech.glide.Glide;
import com.example.playandroid.R;
import com.example.playandroid.adapter.ProjectArticleRecyclerAdapter;
import com.example.playandroid.base.BaseFragment;
import com.example.playandroid.interf.contract.ProjectArticleContract;
import com.example.playandroid.entity.Project;
import com.example.playandroid.interf.datacallback.DataCallBackForArticleAdapter;
import com.example.playandroid.presenter.ProjectContentPresenter;
import com.example.playandroid.view.activity.ArticleDetailActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

public class ProjectContentFragment extends BaseFragment<ProjectContentPresenter> implements ProjectArticleContract.VP {

    private final static int UPDATE_PROJECT_ARTICLE = 1;

    private RecyclerView articleRecycleView;
    private FragmentActivity mActivity;

    private FrameLayout loadingLayout;
    private Context mContext;
//    private ProgressBar progressBar;

    private final List<Project> projectList = new ArrayList<>();

    private final Timer timer = new Timer();

    private int page = 1;

    private boolean over;

    private final int typeId;

    public ProjectContentFragment(int typeId) {
        this.typeId = typeId;
    }

    private ProjectArticleRecyclerAdapter articleRecyclerAdapter;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mActivity = requireActivity();
        mContext = context;
    }

    @Override
    public int getFragmentId() {
        return R.layout.project_content_fragment;
    }

    @Override
    public void initView() {
    }


    @Override
    public void initData() {
        Log.d("test111", "projectInit");
        requestProjectData(0, typeId);
    }

    public void showCollectResult(String msg) {
        Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.project_content_fragment, container, false);
        articleRecycleView = view.findViewById(R.id.project_article_rv);//每加载一次碎片就重新加载recyclerView的适配器，解决切换页面变为空白的问题
        loadingLayout = view.findViewById(R.id.load_layout);

//        progressBar = view.findViewById(R.id.project_progressBar);
        articleRecyclerAdapter = new ProjectArticleRecyclerAdapter(projectList, new DataCallBackForArticleAdapter() {
            @Override
            public void getLoveImg(ImageView loveImg, int articleId) {
                loveImg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (loveImg.isSelected()) {//爱心亮了

                            loveImg.setSelected(false);
                        } else {//爱心没亮
                            mPresenter.collectArticle(articleId);
                            loveImg.setSelected(true);
                        }
                    }
                });
            }
        });
        articleRecyclerAdapter.setOnRecyclerItemClickListener((position, mProjectArticleList) -> {
            Intent intent = new Intent(requireActivity(), ArticleDetailActivity.class);
            intent.setAction("sendArticleData");
            intent.putExtra("articleLink", mProjectArticleList.get(position).getLink());
            intent.putExtra("title", mProjectArticleList.get(position).getTitle());
            startActivity(intent);
        });

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        articleRecycleView.setLayoutManager(mLayoutManager);
        articleRecycleView.addItemDecoration(new DividerItemDecoration(mActivity,
                DividerItemDecoration.VERTICAL));//设置分界线
        articleRecycleView.setAdapter(articleRecyclerAdapter);

        articleRecycleView.addOnScrollListener(new RecyclerView.OnScrollListener() {//滑到底则加载更多数据
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                int lastPosition = -1;

                if (newState == RecyclerView.SCROLL_STATE_IDLE) {

                    RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();

                    assert layoutManager != null;
                    lastPosition = ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();

                    if (lastPosition == recyclerView.getLayoutManager().getItemCount() - 1) {
                        loadMoreProject(typeId);
                    }


                }
            }
        });

        return view;

    }


//    private final Handler handler = new Handler(Looper.getMainLooper()) {
//        @SuppressLint("NotifyDataSetChanged")
//        @Override
//        public void handleMessage(@NonNull Message msg) {
//            if (msg.what == UPDATE_PROJECT_ARTICLE) {
//                if (articleRecyclerAdapter != null) {
//                    articleRecyclerAdapter.notifyDataSetChanged();//刷新界面
//                }
//                progressDialog.dismiss();
//            }
//        }
//    };

    @Override
    public ProjectContentPresenter getPresenterInstance() {
        return new ProjectContentPresenter();
    }

    //加载更多文章数据
    public void loadMoreProject(int typeId) {
        if (!over) {
//            progressBar.setVisibility(View.VISIBLE);
            page++;
            requestProjectData(page, typeId);
        }

    }

    @Override
    public void requestProjectData(int page, int typeId) {
        mPresenter.requestProjectData(page, typeId);
    }

    @Override
    public void requestProjectDataResult(List<Project> projectList, boolean over) {
        this.over = over;
        if (articleRecyclerAdapter != null) {
            articleRecyclerAdapter.addProjectArticle(projectList);
        }
        if (loadingLayout != null) {
            loadingLayout.setVisibility(View.GONE);
        }
    }

    @Override
    public void onDestroyView() {
        Glide.with(this).clear(articleRecycleView);
        projectList.clear();
        articleRecycleView = null;
        articleRecyclerAdapter = null;
        loadingLayout = null;
        super.onDestroyView();
    }
}
