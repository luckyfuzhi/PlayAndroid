package com.example.playandroid.model;

import android.util.Log;

import com.example.playandroid.base.BaseModelForFragment;
import com.example.playandroid.entity.ArticleResponse;
import com.example.playandroid.entity.SingleDataResponse;
import com.example.playandroid.interf.datacallback.DataCallBack;
import com.example.playandroid.interf.datacallback.DataCallBackForArticle;
import com.example.playandroid.interf.contract.KsChildContract;
import com.example.playandroid.entity.Article;
import com.example.playandroid.interf.service.CollectionService;
import com.example.playandroid.interf.service.KsService;
import com.example.playandroid.presenter.KsChildContentPresenter;
import com.example.playandroid.util.RetrofitUtil;
import com.example.playandroid.util.WebUtil;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KsChildContentModel extends BaseModelForFragment<KsChildContentPresenter> implements KsChildContract.M {

    //知识体系某种类的具体文章列表网址
    private final static String KS_CHILD_ARTICLE_URL = "https://www.wanandroid.com/article/list/";
    private final CollectionService collectService = RetrofitUtil.getRetrofitInstance().create(CollectionService.class);
    private final KsService ksService = RetrofitUtil.getRetrofitInstance().create(KsService.class);

    public KsChildContentModel(KsChildContentPresenter mPresenter) {
        super(mPresenter);
    }

    public void collectArticle(int articleId) {
        Call<SingleDataResponse<ArticleResponse>> call = collectService.collectArticle(articleId);
        call.enqueue(new Callback<SingleDataResponse<ArticleResponse>>() {
            @Override
            public void onResponse(Call<SingleDataResponse<ArticleResponse>> call, Response<SingleDataResponse<ArticleResponse>> response) {
                if (response.isSuccessful()){
                    SingleDataResponse<ArticleResponse> dataResponse = response.body();
                    if (dataResponse != null) {
                        mPresenter.msgResult(dataResponse.getErrorMsg());
                    }
                } else {
                    mPresenter.msgResult("收藏失败");
                }

            }

            @Override
            public void onFailure(Call<SingleDataResponse<ArticleResponse>> call, Throwable t) {
                mPresenter.msgResult(t.getMessage());
            }
        });
    }

    @Override
    public void requestArticleData(int page, int typeId) {
        Call<SingleDataResponse<ArticleResponse>> call = ksService.getArticle(page, typeId);
        call.enqueue(new Callback<SingleDataResponse<ArticleResponse>>() {
            @Override
            public void onResponse(Call<SingleDataResponse<ArticleResponse>> call, Response<SingleDataResponse<ArticleResponse>> response) {
                if (response.isSuccessful()) {
                    SingleDataResponse<ArticleResponse> dataResponse = response.body();
                    if (dataResponse != null) {
                        mPresenter.requestArticleDataResult(dataResponse.getData().getDatas());
                    }
                } else {
                    mPresenter.msgResult("获取数据失败");
                }
            }

            @Override
            public void onFailure(Call<SingleDataResponse<ArticleResponse>> call, Throwable t) {
                mPresenter.msgResult(t.getMessage());
            }
        });


//        WebUtil.getDataFromWeb(KS_CHILD_ARTICLE_URL + page + "/json?cid=" + typeId, new DataCallBack() {
//            @Override
//            public void onSuccess(String data) {
//                int startIndex = data.indexOf("[");
//                int endIndex = data.lastIndexOf("]");
//                parseArticleData(data.substring(startIndex, endIndex + 1), new DataCallBackForArticle() {
//                    @Override
//                    public void onSuccess(List<Article> articleList) {
//                        mPresenter.requestArticleDataResult(articleList);
//                    }
//
//                    @Override
//                    public void onFailure(Exception e) {
//                        e.printStackTrace();
//                        Log.e("requestArticleData", "onFailure: 获取置顶文章数据失败/" + e);
//                    }
//                });
//
//            }
//
//            @Override
//            public void onFailure(Exception e) {
//                e.printStackTrace();
//                Log.e("requestArticleData", "onFailure: 获取网络数据失败" + e);
//            }
//
//            @Override
//            public void getCookie(List<String> setCookieList) {
//
//            }
//        });
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
            Log.e("KsChildModel", "parseArticleData: 解析数据出现异常");
            dataCallBack.onFailure(e);
        }
    }
}
