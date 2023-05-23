package com.example.playandroid.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.playandroid.R;
import com.example.playandroid.entity.Project;
import com.example.playandroid.view.activity.ArticleDetailActivity;

import java.util.List;


/**
 * 项目文章recyclerView的适配器
 */
public class ProjectArticleRecyclerAdapter extends RecyclerView.Adapter<ProjectArticleRecyclerAdapter.ViewHolder> {

    List<Project> mProjectArticleList;

    List<Bitmap> bitmapList;

    private Context mContext;

    public ProjectArticleRecyclerAdapter(List<Project> projectList, List<Bitmap> bitmapList) {
        this.mProjectArticleList = projectList;
        this.bitmapList = bitmapList;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        View projectView;
        TextView title;
        TextView author;

        TextView desc;
        TextView time;

        ImageView projectImg;
        ImageView loveImg;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            projectView = itemView;
            title = itemView.findViewById(R.id.project_article_title);
            author = itemView.findViewById(R.id.project_article_author);
            desc = itemView.findViewById(R.id.project_article_desc);
            time = itemView.findViewById(R.id.project_article_time);
            loveImg = itemView.findViewById(R.id.project_article_love);
            projectImg = itemView.findViewById(R.id.project_article_img);
            
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.project_article_item, parent,
                false);
        ViewHolder holder = new ViewHolder(view);
        mContext = parent.getContext();
        return holder;
    }


    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Project projectArticle = mProjectArticleList.get(position);
        holder.title.setText(projectArticle.getTitle());
        holder.author.setText("作者：" + projectArticle.getAuthor());
        holder.desc.setText(projectArticle.getDesc());
        holder.time.setText("时间：" + projectArticle.getNiceShareDate());
        if (bitmapList.size() > position) {
            if (bitmapList.get(position) != null){
                holder.projectImg.setImageBitmap(bitmapList.get(position));
            }
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, ArticleDetailActivity.class);
                intent.setAction("sendArticleData");
                intent.putExtra("articleLink", projectArticle.getLink());
                intent.putExtra("title", projectArticle.getTitle());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mProjectArticleList.size();
    }

}
