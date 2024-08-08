package com.example.playandroid.interf.service;

import com.example.playandroid.entity.ArticleResponse;
import com.example.playandroid.entity.SingleDataResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface KsService {
    @GET("article/list/{page}/json")
    Call<SingleDataResponse<ArticleResponse>> getArticle(@Path("page") int page, @Query("cid") int typeId);
}
