package com.example.xyzreader.di.module;

import android.app.Application;
import android.arch.persistence.room.Room;

import com.example.xyzreader.data.database.ArticlesDao;
import com.example.xyzreader.data.database.ArticlesDatabase;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class XyzReaderAppModule {

    private final Application application;

    public XyzReaderAppModule(Application application) {
        this.application = application;
    }

    @Provides
    Application provideApplication() {
        return application;
    }

    @Singleton
    @Provides
    ArticlesDatabase provideDatabase(Application application) {
        return Room.databaseBuilder(application, ArticlesDatabase.class, ArticlesDatabase.NAME)
                .fallbackToDestructiveMigration()
                .build();
    }

    @Singleton @Provides
    ArticlesDao provideArticlesDao(ArticlesDatabase database) {
        return database.articlesDao();
    }

}
