package com.example.xyzreader.di.module;

import com.example.xyzreader.ui.UserDetailsActivity;
import com.example.xyzreader.ui.WasteDetailsActivity;
import com.example.xyzreader.ui.WasteListActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivitiesModule {

    @ContributesAndroidInjector()
    abstract WasteDetailsActivity contributeWasteDetailActivity();

    @ContributesAndroidInjector
    abstract WasteListActivity contributeWasteListActivity();

    @ContributesAndroidInjector
    abstract UserDetailsActivity contributeUserDetailsActivity();

}
