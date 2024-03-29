package com.communikein.wastetrackingproducer.data.remote;

import com.communikein.wastetrackingproducer.data.model.Authentication;
import com.communikein.wastetrackingproducer.data.model.CreateThing;
import com.communikein.wastetrackingproducer.data.model.UpdateThing;
import com.google.gson.JsonObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * This class interfaces with the BlockChain Web APIs to provide methods to interact with the
 * BlockChain: read, create, update, delete. It also provides authentication to the API backend,
 * generating a Token to be used with any other interaction.
 */
public interface BackendRequests {

    @POST("users/login/")
    @Headers("Content-Type: application/json")
    Call<Void> login(@Body Authentication authentication);


    @POST("v1/schema/")
    Call<String> getAllSchema(@Header("Auth") String token);

    @POST("v1/schema/{id}")
    Call<String> getSchema(@Header("Auth") String token, @Path("id") String id);

    @POST("v1/thing")
    @Headers("Content-Type: application/json")
    Call<Void> createThing(@Header("Auth") String token, @Body CreateThing thing);

    @GET("v1/thing")
    Call<ArrayList<JsonObject>> getAllThings(@Header("Auth") String token);

    @GET("v1/thing/{id}")
    Call<String> getThing(@Header("Auth") String token, @Path("id") String id);

    @POST("v1/thing/{id}/delete")
    @Headers("Content-Type: application/json")
    Call<Void> deleteThing(@Header("Auth") String token);

    @POST("v1/thing/{id}")
    @Headers("Content-Type: application/json")
    Call<String> updateThing(@Header("Auth") String token, @Body UpdateThing thing);

}
