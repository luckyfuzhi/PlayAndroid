package com.example.playandroid.interf.service;

import com.example.playandroid.entity.ArticleResponse;
import com.example.playandroid.entity.CollectArticleResponse;
import com.example.playandroid.entity.SingleDataResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface CollectionService {
    //收藏列表
    @GET("lg/collect/list/{page}/json")
    Call<SingleDataResponse<CollectArticleResponse>> getCollectionArticle(@Path("page") int page);

    //收藏文章
    @POST("lg/collect/{articleId}/json")
    Call<SingleDataResponse<ArticleResponse>> collectArticle(@Path("articleId") int articleId);

    //在文章列表取消收藏
    @POST("lg/uncollect_originId/{articleId}/json")
    Call<SingleDataResponse<String>> uncollectArticleInList(@Path("articleId") int articleId);

    //在我的收藏页面中取消收藏
    @POST("lg/uncollect/{articleId}/json")
    Call<SingleDataResponse<String>> uncollectArticleInCollectionList(
            @Path("articleId") int articleId, @Query("originId") int originId
    );
}
