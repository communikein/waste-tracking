package com.communikein.wastetrackingproducer.di.module;

import android.app.Application;
import android.arch.persistence.room.Room;

import com.communikein.wastetrackingproducer.AppExecutors;
import com.communikein.wastetrackingproducer.data.BlockChainRepository;
import com.communikein.wastetrackingproducer.data.database.WasteDao;
import com.communikein.wastetrackingproducer.data.database.WasteDatabase;
import com.communikein.wastetrackingproducer.data.remote.RemoteSource;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Class necessary to provide access to the Application Context, DB, Web API or the Repository
 * (single source of truth).
 */
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
    RemoteSource provideNetworkDataSource(AppExecutors executors, Application application) {
        return new RemoteSource(executors, application);
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
