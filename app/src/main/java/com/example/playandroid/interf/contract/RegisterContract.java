package com.example.playandroid.interf.contract;

import java.util.Map;

public interface RegisterContract {
    interface M{

        void sendRegisterData(Map<String, String> paramMap);

    }

    interface VP{

        void sendRegisterData(Map<String, String> paramMap);

        void responseRegisterResult(int registerResult);

    }


}
