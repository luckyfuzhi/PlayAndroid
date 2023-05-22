package com.example.playandroid.interf.contract;

import java.util.List;

/**
 * 搜索模块契约接口
 */
public interface SearchContract {

    interface M {
        void requestHotWord();//返回搜索热词数据

    }

    interface VP {

        void requestHotWord();////请求搜索热词数据

        void requestHotWordResult(List<String> hotWordList);//返回搜索热词数据


    }

}
