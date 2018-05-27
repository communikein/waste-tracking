package com.example.xyzreader.data;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.xyzreader.AppExecutors;
import com.example.xyzreader.data.database.ArticlesDao;
import com.example.xyzreader.data.model.Article;
import com.example.xyzreader.data.remote.RemoteSource;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class ReaderRepository {

    private static final String LOG_TAG = ReaderRepository.class.getSimpleName();

    private static final String PREFERENCES = "preferences_articles";
    private static final String KEY_SELECTED_ARTICLE_POSITION = "selected_article_position";

    private final ArticlesDao mArticlesDao;
    private final SharedPreferences mSharedPreferences;
    private final RemoteSource mRemoteSource;

    private final AppExecutors mExecutors;

    private boolean mInitialized = false;


    @Inject
    public ReaderRepository(ArticlesDao articlesDao, RemoteSource remoteSource,
                            AppExecutors executors, Application application) {
        mArticlesDao = articlesDao;
        mSharedPreferences = application.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
        mRemoteSource = remoteSource;

        mExecutors = executors;

        LiveData<List<Article>> articlesOnline = mRemoteSource.getArticlesFromWeb();
        articlesOnline.observeForever(newOnlineData -> mExecutors.diskIO().execute(() -> {
            if (newOnlineData != null)
                Log.d(LOG_TAG, "Repository observer notified. Found " + newOnlineData.size() + " entries.");
            else
                Log.d(LOG_TAG, "Repository observer notified. Found NULL entries.");

            handleArticlesReceived((ArrayList<Article>) newOnlineData);
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

        mExecutors.diskIO().execute(this::fetchArticles);
    }

    private void handleArticlesReceived(ArrayList<Article> newOnlineData) {
        if (areThereArticles() && newOnlineData != null) {
            mArticlesDao.addArticles(newOnlineData);

            Log.d(LOG_TAG, "New entry inserted.");
        }
        else if (newOnlineData != null) {
            ArrayList<Article> oldEntries = getArticles();

            if (oldEntries != null) {
                for (Article newEntry : newOnlineData) {
                    Article oldEntry = getArticle(newEntry.getId());

                    /* If there is a new booklet entry */
                    if (oldEntry == null) {
                        mArticlesDao.addArticle(newEntry);

                        Log.d(LOG_TAG, "New entry inserted.");
                    }
                    /* If the exam already exists locally */
                    else {
                        /* Remove is from the list of those that will be deleted from the DB */
                        oldEntries.remove(oldEntry);

                        /* If the booklet entry's data has been modified */
                        if (!newEntry.isIdentic(oldEntry)) {
                            /* Update the DB entry */
                            mArticlesDao.updateArticle(newEntry);

                            Log.d(LOG_TAG, "Entry updated.");
                        }
                    }
                }

                if (oldEntries.size() > 0)
                    deleteArticles(oldEntries);
            }
        }
    }



    public LiveData<List<Article>> getArticlesAsObserver() {
        initializeData();

        return mArticlesDao.getArticlesAsObservable();
    }

    private ArrayList<Article> getArticles() {
        initializeData();

        return (ArrayList<Article>) mArticlesDao.getArticles();
    }

    public LiveData<Boolean> getLoadingState() {
        return mRemoteSource.getLoadingState();
    }

    private Article getArticle(long id) {
        initializeData();

        return mArticlesDao.getArticle(id);
    }

    private boolean areThereArticles() {
        return mArticlesDao.getArticlesCount() == 0;
    }

    private void clearArticles() {
        mArticlesDao.deleteArticles();
    }

    public void deleteArticle(Article entry) {
        mArticlesDao.deleteArticle(entry.getId());
    }

    private void deleteArticles(ArrayList<Article> entries) {
        for (Article entry : entries)
            deleteArticle(entry);
    }

    public void fetchArticles() {
        mRemoteSource.fetchArticles();
    }


    public void saveSelectedArticlePosition(int position) {
        mSharedPreferences.edit().putInt(KEY_SELECTED_ARTICLE_POSITION, position).apply();
    }

    public int getSelectedArticlePosition() {
        return mSharedPreferences.getInt(KEY_SELECTED_ARTICLE_POSITION, -1);
    }
}
