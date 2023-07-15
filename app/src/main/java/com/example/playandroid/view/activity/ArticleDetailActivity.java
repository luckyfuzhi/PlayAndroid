package com.example.playandroid.view.activity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.playandroid.R;
import com.example.playandroid.base.BaseActivity;
import com.example.playandroid.presenter.ArticleDetailPresenter;

/**
 * 文章详情
 */
public class ArticleDetailActivity extends BaseActivity<ArticleDetailPresenter> {
    private WebView contentWebView;
    private Button back;
    private TextView articleTitle;

    private String articleLink;
    private ProgressBar progressBar;

    private ProgressDialog progressDialog;

    class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            view.loadUrl(request.getUrl().toString());
            return false;
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            // 页面开始加载方法
//            progressBar.setVisibility(View.VISIBLE);
//            progressBar.setProgress(0);
//            progressDialog.show();
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            // 页面加载完成方法
            super.onPageFinished(view, url);
//            progressDialog.dismiss();
            progressBar.setProgress(100);
            progressBar.setVisibility(View.GONE);
            articleTitle.setText(getArticleTitle());
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public void initView() {
        back = findViewById(R.id.article_back);
        articleTitle = findViewById(R.id.article_detail_title);
        contentWebView = findViewById(R.id.wv_article);
        progressBar = findViewById(R.id.load_bar);
        contentWebView.getSettings().setJavaScriptEnabled(true);
        contentWebView.setWebViewClient(new MyWebViewClient());
        back.setOnClickListener(this);


        WebSettings settings = contentWebView.getSettings();
        settings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        settings.setJavaScriptEnabled(true);//启用js
        settings.setBlockNetworkImage(false);//解决图片不显示
        // 开启DOM缓存，开启LocalStorage存储（html5的本地存储方式）
        settings.setDomStorageEnabled(true);
        settings.setDatabaseEnabled(true);
        settings.setTextZoom(90);

//        progressDialog = new ProgressDialog(this);
//        progressDialog.setCancelable(true);
//        progressDialog.setMessage("正在加载数据");

    }

    @Override
    public void initData() {
        articleLink = getArticleLink();
        contentWebView.loadUrl(articleLink);
    }

    public String getArticleLink() {
        Intent intent = getIntent();
        String data = null;
        if ("sendArticleData".equals(intent.getAction())) {
            data = intent.getStringExtra("articleLink");
        }
        return data;
    }

    public String getArticleTitle() {
        Intent intent = getIntent();
        String data = null;
        if ("sendArticleData".equals(intent.getAction())) {
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
        if (view.getId() == R.id.article_back) {
            finish();
        }
    }
}
