package com.example.xyzreader.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.example.xyzreader.data.ReaderRepository;
import com.example.xyzreader.data.model.Article;

import java.util.List;

import javax.inject.Inject;

public class ArticleDetailViewModel extends ViewModel {

    private final ReaderRepository mRepository;

    private final LiveData<List<Article>> mData;
    private final LiveData<Boolean> mLoading;

    private int mSelectedArticlePosition;

    @Inject
    public ArticleDetailViewModel(ReaderRepository repository) {
        this.mRepository = repository;

        mData = mRepository.getArticlesAsObserver();
        mLoading = mRepository.getLoadingState();
    }


    public LiveData<List<Article>> getArticles() {
        return mData;
    }

    public LiveData<Boolean> isLoading() {
        return mLoading;
    }


    public Article getArticle(List<Article> articles, int id) {
        for (Article article : articles) {
            if (article.getId() == id)
                return article;
        }

        return null;
    }


    public void updateSelectedArticle(int articlePosition) {
        this.mSelectedArticlePosition = articlePosition;

        mRepository.saveSelectedArticlePosition(mSelectedArticlePosition);
    }

    public int getSelectedArticlePosition() {
        return mSelectedArticlePosition;
    }

    public boolean isFirstArticleSelected() {
        return mSelectedArticlePosition == 0;
    }

    public boolean isLastArticleSelected() {
        return mData!= null && mData.getValue() != null &&
                mSelectedArticlePosition == mData.getValue().size();
    }

    public Article getSelectedArticle() {
        if (mData.getValue() != null)
            return mData.getValue().get(mSelectedArticlePosition);
        return null;
    }

    public Article getPreviousArticle() {
        if (isFirstArticleSelected())
            return null;
        updateSelectedArticle(this.mSelectedArticlePosition - 1);

        return getSelectedArticle();
    }

    public Article getNextArticle() {
        if (isLastArticleSelected())
            return null;
        updateSelectedArticle(this.mSelectedArticlePosition + 1);

        return getSelectedArticle();
    }
}
