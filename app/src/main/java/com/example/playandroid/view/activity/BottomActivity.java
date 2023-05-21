package com.example.playandroid.view.activity;

import android.app.ActionBar;
import android.content.Intent;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.playandroid.R;
import com.example.playandroid.base.BaseActivity;
import com.example.playandroid.base.BasePresenter;
import com.example.playandroid.view.fragment.BottomDrawerLoginFragment;
import com.example.playandroid.view.fragment.FirstPageFragment;
import com.example.playandroid.view.fragment.KnowledgeSystemFragment;
import com.example.playandroid.view.fragment.ProjectFragment;
import com.example.playandroid.view.fragment.SucceedLoginFragment;


/**
 * 下方导航栏类
 */
public class BottomActivity extends BaseActivity {

    private ImageView firstPageButton;
    private ImageView knowledgeSystemButton;
    private ImageView projectButton;

    private TextView firstPageText;
    private TextView knowledgeSystemText;
    private TextView projectText;
    private Button searchButton;
    
    private Button menuButton;
    private LinearLayout firstPage;
    private LinearLayout knowledgeSystem;
    private LinearLayout project;


    private DrawerLayout mDrawerLayout;

    private FragmentManager fragmentManager;
    private Fragment currentFragment;
    private FirstPageFragment firstPageFragment;
    private KnowledgeSystemFragment knowledgeSystemFragment;
    private ProjectFragment projectFragment;

    @Override
    public void initView() {
        firstPageButton = findViewById(R.id.first_page_img);
        knowledgeSystemButton = findViewById(R.id.knowledge_system_img);
        projectButton = findViewById(R.id.project_img);
        firstPageText = findViewById(R.id.first_page_text);
        knowledgeSystemText = findViewById(R.id.knowledge_system_text);
        projectText = findViewById(R.id.project_text);
        firstPage = findViewById(R.id.home_page);
        knowledgeSystem = findViewById(R.id.knowledge_system);
        project = findViewById(R.id.project);
        currentFragment = new FirstPageFragment();
        fragmentManager = getSupportFragmentManager();
        searchButton = findViewById(R.id.main_search_button);
        menuButton = findViewById(R.id.menu_button);
        mDrawerLayout = findViewById(R.id.bottom_drawer_layout);


        if (getIntent().getBooleanExtra("isSuccessLogin", false)){//如果成功登录则切换侧滑栏视图
            replaceFragment(new SucceedLoginFragment(getIntent().getStringExtra("username")));
        }else {
            replaceFragment(new BottomDrawerLoginFragment());
        }

        if (getIntent().getBooleanExtra("exitLogin", false)){//如果退出登录则切换侧滑栏视图
            replaceFragment(new BottomDrawerLoginFragment());
            Toast.makeText(this, "已退出登录", Toast.LENGTH_SHORT).show();
        }
    }

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
            setSelectedState(R.id.first_page_img);
        }
    }

    @Override
    public void initListener() {
        firstPage.setOnClickListener(this);
        knowledgeSystem.setOnClickListener(this);
        project.setOnClickListener(this);
        searchButton.setOnClickListener(this);
        menuButton.setOnClickListener(this);

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
        if (id == R.id.home_page) {
            setSelectedState(R.id.first_page_img);
            showFragment(firstPageFragment);

        } else if (id == R.id.knowledge_system) {
            setSelectedState(R.id.knowledge_system_img);
            showFragment(knowledgeSystemFragment);

        } else if (id == R.id.project) {
            setSelectedState(R.id.project_img);
            showFragment(projectFragment);

        } else if (id == R.id.main_search_button) {
            Intent intent = new Intent(this, SearchActivity.class);
            startActivity(intent);
        } else if (id == R.id.menu_button) {
            mDrawerLayout.openDrawer(GravityCompat.START);
        }


    }

    /**
     * 设置选中状态
     *
     * @param id
     */
    public void setSelectedState(int id) {
        knowledgeSystemText.setTextColor(getResources().getColor(R.color.black));
        projectText.setTextColor(getResources().getColor(R.color.black));
        firstPageText.setTextColor(getResources().getColor(R.color.black));
        knowledgeSystemButton.setSelected(false);
        projectButton.setSelected(false);
        firstPageButton.setSelected(false);

        if (id == R.id.first_page_img) {
            firstPageButton.setSelected(true);
            firstPageText.setTextColor(getResources().getColor(R.color.green));


        } else if (id == R.id.knowledge_system_img) {
            knowledgeSystemButton.setSelected(true);
            knowledgeSystemText.setTextColor(getResources().getColor(R.color.shallow_blue));


        } else if (id == R.id.project_img) {
            projectButton.setSelected(true);
            projectText.setTextColor(getResources().getColor(R.color.blue));


        }
    }

    public void replaceFragment(Fragment fragment) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.user_drawer_content, fragment);
        transaction.commit();
    }


    //替换上面的replaceFragment方法
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
