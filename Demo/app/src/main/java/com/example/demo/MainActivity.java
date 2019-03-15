package com.example.demo;

import android.app.ProgressDialog;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.example.demo.Utils.Helper;
import com.example.demo.adapter.UsersAdapter;
import com.example.demo.databinding.ActivityMainBinding;
import com.example.demo.model.UserListResponse;
import com.example.demo.webservice.APIClient;
import com.example.demo.webservice.ApiInterface;
import com.facebook.drawee.backends.pipeline.Fresco;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    RecyclerView rvData;
    List<UserListResponse> userListResponseData;
    ActivityMainBinding binding;
    ProgressDialog pDialog;
    private APIClient apiClient;
    private ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        apiInterface = APIClient.getClient().create(ApiInterface.class);
        Fresco.initialize(this);

        bindViews();
        callUserListData();
    }

    private void bindViews() {
        rvData = binding.rvData;
    }

    private void callUserListData() {
        if (Helper.isCheckInternet(MainActivity.this)) {
            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setCancelable(false);
            pDialog.setMessage(getResources().getString(R.string.please_wait));
            pDialog.show();
            apiClient = new APIClient();
            try {
                Call<List<UserListResponse>> requestCall = apiInterface.getUsersList();
                requestCall.enqueue(new Callback<List<UserListResponse>>() {
                    @Override
                    public void onResponse(Call<List<UserListResponse>> call, Response<List<UserListResponse>> response) {
                        pDialog.dismiss(); //dismiss progress dialog
                        userListResponseData = response.body();
                        setDataInRecyclerView();
                    }

                    @Override
                    public void onFailure(Call<List<UserListResponse>> call, Throwable t) {
                        Toast.makeText(MainActivity.this, t.toString(), Toast.LENGTH_LONG).show();
                        pDialog.dismiss(); //dismiss progress dialog
                    }
                });

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    private void setDataInRecyclerView() {
        // set a LinearLayoutManager with default vertical orientation
        GridLayoutManager gridLayoutManager = new GridLayoutManager(MainActivity.this, 3);
        rvData.setLayoutManager(gridLayoutManager);
        // call the constructor of UsersAdapter to send the reference and data to Adapter
        UsersAdapter usersAdapter = new UsersAdapter(MainActivity.this, userListResponseData);
        rvData.setAdapter(usersAdapter); // set the Adapter to RecyclerView
    }
}
