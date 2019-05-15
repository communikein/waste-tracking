package com.example.xyzreader.data.remote;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.util.Log;

import com.example.xyzreader.AppExecutors;
import com.example.xyzreader.R;
import com.example.xyzreader.data.model.Waste;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
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

    private final MutableLiveData<List<Waste>> mDownloadedWaste;
    private final MutableLiveData<Boolean> mLoadingState;


    @Inject
    public RemoteSource(AppExecutors executors) {
        mExecutors = executors;

        mDownloadedWaste = new MutableLiveData<>();
        mLoadingState = new MutableLiveData<>();
    }


    public LiveData<List<Waste>> getWasteFromBlockChain() {
        return mDownloadedWaste;
    }

    public LiveData<Boolean> getLoadingState() {
        return mLoadingState;
    }

    public void fetchWastes(Context context) {
        // Notify any observer that the loading is started
        mLoadingState.postValue(true);

        JSONArray blockChain = getJSON(context);
        ArrayList<Waste> wastes = Waste.fromJSONArray(blockChain);
        mDownloadedWaste.postValue(wastes);

        mLoadingState.postValue(false);
    }

    JSONArray getJSON(Context context) {
        Writer writer = new StringWriter();
        char[] buffer = new char[1024];
        JSONArray blockChain = null;

        try (InputStream is = context.getResources().openRawResource(R.raw.data)) {
            Reader reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
            int n;
            while ((n = reader.read(buffer)) != -1) {
                writer.write(buffer, 0, n);
            }

            blockChain = new JSONArray(writer.toString());
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

        return blockChain;
    }


}
