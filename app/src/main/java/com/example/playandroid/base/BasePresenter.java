package com.example.playandroid.base;

import com.example.playandroid.contract.LoginContract;

public abstract class BasePresenter<V extends BaseActivity, M extends BaseModel>{

    public V mView;

    public M mModel;

    public BasePresenter(){
        this.mModel = getModelInstance();
    }

    /**
     *  绑定view
     * @param mView 传进来的view
     */
    public void bindView(V mView){
        this.mView = mView;
    }

    /**
     * 解绑view
     */
    public void unBindView(){
        this.mView = null;
    }

    public abstract M getModelInstance();

}
