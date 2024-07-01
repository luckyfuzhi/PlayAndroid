package com.example.playandroid.model;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.playandroid.base.BaseModelForFragment;
import com.example.playandroid.entity.ProjectList;
import com.example.playandroid.entity.SingleDataResponse;
import com.example.playandroid.interf.datacallback.DataCallBackForProjectArticle;
import com.example.playandroid.interf.contract.ProjectArticleContract;
import com.example.playandroid.entity.Project;
import com.example.playandroid.interf.service.ProjectService;
import com.example.playandroid.presenter.ProjectContentPresenter;
import com.example.playandroid.util.RetrofitUtil;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProjectContentModel extends BaseModelForFragment<ProjectContentPresenter> implements ProjectArticleContract.M {

    //项目文章数据
    private final static String PROJECT_URL = "https://www.wanandroid.com/project/list/";
    private final ProjectService apiService = RetrofitUtil.getRetrofitInstance().create(ProjectService.class); //没用上

    private static final String TAG = "ProjectContentModel";

    public ProjectContentModel(ProjectContentPresenter mPresenter) {
        super(mPresenter);
    }

    @Override
    public void requestProjectData(int page, int typeId) {
//        String articleListUrl = PROJECT_URL + page + "/json?cid=" + typeId;
//        WebUtil.getDataFromWeb(articleListUrl, new DataCallBack() {
//            @Override
//            public void onSuccess(String data) {
//                boolean over = Boolean.parseBoolean(Objects.requireNonNull(StringUtil.extractSubstring(data, "\"over\":", ",")));
//                int startIndex = data.indexOf("[");
//                int endIndex = data.lastIndexOf("]");
//                parseProjectListData(data.substring(startIndex, endIndex + 1), new DataCallBackForProjectArticle() {
//                    @Override
//                    public void onSuccess(List<Project> projectArticleList) {
//                        mPresenter.requestProjectDataResult(projectArticleList, over);
//                    }
//
//                    @Override
//                    public void onFailure(Exception e) {
//                        e.printStackTrace();
//                        Log.e(TAG, "onFailure: " + e);
//                    }
//                });
//            }
//
//            @Override
//            public void onFailure(Exception e) {
//                e.printStackTrace();
//                Log.e(TAG, "onFailure: " + e);
//            }
//
//            @Override
//            public void getCookie(List<String> setCookieList) {
//
//            }
//        });

        apiService.getProjectList(page, typeId).enqueue(new Callback<SingleDataResponse<ProjectList>>() {
            @Override
            public void onResponse(@NonNull Call<SingleDataResponse<ProjectList>> call, @NonNull Response<SingleDataResponse<ProjectList>> response) {
                if (response.isSuccessful()) {
                    SingleDataResponse<ProjectList> dataResponse = response.body();
                    if (dataResponse != null) {
                        mPresenter.requestProjectDataResult(dataResponse.getData().getData(), dataResponse.getData().isOver());
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<SingleDataResponse<ProjectList>> call, @NonNull Throwable t) {
                t.printStackTrace();
                Log.e("requestProjectData", "fail/" + t);
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
                projectArticle.setId(jsonObject.optInt("id"));
                projectArticle.setChapterId(jsonObject.getInt("chapterId"));
                projectArticle.setAuthor(jsonObject.getString("author"));
                projectArticle.setDesc(jsonObject.getString("desc"));
                projectArticle.setSuperChapterName(jsonObject.getString("superChapterName"));
                projectArticle.setChapterName(jsonObject.getString("chapterName"));
                projectArticle.setLink(jsonObject.getString("link"));
                projectArticle.setTitle(jsonObject.getString("title"));
                projectArticle.setNiceShareDate(jsonObject.getString("niceShareDate"));
                projectArticle.setEnvelopePic(jsonObject.getString("envelopePic"));
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
