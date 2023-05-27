package com.example.playandroid.interf.contract;

import java.util.Map;

/**
 * 注册模块契约接口
 */
public interface RegisterContract {
    interface M {

        void sendRegisterData(Map<String, String> paramMap);//发送注册数据

    }

    interface VP {

        void sendRegisterData(Map<String, String> paramMap);//发送注册数据

        void responseRegisterResult(String registerResult);//返回注册数据

    }


}
