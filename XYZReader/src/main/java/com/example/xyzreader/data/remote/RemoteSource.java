package com.example.xyzreader.data.remote;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.util.Log;

import com.example.xyzreader.AppExecutors;
import com.example.xyzreader.R;
import com.example.xyzreader.data.BlockDeserializer;
import com.example.xyzreader.data.model.Authentication;
import com.example.xyzreader.data.model.CreateThing;
import com.example.xyzreader.data.model.Waste;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Singleton
public class RemoteSource {

    private static final String LOG_TAG = RemoteSource.class.getSimpleName();

    private static final int SCHEMA_ID_WASTE =7;
    private static final int SCHEMA_ID_CHANGE_OWNERSHIP =8;
    private static final int SCHEMA_ID_TREATMENT_PLANT =9;
    private static final int SCHEMA_ID_COLLECTOR =10;
    private static final int SCHEMA_ID_PRODUCER =11;
    private static final int SCHEMA_ID_LANDFILL =12;
    private static final int SCHEMA_ID_RECYCLER =13;
    private static final int SCHEMA_ID_POWER =14;


    private final AppExecutors mExecutors;

    private final MutableLiveData<List<Waste>> mDownloadedWaste;
    private final MutableLiveData<Boolean> mLoadingState;

    private final MutableLiveData<ArrayList<JsonObject>> mDownloadedBlocks;

    @Inject
    public RemoteSource(AppExecutors executors) {
        mExecutors = executors;

        mDownloadedWaste = new MutableLiveData<>();
        mLoadingState = new MutableLiveData<>();

        mDownloadedBlocks = new MutableLiveData<>();
    }


    public LiveData<List<Waste>> getWasteFromBlockChain() {
        return mDownloadedWaste;
    }

    public LiveData<ArrayList<JsonObject>> getBlockChain() { return mDownloadedBlocks; }

    public LiveData<Boolean> getLoadingState() {
        return mLoadingState;
    }


    public void fetchBlockChain() {
        // Notify any observer that the loading is started
        mLoadingState.postValue(true);

        mExecutors.networkIO().execute(() -> {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Config.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            BackendRequests request = retrofit.create(BackendRequests.class);

            try {
                Response<Void> token = request.login(new Authentication("api@tim.it", "dimostratore.2008")).execute();
                if (token != null) {
                    String auth_token = token.headers().get("Auth");

                    retrofit = new Retrofit.Builder()
                            .baseUrl(Config.BASE_URL)
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();
                    request = retrofit.create(BackendRequests.class);

                    request.getAllThings(auth_token).enqueue(new Callback<ArrayList<JsonObject>>() {
                        @Override
                        public void onResponse(Call<ArrayList<JsonObject>> call, Response<ArrayList<JsonObject>> response) {
                            if (response != null && response.body() != null) {
                                mDownloadedBlocks.postValue(response.body());
                            }
                        }

                        @Override
                        public void onFailure(Call<ArrayList<JsonObject>> call, Throwable t) {
                            Log.d("BLA", t.getMessage());
                        }
                    });
                }
            } catch (IOException e) {
                Log.d(LOG_TAG, e.getMessage());
            }

            // Notify any observer that the loading is completed
            mLoadingState.postValue(false);
        });
    }

    public void addWaste(Waste waste) {
        // Notify any observer that the loading is started
        mLoadingState.postValue(true);

        ArrayList<String> thingIdentities = new ArrayList<>();
        thingIdentities.add(waste.getId());
        String thingName = waste.getId();
        CreateThing createThing = new CreateThing(thingIdentities, thingName, SCHEMA_ID_WASTE, waste.toJson());

        mExecutors.networkIO().execute(() -> {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Config.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            BackendRequests request = retrofit.create(BackendRequests.class);

            try {
                Response<Void> token = request.login(new Authentication("api@tim.it", "dimostratore.2008")).execute();
                if (token != null) {
                    String auth_token = token.headers().get("Auth");

                    retrofit = new Retrofit.Builder()
                            .baseUrl(Config.BASE_URL)
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();
                    request = retrofit.create(BackendRequests.class);

                    request.createThing(auth_token, createThing).enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            if (response != null && response.body() != null) {

                            }
                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {
                            Log.d("BLA", t.getMessage());
                        }
                    });
                }
            } catch (IOException e) {
                Log.d(LOG_TAG, e.getMessage());
            }

            // Notify any observer that the loading is completed
            mLoadingState.postValue(false);
        });
    }

    public void fetchWastes(Context context) {
        // Notify any observer that the loading is started
        mLoadingState.postValue(true);

        ArrayList<JsonObject> blockChain = getJSON(context);
        ArrayList<Waste> wastes = Waste.fromJSONArray(blockChain);
        mDownloadedWaste.postValue(wastes);

        mLoadingState.postValue(false);
    }

    ArrayList<JsonObject> getJSON(Context context) {
        Writer writer = new StringWriter();
        char[] buffer = new char[1024];
        ArrayList<JsonObject> result = new ArrayList<>();

        try (InputStream is = context.getResources().openRawResource(R.raw.data)) {
            Reader reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
            int n;
            while ((n = reader.read(buffer)) != -1) {
                writer.write(buffer, 0, n);
            }

            String text = writer.toString();
            JsonArray object = new Gson().fromJson(text, JsonArray.class);

            for (int i=0; i<object.size(); i++)
                result.add(object.get(i).getAsJsonObject());

        } catch (IOException e) {
            e.printStackTrace();
            result = new ArrayList<>();
        }

        return result;
    }


}

