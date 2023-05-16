package com.example.playandroid.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.playandroid.R;
import com.example.playandroid.entity.FPArticle;

import java.util.List;

public class FPArticleRecyclerAdapter extends RecyclerView.Adapter<FPArticleRecyclerAdapter.ViewHolder>{

    List<FPArticle> mArticleList;

    public FPArticleRecyclerAdapter(List<FPArticle> articleList) {
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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fp_article_item, parent,
                 false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        FPArticle article = mArticleList.get(position);
        holder.title.setText(article.getTitle());
        holder.author.setText("作者：" + article.getAuthor());
        holder.type.setText("类别：" + article.getSuperChapterName() + "/" + article.getChapterName());
        holder.time.setText("时间：" + article.getNiceShareDate());
    }

    @Override
    public int getItemCount() {
        return mArticleList.size();
    }


}
