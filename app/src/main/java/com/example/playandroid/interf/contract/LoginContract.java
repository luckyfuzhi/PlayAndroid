package com.example.playandroid.interf.contract;


/**
 * 登录功能的契约合同，规范M层，V层，P层的一些功能
 */
public interface LoginContract {

    interface M{

        void requestLoginData(String account, String password) throws Exception;

    }

    interface VP{

        void requestLogin(String account, String password);

        void responseLoginResult(boolean loginResult);

    }

}
