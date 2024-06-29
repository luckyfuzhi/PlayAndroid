package com.example.playandroid.model;

import android.content.SharedPreferences;
import android.util.Log;

import com.example.playandroid.base.BaseModel;
import com.example.playandroid.entity.SingleDataResponse;
import com.example.playandroid.entity.User;
import com.example.playandroid.interf.contract.LoginContract;
import com.example.playandroid.interf.datacallback.DataCallBack;
import com.example.playandroid.interf.service.LoginApiService;
import com.example.playandroid.manager.TokenManager;
import com.example.playandroid.presenter.LoginPresenter;
import com.example.playandroid.util.RetrofitUtil;
import com.example.playandroid.util.WebUtil;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import okhttp3.Cookie;
import okhttp3.Headers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginModel extends BaseModel<LoginPresenter> implements LoginContract.M {

    //登录网址
    private final static String LOGIN_URL = "https://www.wanandroid.com/user/login";

    private final LoginApiService apiService = RetrofitUtil.getRetrofitInstance()
            .create(LoginApiService.class);

    public LoginModel(LoginPresenter mPresenter) {
        super(mPresenter);
    }

    @Override
    public void requestLoginData(String account, String password) throws Exception {
//        Map<String, String> paramMap = new HashMap<>();
//        paramMap.put("username", account);
//        paramMap.put("password", password);

//        WebUtil.postDataToWeb(LOGIN_URL, paramMap, new DataCallBack() {
//            @Override
//            public void onSuccess(String data) {
//
//                Map<String, Object> parsedData = parseLoginData(data);
//
//                mPresenter.responseLoginResult(parsedData.get("errorMsg").toString());
//
//            }
//
//            @Override
//            public void onFailure(Exception e) {
//                e.printStackTrace();
//                Log.e("requestLoginData", "登录异常/" + e);
//            }
//
//            @Override
//            public void getCookie(List<String> setCookieList) {
//
//                mPresenter.responseCookie(setCookieList);
//            }
//        });



        Call<SingleDataResponse<User>> userCall = apiService.getLoginInfo(account, password);
        //这里没有保存Cookie是因为PersistentCookieJar包已经自动帮我们保存了，并且在下一次要用到Cookie的时候它会
        //自动帮我们交给OkHttp，所以我们只需要处理服务器收到Cookie后的响应报文就好了

        userCall.enqueue(new Callback<SingleDataResponse<User>>() {
            @Override
            public void onResponse(Call<SingleDataResponse<User>> call, Response<SingleDataResponse<User>> response) {
                if (response.isSuccessful()){
                    // 请求成功，处理正常响应
                    SingleDataResponse<User> dataResponse = response.body();
                    Headers headers = response.headers();
                    if (dataResponse != null){
                        //User user = dataResponse.getData();
                        mPresenter.responseLoginResult(dataResponse.getErrorMsg());
                    }
                    int errorCode = response.code();
                    String errorMessage = response.message();
                    mPresenter.responseLoginState(errorCode, errorMessage);


                    //如果有token就用下面的方法获取token
                    String authorizationHeader = response.headers().get("Authorization");
                    String token = extractTokenFromAuthorizationHeader(authorizationHeader);
                    TokenManager.saveTokenToStorage(token);

                } else {
                    // 请求失败，处理错误响应
                    // 获取错误码和错误信息
                    int errorCode = response.code();
                    String errorMessage = response.message();
                    mPresenter.responseLoginState(errorCode, errorMessage);
                    Log.e("LoginModel", "requestLoginData: 响应错误，可能是Cookie失效");
                }
            }

            @Override
            public void onFailure(Call<SingleDataResponse<User>> call, Throwable t) {
                /// 请求失败，处理网络或其他异常
                t.printStackTrace();
                Log.e("LoginModel", "requestLoginData:" +  t);
            }
        });



    }


    /**
     *  从Header的Authorization字段的值中提取出token
     * @param authorizationHeader Authorization字段的值中
     * @return token
     */
    private String extractTokenFromAuthorizationHeader(String authorizationHeader){
        if (authorizationHeader != null && authorizationHeader.startsWith("bearer ")){
            return authorizationHeader.substring(7);
        }
        return null;
    }



    /**
     * 整合cookie为唯一字符串(现在有了PersistentCookieJar之后这个方法就没用了)
     */
    private String encodeCookie(List<String> cookies) {
        StringBuilder sb = new StringBuilder();
        Set<String> cookieSet = new HashSet<>();
        if(cookies != null){
            for (String cookie : cookies) {
                String[] arr = cookie.split(";");
                for (String s : arr) {
                    if (cookieSet.contains(s)) {
                        continue;
                    }
                    cookieSet.add(s);

                }
            }

            for (String cookie : cookieSet) {
                sb.append(cookie).append(";");
            }

            int last = sb.lastIndexOf(";");
            if (sb.length() - 1 == last) {
                sb.deleteCharAt(last);
            }

        }

        return sb.toString();
    }

    /**
     *  把cookie字符串转换为cookie对象的集合（这个也没用了）
     * @param cookieString cookie字符串
     * @return cookie对象的集合
     */
    private List<Cookie> convertStringToCookies(String cookieString) {
        List<Cookie> cookies = new ArrayList<>();

        // 按分号和空格拆分 Cookie 字符串
        String[] cookieArray = cookieString.split(";");

        // 遍历拆分后的字符串数组，并解析每个 Cookie 项
        for (String cookieItem : cookieArray) {
            // 按等号拆分 Cookie 项，获取名称和值
            String[] cookieParts = cookieItem.split("=");

            if (cookieParts.length == 2) {
                // 创建 Cookie 对象并添加到列表中
                Cookie cookie = new Cookie.Builder()
                        .name(cookieParts[0].trim())
                        .value(cookieParts[1].trim())
                        // 设置其他属性（例如域名、路径等）
                        // ...
                        .build();

                cookies.add(cookie);
            }
        }

        return cookies;
    }

    public Map<String, Object> parseLoginData(String data){
        Map<String, Object> msgMap = new HashMap<>();
        try {
            JSONObject jsonObject = new JSONObject(data);

            msgMap.put("data", jsonObject.getString("data"));
            msgMap.put("errorCode", jsonObject.getInt("errorCode"));
            msgMap.put("errorMsg", jsonObject.getString("errorMsg"));

        } catch (Exception e) {
            e.printStackTrace();
            Log.e("LoginModel", "parseLoginData: 解析数据出现异常");
        }
        return msgMap;
    }


}
