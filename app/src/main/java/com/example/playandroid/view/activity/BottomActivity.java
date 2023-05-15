package com.example.playandroid.view.activity;

import android.app.ActionBar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.playandroid.R;
import com.example.playandroid.base.BaseActivity;
import com.example.playandroid.base.BasePresenter;
import com.example.playandroid.view.fragment.FirstPageFragment;
import com.example.playandroid.view.fragment.KnowledgeSystemFragment;
import com.example.playandroid.view.fragment.ProjectFragment;


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
    private LinearLayout firstPage;
    private LinearLayout knowledgeSystem;
    private LinearLayout project;

    @Override
    public void initView() {
        //初始化底部导航栏控件
        firstPageButton = findViewById(R.id.first_page_img);
        knowledgeSystemButton = findViewById(R.id.knowledge_system_img);
        projectButton = findViewById(R.id.project_img);
        firstPageText = findViewById(R.id.first_page_text);
        knowledgeSystemText = findViewById(R.id.knowledge_system_text);
        projectText = findViewById(R.id.project_text);
        firstPage=findViewById(R.id.home_page);
        knowledgeSystem=findViewById(R.id.knowledge_system);
        project=findViewById(R.id.project);
        setSelectedState(R.id.first_page_img);
        replaceFragment(new FirstPageFragment());


    }

    @Override
    public void initData() {
    }

    @Override
    public void initListener() {
        firstPage.setOnClickListener(this);
        knowledgeSystem.setOnClickListener(this);
        project.setOnClickListener(this);
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
            replaceFragment(new FirstPageFragment());

        } else if (id == R.id.knowledge_system) {
            setSelectedState(R.id.knowledge_system_img);
            replaceFragment(new KnowledgeSystemFragment());

        } else if (id == R.id.project) {
            setSelectedState(R.id.project_img);
            replaceFragment(new ProjectFragment());

        }


    }

    /**
     *  设置选中状态
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

    public void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.content_frag, fragment);
        transaction.commit();
    }
}
