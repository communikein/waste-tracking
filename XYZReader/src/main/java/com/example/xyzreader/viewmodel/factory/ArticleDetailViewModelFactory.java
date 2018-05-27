package com.example.xyzreader.viewmodel.factory;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.example.xyzreader.data.ReaderRepository;
import com.example.xyzreader.viewmodel.ArticleDetailViewModel;

import javax.inject.Inject;

public class ArticleDetailViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private final ReaderRepository mRepository;

    @Inject
    public ArticleDetailViewModelFactory(ReaderRepository repository) {
        this.mRepository = repository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        //noinspection unchecked
        return (T) new ArticleDetailViewModel(mRepository);
    }

}
