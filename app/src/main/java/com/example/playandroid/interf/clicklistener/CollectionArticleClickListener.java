package com.example.playandroid.interf.clicklistener;

import com.example.playandroid.entity.CollectArticle;
import com.example.playandroid.entity.Project;

import java.util.List;

public interface CollectionArticleClickListener {
    void onItemClick(int position, List<CollectArticle> mCollectArticleList);
}
