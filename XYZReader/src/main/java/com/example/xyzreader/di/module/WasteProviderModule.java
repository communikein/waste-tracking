package com.example.xyzreader.di.module;

import com.example.xyzreader.data.contentprovider.WasteProvider;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class WasteProviderModule {

    @ContributesAndroidInjector
    abstract WasteProvider contributeArticlesProvider();

}
