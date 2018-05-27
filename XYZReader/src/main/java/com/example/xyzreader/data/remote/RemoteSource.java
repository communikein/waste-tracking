package com.example.xyzreader.data.remote;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import com.example.xyzreader.AppExecutors;
import com.example.xyzreader.data.model.Article;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Singleton
public class RemoteSource {

    private static final String LOG_TAG = RemoteSource.class.getSimpleName();

    private final AppExecutors mExecutors;

    private final MutableLiveData<List<Article>> mDownloadedArticles;
    private final MutableLiveData<Boolean> mLoadingState;


    @Inject
    public RemoteSource(AppExecutors executors) {
        mExecutors = executors;

        mDownloadedArticles = new MutableLiveData<>();
        mLoadingState = new MutableLiveData<>();
    }


    public LiveData<List<Article>> getArticlesFromWeb() {
        return mDownloadedArticles;
    }

    public LiveData<Boolean> getLoadingState() {
        return mLoadingState;
    }

    public void fetchArticles() {
        // Notify any observer that the loading is started
        mLoadingState.postValue(true);

        mExecutors.networkIO().execute(() -> {
            Gson gson = new GsonBuilder()
                    .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
                    .create();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Config.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
            BackendRequests request = retrofit.create(BackendRequests.class);

            try {
                Response<ArrayList<Article>> response = request.getJSON().execute();
                if (response != null) {
                    ArrayList<Article> articles = response.body();
                    if (articles != null) {
                        mDownloadedArticles.postValue(articles);
                    }
                }
            } catch (IOException e) {
                Log.d(LOG_TAG, e.getMessage());
            }

            // Notify any observer that the loading is completed
            mLoadingState.postValue(false);
        });
    }

}
