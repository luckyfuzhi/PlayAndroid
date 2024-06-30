package com.example.playandroid.entity;

import java.util.List;

public class ProjectList {
    private int curPage;
    private int offset;
    private boolean over;
    private int pageCount;
    private int size;

    public int getCurPage() {
        return curPage;
    }

    public void setCurPage(int curPage) {
        this.curPage = curPage;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public boolean isOver() {
        return over;
    }

    public void setOver(boolean over) {
        this.over = over;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<Project> getData() {
        return data;
    }

    public void setData(List<Project> data) {
        this.data = data;
    }

    private int total;
    private List<Project> data;
}
