package com.example.playandroid.entity;

/**
 *  之所以用这个类是因为不是所有的data返回的都是jsonArray，这个类适用于jsonObject
 * @param <E> 返回的jsonObject对应的实体类
 */
public class SingleDataResponse<E> {
    private E data;
    private int errorCode;
    private String errorMsg;

    public E getData() {
        return data;
    }

    public void setData(E data) {
        this.data = data;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}
