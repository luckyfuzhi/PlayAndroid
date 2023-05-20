package com.example.playandroid.contract;

import com.example.playandroid.entity.ProjectType;

import java.util.List;

public interface SearchContract {

    interface M {
        void requestHotWord();

    }

    interface VP {

        void requestHotWord();

        void requestHotWordResult(List<String> hotWordList);


    }

}
