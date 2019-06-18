package com.communikein.wastetrackingproducer;

import android.app.Activity;
import android.app.Application;
import android.app.Service;
import android.content.ContentProvider;
import android.content.Context;

import com.communikein.wastetrackingproducer.di.AppComponent;
import com.communikein.wastetrackingproducer.di.module.XyzReaderAppModule;
import com.communikein.wastetrackingproducer.di.DaggerAppComponent;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;
import dagger.android.HasContentProviderInjector;
import dagger.android.HasServiceInjector;

public class XyzReaderApp extends Application implements
        HasActivityInjector, HasServiceInjector, HasContentProviderInjector {

    @Inject
    DispatchingAndroidInjector<Activity> dispatchingAndroidActivityInjector;

    @Inject
    DispatchingAndroidInjector<Service> dispatchingAndroidServiceInjector;

    @Inject
    DispatchingAndroidInjector<ContentProvider> dispatchingAndroidContentProviderInjector;

    AppComponent appComponent;

    public static XyzReaderApp get(Context context) {
        return (XyzReaderApp) context.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();

        appComponent = DaggerAppComponent
                .builder()
                .xyzReaderAppModule(new XyzReaderAppModule(this))
                .build();

        appComponent.inject(this);
    }

    public AppComponent getComponent() {
        return appComponent;
    }

    @Override
    public AndroidInjector<Activity> activityInjector() {
        return dispatchingAndroidActivityInjector;
    }

    @Override
    public AndroidInjector<Service> serviceInjector() {
        return dispatchingAndroidServiceInjector;
    }

    @Override
    public AndroidInjector<ContentProvider> contentProviderInjector() {
        return dispatchingAndroidContentProviderInjector;
    }
}
