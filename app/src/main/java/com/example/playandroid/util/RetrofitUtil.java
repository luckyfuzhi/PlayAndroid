package com.example.playandroid.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.NonNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import com.example.playandroid.MyApplication;
import com.example.playandroid.manager.TokenManager;
import com.franmontiel.persistentcookiejar.PersistentCookieJar;
import com.franmontiel.persistentcookiejar.cache.SetCookieCache;
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor;



public class RetrofitUtil {
    private static final String BASE_URL = "https://www.wanandroid.com/";

    private static Retrofit retrofit;
    private final static PersistentCookieJar cookieJar = new PersistentCookieJar(new SetCookieCache(),
            new SharedPrefsCookiePersistor(MyApplication.getContext()));
    private static final OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS)
            .readTimeout(12, TimeUnit.SECONDS)
            .cookieJar(cookieJar)
            .addInterceptor(new AuthInterceptor())
            .addInterceptor(new ReceivedCookiesInterceptor())
            .build();

    public static Retrofit getRetrofitInstance() {
        if (retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return retrofit;
    }


    // 自定义的AuthInterceptor拦截器
    private static class AuthInterceptor implements Interceptor {

        @NonNull
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request originalRequest = chain.request();
            Request.Builder requestBuilder = originalRequest.newBuilder();

            // 如果有token，则添加到请求头部
            String token = TokenManager.getSavedTokenFromStorage(); // 从缓存或SharedPreferences中获取token
            if (token != null) {
                requestBuilder.header("Authorization", "Bearer " + token);
            }

            Request newRequest = requestBuilder.build();
            return chain.proceed(newRequest);
        }
    }

    // 自定义的ReceivedCookiesInterceptor拦截器
    private static class ReceivedCookiesInterceptor implements Interceptor {
        @NonNull
        @Override
        public Response intercept(Chain chain) throws IOException {
            Response response = chain.proceed(chain.request());
            Request request = chain.request();

            if (request.url().toString().contains("login")) {
                // 获取Set-Cookie头部
                if (!response.headers("Set-Cookie").isEmpty()) {
                    HashSet<String> cookies = new HashSet<>(response.headers("Set-Cookie"));
                    // 保存cookies到SharedPreferences
                    SharedPreferences sharedPreferences = MyApplication.getContext().getSharedPreferences("cookies_prefs", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putStringSet("cookies", cookies);
                    editor.apply();
                }
            }

            return response;
        }
    }

}
