package com.example.playandroid.base;

public abstract class BasePresenterForFragment<V extends BaseFragment, M extends BaseModelForFragment>{

    public V mView;

    public M mModel;

    public BasePresenterForFragment(){
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
