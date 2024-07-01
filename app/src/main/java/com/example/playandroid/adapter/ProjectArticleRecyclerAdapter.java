package com.example.playandroid.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.playandroid.R;
import com.example.playandroid.entity.Project;
import com.example.playandroid.interf.clicklistener.ProjectArticleItemListener;

import java.util.List;
import java.util.Objects;


/**
 * 项目文章recyclerView的适配器
 */
public class ProjectArticleRecyclerAdapter extends RecyclerView.Adapter<ProjectArticleRecyclerAdapter.ViewHolder> {

    List<Project> mProjectArticleList;

    private ProjectArticleItemListener mProjectArticleItemListener;

//    private final DataCallBackForArticleAdapter dataCallBack;


    public ProjectArticleRecyclerAdapter(List<Project> projectList) {
        this.mProjectArticleList = projectList;
//        this.dataCallBack = new WeakReference<>(dataCallBack).get();
    }

    public void setOnRecyclerItemClickListener(ProjectArticleItemListener projectArticleItemListener) {
        this.mProjectArticleItemListener = projectArticleItemListener;
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
        if (projectArticle.getEnvelopePic() == null || Objects.equals(projectArticle.getEnvelopePic(), "")) {
            holder.projectImg.setImageResource(R.drawable.no_img);
        } else {
            Glide.with(holder.projectView.getContext())
                    .applyDefaultRequestOptions(new RequestOptions().placeholder(R.drawable.no_img))
                    .load(projectArticle.getEnvelopePic())
                    .into(holder.projectImg);
        }
        int itemPosition = position;
        holder.projectView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mProjectArticleItemListener.onItemClick(itemPosition, mProjectArticleList);
            }
        });

//        dataCallBack.getLoveImg(holder.loveImg);

    }

    @Override
    public int getItemCount() {
        return mProjectArticleList.size();
    }

}
