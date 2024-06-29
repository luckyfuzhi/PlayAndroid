package com.example.playandroid.util;

import androidx.annotation.NonNull;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;


/**
 * token拦截器类
 */
public class InterceptorUtil implements Interceptor {

    private String token;

    public InterceptorUtil(String token) {
        this.token = token;
    }

    @NonNull
    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Request originalRequest = chain.request();
        Request.Builder requestBuilder = originalRequest.newBuilder()
                .header("Authorization", "Bearer" + token);
        Request newRequest = requestBuilder.build();
        return chain.proceed(newRequest);
    }


}
