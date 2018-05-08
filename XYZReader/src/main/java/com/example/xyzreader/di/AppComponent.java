package com.example.xyzreader.di;

import android.app.Application;

import com.example.xyzreader.XyzReaderApp;
import com.example.xyzreader.di.module.ActivitiesModule;
import com.example.xyzreader.di.module.ArticlesProviderModule;
import com.example.xyzreader.di.module.IntentServiceModule;
import com.example.xyzreader.di.module.XyzReaderAppModule;

import javax.inject.Singleton;

import dagger.Component;
import dagger.android.support.AndroidSupportInjectionModule;

@Singleton
@Component(modules = {
        AndroidSupportInjectionModule.class,
        XyzReaderAppModule.class,
        ActivitiesModule.class,
        ArticlesProviderModule.class,
        IntentServiceModule.class})
public interface AppComponent {

    void inject(XyzReaderApp app);

    Application getApplication();

}
