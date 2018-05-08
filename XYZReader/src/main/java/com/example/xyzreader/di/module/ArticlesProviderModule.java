package com.example.xyzreader.di.module;

import com.example.xyzreader.data.contentprovider.ArticlesProvider;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ArticlesProviderModule {

    @ContributesAndroidInjector
    abstract ArticlesProvider contributeArticlesProvider();

}
