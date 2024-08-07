package com.example.playandroid.interf.contract;


import java.util.List;

/**
 * 登录功能的契约合同，规范M层，V层，P层的一些功能
 */
public interface LoginContract {

    interface M {

        void requestLoginData(String account, String password) throws Exception;//返回登录数据

    }

    interface VP {

        void requestLogin(String account, String password);//请求登录数据

        void responseLoginResult(String loginResult);//返回登录结果

        void responseCookie(List<String> setCookies);//返回cookie数据

        void responseLoginState(int errorCode, String errorMessage);//返回状态码和状态信息
    }

}
