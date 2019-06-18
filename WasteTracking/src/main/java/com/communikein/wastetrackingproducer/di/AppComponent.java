package com.communikein.wastetrackingproducer.di;

import android.app.Application;

import com.communikein.wastetrackingproducer.XyzReaderApp;
import com.communikein.wastetrackingproducer.di.module.ActivitiesModule;
import com.communikein.wastetrackingproducer.di.module.WasteProviderModule;
import com.communikein.wastetrackingproducer.di.module.XyzReaderAppModule;

import javax.inject.Singleton;

import dagger.Component;
import dagger.android.support.AndroidSupportInjectionModule;

@Singleton
@Component(modules = {
        AndroidSupportInjectionModule.class,
        XyzReaderAppModule.class,
        ActivitiesModule.class,
        WasteProviderModule.class})
public interface AppComponent {

    void inject(XyzReaderApp app);

    Application getApplication();

}
