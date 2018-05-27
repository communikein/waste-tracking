package com.example.xyzreader.data.remote;

import com.example.xyzreader.data.model.Article;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface BackendRequests {

    @GET("xyz-reader-json")
    Call<ArrayList<Article>> getJSON();

}
