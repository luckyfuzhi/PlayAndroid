package com.example.playandroid.view.activity;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.playandroid.R;
import com.example.playandroid.base.BaseActivity;
import com.example.playandroid.contract.KsChildContract;
import com.example.playandroid.entity.Article;
import com.example.playandroid.entity.KnowledgeType;
import com.example.playandroid.presenter.KsChildPresenter;
import com.example.playandroid.view.fragment.KsChildContentFragment;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class KsChildActivity extends BaseActivity<KsChildPresenter>{

    private Button backImg;

    private TextView title;

    private TabLayout childTypeNameTl;

    private ViewPager childArticleVp;

    private KnowledgeType mKnowledgeType;

    private List<String> childNameList = new ArrayList<>();
    private List<KsChildContentFragment> ksChildContentFragmentList = new ArrayList<>();



    @Override
    public void initView() {
        backImg = findViewById(R.id.ks_article_back);
        title = findViewById(R.id.article_detail_title);
        childTypeNameTl = findViewById(R.id.ks_article_type_tl);
        childArticleVp = findViewById(R.id.ks_article_content_vp);

        mKnowledgeType = getKnowledgeType();
        for (KnowledgeType knowledgeType : mKnowledgeType.getChildList()) {
            childTypeNameTl.addTab(childTypeNameTl.newTab().setText(knowledgeType.getName()));
            ksChildContentFragmentList.add(new KsChildContentFragment(knowledgeType.getId()));
        }

        childArticleVp.setAdapter(new FragmentPagerAdapter(this.getSupportFragmentManager(),
                FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
            @NonNull
            @Override
            public Fragment getItem(int position) {
                return ksChildContentFragmentList.get(position);
            }

            @Override
            public int getCount() {
                return ksChildContentFragmentList.size();
            }

            @Nullable
            @Override
            public CharSequence getPageTitle(int position) {
                return mKnowledgeType.getChildList().get(position).getName();
            }
        });

        childTypeNameTl.setupWithViewPager(childArticleVp);

    }

    @Override
    public void initData() {

    }

    //获取父知识体系分类
    public KnowledgeType getKnowledgeType(){
        Intent intent = getIntent();
        KnowledgeType knowledgeType = null;
        if("sendKnowledgeType".equals(intent.getAction())){
            knowledgeType = (KnowledgeType) intent.getSerializableExtra("KnowledgeTypeData");
        }
        return knowledgeType;
    }


    @Override
    public void initListener() {
        backImg.setOnClickListener(this);
    }

    @Override
    public int getContentViewId() {
        return R.layout.knowledge_system_detail_activity;
    }

    @Override
    public KsChildPresenter getPresenterInstance() {
        return new KsChildPresenter();
    }

    @Override
    public void destroy() {

    }

    @Override
    public <ERROR> void responseError(ERROR error, Throwable throwable) {

    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.ks_article_back){
            finish();
        }
    }


}
