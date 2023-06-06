package com.example.playandroid.interf.service;

import com.example.playandroid.entity.Article;
import com.example.playandroid.entity.ArticleResponse;
import com.example.playandroid.entity.Banner;
import com.example.playandroid.entity.DataResponse;
import com.example.playandroid.entity.SingleDataResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiService {

    @GET("banner/json")
    Call<DataResponse<Banner>> getBanner();

    @GET("article/list/{page}/json")
    Call<SingleDataResponse<ArticleResponse>> getArticle(@Path("page") int page);
}
