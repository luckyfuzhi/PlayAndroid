package com.example.playandroid.model;

import android.util.Log;

import com.example.playandroid.base.BaseModelForFragment;
import com.example.playandroid.contract.DataCallBack;
import com.example.playandroid.contract.DataCallBackForProjectArticle;
import com.example.playandroid.contract.DataCallBackForProjectType;
import com.example.playandroid.contract.ProjectArticleContract;
import com.example.playandroid.entity.Project;
import com.example.playandroid.entity.ProjectType;
import com.example.playandroid.presenter.ProjectContentPresenter;
import com.example.playandroid.util.WebUtil;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ProjectContentModel extends BaseModelForFragment<ProjectContentPresenter> implements ProjectArticleContract.M {

    private final static String PROJECT_URL = "https://www.wanandroid.com/project/list/";

    private static final String TAG = "ProjectContentModel";

    public ProjectContentModel(ProjectContentPresenter mPresenter) {
        super(mPresenter);
    }

    @Override
    public void requestProjectData(int page, int typeId) {
        String articleListUrl = PROJECT_URL + page + "/json?cid=" + typeId;
        WebUtil.getDataFromWeb(articleListUrl, new DataCallBack() {
            @Override
            public void onSuccess(String data) {
                int startIndex = data.indexOf("[");
                int endIndex = data.lastIndexOf("]");
                parseProjectListData(data.substring(startIndex, endIndex + 1), new DataCallBackForProjectArticle() {
                    @Override
                    public void onSuccess(List<Project> projectArticleList) {
                        mPresenter.requestProjectDataResult(projectArticleList);
                    }

                    @Override
                    public void onFailure(Exception e) {
                        e.printStackTrace();
                        Log.e(TAG, "onFailure: " + e);
                    }
                });
            }

            @Override
            public void onFailure(Exception e) {
                e.printStackTrace();
                Log.e(TAG, "onFailure: " + e);
            }
        });
    }

    /**
     * 解析ProjectArticleList的Json数据
     *
     * @param data Json数据
     * @return Project对象的集合
     */
    public void parseProjectListData(String data, DataCallBackForProjectArticle dataCallBack) {

        List<Project> projectArticleList = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(data);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Project projectArticle = new Project();
                projectArticle.setChapterId(jsonObject.getInt("chapterId"));
                projectArticle.setAuthor(jsonObject.getString("author"));
                projectArticle.setDesc(jsonObject.getString("desc"));
                projectArticle.setSuperChapterName(jsonObject.getString("superChapterName"));
                projectArticle.setChapterName(jsonObject.getString("chapterName"));
                projectArticle.setLink(jsonObject.getString("link"));
                projectArticle.setTitle(jsonObject.getString("title"));
                projectArticle.setNiceShareDate(jsonObject.getString("niceShareDate"));
                projectArticle.setImgLink(jsonObject.getString("envelopePic"));
                projectArticleList.add(projectArticle);
            }
            dataCallBack.onSuccess(projectArticleList);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("parseProjectArticleData", "parseProjectArticleData: 解析数据出现异常");
            dataCallBack.onFailure(e);
        }
    }

}