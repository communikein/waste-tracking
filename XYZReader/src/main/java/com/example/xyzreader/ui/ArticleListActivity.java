package com.example.xyzreader.ui;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.SharedElementCallback;
import android.support.v4.content.ContextCompat;
import android.support.v4.util.Pair;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import com.example.xyzreader.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import dagger.android.AndroidInjection;

import com.example.xyzreader.data.model.Article;
import com.example.xyzreader.databinding.ActivityArticleListBinding;
import com.example.xyzreader.viewmodel.ArticlesListViewModel;
import com.example.xyzreader.viewmodel.factory.ArticlesListViewModelFactory;

import javax.inject.Inject;

/**
 * An activity representing a list of Articles. This activity has different presentations for
 * handset and tablet-size devices. On handsets, the activity presents a list of items, which when
 * touched, lead to a {@link ArticleDetailActivity} representing item details. On tablets, the
 * activity presents a grid of items as cards.
 */
public class ArticleListActivity extends AppCompatActivity
        implements ArticlesListAdapter.ArticleClickCallback, SwipeRefreshLayout.OnRefreshListener {

    private ActivityArticleListBinding mBinding;

    /* */
    @Inject
    ArticlesListViewModelFactory viewModelFactory;

    /* */
    private ArticlesListViewModel mViewModel;

    ArticlesListAdapter mAdapter;
    int selectedArticlePosition;

    long[] mArticlesIds;
    private boolean mIsRefreshing = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_article_list);
        mViewModel = ViewModelProviders
                .of(this, viewModelFactory)
                .get(ArticlesListViewModel.class);

        initToolbar();

        mAdapter = new ArticlesListAdapter(this);
        mAdapter.setHasStableIds(true);

        int columnCount = getResources().getInteger(R.integer.list_column_count);
        StaggeredGridLayoutManager layoutManager =
                new StaggeredGridLayoutManager(columnCount, StaggeredGridLayoutManager.VERTICAL);
        mBinding.recyclerView.setLayoutManager(layoutManager);
        mBinding.recyclerView.setAdapter(mAdapter);

        mArticlesIds = null;
        mViewModel.getArticles().observe(this, articles -> {
            if (articles != null) {
                mAdapter.setList(new ArrayList<>(articles));

                mArticlesIds = new long[articles.size()];
                for (int i=0; i<articles.size(); i++)
                    mArticlesIds[i] = articles.get(i).getId();
            }
            else {
                mAdapter.setList(new ArrayList<>());

                mArticlesIds = null;
            }

            mAdapter.notifyDataSetChanged();
        });

        if (savedInstanceState == null) {
            refresh();
        }

        mViewModel.isLoading().observe(this, loadingState -> {
            if (loadingState != null) {
                mIsRefreshing = loadingState;
                updateRefreshingUI();
            }
        });

        setExitSharedElementCallback(new SharedElementCallback() {
            @Override
            public void onMapSharedElements(List<String> names, Map<String, View> sharedElements) {
                // Locate the ViewHolder for the clicked position.
                RecyclerView.ViewHolder selectedViewHolder = mBinding.recyclerView
                        .findViewHolderForAdapterPosition(mViewModel.getCurrentArticlePosition());
                if (selectedViewHolder == null || selectedViewHolder.itemView == null) {
                    return;
                }

                // Map the first shared element name to the child ImageView.
                sharedElements.put(names.get(0),
                        selectedViewHolder.itemView.findViewById(R.id.article_image_thumbnail));
                sharedElements.put(names.get(1),
                        selectedViewHolder.itemView.findViewById(R.id.text_background));
                sharedElements.put(names.get(2),
                        selectedViewHolder.itemView.findViewById(R.id.article_title));
                sharedElements.put(names.get(3),
                        selectedViewHolder.itemView.findViewById(R.id.article_byline));
            }
        });
    }

    @Override
    public void onListItemClick(View view, Article article) {
        Intent intent = new Intent(this, ArticleDetailActivity.class);

        selectedArticlePosition = mAdapter.getList().indexOf(article);
        long id = article.getId();
        mViewModel.saveCurrentArticlePosition(selectedArticlePosition);

        intent.putExtra(ArticleDetailActivity.ARG_ARTICLE_POSITION, selectedArticlePosition);
        intent.putExtra(ArticleDetailActivity.ARG_ARTICLE_ID, id);

        // Get the views that the animation will start from
        View articleImage = view.findViewById(R.id.article_image_thumbnail);
        View articleShade = view.findViewById(R.id.text_background);
        View articleTitle = view.findViewById(R.id.article_title);
        View articleByLine = view.findViewById(R.id.article_byline);

        // Get the transition names from the views
        String imageTransitionName = ViewCompat.getTransitionName(articleImage);
        String shadeTransitionName = ViewCompat.getTransitionName(articleShade);
        String titleTransitionName = ViewCompat.getTransitionName(articleTitle);
        String bylineTransitionName = ViewCompat.getTransitionName(articleByLine);

        List<Pair<View, String>> pairs = new ArrayList<>();
        pairs.add(Pair.create(articleImage, imageTransitionName));
        pairs.add(Pair.create(articleShade, shadeTransitionName));
        pairs.add(Pair.create(articleTitle, titleTransitionName));
        pairs.add(Pair.create(articleByLine, bylineTransitionName));

        ActivityOptionsCompat options = ActivityOptionsCompat
                .makeSceneTransitionAnimation(ArticleListActivity.this, pairs.toArray(new Pair[]{}));

        //Start the Intent
        ActivityCompat.startActivity(this, intent, options.toBundle());
    }



    private void initToolbar() {
        ViewCompat.setElevation(mBinding.appbar, getResources().getDimension(R.dimen.app_bar_elevation));

        // Change FAB icon color
        Drawable myFabSrc = getResources().getDrawable(R.drawable.logo);
        Drawable willBeWhite = myFabSrc.getConstantState().newDrawable();

        int color = ContextCompat.getColor(ArticleListActivity.this, R.color.theme_primary_dark);
        willBeWhite.mutate().setColorFilter(color, PorterDuff.Mode.MULTIPLY);
        mBinding.appLogo.setImageDrawable(willBeWhite);

        setSupportActionBar(mBinding.toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
    }

    private void refresh() {
        mViewModel.refreshArticles();
    }

    private void updateRefreshingUI() {
        mBinding.swipeRefreshLayout.setRefreshing(mIsRefreshing);
    }

    @Override
    public void onRefresh() {
        refresh();
    }
}
