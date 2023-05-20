package com.example.playandroid.entity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.List;

public class KnowledgeType implements Serializable {
    private List<KnowledgeType> childList;
    private Integer id;
    private String name;

    public List<KnowledgeType> getChildList() {
        return childList;
    }

    public void setChildList(List<KnowledgeType> childList) {
        this.childList = childList;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
