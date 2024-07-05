package com.example.playandroid.model;

import android.util.Log;

import com.example.playandroid.base.BaseModelForFragment;
import com.example.playandroid.entity.Article;
import com.example.playandroid.entity.ArticleResponse;
import com.example.playandroid.entity.SingleDataResponse;
import com.example.playandroid.interf.contract.SearchResultContract;
import com.example.playandroid.interf.datacallback.DataCallBack;
import com.example.playandroid.interf.datacallback.DataCallBackForArticle;
import com.example.playandroid.interf.service.CollectionService;
import com.example.playandroid.presenter.KsChildContentPresenter;
import com.example.playandroid.presenter.SearchResultPresenter;
import com.example.playandroid.util.RetrofitUtil;
import com.example.playandroid.util.WebUtil;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchResultModel extends BaseModelForFragment<SearchResultPresenter> implements SearchResultContract.M {

    //获取搜索结果网址
    private final static String SEARCH_RESULT_URL = "https://www.wanandroid.com/article/query/";

    public SearchResultModel(SearchResultPresenter mPresenter) {
        super(mPresenter);
    }
    private final CollectionService collectService = RetrofitUtil.getRetrofitInstance().create(CollectionService.class);

    public void collectArticle(int articleId) {
        Call<SingleDataResponse<ArticleResponse>> call = collectService.collectArticle(articleId);
        call.enqueue(new Callback<SingleDataResponse<ArticleResponse>>() {
            @Override
            public void onResponse(Call<SingleDataResponse<ArticleResponse>> call, Response<SingleDataResponse<ArticleResponse>> response) {
                if (response.isSuccessful()){
                    SingleDataResponse<ArticleResponse> dataResponse = response.body();
                    if (dataResponse != null) {
                        mPresenter.collectResult(dataResponse.getErrorMsg());
                    }
                } else {
                    mPresenter.collectResult("收藏失败");
                }

            }

            @Override
            public void onFailure(Call<SingleDataResponse<ArticleResponse>> call, Throwable t) {
                mPresenter.collectResult(t.getMessage());
            }
        });
    }

    @Override
    public void requestArticleData(int page, Map<String, String> paramMap) {
        WebUtil.postDataToWeb(SEARCH_RESULT_URL + page + "/json", paramMap, new DataCallBack() {
            @Override
            public void onSuccess(String data) {
                int startIndex = data.indexOf("[");
                int endIndex = data.lastIndexOf("]");
                parseArticleData(data.substring(startIndex, endIndex + 1), new DataCallBackForArticle() {
                    @Override
                    public void onSuccess(List<Article> articleList) {
                        //若成功解析，则返回获取到的Article对象集合数据
                        mPresenter.requestArticleDataResult(articleList);
                    }

                    @Override
                    public void onFailure(Exception e) {
                        e.printStackTrace();
                        Log.e("postDataToWeb", "onFailure: 获取首页文章数据失败");
                    }
                });
            }

            @Override
            public void onFailure(Exception e) {
                e.printStackTrace();
                Log.e("requestArticleData", "onFailure: 获取网络数据失败/" + e);
            }

            @Override
            public void getCookie(List<String> setCookieList) {

            }
        });
    }

    /**
     * 解析Article的Json数据
     *
     * @param data Json数据
     * @return Article对象的集合
     */
    public void parseArticleData(String data, DataCallBackForArticle dataCallBack) {

        List<Article> articleList = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(data);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Article article = new Article();
                article.setTitle(jsonObject.getString("title"));
                article.setId(jsonObject.getInt("id"));
                article.setAuthor(jsonObject.getString("author").equals("") ?
                        jsonObject.getString("shareUser") : jsonObject.getString("author"));
                article.setLink(jsonObject.getString("link"));
                article.setChapterName(jsonObject.getString("chapterName"));
                article.setSuperChapterName(jsonObject.getString("superChapterName"));
                article.setNiceShareDate(jsonObject.getString("niceShareDate"));
                articleList.add(article);
            }
            dataCallBack.onSuccess(articleList);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("parseArticleData", "parseArticleData: 解析数据出现异常");
            dataCallBack.onFailure(e);
        }
    }
}
