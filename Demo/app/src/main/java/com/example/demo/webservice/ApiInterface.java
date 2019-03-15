package com.example.demo.webservice;

import com.example.demo.model.UserListResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.GET;

public interface ApiInterface {
    @GET("getproductsbycategory/7003?pageNum=1&productCount=10")
    Call<List<UserListResponse>> getUsersList();
}
