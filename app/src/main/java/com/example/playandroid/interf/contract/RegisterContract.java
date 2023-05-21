package com.example.playandroid.interf.contract;

import java.util.Map;

public interface RegisterContract {
    interface M{

        void sendRegisterData(Map<String, String> paramMap);//发送注册数据

    }

    interface VP{

        void sendRegisterData(Map<String, String> paramMap);//发送注册数据

        void responseRegisterResult(int registerResult);//返回注册数据

    }


}
