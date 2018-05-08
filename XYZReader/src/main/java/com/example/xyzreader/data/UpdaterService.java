package com.example.xyzreader.data;

import android.app.IntentService;
import android.app.Service;
import android.content.ContentProviderOperation;
import android.content.ContentValues;
import android.content.Intent;
import android.content.OperationApplicationException;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.RemoteException;
import android.text.format.Time;
import android.util.Log;

import com.example.xyzreader.data.database.ArticlesDao;
import com.example.xyzreader.data.model.Article;
import com.example.xyzreader.remote.BackendRequests;
import com.example.xyzreader.remote.Config;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasServiceInjector;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UpdaterService extends IntentService implements HasServiceInjector {

    public static final String TAG = "UpdaterService";

    public static final String EXTRA_REFRESHING = "REFRESHING";

    @Inject
    DispatchingAndroidInjector<Service> dispatchingAndroidInjector;

    @Inject
    ArticlesDao articlesDao;

    SharedPreferences sharedPreferences;

    public UpdaterService() {
        super(TAG);
    }

    @Override
    public void onCreate() {
        AndroidInjection.inject(this);
        super.onCreate();

        sharedPreferences = getSharedPreferences(TAG, MODE_PRIVATE);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        if (ni == null || !ni.isConnected())
            return;

        updateRefreshingState(true);
        try {
            Gson gson = new GsonBuilder()
                    .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
                    .create();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Config.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
            BackendRequests request = retrofit.create(BackendRequests.class);

            Response<ArrayList<Article>> response = request.getJSON().execute();
            if (response != null) {
                ArrayList<Article> articles = response.body();
                if (articles != null) {
                    for(Article article : articles) {
                        if (articlesDao.getArticle(article.getId()) != null)
                            articlesDao.updateArticle(article);
                        else
                            articlesDao.addArticle(article);
                    }
                }
            }
        } catch (IOException e) {
            Log.e(TAG, "Error updating content.", e);
        }
        updateRefreshingState(false);
    }

    private void updateRefreshingState(boolean refreshing) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(EXTRA_REFRESHING, refreshing);
        editor.commit();
    }


    @Override
    public AndroidInjector<Service> serviceInjector() {
        return dispatchingAndroidInjector;
    }
}
