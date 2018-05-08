package com.example.xyzreader.ui;

import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.DividerItemDecoration;
import android.text.Html;
import android.text.Spanned;
import android.text.format.DateUtils;

import com.example.xyzreader.R;
import com.example.xyzreader.data.UpdaterService;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import dagger.android.AndroidInjection;

import com.example.xyzreader.data.database.ArticlesDao;
import com.example.xyzreader.data.model.Article;
import com.example.xyzreader.databinding.ActivityArticleListBinding;
import com.example.xyzreader.databinding.ListItemArticleBinding;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;

/**
 * An activity representing a list of Articles. This activity has different presentations for
 * handset and tablet-size devices. On handsets, the activity presents a list of items, which when
 * touched, lead to a {@link ArticleDetailActivity} representing item details. On tablets, the
 * activity presents a grid of items as cards.
 */
public class ArticleListActivity extends AppCompatActivity
        implements ArticleAdapter.ArticleClickCallback, SwipeRefreshLayout.OnRefreshListener {

    private ActivityArticleListBinding mBinding;

    @Inject
    ArticlesDao articlesDao;

    ArticleAdapter mAdapter;
    long[] mArticlesIds;
    private boolean mIsRefreshing = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_article_list);

        initToolbar();

        mAdapter = new ArticleAdapter(this);
        mAdapter.setHasStableIds(true);

        int columnCount = getResources().getInteger(R.integer.list_column_count);
        StaggeredGridLayoutManager layoutManager =
                new StaggeredGridLayoutManager(columnCount, StaggeredGridLayoutManager.VERTICAL);
        mBinding.recyclerView.setLayoutManager(layoutManager);
        mBinding.recyclerView.setAdapter(mAdapter);
        mBinding.recyclerView.addItemDecoration(
                new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        mArticlesIds = null;
        articlesDao.getArticlesAsObservable().observe(this, new Observer<List<Article>>() {
            @Override
            public void onChanged(@Nullable List<Article> articles) {
                if (articles != null) {
                    mAdapter.setList(new ArrayList<>(articles));

                    mArticlesIds = new long[articles.size()];
                    for (int i=0; i<articles.size(); i++)
                        mArticlesIds[i] = articles.get(i).getId();
                }
                else {
                    mAdapter.setList(new ArrayList<Article>());

                    mArticlesIds = null;
                }

                mAdapter.notifyDataSetChanged();
            }
        });

        if (savedInstanceState == null) {
            refresh();
        }

        SharedPreferences sharedPreferences = getSharedPreferences(UpdaterService.TAG, MODE_PRIVATE);
        sharedPreferences.registerOnSharedPreferenceChangeListener(new SharedPreferences.OnSharedPreferenceChangeListener() {
            @Override
            public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
                if (key.equals(UpdaterService.EXTRA_REFRESHING))
                    mIsRefreshing = sharedPreferences.getBoolean(UpdaterService.EXTRA_REFRESHING, false);
                updateRefreshingUI();
            }
        });
    }

    @Override
    public void onListItemClick(Article article) {
        Intent intent = new Intent(this, ArticleDetailActivity.class);
        intent.putExtra(ArticleDetailActivity.ARG_ARTICLES_IDS, mArticlesIds);
        intent.putExtra(ArticleDetailActivity.ARG_ARTICLE_POSITION, mAdapter.getItemPosition(article));
        intent.putExtra(ArticleDetailActivity.ARG_ARTICLE_ID, article.getId());

        startActivity(intent);
    }



    private void initToolbar() {
        setSupportActionBar(mBinding.toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
    }

    private void refresh() {
        startService(new Intent(this, UpdaterService.class));
    }

    private void updateRefreshingUI() {
        mBinding.swipeRefreshLayout.setRefreshing(mIsRefreshing);
    }

    @Override
    public void onRefresh() {
        refresh();
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

        public void bindToData() {
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
            mBinding.articleSubtitle.setText(text);
            Picasso.get()
                    .load(article.getThumbnailUrl())
                    .into(mBinding.articleImageThumbnail);
        }
    }
}
