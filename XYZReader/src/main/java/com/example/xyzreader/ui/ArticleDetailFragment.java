package com.example.xyzreader.ui;

import android.content.Context;
import android.databinding.DataBindingUtil;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.xyzreader.R;
import com.example.xyzreader.data.database.ArticlesDao;
import com.example.xyzreader.data.model.Article;
import com.example.xyzreader.databinding.FragmentArticleDetailBinding;
import com.example.xyzreader.viewmodel.ArticleDetailViewModel;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;

/**
 * A fragment representing a single Article detail screen. This fragment is
 * either contained in a {@link ArticleListActivity} in two-pane mode (on
 * tablets) or a {@link ArticleDetailActivity} on handsets.
 */
public class ArticleDetailFragment extends Fragment {

    @Inject
    ArticlesDao articlesDao;

    private FragmentArticleDetailBinding mBinding;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ArticleDetailFragment() {
    }

    public static ArticleDetailFragment newInstance() {
        return new ArticleDetailFragment();
    }

    @NonNull
    private ArticleDetailActivity getParentActivity() {
        return (ArticleDetailActivity) getActivity();
    }

    private ArticleDetailViewModel getViewModel() {
        return getParentActivity().mViewModel;
    }

    @Override
    public void onAttach(Context context) {
        AndroidSupportInjection.inject(this);
        super.onAttach(context);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(LayoutInflater.from(container.getContext()),
                R.layout.fragment_article_detail, container, false);

        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        bindViews();
    }

    private void bindViews() {
        Article selected = getViewModel().getSelectedArticle();

        if (selected != null) {
            mBinding.getRoot().setAlpha(0);
            mBinding.getRoot().setVisibility(View.VISIBLE);
            mBinding.getRoot().animate().alpha(1);

            String textBegin = selected.getBody().substring(0, 1000);
            mBinding.articleBody.setText(textBegin);
        } else {
            mBinding.getRoot().setVisibility(View.GONE);
            mBinding.articleBody.setText("N/A");
        }
    }
}
