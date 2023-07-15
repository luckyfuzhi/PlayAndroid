package com.example.playandroid.interf.service;

import com.example.playandroid.entity.Article;
import com.example.playandroid.entity.ArticleResponse;
import com.example.playandroid.entity.Banner;
import com.example.playandroid.entity.DataResponse;
import com.example.playandroid.entity.SingleDataResponse;
import com.example.playandroid.entity.User;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface FpApiService {

    @GET("banner/json")
    Call<DataResponse<Banner>> getBanner();

    @GET("article/list/{page}/json")
    Call<SingleDataResponse<ArticleResponse>> getArticle(@Path("page") int page);

    @GET("article/top/json")
    Call<DataResponse<Article>> getTopArticle();


}
