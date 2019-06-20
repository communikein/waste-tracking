package com.communikein.wastetrackingproducer.data.remote;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.util.Log;

import com.communikein.wastetrackingproducer.AppExecutors;
import com.communikein.wastetrackingproducer.data.contentprovider.BlockChainContract;
import com.communikein.wastetrackingproducer.data.model.Authentication;
import com.communikein.wastetrackingproducer.data.model.CreateThing;
import com.communikein.wastetrackingproducer.data.model.Waste;
import com.communikein.wastetrackingproducer.R;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

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

/**
 * This class provides the single source of truth for interfacing with the BlockChain Web APIs.
 * It abstracts the complexity of the APIs creating an higher level for the programmer.
 */
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

    private final Authentication mCredentials;

    @Inject
    public RemoteSource(AppExecutors executors, Application application) {
        mExecutors = executors;

        mDownloadedWaste = new MutableLiveData<>();
        mLoadingState = new MutableLiveData<>();
        mDownloadedBlocks = new MutableLiveData<>();

        JsonObject auth = getCredentials(application);
        mCredentials = new Authentication(auth.get("username").getAsString(),
                auth.get("password").getAsString());
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
                Response<Void> token = request.login(mCredentials).execute();
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
                Response<Void> token = request.login(mCredentials).execute();
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

    public void fetchWastes() {
        // Notify any observer that the loading is started
        mLoadingState.postValue(true);

        mExecutors.networkIO().execute(() -> {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Config.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            BackendRequests request = retrofit.create(BackendRequests.class);

            try {
                Response<Void> token = request.login(mCredentials).execute();
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
                                ArrayList<Waste> wastes = new ArrayList<>();
                                for (JsonObject obj : response.body()) {
                                    if (obj.getAsJsonObject("data").has(BlockChainContract.BlockEntry.COLUMN_WASTE_ID))
                                        wastes.add(new Gson().fromJson(obj.getAsJsonObject("data"), Waste.class));
                                }

                                mDownloadedWaste.postValue(wastes);
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

    private JsonObject getCredentials(Context context) {
        Writer writer = new StringWriter();
        char[] buffer = new char[1024];
        JsonObject result = null;

        try (InputStream is = context.getResources().openRawResource(R.raw.credentials)) {
            Reader reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
            int n;
            while ((n = reader.read(buffer)) != -1) {
                writer.write(buffer, 0, n);
            }

            String text = writer.toString();
            result = new Gson().fromJson(text, JsonObject.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

}

