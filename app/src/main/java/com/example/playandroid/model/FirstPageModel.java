package com.example.playandroid.model;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.playandroid.base.BaseModelForFragment;
import com.example.playandroid.entity.ArticleResponse;
import com.example.playandroid.entity.DataResponse;
import com.example.playandroid.entity.SingleDataResponse;
import com.example.playandroid.interf.datacallback.DataCallBack;
import com.example.playandroid.interf.datacallback.DataCallBackForArticle;
import com.example.playandroid.interf.datacallback.DataCallBackForBanner;
import com.example.playandroid.interf.contract.FirstPageContract;
import com.example.playandroid.entity.Banner;
import com.example.playandroid.entity.Article;
import com.example.playandroid.interf.service.ApiService;
import com.example.playandroid.presenter.FirstPagePresenter;
import com.example.playandroid.util.RetrofitUtil;
import com.example.playandroid.util.WebUtil;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FirstPageModel extends BaseModelForFragment<FirstPagePresenter> implements FirstPageContract.M {

    //banner网址
    private final static String BANNER_URL = "https://www.wanandroid.com/banner/json";
    //首页文章网址
    private final static String FP_ARTICLE_URL = "https://www.wanandroid.com/article/list/";
    //置顶文章网址
    private final static String TOP_ARTICLE_URL = "https://www.wanandroid.com/article/top/json";

    private static final String TAG = "BannerModel";

    private final ApiService apiService = RetrofitUtil.getRetrofitInstance().create(ApiService.class);

    public FirstPageModel(FirstPagePresenter mPresenter) {
        super(mPresenter);
    }

    /**
     * 获取banner数据
     */
    public void getBannerData() {

//        WebUtil.getDataFromWeb(BANNER_URL, new DataCallBack() {
//            @Override
//            public void onSuccess(String data) {
//                int startIndex = data.indexOf("[");
//                int endIndex = data.lastIndexOf("]");
//                //解析获取到的数据
//                parseBannerData(data.substring(startIndex, endIndex + 1), new DataCallBackForBanner() {
//                    @Override
//                    public void onSuccess(List<Banner> bannerList) {
//                        //若成功解析，则返回获取到的Banner对象集合数据
//                        mPresenter.requestBannerDataResult(bannerList);
//                    }
//
//                    @Override
//                    public void onFailure(Exception e) {
//                        e.printStackTrace();
//                        Log.e(TAG, "onFailure: 获取Banner数据失败");
//                    }
//                });
//            }
//
//            @Override
//            public void onFailure(Exception e) {
//                Log.e(TAG, "" + e);
//            }
//
//            @Override
//            public void getCookie(List<String> setCookieList) {
//
//            }
//        });

        //--------------------------------------------------

        Call<DataResponse<Banner>> call = apiService.getBanner();
        call.enqueue(new Callback<DataResponse<Banner>>() {
            @Override
            public void onResponse(@NonNull Call<DataResponse<Banner>> call, @NonNull Response<DataResponse<Banner>> response) {
                if (response.isSuccessful()){
                    DataResponse<Banner> dataResponse = response.body();
                    assert dataResponse != null;
                    List<Banner> bannerList = dataResponse.getData();
                    mPresenter.requestBannerDataResult(bannerList);
                }
            }

            @Override
            public void onFailure(@NonNull Call<DataResponse<Banner>> call, @NonNull Throwable t) {
                t.printStackTrace();
                Log.e("getBannerData", "fail/" + t);
            }
        });



    }


    /**
     * 获取首页文章数据
     */
    public void getFpArticleData(int page) {

//        WebUtil.getDataFromWeb(FP_ARTICLE_URL + page + "/json", new DataCallBack() {
//            @Override
//            public void onSuccess(String data) {
//                int startIndex = data.indexOf("[");
//                int endIndex = data.lastIndexOf("]");
//                //解析获取到的数据
//                parseArticleData(data.substring(startIndex, endIndex + 1), new DataCallBackForArticle() {
//                    @Override
//                    public void onSuccess(List<Article> articleList) {
//                        //若成功解析，则返回获取到的Article对象集合数据
//                        mPresenter.requestArticleDataResult(articleList);
//                    }
//
//                    @Override
//                    public void onFailure(Exception e) {
//                        e.printStackTrace();
//                        Log.e(TAG, "onFailure: 获取首页文章数据失败");
//                    }
//                });
//            }
//
//            @Override
//            public void onFailure(Exception e) {
//                e.printStackTrace();
//                Log.e(TAG, "onFailure: 获取网络数据失败/" + e);
//            }
//
//            @Override
//            public void getCookie(List<String> setCookieList) {
//
//            }
//        });


        Call<SingleDataResponse<ArticleResponse>> call = apiService.getArticle(page);
        call.enqueue(new Callback<SingleDataResponse<ArticleResponse>>() {
            @Override
            public void onResponse(Call<SingleDataResponse<ArticleResponse>> call, Response<SingleDataResponse<ArticleResponse>> response) {
                if (response.isSuccessful()){
                    SingleDataResponse<ArticleResponse> dataResponse = response.body();
                    if (dataResponse != null){
                        ArticleResponse articleResponse = dataResponse.getData();
                        List<Article> articleList = articleResponse.getDatas();
                        mPresenter.requestArticleDataResult(articleList);

                    } else {
                        Log.e("getFpArticleData", "响应体response为空");
                    }

                } else {
                    Log.d("getFpArticleData", "响应失败");
                }
            }

            @Override
            public void onFailure(Call<SingleDataResponse<ArticleResponse>> call, Throwable t) {
                t.printStackTrace();
                Log.e("getFpArticleData", "fail/" + t);
            }
        });


    }

    /**
     * 获取置顶文章数据
     */
    public void getTopArticleData() {

        WebUtil.getDataFromWeb(TOP_ARTICLE_URL, new DataCallBack() {
            @Override
            public void onSuccess(String data) {
                int startIndex = data.indexOf("[");
                int endIndex = data.lastIndexOf("]");
                //解析获取到的数据
                parseArticleData(data.substring(startIndex, endIndex + 1), new DataCallBackForArticle() {
                    @Override
                    public void onSuccess(List<Article> articleList) {
                        //若成功解析，则返回获取到的置顶文章对象集合数据
                        mPresenter.requestTopArticleDataResult(articleList);
                    }

                    @Override
                    public void onFailure(Exception e) {
                        e.printStackTrace();
                        Log.e(TAG, "onFailure: 获取置顶文章数据失败/" + e);
                    }
                });
            }

            @Override
            public void onFailure(Exception e) {
                e.printStackTrace();
                Log.e(TAG, "onFailure: 获取网络数据失败" + e);
            }

            @Override
            public void getCookie(List<String> setCookieList) {

            }
        });
    }

    /**
     * 解析Banner的Json数据
     *
     * @param data Json数据
     * @return banner对象的集合
     */
    public void parseBannerData(String data, DataCallBackForBanner dataCallBack) {

        List<Banner> bannerList = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(data);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Banner banner = new Banner();
                banner.setDesc(jsonObject.getString("desc"));
                banner.setId(jsonObject.getInt("id"));
                banner.setImagePath(jsonObject.getString("imagePath"));
                banner.setIsVisible(jsonObject.getInt("isVisible"));
                banner.setOrder(jsonObject.getInt("order"));
                banner.setTitle(jsonObject.getString("title"));
                banner.setType(jsonObject.getInt("type"));
                banner.setUrl(jsonObject.getString("url"));
                bannerList.add(banner);
            }
            dataCallBack.onSuccess(bannerList);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "parseBannerData: 解析数据出现异常");
            dataCallBack.onFailure(e);
        }
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
            Log.e(TAG, "parseArticleData: 解析数据出现异常");
            dataCallBack.onFailure(e);
        }
    }


    @Override
    public void requestBannerData() {
        getBannerData();
    }

    @Override
    public void requestArticleData(int page) {
        getFpArticleData(page);
    }

    @Override
    public void requestTopArticleData() {
        getTopArticleData();
    }
}
