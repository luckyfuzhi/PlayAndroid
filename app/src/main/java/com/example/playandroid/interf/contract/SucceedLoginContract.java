package com.example.playandroid.interf.contract;


public interface SucceedLoginContract {
    interface M {

        void exitLogin();//发送退出登录数据

    }

    interface VP {

        void exitLogin();//发送退出登录数据

        void exitLoginResult();//返回退出登录数据


    }

}
