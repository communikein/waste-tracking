package com.example.xyzreader.di.module;

import com.example.xyzreader.ui.ArticleDetailFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class FragmentsBuilderModule {

    @ContributesAndroidInjector
    abstract ArticleDetailFragment contributeArticleDetailFragment();

}
