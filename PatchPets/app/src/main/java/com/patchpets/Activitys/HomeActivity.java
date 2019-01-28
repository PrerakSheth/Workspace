package com.patchpets.Activitys;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.patchpets.Adapters.DogsGridPagerAdapter;
import com.patchpets.Adapters.DrawerLayoutAdapter;
import com.patchpets.R;
import com.patchpets.interfaces.OnHomeMenuClick;
import com.patchpets.interfaces.OnItemClickListener;
import com.patchpets.location.MyLocation;
import com.patchpets.location.OnLocationFound;
import com.patchpets.model.DogDetails;
import com.patchpets.model.User;
import com.patchpets.model.requestModel.HomeDataRequest;
import com.patchpets.model.responseModel.HomeDataResponse;
import com.patchpets.sharedPreferences.SessionManager;
import com.patchpets.utils.AlertDialogUtility;
import com.patchpets.utils.Constants;
import com.patchpets.utils.HeaderBar;
import com.patchpets.utils.Helper;
import com.patchpets.utils.LogM;
import com.patchpets.webservices.APIRequest;
import com.patchpets.webservices.ResponseCallback;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener, OnHomeMenuClick, OnItemClickListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private ArrayList<String> breedList, ageList, genderList, statusList, willingToBreed, distanceList, sizeList;
    private HeaderBar headerBar;
    private SwipeRefreshLayout pullToRefresh;
    private ImageButton ibExplore;
    private TextView tvEmpty;
    private RecyclerView rvSlider, rvData;
    private DrawerLayout drawerLayout;
    private DrawerLayoutAdapter drawerLayoutAdapter;
    private DogsGridPagerAdapter adapter;
    private String[] drawerTitle = {"",
            Constants.HOME,
            Constants.FIND_A_PARK,
            Constants.MESSAGES,
            Constants.DIRECTORY,
            Constants.FAVOURITES,
            Constants.SUBMIT_A_PARK,
            Constants.ADS,
            Constants.SETTINGS
    };

    private User user;
    private APIRequest apiRequest;
    private ProgressDialog pDialog;
    private ArrayList<DogDetails> alDogs = new ArrayList<>();
    private View snackBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        snackBar = findViewById(android.R.id.content);
        bindViews();
        setListener();
        buildGoogleApiClient();
        setData();

        adapter = new DogsGridPagerAdapter(HomeActivity.this, this, alDogs);
        rvData.setLayoutManager(new GridLayoutManager(this, 3));
        rvData.setAdapter(adapter);
    }

    private void bindViews() {
        pullToRefresh = findViewById(R.id.pullToRefresh);
        drawerLayout = findViewById(R.id.drawerLayout);
        headerBar = findViewById(R.id.headerBar);
        ibExplore = findViewById(R.id.ibExplore);
        rvSlider = findViewById(R.id.rvSlider);
        rvData = findViewById(R.id.rvData);
        tvEmpty = findViewById(R.id.tvEmpty);

        headerBar.tvTitle.setText(getResources().getString(R.string.home));
        headerBar.ibSwitchBtn.setVisibility(View.VISIBLE);
        headerBar.ibFilter.setVisibility(View.VISIBLE);
        headerBar.ibLeft.setImageDrawable(getResources().getDrawable(R.drawable.menu));
        headerBar.ibRight.setImageDrawable(getResources().getDrawable(R.drawable.message));
    }

    private void setListener() {
        headerBar.ibLeft.setOnClickListener(this);
        headerBar.ibRight.setOnClickListener(this);
        headerBar.ibSwitchBtn.setOnClickListener(this);
        headerBar.ibFilter.setOnClickListener(this);
        ibExplore.setOnClickListener(this);
        pullToRefresh.setOnRefreshListener(this);
    }

    private void setData() {
        breedList = new ArrayList<>();
        ageList = new ArrayList<>();
        genderList = new ArrayList<>();
        statusList = new ArrayList<>();
        willingToBreed = new ArrayList<>();
        distanceList = new ArrayList<>();
        sizeList = new ArrayList<>();
    }

    private void setDrawer() {
        try {
            DisplayMetrics metrics = getResources().getDisplayMetrics();
            int width = metrics.widthPixels;

            DrawerLayout.LayoutParams layoutParams = (DrawerLayout.LayoutParams) rvSlider.getLayoutParams();
            layoutParams.width = (int) (width * 0.70);
            rvSlider.setLayoutParams(layoutParams);
            rvSlider.setAdapter(drawerLayoutAdapter);
            rvSlider.setLayoutManager(new LinearLayoutManager(HomeActivity.this));

            if (drawerLayout.isDrawerOpen(rvSlider)) {
                drawerLayout.closeDrawer(rvSlider);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void openDrawer() {
        try {
            rvSlider.invalidate();
            drawerLayout.openDrawer(rvSlider);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void closeDrawer() {
        try {
            if (drawerLayout.isDrawerOpen(rvSlider)) {
                drawerLayout.closeDrawer(rvSlider);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        user = SessionManager.getInstance().getUser(HomeActivity.this);
        drawerLayoutAdapter = new DrawerLayoutAdapter(HomeActivity.this, user, drawerTitle, this);
        setDrawer();
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onClick(View v) {
        try {
            switch (v.getId()) {
                case R.id.ibLeft:
                    openDrawer();
                    break;

                case R.id.ibRight:
                    startActivitys(MessagesActivity.class);
                    break;

                case R.id.ibSwitchBtn:
                    headerBar.ibFilter.performClick();
                    break;

                case R.id.ibFilter:
                    Intent i = new Intent(getApplicationContext(), FilterActivity.class);
                    i.putStringArrayListExtra(Constants.BREED_LIST, breedList);
                    i.putStringArrayListExtra(Constants.AGE_LIST, ageList);
                    i.putStringArrayListExtra(Constants.GENDER_LIST, genderList);
                    i.putStringArrayListExtra(Constants.STATUS_LIST, statusList);
                    i.putStringArrayListExtra(Constants.WILLING_TO_BREED_LIST, willingToBreed);
                    i.putStringArrayListExtra(Constants.DISTANCE_LIST, distanceList);
                    i.putStringArrayListExtra(Constants.SIZE_LIST, sizeList);

                    startActivityForResult(i, 101);
                    break;

                case R.id.ibExplore:
                    startActivitys(ExploreActivity.class);
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void homeMenuClick(int position, View view) {
        closeDrawer();
        switch (position) {
            case Constants.POSITION_PROFILE:
                switch (view.getId()) {
                    case R.id.ibEdit:
                        if (user.getUserType() == Constants.BUSINESS_USER) {
                            startActivitys(SetupBusinessProfileActivity.class);
                        } else if (user.getUserType() == Constants.DOG_OWNER) {
                            startActivitys(SetupProfileActivity.class);
                        }
                        break;

                    default:
                        startActivitys(ProfileActivity.class);
                        break;
                }
                break;

            case Constants.POSITION_FIND_A_PARK:
                startActivitys(ExploreActivity.class);
                break;

            case Constants.POSITION_MESSAGES:
                startActivitys(MessagesActivity.class);
                break;

            case Constants.POSITION_DIRECTORY:
                Intent iExplore = new Intent(this, ExploreActivity.class);
                iExplore.putExtra(Constants.EXPLORE_DIRECTORY_TAB_NUMBER, Constants.EXPLORE_DIRECTORY);
                startActivity(iExplore);
                break;

            case Constants.POSITION_FAVOURITES:
                startActivitys(FavouritesDogActivity.class);
                break;

            case Constants.POSITION_SUBMIT_A_PARK:
                startActivitys(SubmitParkActivity.class);
                break;

            case Constants.POSITION_ADS:
                startActivitys(AdsActivity.class);
                break;

            case Constants.POSITION_SETTINGS:
                startActivitys(SettingsActivity.class);
                break;
        }
    }

    @Override
    public void onItemClicked(int position) {
        Intent iDogDetails = new Intent(this, DogDetailsActivity.class);
        iDogDetails.putExtra(Constants.DOG_DETAILS, alDogs.get(position));
        startActivity(iDogDetails);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 101) {
            breedList = data.getStringArrayListExtra(Constants.BREED_LIST);
            ageList = data.getStringArrayListExtra(Constants.AGE_LIST);
            genderList = data.getStringArrayListExtra(Constants.GENDER_LIST);
            statusList = data.getStringArrayListExtra(Constants.STATUS_LIST);
            willingToBreed = data.getStringArrayListExtra(Constants.WILLING_TO_BREED_LIST);
            distanceList = data.getStringArrayListExtra(Constants.DISTANCE_LIST);
            sizeList = data.getStringArrayListExtra(Constants.SIZE_LIST);
            boolean sizeCheck;
            if (breedList.size() > 0 || ageList.size() > 0 || genderList.size() > 0 || statusList.size() > 0 || willingToBreed.size() > 0 || distanceList.size() > 0 || sizeList.size() > 0) {
                sizeCheck = true;
            } else {
                sizeCheck = false;
            }
            setBtnStatus(sizeCheck);
        }

        if (requestCode == Constants.GPS_REQUEST_CODE) {
            LocationManager mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            boolean enabled = mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            if (enabled) {
                findLocation();
            } else {
                checkGpsEnable();
            }
        }
    }

    public void setBtnStatus(boolean size) {
        if (size) {
            headerBar.ibSwitchBtn.setImageResource(R.drawable.on_btn);
            headerBar.ibFilter.setImageResource(R.drawable.funnel_green);
        } else {
            headerBar.ibSwitchBtn.setImageResource(R.drawable.off_btn);
            headerBar.ibFilter.setImageResource(R.drawable.funnel_grey);
        }
    }

    public void startActivitys(Class activityClass) {
        Intent intent = new Intent(this, activityClass);
        startActivity(intent);
    }

    private void callHomeDogListAPI() {
        if (Helper.isCheckInternet(this)) {
            pDialog = new ProgressDialog(this);
            pDialog.setCancelable(false);
            pDialog.setMessage(getResources().getString(R.string.please_wait));
            pDialog.show();
            apiRequest = new APIRequest();
            apiRequest.homeDogListAPI(request(1), responseCallback);
        }
    }

    private HomeDataRequest request(int pageIndex) {
        try {
            HomeDataRequest request = new HomeDataRequest();
            request.setUserId(user.getUserId());
            request.setAccessToken(user.getAccessToken());
            request.setIsFilterOn(2);
            request.setLatitude(mCurrentLocation.getLatitude());
            request.setLatitude(mCurrentLocation.getLongitude());
            request.setBreed(new int[]{1, 2, 3});
            request.setAge(new int[]{1, 2, 3});
            request.setGender(Constants.MALE);
            request.setStatus(Constants.DESEXED);
            request.setDistance(new int[]{1, 2, 3});
            request.setDogSize(new String[]{"Toy", "Small", "Large"});
            request.setPageIndex(pageIndex);
            request.setPageCount(Constants.PAGE_COUNT);
            return request;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    ResponseCallback responseCallback = new ResponseCallback() {
        @Override
        public void ResponseSuccessCallBack(Object object) {
            if (pDialog != null && pDialog.isShowing()) {
                pDialog.dismiss();
            }
            if (object != null) {
                HomeDataResponse response = (HomeDataResponse) object;
                if (response.getStatus() == 1) {
                    alDogs.clear();
                    for (int i = 0; i < response.getUserData().size(); i++) {
                        DogDetails dog = new DogDetails();
                        dog.setUserId(response.getUserData().get(i).getUserId());
                        dog.setLocation(response.getUserData().get(i).getLocation());
                        dog.setIsUserActive(response.getUserData().get(i).getIsUserActive());
                        dog.setDistance(String.valueOf(response.getUserData().get(i).getDistance()));
                        for (int j = 0; j < response.getUserData().get(i).getDogDetails().size(); j++) {
                            dog.setDogId(response.getUserData().get(i).getDogDetails().get(j).getDogId());
                            dog.setDogName(response.getUserData().get(i).getDogDetails().get(j).getDogName());
                            dog.setDogBreed(response.getUserData().get(i).getDogDetails().get(j).getDogBreed());
                            dog.setDogAge(response.getUserData().get(i).getDogDetails().get(j).getDogAge());
                            dog.setGender(response.getUserData().get(i).getDogDetails().get(j).getGender());
                            dog.setIsDesexed(response.getUserData().get(i).getDogDetails().get(j).getIsDesexed());
                            dog.setIsWillingToBreed(response.getUserData().get(i).getDogDetails().get(j).getIsWillingToBreed());
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
                        rvData.setVisibility(View.VISIBLE);
                        tvEmpty.setVisibility(View.GONE);
                    } else {
                        tvEmpty.setVisibility(View.VISIBLE);
                        rvData.setVisibility(View.GONE);
                    }
                } else if (response.getMessage().contains(getResources().getString(R.string.access_token_has_been_expired))) {
                    AlertDialogUtility.showAlert(HomeActivity.this, response.getMessage(),
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                    Intent intent = new Intent(HomeActivity.this, SignInActivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intent);
                                    finish();
                                }
                            });
                } else {
                    AlertDialogUtility.showSnakeBar(response.getMessage(), snackBar, HomeActivity.this);
                }
                pullToRefresh.setRefreshing(false);
            }
        }

        @Override
        public void ResponseFailCallBack(Object object) {
            if (pDialog != null && pDialog.isShowing()) {
                pDialog.dismiss();
            }
        }
    };

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        try {
            if (requestCode == Constants.PERMISSION_REQUEST_LOCATION) {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(HomeActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, Constants.PERMISSION_REQUEST_LOCATION);
                        return;
                    }
                    mCurrentLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
                    if (mCurrentLocation == null) {
                        LocationManager mlocManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                        boolean enabled = mlocManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
                        if (enabled) {
                            findLocation();
                        } else {
                            checkGpsEnable();
                        }
                    }
                } else if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.msg_permission_denied), Toast.LENGTH_LONG).show();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Location mCurrentLocation;
    private GoogleApiClient mGoogleApiClient;

    protected synchronized void buildGoogleApiClient() {
        try {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
            mGoogleApiClient.connect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        try {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(HomeActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, Constants.PERMISSION_REQUEST_LOCATION);
            } else {
                mCurrentLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
                if (mCurrentLocation == null) {
                    LocationManager mLocManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                    boolean enabled = mLocManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
                    if (enabled) {
                        findLocation();
                    } else {
                        checkGpsEnable();
                    }
                } else {
                    findLocation();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
    }

    private void checkGpsEnable() {
        try {
            LocationManager mlocManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            boolean enabled = mlocManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

            if (!enabled) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
                alertDialogBuilder
                        .setTitle(getResources().getString(R.string.app_name))
                        .setMessage(getResources().getString(R.string.gps_alert))
                        .setCancelable(false)
                        .setPositiveButton(getResources().getString(R.string.gps_settings), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Intent callGPSSettingIntent = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                startActivityForResult(callGPSSettingIntent, Constants.GPS_REQUEST_CODE);
                            }
                        });
                AlertDialog alert = alertDialogBuilder.create();
                alert.show();
            } else {
                findLocation();
            }
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }
    }

    private void findLocation() {
        try {
            new MyLocation(HomeActivity.this, new OnLocationFound() {
                @Override
                public void myLocation(Location location) {
                    mCurrentLocation = location;
                    LogM.e("Latitude : " + mCurrentLocation.getLatitude() + ", Longitude : " + mCurrentLocation.getLongitude());
                    callHomeDogListAPI();
                }
            }).createLocationRequest();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onRefresh() {
        pullToRefresh.setRefreshing(true);
        callHomeDogListAPI();
    }
}
