package com.example.playandroid.view.fragment;

import android.annotation.SuppressLint;
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
import android.widget.ImageView;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.playandroid.R;
import com.example.playandroid.adapter.BannerAdapter;
import com.example.playandroid.base.BaseFragment;
import com.example.playandroid.contract.DataCallBackForImage;
import com.example.playandroid.contract.FirstPageContract;
import com.example.playandroid.entity.Banner;
import com.example.playandroid.presenter.FirstPagePresenter;
import com.example.playandroid.util.WebUtil;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class FirstPageFragment extends BaseFragment<FirstPagePresenter> implements FirstPageContract.VP {

    private final int MSG_UPDATE_VIEW = 1;

    private final int MSG_RECYCLE_VIEW = 0;

    private BannerAdapter bannerAdapter;
    private ViewPager bannerViewPager;

    private RecyclerView articleRecyclerView;
    private List<Banner> bannerList;
    private List<ImageView> imageViewList;

    private Bitmap bitmap;


    private static final String TAG = "FirstPageFragment";

    @Override
    public int getFragmentId() {
        return R.layout.first_page_fragment;
    }


    /**
     * 初始化控件
     */
    @Override
    public void initView() {
        imageViewList = new ArrayList<>();
        bannerViewPager = requireActivity().findViewById(R.id.banner);
//        imageView = requireActivity().findViewById(R.id.vp_img);
        requestBannerData();
        bannerAdapter = new BannerAdapter(imageViewList);
        bannerViewPager.setAdapter(bannerAdapter);

    }

    /**
     * 初始化数据
     */
    @Override
    public void initData() {

    }

    @Override
    public FirstPagePresenter getPresenterInstance() {
        return new FirstPagePresenter();
    }

    /**
     * 接收设置图片信息的信号
     */
    private Handler handler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(@NonNull Message message) {
            switch (message.what) {
                case MSG_UPDATE_VIEW:
                    ImageView imageView = new ImageView(root.getContext());
                    //ImageView imageView = root.findViewById(R.id.vp_img);
                    //使用这个会报异常：The specified child already has a parent.
                    //               You must call removeView() on the child's parent first.
                    imageView.setImageBitmap(bitmap);
                    imageViewList.add(imageView);
                    bannerAdapter.notifyDataSetChanged();
                    if (imageViewList.size() == 3) {
                        mHandler.sendEmptyMessageDelayed(MSG_RECYCLE_VIEW, 2000);
                    }
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
            if (msg.what == 0) {
                int count = 3;
                int index = bannerViewPager.getCurrentItem();
                index = (index + 1) % count;
                bannerViewPager.setCurrentItem(index);
                //循环发送
                mHandler.sendEmptyMessageDelayed(0, 2000);
            } else {
                bannerAdapter.notifyDataSetChanged();
            }
        }
    };

    public void parseImageData(String imgUrl) {
        new Thread(new Runnable() {
            @Override
            public void run() {

                WebUtil.getImageData(imgUrl, new DataCallBackForImage() {
                    @Override
                    public void onSuccess(InputStream data) {
                        bitmap = BitmapFactory.decodeStream(data);
                        //Log.d(TAG, bitmap.toString());
                        Message msg = new Message();
                        msg.what = MSG_UPDATE_VIEW;//消息发送的标志
                        handler.sendMessage(msg);
                    }

                    @Override
                    public void onFailure(Exception e) {
                    }
                });

//                Log.d(TAG, "测试 ");
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
            parseImageData(banner.getImagePath());
        }
    }

}
