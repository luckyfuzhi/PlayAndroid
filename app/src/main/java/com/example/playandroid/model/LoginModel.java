package com.example.playandroid.model;

import android.content.SharedPreferences;
import android.util.Log;

import com.example.playandroid.base.BaseModel;
import com.example.playandroid.interf.contract.LoginContract;
import com.example.playandroid.interf.datacallback.DataCallBack;
import com.example.playandroid.presenter.LoginPresenter;
import com.example.playandroid.util.WebUtil;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LoginModel extends BaseModel<LoginPresenter> implements LoginContract.M {

    //登录网址
    private final static String LOGIN_URL = "https://www.wanandroid.com/user/login";

    public LoginModel(LoginPresenter mPresenter) {
        super(mPresenter);
    }

    @Override
    public void requestLoginData(String account, String password) throws Exception {
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("username", account);
        paramMap.put("password", password);

        WebUtil.postDataToWeb(LOGIN_URL, paramMap, new DataCallBack() {
            @Override
            public void onSuccess(String data) {

                Map<String, Object> parsedData = parseLoginData(data);

                mPresenter.responseLoginResult(parsedData.get("errorMsg").toString());

            }

            @Override
            public void onFailure(Exception e) {
                e.printStackTrace();
                Log.e("requestLoginData", "登录异常/" + e);
            }

            @Override
            public void getCookie(List<String> setCookieList) {

                mPresenter.responseCookie(setCookieList);
            }
        });
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
