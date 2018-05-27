package com.example.xyzreader.ui;

import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.Spanned;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.xyzreader.R;
import com.example.xyzreader.data.model.Article;
import com.example.xyzreader.databinding.ListItemArticleBinding;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;

public class ArticlesListAdapter extends RecyclerView.Adapter<ArticlesListAdapter.ViewHolder> {

    @Nullable
    private final ArticleClickCallback mArticleClickCallback;
    public interface ArticleClickCallback {
        void onListItemClick(View view, Article article);
    }

    private ArrayList<Article> mData;

    ArticlesListAdapter(@Nullable ArticleClickCallback callback) {
        this.mArticleClickCallback = callback;
        this.mData = new ArrayList<>();
    }

    public void setList(ArrayList<Article> articles) {
        mData = articles;
    }

    @Override
    public long getItemId(int position) {
        return mData.get(position).getId();
    }

    public ArrayList<Article> getList() {
        return mData;
    }

    @Override
    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ListItemArticleBinding mBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.list_item_article, parent, false);
        mBinding.setCallback(mArticleClickCallback);

        return new ViewHolder(mBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Article article = mData.get(position);
        holder.mBinding.setArticle(article);

        holder.bindToData();
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }


    static class ViewHolder extends RecyclerView.ViewHolder {

        // Use default locale format
        private SimpleDateFormat outputFormat = new SimpleDateFormat();
        // Most time functions can only handle 1902 - 2037
        private GregorianCalendar START_OF_EPOCH = new GregorianCalendar(2,1,1);

        ListItemArticleBinding mBinding;

        ViewHolder(ListItemArticleBinding binding) {
            super(binding.getRoot());

            this.mBinding = binding;
        }

        void bindToData() {
            Article article = mBinding.getArticle();
            mBinding.articleTitle.setText(article.getTitle());

            Spanned text;
            if (!article.getPublishedDate().before(START_OF_EPOCH.getTime())) {
                text = Html.fromHtml(
                        DateUtils.getRelativeTimeSpanString(
                                mBinding.getArticle().getPublishedDate().getTime(),
                                System.currentTimeMillis(), DateUtils.HOUR_IN_MILLIS,
                                DateUtils.FORMAT_ABBREV_ALL).toString()
                                + "<br/>" + " by "
                                + article.getAuthor());

            } else {
                text = Html.fromHtml(
                        outputFormat.format(article.getPublishedDate())
                                + "<br/>" + " by "
                                + article.getAuthor());
            }
            mBinding.articleByline.setText(text);
            Picasso.get()
                    .load(article.getThumbnailUrl())
                    .into(target);
        }

        Target target = new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                mBinding.articleImageThumbnail.setImageBitmap(bitmap);

                Palette.from(bitmap)
                        .generate(palette -> {
                            Palette.Swatch textSwatch = palette.getDominantSwatch();

                            if (textSwatch != null) {
                                mBinding.articleTitle.setTextColor(textSwatch.getTitleTextColor());
                                mBinding.articleByline.setTextColor(textSwatch.getBodyTextColor());

                                mBinding.textBackground.setBackgroundColor(textSwatch.getRgb());
                                mBinding.textBackground.setAlpha(1f);
                            }
                        });
            }

            @Override
            public void onBitmapFailed(Exception e, Drawable errorDrawable) {
                mBinding.articleImageThumbnail.setImageResource(R.drawable.ic_broken_image_black_24dp);
            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {
                mBinding.articleImageThumbnail.setImageResource(R.drawable.ic_image_black_24dp);
            }
        };
    }
}