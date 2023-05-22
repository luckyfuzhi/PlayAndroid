package com.example.playandroid.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.playandroid.R;
import com.example.playandroid.entity.Article;
import com.example.playandroid.entity.KnowledgeType;
import com.example.playandroid.view.activity.ArticleDetailActivity;
import com.example.playandroid.view.activity.KsChildActivity;

import java.util.List;


/**
 * 知识体系的种类RecyclerView的适配器
 */
public class KsTypeRecyclerAdapter extends RecyclerView.Adapter<KsTypeRecyclerAdapter.ViewHolder> {

    private List<KnowledgeType> mKnowledgeTypeList;
    List<String> mChildNameList;

    private Context mContext;


    public KsTypeRecyclerAdapter(List<KnowledgeType> mKnowledgeTypeList, List<String> childNameList) {
        this.mKnowledgeTypeList = mKnowledgeTypeList;
        this.mChildNameList = childNameList;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        View typeView;
        TextView name;
        TextView childrenName;
        ImageView checkDetail;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            typeView = itemView;
            name = itemView.findViewById(R.id.ks_type_name);
            childrenName = itemView.findViewById(R.id.ks_type_content);
            checkDetail = itemView.findViewById(R.id.check_detail);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.knowledge_system_type_item, parent,
                false);
        ViewHolder holder = new ViewHolder(view);
        mContext = parent.getContext();
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        KnowledgeType knowledgeType = mKnowledgeTypeList.get(position);
        holder.name.setText(knowledgeType.getName());
        holder.childrenName.setText(mChildNameList.get(position));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, KsChildActivity.class);
                intent.setAction("sendKnowledgeType");
                intent.putExtra("KnowledgeTypeData", knowledgeType);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mKnowledgeTypeList.size();
    }


}
