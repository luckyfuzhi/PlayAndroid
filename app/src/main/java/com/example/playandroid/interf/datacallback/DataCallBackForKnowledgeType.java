package com.example.playandroid.interf.datacallback;

import com.example.playandroid.entity.KnowledgeType;

import java.util.List;

public interface DataCallBackForKnowledgeType {

    void onSuccess(List<KnowledgeType> knowledgeTypeList);

    void onFailure(Exception e);
}
