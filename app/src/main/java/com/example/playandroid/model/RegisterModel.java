package com.example.playandroid.model;

import android.util.Log;

import com.example.playandroid.base.BaseModel;
import com.example.playandroid.entity.Article;
import com.example.playandroid.interf.contract.RegisterContract;
import com.example.playandroid.interf.datacallback.DataCallBack;
import com.example.playandroid.presenter.RegisterPresenter;
import com.example.playandroid.util.WebUtil;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class RegisterModel extends BaseModel<RegisterPresenter> implements RegisterContract.M {

    //注册网址
    private final static String REGISTER_URL = "https://www.wanandroid.com/user/register";

    private final static int PSW_LENGTH_LACK = -2;
    private final static int REGISTER_SUCCESS = 1;

    private final static int USERNAME_REGISTERED = -1;

    public RegisterModel(RegisterPresenter mPresenter) {
        super(mPresenter);
    }

    @Override
    public void sendRegisterData(Map<String, String> paramMap) {
        WebUtil.postDataToWeb(REGISTER_URL, paramMap, new DataCallBack() {
            @Override
            public void onSuccess(String data) {

                Map<String, Object> parsedData = parseRegisterData(data);

                mPresenter.responseRegisterResult(parsedData.get("errorMsg").toString());

            }

            @Override
            public void onFailure(Exception e) {
                e.printStackTrace();
                Log.e("sendRegisterData", e.toString());
            }

            @Override
            public void getCookie(List<String> setCookieList) {

            }
        });
    }

    public Map<String, Object> parseRegisterData(String data){
        Map<String, Object> msgMap = new HashMap<>();
        try {
            JSONObject jsonObject = new JSONObject(data);

            msgMap.put("data", jsonObject.getString("data"));
            msgMap.put("errorCode", jsonObject.getInt("errorCode"));
            msgMap.put("errorMsg", jsonObject.getString("errorMsg"));

        } catch (Exception e) {
            e.printStackTrace();
            Log.e("RegisterModel", "parseRegisterData: 解析数据出现异常");
        }
        return msgMap;
    }

}
