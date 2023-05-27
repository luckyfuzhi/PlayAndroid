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
//                Log.d("test12313", data);
//                if (data.contains("密码长度必须大于6位！")) {
//                    mPresenter.responseRegisterResult(PSW_LENGTH_LACK);
//                } else if (data.contains("用户名已经被注册!")) {
//                    mPresenter.responseRegisterResult(USERNAME_REGISTERED);
//                } else if(data.contains("注册成功")){
//                    mPresenter.responseRegisterResult(REGISTER_SUCCESS);
//                }
                Map<String, Object> parsedData = parseRegisterData(data);

                mPresenter.responseRegisterResult(parsedData.get("errorMsg").toString());

//                if(parsedData.get("errorMsg") != null){
//
//                    if (Objects.equals(Objects.requireNonNull(parsedData.get("errorMsg")).toString(), "密码长度必须大于6位")) {
//                        mPresenter.responseRegisterResult(PSW_LENGTH_LACK);
//                    } else if (Objects.equals(Objects.requireNonNull(parsedData.get("errorMsg")).toString(), "用户名已经被注册")) {
//                        Log.d("test222", Objects.requireNonNull(parsedData.get("errorMsg")).toString());
//                        mPresenter.responseRegisterResult(USERNAME_REGISTERED);
//
//                    }
//                } else {
//                    mPresenter.responseRegisterResult(REGISTER_SUCCESS);
//                }
            }

            @Override
            public void onFailure(Exception e) {
                e.printStackTrace();
                Log.e("sendRegisterData", e.toString());
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
            Log.e("KsChildModel", "parseArticleData: 解析数据出现异常");
        }
        return msgMap;
    }

}
