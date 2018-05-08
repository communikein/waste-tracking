package com.example.xyzreader.di.module;

import com.example.xyzreader.data.UpdaterService;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class IntentServiceModule {

    @ContributesAndroidInjector
    abstract UpdaterService contributeUpdateService();

}
