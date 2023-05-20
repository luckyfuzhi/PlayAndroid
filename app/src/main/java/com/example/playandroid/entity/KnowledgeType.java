package com.example.playandroid.entity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

public class KnowledgeType {
    private JSONArray childrenChapter;
    private List<JSONObject> childList;
    private int id;
    private String name;

    public List<JSONObject> getChildList() {
        return childList;
    }

    public void setChildList(List<JSONObject> childList) {
        this.childList = childList;
    }

    public JSONArray getChildrenChapter() {
        return childrenChapter;
    }

    public void setChildrenChapter(JSONArray childrenChapter) {
        this.childrenChapter = childrenChapter;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
