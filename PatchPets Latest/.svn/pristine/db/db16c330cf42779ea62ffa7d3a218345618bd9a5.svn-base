package com.patchpets.Activitys;

import android.app.ProgressDialog;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.patchpets.Adapters.DogsGridImageAdapter;
import com.patchpets.R;
import com.patchpets.databinding.ActivityFavouritesDogBinding;
import com.patchpets.interfaces.OnItemClickListener;
import com.patchpets.model.DogDetails;
import com.patchpets.model.User;
import com.patchpets.model.requestModel.ApiRequest;
import com.patchpets.model.responseModel.FavouriteDogListResponse;
import com.patchpets.sharedPreferences.SessionManager;
import com.patchpets.utils.Constants;
import com.patchpets.utils.HeaderBar;
import com.patchpets.utils.Helper;
import com.patchpets.webservices.APIRequest;
import com.patchpets.webservices.ResponseCallback;

import java.util.ArrayList;

public class FavouritesDogActivity extends AppCompatActivity implements View.OnClickListener, OnItemClickListener {

    private ActivityFavouritesDogBinding binding;
    private HeaderBar headerBar;
    private RecyclerView rvFavouritesDog;
    private TextView tvEmpty;
    private DogsGridImageAdapter adapter;
    private ArrayList<DogDetails> alDogs = new ArrayList<>();
    private boolean isFromDogPark;
    private APIRequest apiRequest;
    private ProgressDialog pDialog;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_favourites_dog);
        user = SessionManager.getInstance().getUser(this);

        isFromDogPark = getIntent().getBooleanExtra(Constants.FROM_DOG_PARK, false);

        bindViews();
        setListener();

        adapter = new DogsGridImageAdapter(FavouritesDogActivity.this, this, alDogs);
        rvFavouritesDog.setLayoutManager(new GridLayoutManager(this, 3));
        rvFavouritesDog.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        callGetFavouriteDogListAPI();
    }

    private void bindViews() {
        headerBar = binding.headerBar;
        rvFavouritesDog = binding.rvFavouritesDog;
        tvEmpty = binding.tvEmpty;

        if (isFromDogPark) {
            headerBar.tvTitle.setText("Garden");
        } else {
            headerBar.tvTitle.setText(getResources().getString(R.string.favourites_dog));
        }
        headerBar.ibSwitchBtn.setVisibility(View.GONE);
        headerBar.ibFilter.setVisibility(View.GONE);
        headerBar.ibRight.setVisibility(View.GONE);
        headerBar.ibLeft.setImageDrawable(getResources().getDrawable(R.drawable.back));
    }

    private void setListener() {
        headerBar.ibLeft.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        try {
            switch (v.getId()) {
                case R.id.ibLeft:
                    onBackPressed();
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onItemClicked(int position) {
        Intent iDogDetails = new Intent(this, DogDetailsActivity.class);
        iDogDetails.putExtra(Constants.DOG_DETAILS, alDogs.get(position));
        startActivity(iDogDetails);
    }

    private void callGetFavouriteDogListAPI() {
        if (Helper.isCheckInternet(this)) {
            pDialog = new ProgressDialog(this);
            pDialog.setCancelable(false);
            pDialog.setMessage(getResources().getString(R.string.please_wait));
            pDialog.show();
            apiRequest = new APIRequest();
            apiRequest.favouriteDogListAPI(request(), responseCallback);
        }
    }

    private ApiRequest request() {
        ApiRequest request = new ApiRequest();
        request.setUserId(user.getUserId());
        request.setAccessToken(user.getAccessToken());
        return request;
    }

    ResponseCallback responseCallback = new ResponseCallback() {
        @Override
        public void ResponseSuccessCallBack(Object object) {
            if (pDialog != null && pDialog.isShowing()) {
                pDialog.dismiss();
            }
            if (object != null) {
                FavouriteDogListResponse response = (FavouriteDogListResponse) object;
                if (response.getStatus() == 1) {
                    alDogs.clear();
                    for (int i = 0; i < response.getUserData().size(); i++) {
                        DogDetails dog = new DogDetails();
                        dog.setLocation(response.getUserData().get(i).getLocation());
                        dog.setIsUserActive(response.getUserData().get(i).getIsUserActive());
                        for (int j = 0; j < response.getUserData().get(i).getDogDetails().size(); j++) {
                            dog.setDogId(response.getUserData().get(i).getDogDetails().get(j).getDogId());
                            dog.setDogName(response.getUserData().get(i).getDogDetails().get(j).getDogName());
                            dog.setDogBreed(response.getUserData().get(i).getDogDetails().get(j).getDogBreed());
                            dog.setDogAge(response.getUserData().get(i).getDogDetails().get(j).getDogAge());
                            dog.setGender(response.getUserData().get(i).getDogDetails().get(j).getGender());
                            dog.setIsDesexed(response.getUserData().get(i).getDogDetails().get(j).getIsDesexed());
                            dog.setIsFavourite(response.getUserData().get(i).getDogDetails().get(j).getIsFavourite());
                            dog.setDogSize(response.getUserData().get(i).getDogDetails().get(j).getDogSize());
                            dog.setLastActiveTime(response.getUserData().get(i).getDogDetails().get(j).getLastActiveTime());
                            dog.setVaccinations(response.getUserData().get(i).getDogDetails().get(j).getVaccinations());
                            dog.setDogDesc(response.getUserData().get(i).getDogDetails().get(j).getDogDesc());
                            dog.setDogInstaLink(response.getUserData().get(i).getDogDetails().get(j).getDogInstaLink());
                            dog.setDogProfilePic(response.getUserData().get(i).getDogDetails().get(j).getDogProfilePic());

                            ArrayList<String> dogPics = new ArrayList<>();
                            for (int k = 0; k < response.getUserData().get(i).getDogDetails().get(j).getDogOtherPics().size(); k++) {
                                dogPics.add(response.getUserData().get(i).getDogDetails().get(j).getDogOtherPics().get(k));
                            }
                            dog.setDogPics(dogPics);
                            alDogs.add(dog);
                        }
                    }
                    adapter.notifyDataSetChanged();
                    if (alDogs.size() > 0) {
                        rvFavouritesDog.setVisibility(View.VISIBLE);
                        tvEmpty.setVisibility(View.GONE);
                    } else {
                        tvEmpty.setVisibility(View.VISIBLE);
                        rvFavouritesDog.setVisibility(View.GONE);
                    }
                }
            }
        }

        @Override
        public void ResponseFailCallBack(Object object) {
            if (pDialog != null && pDialog.isShowing()) {
                pDialog.dismiss();
            }
        }
    };
}
