package com.example.playandroid.view.activity;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.playandroid.MyApplication;
import com.example.playandroid.R;
import com.example.playandroid.base.BaseActivity;
import com.example.playandroid.base.BasePresenter;
import com.example.playandroid.view.fragment.BottomDrawerLoginFragment;
import com.example.playandroid.view.fragment.FirstPageFragment;
import com.example.playandroid.view.fragment.KnowledgeSystemFragment;
import com.example.playandroid.view.fragment.ProjectFragment;
import com.example.playandroid.view.fragment.SucceedLoginFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarMenuView;
import com.google.android.material.navigation.NavigationBarView;

import java.lang.reflect.Field;


/**
 * 程序主类，包含各个模块功能的切换入口
 */
public class BottomActivity extends BaseActivity {
    private Button searchButton;

    private Button menuButton;


    private DrawerLayout mDrawerLayout;

    private FragmentManager fragmentManager;
    private Fragment currentFragment;
    private FirstPageFragment firstPageFragment;
    private KnowledgeSystemFragment knowledgeSystemFragment;
    private ProjectFragment projectFragment;

    private BottomNavigationView bottomNavigationView;

    @Override
    public void initView() {
        bottomNavigationView = findViewById(R.id.navigation);
        currentFragment = new FirstPageFragment();
        fragmentManager = getSupportFragmentManager();
        searchButton = findViewById(R.id.main_search_button);
        menuButton = findViewById(R.id.menu_button);
        mDrawerLayout = findViewById(R.id.bottom_drawer_layout);

        bottomNavigationView.setOnItemSelectedListener(listen);


        if (getIntent().getBooleanExtra("isSuccessLogin", false)) {//如果成功登录则切换侧滑栏视图
            replaceFragment(new SucceedLoginFragment(getIntent().getStringExtra("username")));
        } else {
            replaceFragment(new BottomDrawerLoginFragment());
        }

        if (getIntent().getBooleanExtra("exitLogin", false)) {//如果退出登录则切换侧滑栏视图
            replaceFragment(new BottomDrawerLoginFragment());

            //删除SharePreference中的cookie数据
            SharedPreferences sharedPreferences = getSharedPreferences("cookies_prefs", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.clear();
            editor.apply();

            Toast.makeText(this, "已退出登录", Toast.LENGTH_SHORT).show();
        }

        if (getIntent().getBooleanExtra("isAutoLogin", false)) {//如果自动登录成功就切换侧滑栏视图
            String userName = getIntent().getStringExtra("userName");
            replaceFragment(new SucceedLoginFragment(userName));
        }
    }

    private final NavigationBarView.OnItemSelectedListener listen = new NavigationBarView.OnItemSelectedListener() {
        @SuppressLint("NonConstantResourceId")
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            int id = item.getItemId();
            if (id == R.id.navigation_home) {
                showFragment(firstPageFragment);
            } else if (id == R.id.navigation_knowledge) {
                showFragment(knowledgeSystemFragment);
            } else if (id == R.id.navigation_project) {
                showFragment(projectFragment);
            } else {
                showFragment(firstPageFragment);
            }

            return true;
        }
    };

    @Override
    public void initData() {
        if (mSavedInstanceState != null) {
            firstPageFragment = (FirstPageFragment) fragmentManager.findFragmentByTag(FirstPageFragment.class.getName());
            knowledgeSystemFragment = (KnowledgeSystemFragment) fragmentManager.findFragmentByTag(KnowledgeSystemFragment.class.getName());
            projectFragment = (ProjectFragment) fragmentManager.findFragmentByTag(ProjectFragment.class.getName());

            fragmentManager.beginTransaction().show(firstPageFragment).hide(knowledgeSystemFragment)
                    .hide(projectFragment).commit();

            currentFragment = firstPageFragment;//记录当前显示的Fragment

        } else { //正常启动时调用
            firstPageFragment = new FirstPageFragment();
            knowledgeSystemFragment = new KnowledgeSystemFragment();
            projectFragment = new ProjectFragment();

            showFragment(firstPageFragment);
        }

    }

    @Override
    public void initListener() {
        searchButton.setOnClickListener(this);
        menuButton.setOnClickListener(this);
    }

    @Override
    public void onBackPressed() {//重写back点击方法，防止点击back之后已登录状态又变回未登录状态
        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setTitle("退出应用")
                .setMessage("确定要退出玩安卓吗？")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 点击确定按钮，退出应用
                        ActivityCollector.finishAll();
                    }
                })
                .setNegativeButton("取消", null)
                .show();
        Button positiveButton = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
        if (positiveButton != null) {
            positiveButton.setTextColor(Color.BLACK);
        }
        Button negativeButton = alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE);
        if (negativeButton != null) {
            negativeButton.setTextColor(Color.BLACK);
        }
    }

    @Override
    public int getContentViewId() {
        return R.layout.bottom;
    }

    @Override
    public BasePresenter getPresenterInstance() {
        return null;
    }

    @Override
    public void destroy() {
    }

    @Override
    public void responseError(Object o, Throwable throwable) {
        Toast.makeText(this, "打开应用失败", Toast.LENGTH_SHORT).show();
        Log.e("BottomActivity", "responseError: 打开应用失败/" + o, null);
        throwable.printStackTrace();
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.main_search_button) {
            Intent intent = new Intent(this, SearchActivity.class);
            startActivity(intent);
        } else if (id == R.id.menu_button) {
            mDrawerLayout.openDrawer(GravityCompat.START);
        }


    }



    /**
     * 切换碎片
     *
     * @param fragment 要切换的碎片
     */
    public void replaceFragment(Fragment fragment) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.user_drawer_content, fragment);
        transaction.commit();
    }


    /**
     * 用于切换程序三大模块的碎片
     *
     * @param fragment 要切换的碎片
     */
    //替换上面的replaceFragment方法，防止出现碎片切换空白问题
    private void showFragment(Fragment fragment) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        if (!fragment.isAdded()) {//如果之前没有添加过
            transaction
                    .hide(currentFragment)
                    .add(R.id.content_frag, fragment, fragment.getClass().getName());  //第三个参数为当前的fragment绑定一个tag，tag为当前绑定fragment的类名
        } else {
            transaction
                    .hide(currentFragment)
                    .show(fragment);
        }

        currentFragment = fragment;//记录当前Fragment

        transaction.commit();

    }
}
