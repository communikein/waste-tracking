package com.communikein.wastetrackingproducer.di.module;

import com.communikein.wastetrackingproducer.data.contentprovider.WasteProvider;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Class necessary to allow for dependency injection in the Providers. Every Provider that makes
 * use of dependency injection must appear here.
 */
@Module
public abstract class WasteProviderModule {

    @ContributesAndroidInjector
    abstract WasteProvider contributeArticlesProvider();

}
