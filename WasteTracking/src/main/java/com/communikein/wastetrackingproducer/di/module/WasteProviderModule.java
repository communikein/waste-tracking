package com.communikein.wastetrackingproducer.di.module;

import com.communikein.wastetrackingproducer.data.contentprovider.WasteProvider;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class WasteProviderModule {

    @ContributesAndroidInjector
    abstract WasteProvider contributeArticlesProvider();

}
