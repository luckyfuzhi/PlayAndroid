package com.example.playandroid.base;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.playandroid.view.activity.ActivityCollector;

public abstract class BaseActivity<P extends BasePresenter> extends AppCompatActivity implements View.OnClickListener{

    public P mPresenter;

    public Bundle mSavedInstanceState;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentViewId());
        mSavedInstanceState = savedInstanceState;

        //隐藏状态栏
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        } else {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }

        ActivityCollector.addActivity(this);
        initView();
        initData();
        initListener();
        mPresenter = getPresenterInstance();
        if(mPresenter != null) {
            mPresenter.bindView(this);
        }
    }

    /**
     * 初始化界面
     */
    public abstract void initView();

    /**
     * 初始化数据
     */
    public abstract void initData();

    /**
     * 初始化监听器
     */
    public abstract void initListener();

    /**
     * 获取当前子活动id
     */
    public abstract int getContentViewId();

    /**
     *  获取Presenter实例
     * @return Presenter实例
     */
    public abstract P getPresenterInstance();

    /**
     * 活动销毁时调用
     */
    public abstract void destroy();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        destroy();
        ActivityCollector.removeActivity(this);
    }

    /**
     * 异常时调用
     * @param error ERROR对象
     * @param throwable 异常对象
     * @param <ERROR> Error
     */
    public abstract <ERROR extends Object> void responseError(ERROR error, Throwable throwable);

}
