package com.example.playandroid.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.playandroid.R;

public abstract class BaseFragment<P extends BasePresenterForFragment> extends Fragment implements View.OnClickListener {

    public View root;

    public P mPresenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = getPresenterInstance();
        if (mPresenter != null) {
            mPresenter.bindView(this);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(getFragmentId(), container, false);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initData();
        initView();
    }

    public abstract int getFragmentId();

    public abstract void initView();

    public abstract void initData();

    public abstract P getPresenterInstance();

    @Override
    public void onClick(View view) {
    }
}
