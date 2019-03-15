package com.example.demo.webservice;

import com.example.demo.model.UserListResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIClient {
    private static Retrofit retrofit = null;

    public static Retrofit getClient() {

        retrofit = new Retrofit.Builder()
                .baseUrl("https://onecartlive.azurewebsites.net/v1/api/products/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit;
    }


}
