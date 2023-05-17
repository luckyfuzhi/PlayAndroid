package com.example.playandroid.view.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.playandroid.R;
import com.example.playandroid.base.BaseActivity;
import com.example.playandroid.presenter.ArticleDetailPresenter;

public class ArticleDetailActivity extends BaseActivity<ArticleDetailPresenter> {
    private WebView contentWebView;
    private ImageView back;
    private TextView articleTitle;

    private String articleLink;
    private ProgressBar progressBar;

    class MyWebViewClient extends WebViewClient{
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            view.loadUrl(request.getUrl().toString());
            return super.shouldOverrideUrlLoading(view, request);
        }
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            // 页面开始加载方法
            progressBar.setVisibility(View.VISIBLE);
            progressBar.setProgress(0);
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            // 页面加载完成方法
            super.onPageFinished(view, url);
            progressBar.setProgress(100);
            progressBar.setVisibility(View.GONE);
            articleTitle.setText(getArticleTitle());
        }
    }

    @Override
    public void initView() {
        back = findViewById(R.id.article_back);
        articleTitle = findViewById(R.id.article_detail_title);
        contentWebView = findViewById(R.id.wv_article);
        progressBar = findViewById(R.id.load_bar);
        contentWebView.getSettings().setJavaScriptEnabled(true);
        contentWebView.setWebViewClient(new MyWebViewClient());
        back.setOnClickListener(this);
    }

    @Override
    public void initData() {
        articleLink = getArticleLink();
        contentWebView.loadUrl(articleLink);
    }

    public String getArticleLink(){
        Intent intent = getIntent();
        String data = null;
        if("sendArticleData".equals(intent.getAction())){
            data = intent.getStringExtra("articleLink");
        }
        return data;
    }

    public String getArticleTitle(){
        Intent intent = getIntent();
        String data = null;
        if("sendArticleData".equals(intent.getAction())){
            data = intent.getStringExtra("title");
        }
        return data;
    }


    @Override
    public void initListener() {
    }

    @Override
    public int getContentViewId() {
        return R.layout.article;
    }

    @Override
    public ArticleDetailPresenter getPresenterInstance() {
        return new ArticleDetailPresenter();
    }

    @Override
    public void destroy() {
        contentWebView.stopLoading();
        contentWebView.removeAllViews();
        contentWebView.destroy();
    }

    @Override
    public <ERROR> void responseError(ERROR error, Throwable throwable) {

    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.article_back){
//            Intent intent = new Intent(this, BottomActivity.class);
//            startActivity(intent);
            finish();
        }
    }
}
