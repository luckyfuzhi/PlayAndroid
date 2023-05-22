package com.example.playandroid.interf.datacallback;

import com.example.playandroid.entity.KnowledgeType;

import java.util.List;

/**
 * 回调知识体系种类数据
 */
public interface DataCallBackForKnowledgeType {

    void onSuccess(List<KnowledgeType> knowledgeTypeList);

    void onFailure(Exception e);
}
