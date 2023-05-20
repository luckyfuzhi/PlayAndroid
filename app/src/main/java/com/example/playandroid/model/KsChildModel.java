package com.example.playandroid.model;

import android.util.Log;

import com.example.playandroid.base.BaseModel;
import com.example.playandroid.contract.DataCallBack;
import com.example.playandroid.contract.DataCallBackForArticle;
import com.example.playandroid.contract.KnowledgeSystemContract;
import com.example.playandroid.contract.KsChildContract;
import com.example.playandroid.entity.Article;
import com.example.playandroid.presenter.KsChildPresenter;
import com.example.playandroid.util.WebUtil;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class KsChildModel extends BaseModel<KsChildPresenter>{


    public KsChildModel(KsChildPresenter mPresenter) {
        super(mPresenter);
    }


}
