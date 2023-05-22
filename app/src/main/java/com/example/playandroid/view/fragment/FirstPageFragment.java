package com.example.playandroid.view.fragment;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;


import androidx.annotation.NonNull;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.playandroid.R;
import com.example.playandroid.adapter.BannerAdapter;
import com.example.playandroid.adapter.ArticleRecyclerAdapter;
import com.example.playandroid.base.BaseFragment;
import com.example.playandroid.interf.datacallback.DataCallBackForImage;
import com.example.playandroid.interf.contract.FirstPageContract;
import com.example.playandroid.entity.Banner;
import com.example.playandroid.entity.Article;
import com.example.playandroid.presenter.FirstPagePresenter;
import com.example.playandroid.util.WebUtil;
import com.example.playandroid.view.activity.ArticleDetailActivity;
import com.example.playandroid.view.activity.BottomActivity;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class FirstPageFragment extends BaseFragment<FirstPagePresenter> implements FirstPageContract.VP {

    private final int SET_IMG = 1;
    private final int MSG_RECYCLE_VIEW = 0;
    private final int TOP_ARTICLE_READY = 2;
    private final int NORMAL_ARTICLE_READY = 3;


    private int flag = 0;
    private int flag1 = 0;
    private boolean isAutoBanner = true;

    private FragmentActivity mActivity;
    private ProgressBar progressBar;
    private BannerAdapter bannerAdapter;
    private ViewPager bannerViewPager;

    private ProgressDialog progressDialog;

    private NestedScrollView nestedScrollView;
    private RecyclerView articleRecyclerView;
    private ArticleRecyclerAdapter articleRecyclerAdapter;

    private int page = 0;
    private List<Banner> bannerList;
    private List<ImageView> imageViewList;

    private List<Article> finalArticleList;

    private int isStop = 0;

    private int i = 0;
    private int j = 0;
    private int k = 0;
    private List<Bitmap> bitmapList = new ArrayList<>();
    private List<String> urlList = new ArrayList<>();
    private List<String> titleList = new ArrayList<>();


    private static final String TAG = "FirstPageFragment";


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mActivity = requireActivity();
    }


    @Override
    public int getFragmentId() {
        return R.layout.first_page_fragment;
    }


    /**
     * 初始化数据
     */
    @Override
    public void initData() {
        imageViewList = new ArrayList<>();
        finalArticleList = new ArrayList<>();
    }

    /**
     * 初始化控件
     */
    @Override
    public void initView() {
        progressDialog = new ProgressDialog(requireActivity());
        progressDialog.setMessage("正在努力加载页面");
        progressDialog.setCancelable(false);
        progressDialog.show();
        nestedScrollView = root.findViewById(R.id.fp_scroll_view);
        bannerViewPager = requireActivity().findViewById(R.id.banner);
        progressBar = requireActivity().findViewById(R.id.progressBar);
        articleRecyclerView = root.findViewById(R.id.first_page_recycler);
        bannerAdapter = new BannerAdapter(imageViewList);
        requestBannerData();//请求banner数据
        requestTopArticleData();//请求置顶文章数据
        requestArticleData(0);//请求普通文章数据
        bannerViewPager.setAdapter(bannerAdapter);
    }


    @Override
    public FirstPagePresenter getPresenterInstance() {
        return new FirstPagePresenter();
    }

    /**
     * 设置首页文章列表展示
     */
    public void setArticleRecyclerView() {
        //设置整屏不能滚动
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(root.getContext(), LinearLayoutManager.VERTICAL, false) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        articleRecyclerView.setLayoutManager(mLayoutManager);
        articleRecyclerView.addItemDecoration(new DividerItemDecoration(root.getContext(),
                DividerItemDecoration.VERTICAL));//设置分界线
        articleRecyclerAdapter = new ArticleRecyclerAdapter(finalArticleList);
        articleRecyclerView.setAdapter(articleRecyclerAdapter);
        nestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (scrollY == (v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight())) {
                    //滑动到底部加载数据
                    loadMoreArticle();
                }
            }
        });
    }

    //加载更多文章数据
    public void loadMoreArticle() {
        progressBar.setVisibility(View.VISIBLE);
        page++;
        requestArticleData(page);
    }


    /**
     * 消息处理器
     */
    private Handler handler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(@NonNull Message message) {
            switch (message.what) {
                case TOP_ARTICLE_READY:
                    flag1++;
                case NORMAL_ARTICLE_READY:
                    flag++;
                    if (flag == 1 && flag1 == 1) {//说明是首次加载文章数据
                        flag1 = 0;
                        progressBar.setVisibility(View.VISIBLE);
                        setArticleRecyclerView();
                    }

                    mActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (articleRecyclerAdapter != null) {
                                articleRecyclerAdapter.notifyDataSetChanged();//刷新页面
                                progressDialog.dismiss();
                            }
                        }
                    });
                    break;

                default:
                    break;
            }
        }
    };


    /**
     * 接收轮播图自动轮播信号
     */
    @SuppressLint("HandlerLeak")
    public Handler mHandler = new Handler(Looper.getMainLooper()) {
        @SuppressLint("NotifyDataSetChanged")
        public void handleMessage(Message msg) {
            if (msg.what == MSG_RECYCLE_VIEW) {
                int count = 3;
                int index = bannerViewPager.getCurrentItem();
                index = (index + 1) % count;
                bannerViewPager.setCurrentItem(index);
                if (isAutoBanner) {//循环发送
                    mHandler.sendEmptyMessageDelayed(MSG_RECYCLE_VIEW, 2000);
                }
            } else {
                bannerAdapter.notifyDataSetChanged();
            }
        }
    };

    public Handler mHandlerForImg = new Handler(Looper.getMainLooper()) {
        @SuppressLint("NotifyDataSetChanged")
        public void handleMessage(Message msg) {
            if (msg.what == SET_IMG) {
                ImageView imageView = new ImageView(root.getContext());

                //ImageView imageView = root.findViewById(R.id.vp_img);
                //使用这个会报异常：The specified child already has a parent.
                //               You must call removeView() on the child's parent first.

                imageView.setImageBitmap(bitmapList.get(i++));
                //Log.d("test222", bitmap.toString());
                imageView.setOnClickListener(new View.OnClickListener() {//设置轮播图点击事件
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(root.getContext(), ArticleDetailActivity.class);
                        intent.setAction("sendArticleData");
                        intent.putExtra("articleLink", urlList.get(j++));
                        intent.putExtra("title", titleList.get(k++));
                        root.getContext().startActivity(intent);
                    }
                });
                bannerViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                    @Override
                    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                    }

                    @Override
                    public void onPageSelected(int position) {
                    }

                    @Override
                    public void onPageScrollStateChanged(int state) {
                        if (state == 1) {
                            mHandler.removeCallbacksAndMessages(null);
                            isStop = 1;
                        } else if (state == 0 && isStop == 1) {
                            mHandler.sendEmptyMessageDelayed(MSG_RECYCLE_VIEW, 2000);
                            isStop = 0;
                        }
                    }
                });

                imageViewList.add(imageView);
                bannerAdapter.notifyDataSetChanged();//必须设置这个来通知适配器数据变化了，要进行更新
                if (imageViewList.size() == 3) {
                    mHandler.sendEmptyMessageDelayed(MSG_RECYCLE_VIEW, 2000);
                }
            }
        }
    };

    public void setImageData(String imgUrl, String articleLink, String title) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                WebUtil.getImageData(imgUrl, new DataCallBackForImage() {
                    @SuppressLint("ClickableViewAccessibility")
                    @Override
                    public void onSuccess(InputStream data) {
                        bitmapList.add(BitmapFactory.decodeStream(data));
                        urlList.add(articleLink);
                        titleList.add(title);
                        Message message = new Message();
                        message.what = SET_IMG;
                        mHandlerForImg.sendMessage(message);

                    }

                    @Override
                    public void onFailure(Exception e) {
                        Log.e("图片数据请求：", "网络请求错误/" + e);
                    }
                });

            }

        }).start();


    }


    @Override
    public void requestBannerData() {
        mPresenter.requestBannerData();
    }

    @Override
    public void requestBannerDataResult(List<Banner> bannerList) {
        this.bannerList = bannerList;
        for (Banner banner : bannerList) {
            setImageData(banner.getImagePath(), banner.getUrl(), banner.getTitle());
        }
    }

    @Override
    public void requestArticleData(int page) {
        mPresenter.requestArticleData(page);
    }

    @Override
    public void requestArticleDataResult(List<Article> articleList) {
        finalArticleList.addAll(articleList);
        Message msg = new Message();//发送普通文章数据就绪的信号
        msg.what = NORMAL_ARTICLE_READY;
        handler.sendMessage(msg);
    }

    @Override
    public void requestTopArticleData() {
        mPresenter.requestTopArticleData();
    }

    @Override
    public void requestTopArticleDataResult(List<Article> topArticleList) {
        finalArticleList.addAll(topArticleList);
        Message msg = new Message();//发送置顶文章数据就绪的信号
        msg.what = TOP_ARTICLE_READY;
        handler.sendMessage(msg);
    }


}
