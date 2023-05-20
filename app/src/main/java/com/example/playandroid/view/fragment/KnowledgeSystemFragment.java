package com.example.playandroid.view.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.playandroid.R;
import com.example.playandroid.adapter.KsTypeRecyclerAdapter;
import com.example.playandroid.base.BaseFragment;
import com.example.playandroid.contract.KnowledgeSystemContract;
import com.example.playandroid.entity.Article;
import com.example.playandroid.entity.KnowledgeType;
import com.example.playandroid.presenter.KnowledgeSystemPresenter;

import java.util.ArrayList;
import java.util.List;

public class KnowledgeSystemFragment extends BaseFragment<KnowledgeSystemPresenter> implements KnowledgeSystemContract.VP {
    private final static int UPDATE_TYPE_RV = 1;

    private RecyclerView ksTypeRecyclerView;

    private List<KnowledgeType> knowledgeTypeList = new ArrayList<>();

    private List<String> childNameList = new ArrayList<>();

    private List<String> oneChildNameList = new ArrayList<>();

    private KsTypeRecyclerAdapter ksTypeRecyclerAdapter = new KsTypeRecyclerAdapter(knowledgeTypeList, childNameList);


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.knowledge_system_fragement, container, false);
        ksTypeRecyclerView = view.findViewById(R.id.knowledge_system_type_rv);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this.getContext(), LinearLayoutManager.VERTICAL, false);
        ksTypeRecyclerView.setLayoutManager(mLayoutManager);
        ksTypeRecyclerView.addItemDecoration(new DividerItemDecoration(this.getContext(),
                DividerItemDecoration.VERTICAL));//设置分界线
        ksTypeRecyclerView.setAdapter(ksTypeRecyclerAdapter);
        return view;
    }

    @Override
    public int getFragmentId() {
        return R.layout.knowledge_system_fragement;
    }

    @Override
    public void initData() {
        requestKsData();
    }

    @Override
    public void initView() {

    }

    private Handler handler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(@NonNull Message message) {
            switch (message.what){
                case UPDATE_TYPE_RV:
                    ksTypeRecyclerAdapter.notifyDataSetChanged();
                    break;
                default:
                    break;
            }
        }
    };


    @Override
    public KnowledgeSystemPresenter getPresenterInstance() {
        return new KnowledgeSystemPresenter();
    }

    @Override
    public void requestKsData() {
        mPresenter.requestKsData();
    }
int k = 0;
    @Override
    public void requestKsDataResult(List<KnowledgeType> knowledgeTypeList) {

        this.knowledgeTypeList.addAll(knowledgeTypeList);

        for (int i = 0; i < knowledgeTypeList.size(); i++) {

            StringBuilder stringBuilder = new StringBuilder();
            for (int j = 0; j < (knowledgeTypeList.get(i).getChildList()).size(); j++) {
                stringBuilder.append(knowledgeTypeList.get(i).getChildList().get(j).getName() + "  ");

            }
            childNameList.add(stringBuilder.toString());

        }
        Message message = new Message();
        message.what = UPDATE_TYPE_RV;
        handler.sendMessage(message);
    }


}
