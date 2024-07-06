package com.example.playandroid.adapter;

import android.annotation.SuppressLint;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.playandroid.R;
import com.example.playandroid.entity.Article;
import com.example.playandroid.entity.CollectArticle;
import com.example.playandroid.interf.clicklistener.CollectionArticleClickListener;
import com.example.playandroid.interf.clicklistener.ProjectArticleItemListener;
import com.example.playandroid.interf.datacallback.DataCallBackForArticleAdapter;

import java.util.List;

public class CollectionAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private static final int VIEW_TYPE_ITEM = 0;
    private static final int VIEW_TYPE_LOADING = 1;

    private List<CollectArticle> articleList;
    DataCallBackForArticleAdapter dataCallBack;

    private CollectionArticleClickListener mCollectionArticleClickListener;

    public CollectionAdapter(List<CollectArticle> articleList, DataCallBackForArticleAdapter dataCallBack) {
        this.articleList = articleList;
        this.dataCallBack = dataCallBack;
    }

    public void setOnRecyclerItemClickListener(CollectionArticleClickListener collectionArticleClickListener) {
        this.mCollectionArticleClickListener = collectionArticleClickListener;
    }

    @Override
    public int getItemViewType(int position) {
        return articleList.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ITEM) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.article_item, parent,
                    false);
            return new ArticleViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_loading, parent,
                    false);
            return new LoadingViewHolder(view);
        }
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ArticleViewHolder) {
            CollectArticle article = articleList.get(position);
            ((ArticleViewHolder)holder).author.setText("作者：" + article.getAuthor());
            ((ArticleViewHolder)holder).type.setText("类别：" + article.getChapterName() + "/" + article.getChapterName());
            ((ArticleViewHolder)holder).time.setText("时间：" + article.getNiceDate());
            ((ArticleViewHolder)holder).title.setText(Html.fromHtml(article.getTitle()));//解决返回带有html代码的数据问题
            ((ArticleViewHolder)holder).loveImg.setSelected(true);
            int itemPosition = position;
            ((ArticleViewHolder)holder).articleView.setOnClickListener(view ->
                    mCollectionArticleClickListener.onItemClick(itemPosition, articleList));
        } else if (holder instanceof LoadingViewHolder) {
            ((LoadingViewHolder) holder).progressBar.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return articleList == null ? 0 : articleList.size();
    }

    static class ArticleViewHolder extends RecyclerView.ViewHolder {

        View articleView;
        TextView title;
        TextView author;
        TextView type;
        TextView time;
        ImageView loveImg;

        public ArticleViewHolder(@NonNull View itemView) {
            super(itemView);
            articleView = itemView;
            title = itemView.findViewById(R.id.article_title);
            author = itemView.findViewById(R.id.article_author);
            type = itemView.findViewById(R.id.article_type);
            time = itemView.findViewById(R.id.article_time);
            loveImg = itemView.findViewById(R.id.love_img);
        }
    }

    static class LoadingViewHolder extends RecyclerView.ViewHolder {

        ProgressBar progressBar;

        public LoadingViewHolder(@NonNull View itemView) {
            super(itemView);
            progressBar = itemView.findViewById(R.id.progressBar);
        }
    }
}
