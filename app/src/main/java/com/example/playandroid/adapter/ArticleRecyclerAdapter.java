package com.example.playandroid.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.playandroid.R;
import com.example.playandroid.entity.Article;
import com.example.playandroid.view.activity.ArticleDetailActivity;

import java.util.List;

public class ArticleRecyclerAdapter extends RecyclerView.Adapter<ArticleRecyclerAdapter.ViewHolder>{

    List<Article> mArticleList;

    private Context mContext;

    public ArticleRecyclerAdapter(List<Article> articleList) {
        this.mArticleList = articleList;
    }

    static class ViewHolder extends RecyclerView.ViewHolder{

        View articleView;
        TextView title;
        TextView author;
        TextView type;
        TextView time;
        ImageView loveImg;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            articleView = itemView;
            title = itemView.findViewById(R.id.article_title);
            author = itemView.findViewById(R.id.article_author);
            type = itemView.findViewById(R.id.article_type);
            time = itemView.findViewById(R.id.article_time);
            loveImg = itemView.findViewById(R.id.love_img);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.article_item, parent,
                 false);
        ViewHolder holder = new ViewHolder(view);
        mContext = parent.getContext();
        return holder;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Article article = mArticleList.get(position);
        holder.title.setText(article.getTitle());
        holder.author.setText("作者：" + article.getAuthor());
        holder.type.setText("类别：" + article.getSuperChapterName() + "/" + article.getChapterName());
        holder.time.setText("时间：" + article.getNiceShareDate());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, ArticleDetailActivity.class);
                intent.setAction("sendArticleData");
                intent.putExtra("articleLink", article.getLink());
                intent.putExtra("title", article.getTitle());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mArticleList.size();
    }


}