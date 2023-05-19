package com.example.playandroid.view.fragment;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.playandroid.R;
import com.example.playandroid.adapter.FPArticleRecyclerAdapter;
import com.example.playandroid.adapter.ProjectArticleRecyclerAdapter;
import com.example.playandroid.base.BaseFragment;
import com.example.playandroid.contract.DataCallBackForBitmap;
import com.example.playandroid.contract.DataCallBackForImage;
import com.example.playandroid.contract.ProjectArticleContract;
import com.example.playandroid.entity.Project;
import com.example.playandroid.presenter.ProjectContentPresenter;
import com.example.playandroid.util.WebUtil;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class ProjectContentFragment extends BaseFragment<ProjectContentPresenter> implements ProjectArticleContract.VP {

    private final static int SET_PROJECT_ARTICLE = 1;
    private boolean isFirstLord = true;

    private FragmentActivity mActivity;
    private Context mContext;
    private RecyclerView articleRecycleView;
    private ProgressBar progressBar;

    private List<Project> projectList = new ArrayList<>();
    private List<Bitmap> bitmapList = new ArrayList<>();

    private int page = 0;

    private int typeId;

    public ProjectContentFragment(int typeId) {
        this.typeId = typeId;
    }

    private ProjectArticleRecyclerAdapter articleRecyclerAdapter = new ProjectArticleRecyclerAdapter(projectList, bitmapList);

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
        requestProjectData(0, typeId);
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.project_content_fragment, container, false);

        articleRecycleView = view.findViewById(R.id.project_article_rv);//每加载一次碎片就重新加载recyclerView的适配器，解决切换页面变为空白的问题
        progressBar = view.findViewById(R.id.project_progressBar);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        articleRecycleView.setLayoutManager(mLayoutManager);
        articleRecycleView.addItemDecoration(new DividerItemDecoration(mActivity,
                DividerItemDecoration.VERTICAL));//设置分界线
        articleRecycleView.setAdapter(articleRecyclerAdapter);

        articleRecycleView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                int lastPosition = -1;

                if (newState == RecyclerView.SCROLL_STATE_IDLE) {

                    RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();

                    lastPosition = ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();

                    if (lastPosition == recyclerView.getLayoutManager().getItemCount() - 1) {
                        loadMoreProject(typeId);
                    }
                }
            }
        });
        return view;

    }


    private Handler handler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what) {
                case SET_PROJECT_ARTICLE:
                    if (isFirstLord) {//第一次加载则设置适配器
                        isFirstLord = false;
                        if (projectList.size() != 0) {
//                            LinearLayoutManager mLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false) ;
//                            articleRecyclerAdapter = new ProjectArticleRecyclerAdapter(projectList, mActivity);
//                            articleRecycleView.setLayoutManager(mLayoutManager);
//                            articleRecycleView.addItemDecoration(new DividerItemDecoration(root.getContext(),
//                                    DividerItemDecoration.VERTICAL));//设置分界线
//                            articleRecycleView.setAdapter(articleRecyclerAdapter);
//
//                            articleRecycleView.addOnScrollListener(new RecyclerView.OnScrollListener() {
//                                @Override
//                                public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
//                                    int lastPosition = -1;
//                                    if(newState == RecyclerView.SCROLL_STATE_IDLE){
//                                        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
//                                        lastPosition = ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
//                                        if (lastPosition == recyclerView.getLayoutManager().getItemCount() - 1){
//                                            loadMoreProject(typeId);
//                                        }
//                                    }
//                                }
//                            });
                        }
                    }
                    mActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            System.out.println(projectList.size());
                            articleRecyclerAdapter.notifyDataSetChanged();//刷新界面
                        }
                    });
                    break;
                default:
                    break;
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
        page++;
        requestProjectData(page, typeId);
    }

    @Override
    public void requestProjectData(int page, int typeId) {
        mPresenter.requestProjectData(page, typeId);
    }

    @Override
    public void requestProjectDataResult(List<Project> projectList) {
        for (int i = 0; i < projectList.size(); i++) {
            WebUtil.getImageData(projectList.get(i).getImgLink(), new DataCallBackForBitmap() {
                @Override
                public void onSuccess(Bitmap data) {
                    mActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            bitmapList.add(data);
                            articleRecyclerAdapter.notifyItemChanged(bitmapList.size() - 1);//刷新界面
                            //articleRecyclerAdapter.notifyDataSetChanged();
                        }
                    });
                }

                @Override
                public void onFailure(Exception e) {
                    e.printStackTrace();
                    Log.e("requestPDataResult", "onFailure:获取图片数据失败/ " + e);
                }
            });
        }
        this.projectList.addAll(projectList);
        Message message = new Message();
        message.what = SET_PROJECT_ARTICLE;
        handler.sendMessage(message);

    }

}