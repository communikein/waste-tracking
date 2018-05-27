package com.example.xyzreader.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.example.xyzreader.data.ReaderRepository;
import com.example.xyzreader.data.model.Article;

import java.util.List;

import javax.inject.Inject;

public class ArticlesListViewModel extends ViewModel {

    private final ReaderRepository mRepository;

    private final LiveData<List<Article>> mData;
    private final LiveData<Boolean> mLoading;

    @Inject
    public ArticlesListViewModel(ReaderRepository repository) {
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

    public void refreshArticles() {
        mRepository.fetchArticles();
    }

    public void saveCurrentArticlePosition(int position) {
        mRepository.saveSelectedArticlePosition(position);
    }

    public int getCurrentArticlePosition() {
        return mRepository.getSelectedArticlePosition();
    }

}
