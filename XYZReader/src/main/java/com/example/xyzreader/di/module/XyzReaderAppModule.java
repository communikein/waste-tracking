package com.example.xyzreader.di.module;

import android.app.Application;
import android.arch.persistence.room.Room;

import com.example.xyzreader.AppExecutors;
import com.example.xyzreader.data.BlockChainRepository;
import com.example.xyzreader.data.database.WasteDao;
import com.example.xyzreader.data.database.WasteDatabase;
import com.example.xyzreader.data.remote.RemoteSource;

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

    @Singleton @Provides
    BlockChainRepository provideRepository(WasteDatabase database, RemoteSource remoteSource,
                                           AppExecutors executors, Application application) {
        return new BlockChainRepository(database.wasteDao(), remoteSource, executors, application);
    }

    @Singleton @Provides
    RemoteSource provideNetworkDataSource(AppExecutors executors) {
        return new RemoteSource(executors);
    }

    @Singleton
    @Provides
    WasteDatabase provideDatabase(Application application) {
        return Room.databaseBuilder(application, WasteDatabase.class, WasteDatabase.NAME)
                .fallbackToDestructiveMigration()
                .build();
    }

    @Singleton @Provides
    WasteDao provideWasteDao(WasteDatabase database) {
        return database.wasteDao();
    }

}
