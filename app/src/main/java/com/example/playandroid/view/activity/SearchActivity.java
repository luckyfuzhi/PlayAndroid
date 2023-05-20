package com.example.playandroid.view.activity;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.playandroid.R;
import com.example.playandroid.base.BaseActivity;
import com.example.playandroid.presenter.SearchPresenter;

public class SearchActivity extends BaseActivity<SearchPresenter> {

    private Button backButton;

    private EditText editText;

    private Button searchButton;

    private TextView hotWordText;

    @Override
    public void initView() {
        backButton = findViewById(R.id.search_back);
        editText = findViewById(R.id.search_edit);
        searchButton = findViewById(R.id.search_activity_button);
        hotWordText = findViewById(R.id.hot_word_textview);
    }

    @Override
    public void initData() {

    }

    @Override
    public void initListener() {
        backButton.setOnClickListener(this);
    }

    @Override
    public int getContentViewId() {
        return R.layout.search_activity;
    }

    @Override
    public SearchPresenter getPresenterInstance() {
        return new SearchPresenter();
    }

    @Override
    public void destroy() {

    }

    @Override
    public <ERROR> void responseError(ERROR error, Throwable throwable) {

    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.search_back){
            finish();
        }
    }
}
