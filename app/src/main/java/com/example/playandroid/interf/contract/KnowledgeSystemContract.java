package com.example.playandroid.interf.contract;

import com.example.playandroid.entity.KnowledgeType;

import java.util.List;

/**
 * 知识体系模块契约接口
 */
public interface KnowledgeSystemContract {
    interface M {
        void requestKsData();//返回知识体系数据
    }

    interface VP {

        void requestKsData();//请求知识体系数据

        void requestKsDataResult(List<KnowledgeType> knowledgeTypeList);//返回知识体系数据

    }
}
