package com.example.xyzreader.data;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.xyzreader.AppExecutors;
import com.example.xyzreader.data.database.WasteDao;
import com.example.xyzreader.data.model.Block;
import com.example.xyzreader.data.model.Thing;
import com.example.xyzreader.data.model.Waste;
import com.example.xyzreader.data.remote.RemoteSource;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class BlockChainRepository {

    private static final String LOG_TAG = BlockChainRepository.class.getSimpleName();

    private static final String PREFERENCES = "preferences_wastes";
    private static final String KEY_SELECTED_WASTE_POSITION = "selected_waste_position";

    private final WasteDao mWasteDao;
    private final SharedPreferences mSharedPreferences;
    private final RemoteSource mRemoteSource;
    private final Application mApplication;

    private final AppExecutors mExecutors;

    private boolean mInitialized = false;


    @Inject
    public BlockChainRepository(WasteDao wasteDao, RemoteSource remoteSource,
                                AppExecutors executors, Application application) {
        mWasteDao = wasteDao;
        mApplication = application;
        mSharedPreferences = application.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
        mRemoteSource = remoteSource;

        mExecutors = executors;

        LiveData<List<Waste>> articlesOnline = mRemoteSource.getWasteFromBlockChain();
        articlesOnline.observeForever(newOnlineData -> mExecutors.diskIO().execute(() -> {
            if (newOnlineData != null)
                Log.d(LOG_TAG, "Repository observer notified. Found " + newOnlineData.size() + " entries.");
            else
                Log.d(LOG_TAG, "Repository observer notified. Found NULL entries.");

            handleBlockReceived((ArrayList<Waste>) newOnlineData);
        }));
    }

    /**
     * Creates periodic sync tasks and checks to see if an immediate sync is required. If an
     * immediate sync is required, this method will take care of making sure that sync occurs.
     */
    private synchronized void initializeData() {
        // Only perform initialization once per app lifetime. If initialization has already been
        // performed, we have nothing to do in this method.
        if (mInitialized) return;
        mInitialized = true;

        mExecutors.diskIO().execute(() -> {
            fetchWastes(mApplication);
            fetchBlockChain(mApplication);
        });
    }

    private void handleBlockReceived(ArrayList<Waste> newOnlineData) {
        if (isThereWaste() && newOnlineData != null) {
            mWasteDao.addWaste(newOnlineData);

            Log.d(LOG_TAG, "New entry inserted.");
        }
        else if (newOnlineData != null) {
            ArrayList<Waste> oldEntries = getWastes();

            if (oldEntries != null) {
                for (Waste newEntry : newOnlineData) {
                    Waste oldEntry = getWaste(newEntry.getId());

                    /* If there is a new booklet entry */
                    if (oldEntry == null) {
                        mWasteDao.addWaste(newEntry);

                        Log.d(LOG_TAG, "New entry inserted.");
                    }
                    /* If the exam already exists locally */
                    else {
                        /* Remove is from the list of those that will be deleted from the DB */
                        oldEntries.remove(oldEntry);
                    }
                }

                if (oldEntries.size() > 0)
                    deleteWastes(oldEntries);
            }
        }
    }



    public LiveData<List<Waste>> getWastesAsObserver() {
        initializeData();

        return mWasteDao.getWastesAsObservable();
    }

    private ArrayList<Waste> getWastes() {
        initializeData();

        return (ArrayList<Waste>) mWasteDao.getWastes();
    }

    public LiveData<ArrayList<Thing>> getBlockChainAsObserver() {
        return mRemoteSource.getBlockChain();
    }

    public LiveData<Boolean> getLoadingState() {
        return mRemoteSource.getLoadingState();
    }

    private Waste getWaste(String id) {
        initializeData();

        return mWasteDao.getWaste(id);
    }

    private boolean isThereWaste() {
        return mWasteDao.getWastesCount() == 0;
    }

    private void clearWastes() {
        mWasteDao.deleteWastes();
    }

    public void deleteWaste(Waste entry) {
        mWasteDao.deleteWaste(entry.getId());
    }

    private void deleteWastes(ArrayList<Waste> entries) {
        for (Waste entry : entries)
            deleteWaste(entry);
    }

    public void fetchWastes(Context context) {
        mRemoteSource.fetchWastes(context);
    }

    public void fetchBlockChain(Context context) {
        mRemoteSource.fetchBlockChain(context);
    }


    public void saveSelectedWastePosition(int position) {
        mSharedPreferences.edit().putInt(KEY_SELECTED_WASTE_POSITION, position).apply();
    }

    public int getSelectedWastePosition() {
        return mSharedPreferences.getInt(KEY_SELECTED_WASTE_POSITION, -1);
    }
}
