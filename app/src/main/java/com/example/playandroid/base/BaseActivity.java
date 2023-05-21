package com.example.playandroid.base;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
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
        mPresenter = getPresenterInstance();


        WindowManager.LayoutParams lp = this.getWindow().getAttributes();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            lp.layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES;
        }
        this.getWindow().setAttributes(lp);


        ActivityCollector.addActivity(this);
        initView();
        initData();
        initListener();

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
