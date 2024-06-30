package com.example.playandroid.interf.service;

import com.example.playandroid.entity.ProjectList;
import com.example.playandroid.entity.SingleDataResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ProjectService {

    @GET("project/list/{page}/json?cid={cid}")
    Call<SingleDataResponse<ProjectList>> getProjectList(@Path("page") int page, @Path("cid") int cid);
}
