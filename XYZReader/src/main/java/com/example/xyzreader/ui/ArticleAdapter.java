package com.example.xyzreader.ui;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.xyzreader.R;
import com.example.xyzreader.data.model.Article;
import com.example.xyzreader.databinding.ListItemArticleBinding;

import java.util.ArrayList;
import java.util.List;

public class ArticleAdapter  extends RecyclerView.Adapter<ArticleListActivity.ViewHolder> {

    @Nullable
    private final ArticleClickCallback mArticleClickCallback;
    public interface ArticleClickCallback {
        void onListItemClick(Article article);
    }

    private ArrayList<Article> mData;

    ArticleAdapter(@Nullable ArticleClickCallback callback) {
        mArticleClickCallback = callback;
        mData = new ArrayList<>();
    }

    public void setList(ArrayList<Article> articles) {
        mData = articles;
    }

    @Override
    public long getItemId(int position) {
        return mData.get(position).getId();
    }

    public int getItemPosition(Article item) {
        return mData.indexOf(item);
    }

    public ArrayList<Article> getList() {
        return mData;
    }

    @Override
    @NonNull
    public ArticleListActivity.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ListItemArticleBinding mBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.list_item_article, parent, false);
        mBinding.setCallback(mArticleClickCallback);

        return new ArticleListActivity.ViewHolder(mBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ArticleListActivity.ViewHolder holder, int position) {
        holder.mBinding.setArticle(mData.get(position));
        holder.bindToData();
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }
}