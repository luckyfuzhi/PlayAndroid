package com.example.playandroid.view.fragment;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
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
import com.example.playandroid.adapter.ProjectArticleRecyclerAdapter;
import com.example.playandroid.base.BaseFragment;
import com.example.playandroid.interf.datacallback.DataCallBackForArticleAdapter;
import com.example.playandroid.interf.datacallback.DataCallBackForBitmap;
import com.example.playandroid.interf.contract.ProjectArticleContract;
import com.example.playandroid.entity.Project;
import com.example.playandroid.presenter.ProjectContentPresenter;
import com.example.playandroid.util.WebUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

public class ProjectContentFragment extends BaseFragment<ProjectContentPresenter> implements ProjectArticleContract.VP {

    private final static int UPDATE_PROJECT_ARTICLE = 1;

    private ProgressDialog progressDialog;
    private FragmentActivity mActivity;
    private Context mContext;
    private ProgressBar progressBar;

    private final List<Project> projectList = new ArrayList<>();
    private final List<Bitmap> mBitmapList = new ArrayList<>();

    private final Timer timer = new Timer();

    private int page = 0;

    private final int typeId;

    public ProjectContentFragment(int typeId) {
        this.typeId = typeId;
    }

    private final ProjectArticleRecyclerAdapter articleRecyclerAdapter = new ProjectArticleRecyclerAdapter(projectList, mBitmapList, new DataCallBackForArticleAdapter() {
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
        progressDialog = new ProgressDialog(requireContext());
        progressDialog.setMessage("正在努力加载中");
        progressDialog.setCancelable(false);
    }


    @Override
    public void initData() {
        requestProjectData(0, typeId);
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.project_content_fragment, container, false);
        RecyclerView articleRecycleView = view.findViewById(R.id.project_article_rv);//每加载一次碎片就重新加载recyclerView的适配器，解决切换页面变为空白的问题

        progressBar = view.findViewById(R.id.project_progressBar);

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


    private final Handler handler = new Handler(Looper.getMainLooper()) {
        @SuppressLint("NotifyDataSetChanged")
        @Override
        public void handleMessage(@NonNull Message msg) {
            if (msg.what == UPDATE_PROJECT_ARTICLE) {
                articleRecyclerAdapter.notifyDataSetChanged();//刷新界面
                progressDialog.dismiss();
                progressBar.setVisibility(View.GONE);
            }
        }
    };

    @Override
    public ProjectContentPresenter getPresenterInstance() {
        return new ProjectContentPresenter();
    }

    //加载更多文章数据
    public void loadMoreProject(int typeId) {
        progressBar.setVisibility(View.VISIBLE);
        progressDialog.show();
        page++;
        requestProjectData(page, typeId);
    }

    @Override
    public void requestProjectData(int page, int typeId) {
        mPresenter.requestProjectData(page, typeId);
    }

    @Override
    public void requestProjectDataResult(List<Project> projectList) {

        this.projectList.addAll(projectList);
        Message message = new Message();
        message.what = UPDATE_PROJECT_ARTICLE;
        handler.sendMessage(message);
    }

    @Override
    public void requestProjectImgResult(Bitmap bitmap) {
        mBitmapList.add(bitmap);
        mActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                articleRecyclerAdapter.notifyItemChanged(mBitmapList.size() - 1);//刷新界面
            }
        });
    }

}
