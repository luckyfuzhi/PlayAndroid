package com.example.playandroid.interf.service;

import com.example.playandroid.entity.SingleDataResponse;
import com.example.playandroid.entity.User;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface LoginApiService {

    @FormUrlEncoded
    @POST("user/login")
    Call<SingleDataResponse<User>> getLoginInfo(@Field("username") String username, @Field("password") String password);

}
