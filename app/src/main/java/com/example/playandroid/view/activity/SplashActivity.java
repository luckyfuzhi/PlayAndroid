package com.example.playandroid.view.activity;

import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.playandroid.R;
import com.example.playandroid.base.BaseActivity;
import com.example.playandroid.base.BasePresenter;

/**
 *  启动画面
 */
public class SplashActivity extends BaseActivity {

    public static int SPLASH_DISPLAY_LENGTH = 1000;
    @Override
    public void initView() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, BottomActivity.class);
                startActivity(intent);
                destroy();
            }
        }, SPLASH_DISPLAY_LENGTH);
    }

    @Override
    public void initData() {
    }

    @Override
    public void initListener() {
    }

    @Override
    public int getContentViewId() {
        return R.layout.splash_layout;
    }

    @Override
    public BasePresenter getPresenterInstance() {
        return null;
    }

    @Override
    public void destroy() {
        SplashActivity.this.finish();
    }

    @Override
    public void responseError(Object o, Throwable throwable) {
        Toast.makeText(this, "打开应用失败", Toast.LENGTH_SHORT).show();
        Log.e("SplashActivity", "responseError: 打开应用失败" + o, null);
        throwable.printStackTrace();
    }

    @Override
    public void onClick(View view) {

    }
}
