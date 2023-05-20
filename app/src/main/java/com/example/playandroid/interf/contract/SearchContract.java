package com.example.playandroid.interf.contract;

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
