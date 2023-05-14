package com.example.playandroid.view.activity;

import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.playandroid.MyApplication;
import com.example.playandroid.R;
import com.example.playandroid.base.BaseActivity;
import com.example.playandroid.base.BasePresenter;


/**
 * 下方导航栏类
 */
public class BottomActivity extends BaseActivity {

    private ImageButton firstPageButton;
    private ImageButton knowledgeSystemButton;
    private ImageButton projectButton;

    private TextView firstPageText;
    private TextView knowledgeSystemText;
    private TextView projectText;

    @Override
    public void initView() {
        firstPageButton = findViewById(R.id.first_page_img);
        knowledgeSystemButton = findViewById(R.id.knowledge_system_img);
        projectButton = findViewById(R.id.project_img);
        firstPageText = findViewById(R.id.first_page_text);
        knowledgeSystemText = findViewById(R.id.knowledge_system_text);
        projectText = findViewById(R.id.project_text);
    }

    @Override
    public void initData() {
    }

    @Override
    public void initListener() {
        firstPageButton.setOnClickListener(this);
        knowledgeSystemButton.setOnClickListener(this);
        projectButton.setOnClickListener(this);
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
        if(view.getId() == R.id.first_page_img){
            //设置选中状态
            findViewById(R.id.knowledge_system_img).setSelected(false);
            findViewById(R.id.project_img).setSelected(false);
            knowledgeSystemText.setTextColor(Color.BLACK);
            projectText.setTextColor(Color.BLACK);
            firstPageText.setTextColor(Color.GREEN);
            view.setSelected(true);



        } else if (view.getId() == R.id.knowledge_system_img) {
            //设置选中状态
            findViewById(R.id.first_page_img).setSelected(false);
            findViewById(R.id.project_img).setSelected(false);
            firstPageText.setTextColor(Color.BLACK);
            projectText.setTextColor(Color.BLACK);
            knowledgeSystemText.setTextColor(Color.parseColor("#1AB5A6"));
            view.setSelected(true);



        } else if (view.getId() == R.id.project_img) {
            //设置选中状态
            findViewById(R.id.first_page_img).setSelected(false);
            findViewById(R.id.knowledge_system_img).setSelected(false);
            firstPageText.setTextColor(Color.BLACK);
            knowledgeSystemText.setTextColor(Color.BLACK);
            projectText.setTextColor(Color.parseColor("#0787DD"));
            view.setSelected(true);
        }
    }
}
