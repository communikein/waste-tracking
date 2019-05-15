package com.example.xyzreader.data.remote;

import com.example.xyzreader.data.model.Waste;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;

public interface BackendRequests {

    @GET("data.json")
    Call<ArrayList<Waste>> getJSON();

}
