package com.example.xyzreader.di.module;

import com.example.xyzreader.ui.ArticleDetailActivity;
import com.example.xyzreader.ui.ArticleListActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivitiesModule {

    @ContributesAndroidInjector(modules = FragmentsBuilderModule.class)
    abstract ArticleDetailActivity contributeArticleDetailActivity();

    @ContributesAndroidInjector
    abstract ArticleListActivity contributeArticleListActivity();

}
