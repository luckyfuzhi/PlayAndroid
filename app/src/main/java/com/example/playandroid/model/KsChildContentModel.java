package com.example.playandroid.model;

import android.util.Log;

import com.example.playandroid.base.BaseModelForFragment;
import com.example.playandroid.contract.DataCallBack;
import com.example.playandroid.contract.DataCallBackForArticle;
import com.example.playandroid.contract.KsChildContract;
import com.example.playandroid.entity.Article;
import com.example.playandroid.presenter.KsChildContentPresenter;
import com.example.playandroid.util.WebUtil;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class KsChildContentModel extends BaseModelForFragment<KsChildContentPresenter> implements KsChildContract.M {
    private final static String KS_CHILD_ARTICLE_URL = "https://www.wanandroid.com/article/list/";

    public KsChildContentModel(KsChildContentPresenter mPresenter) {
        super(mPresenter);
    }


    @Override
    public void requestArticleData(int page, int typeId) {
        WebUtil.getDataFromWeb(KS_CHILD_ARTICLE_URL + page + "/json?cid=" + typeId, new DataCallBack() {
            @Override
            public void onSuccess(String data) {
                int startIndex = data.indexOf("[");
                int endIndex = data.lastIndexOf("]");
                parseArticleData(data.substring(startIndex, endIndex + 1), new DataCallBackForArticle() {
                    @Override
                    public void onSuccess(List<Article> articleList) {
                        mPresenter.requestArticleDataResult(articleList);
                    }

                    @Override
                    public void onFailure(Exception e) {
                        e.printStackTrace();
                        Log.e("requestArticleData", "onFailure: 获取置顶文章数据失败/" + e);
                    }
                });

            }

            @Override
            public void onFailure(Exception e) {
                e.printStackTrace();
                Log.e("requestArticleData", "onFailure: 获取网络数据失败" + e);
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
            Log.e("KsChildModel", "parseArticleData: 解析数据出现异常");
            dataCallBack.onFailure(e);
        }
    }
}
