package com.example.project.Model;

import com.google.gson.JsonObject;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;

public interface Link {
    @GET("/books/v1/volumes/zyTCAlFPjgYC")
    Call<JsonObject> getBook( @QueryMap Map<String,String> map);

    @GET("/books/v1/volumes")
    Call<JsonObject> getBooks( @QueryMap Map<String,String> map);
}
