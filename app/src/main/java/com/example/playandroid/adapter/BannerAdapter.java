package com.example.playandroid.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import java.util.List;

/**
 *  banner轮播图的PagerView的适配器
 */
public class BannerAdapter extends PagerAdapter {

    private List<ImageView> mImageViewList;

    public BannerAdapter(List<ImageView> mImageViewList) {
        this.mImageViewList = mImageViewList;
    }

    @Override
    public int getCount() {
        return mImageViewList == null ? 0 : mImageViewList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        ImageView imageView = mImageViewList.get(position);
        container.addView(imageView);
        return imageView;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
