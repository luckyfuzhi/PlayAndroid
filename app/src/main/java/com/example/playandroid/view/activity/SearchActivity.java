package com.example.playandroid.view.activity;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.playandroid.R;
import com.example.playandroid.base.BaseActivity;
import com.example.playandroid.interf.contract.SearchContract;
import com.example.playandroid.presenter.SearchPresenter;
import com.example.playandroid.view.fragment.HotWordFragment;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends BaseActivity<SearchPresenter> implements SearchContract.VP {

    private final static int SHOW_HOT_WORD = 1;

    private Button backButton;

    private EditText editText;

    private Button searchButton;

    private String editContent;

    private HotWordFragment hotWordFragment;

    private FragmentManager fragmentManager;

    private List<String> hotWordList = new ArrayList<>();

    @Override
    public void initView() {
        backButton = findViewById(R.id.search_back);
        editText = findViewById(R.id.search_edit);
        searchButton = findViewById(R.id.search_activity_button);
        hotWordFragment = new HotWordFragment(hotWordList);
        fragmentManager = getSupportFragmentManager();
    }

    @Override
    public void initData() {
        requestHotWord();
        editContent = editText.getText().toString();
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


    private Handler handler = new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what){
                case SHOW_HOT_WORD:
                    replaceFragment(hotWordFragment);
                    break;
                default:
                    break;
            }
        }
    };

    public void replaceFragment(Fragment fragment){
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.search_content, fragment);
        transaction.commit();
    }

    @Override
    public void requestHotWord() {
        mPresenter.requestHotWord();
    }

    @Override
    public void requestHotWordResult(List<String> hotWordList) {
        this.hotWordList.addAll(hotWordList);
        Message message = new Message();
        message.what = SHOW_HOT_WORD;
        handler.sendMessage(message);
    }
}
